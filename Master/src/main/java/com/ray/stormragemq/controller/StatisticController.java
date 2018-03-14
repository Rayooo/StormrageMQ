package com.ray.stormragemq.controller;

import com.ray.stormragemq.constant.ConstantVariable;
import com.ray.stormragemq.dao.MessageDao;
import com.ray.stormragemq.dao.MessageStatisticsDao;
import com.ray.stormragemq.entity.MessageStatisticsEntity;
import com.ray.stormragemq.schedule.MessageStatisticsSchedule;
import com.ray.stormragemq.util.BaseException;
import com.ray.stormragemq.util.BaseResponse;
import com.ray.stormragemq.util.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;


@RestController
@RequestMapping("/statistic")
public class StatisticController {

    @Autowired
    private MessageStatisticsDao messageStatisticsDao;

    @Autowired
    private MessageDao messageDao;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @RequestMapping("/messageCount")
    public BaseResponse<List> messageCount(@RequestBody Map<String, String> p) throws BaseException {
        calculateTodayMessage();
        String statisticsType = p.get("statisticsType");
        if(statisticsType == null){
            throw new BaseException("类型错误");
        }

        if(MessageStatisticsSchedule.EVERY_DAY_MESSAGE_COUNT.equals(statisticsType)
                || MessageStatisticsSchedule.EVERY_DAY_QUEUE_MESSAGE_SEND.equals(statisticsType)
                || MessageStatisticsSchedule.EVERY_DAY_QUEUE_MESSAGE_UNSEND.equals(statisticsType)){
            Date start = tenDaysBefore();
            Date end = new Date();
            Map<String, Object> param = new HashMap<>();
            param.put("startTime", start);
            param.put("endTime", end);
            param.put("name", statisticsType);
            List list = messageStatisticsDao.getMessageByTypeAndTime(param);
            return new BaseResponse<>(list);
        }
        else{
            throw new BaseException("类型错误");
        }

    }

    private Date tenDaysBefore() {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -10);

        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        return cal.getTime();
    }

    private void calculateTodayMessage(){

        final Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date todayStart = cal.getTime();
        Date todayEnd = new Date();

        //计算今天的消息数目
        Map<String, Object> param = new HashMap<>();
        param.put("startTime", todayStart);
        param.put("endTime", todayEnd);
        param.put("name", MessageStatisticsSchedule.EVERY_DAY_MESSAGE_COUNT);
        messageStatisticsDao.deleteStatistics(param);
        int sendMessageCount = messageDao.sendMessageCount(param);
        MessageStatisticsEntity messageStatistics = new MessageStatisticsEntity();
        messageStatistics.setName(MessageStatisticsSchedule.EVERY_DAY_MESSAGE_COUNT);
        messageStatistics.setCount(sendMessageCount);
        messageStatistics.setStatisticsTime(todayStart);
        messageStatisticsDao.insertStatistics(messageStatistics);


        //计算队列消息
        param.put("name", null);
        int sendQueueMessageCount = messageStatisticsDao.countSendQueueMessage(param);
        int unsendQueueMessageCount = messageStatisticsDao.countUnSendQueueMessage(param);
        List list = redisTemplate.opsForHash().values(ConstantVariable.MESSAGE_QUEUE_KEY);
        unsendQueueMessageCount += list.size();
        LogUtil.logInfo("今天发送的队列消息" + sendQueueMessageCount + "   今天未发送的队列消息" + unsendQueueMessageCount);

        param.put("name", MessageStatisticsSchedule.EVERY_DAY_QUEUE_MESSAGE_SEND);
        messageStatisticsDao.deleteStatistics(param);
        param.put("name", MessageStatisticsSchedule.EVERY_DAY_QUEUE_MESSAGE_UNSEND);
        messageStatisticsDao.deleteStatistics(param);

        MessageStatisticsEntity m1 = new MessageStatisticsEntity();
        m1.setName(MessageStatisticsSchedule.EVERY_DAY_QUEUE_MESSAGE_SEND);
        m1.setCount(sendQueueMessageCount);
        m1.setStatisticsTime(todayStart);
        messageStatisticsDao.insertStatistics(m1);

        MessageStatisticsEntity m2 = new MessageStatisticsEntity();
        m2.setName(MessageStatisticsSchedule.EVERY_DAY_QUEUE_MESSAGE_UNSEND);
        m2.setCount(unsendQueueMessageCount);
        m2.setStatisticsTime(todayStart);
        messageStatisticsDao.insertStatistics(m2);


    }


}
