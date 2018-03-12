package com.ray.stormragemq.schedule;

import com.ray.stormragemq.constant.ConstantVariable;
import com.ray.stormragemq.dao.MessageDao;
import com.ray.stormragemq.dao.MessageStatisticsDao;
import com.ray.stormragemq.entity.MessageStatisticsEntity;
import com.ray.stormragemq.entity.QueueEntity;
import com.ray.stormragemq.util.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

@EnableScheduling
@Component
public class MessageStatisticsSchedule {

    @Autowired
    private MessageDao messageDao;

    @Autowired
    private MessageStatisticsDao messageStatisticsDao;

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 每天接收消息标识符 message中
     * */
    public static final String EVERY_DAY_MESSAGE_COUNT = "EVERY_DAY_MESSAGE_COUNT";

    /**
     * 每天发送的消息，queueMessage中
     * */
    public static final String EVERY_DAY_QUEUE_MESSAGE_SEND = "EVERY_DAY_QUEUE_MESSAGE_SEND";

    /**
     * 每天还没有发送的消息 queueMessage中
     * */
    public static final String EVERY_DAY_QUEUE_MESSAGE_UNSEND = "EVERY_DAY_QUEUE_MESSAGE_UNSEND";


    /**
     * 每天00:00:01统计消息信息
     * */
//    @Scheduled(cron = "1 1 1 * * ?")
    @Scheduled(cron = "*/5 * * * * *")
    public void statistics(){
        LogUtil.logInfo("统计消息的任务开始");

        Map<String, Date> param = new HashMap<>();
        param.put("startTime", yesterdayStart());
        param.put("endTime", yesterdayEnd());

        int sendMessageCount = messageDao.sendMessageCount(param);
        LogUtil.logInfo("今天接收到的消息" + sendMessageCount);
        saveMessageCount(sendMessageCount);


        int sendQueueMessageCount = messageStatisticsDao.countSendQueueMessage(param);
        int unsendQueueMessageCount = messageStatisticsDao.countUnSendQueueMessage(param);
        List list = redisTemplate.opsForHash().values(ConstantVariable.MESSAGE_QUEUE_KEY);
        unsendQueueMessageCount += list.size();
        LogUtil.logInfo("今天发送的队列消息" + sendQueueMessageCount + "   今天未发送的队列消息" + unsendQueueMessageCount);
        saveQueueMessageCount(sendQueueMessageCount, unsendQueueMessageCount);

        LogUtil.logInfo("统计消息的任务结束");


    }

    private void saveQueueMessageCount(int sendCount, int unsendCount){
        Map<String, Object> param = new HashMap<>();
        param.put("name", EVERY_DAY_QUEUE_MESSAGE_SEND);
        param.put("startTime", yesterdayStart());
        param.put("endTime", yesterdayEnd());
        messageStatisticsDao.deleteStatistics(param);
        param.put("name", EVERY_DAY_QUEUE_MESSAGE_UNSEND);
        messageStatisticsDao.deleteStatistics(param);

        MessageStatisticsEntity messageStatistics = new MessageStatisticsEntity();
        messageStatistics.setName(EVERY_DAY_QUEUE_MESSAGE_SEND);
        messageStatistics.setCount(sendCount);
        messageStatistics.setStatisticsTime(yesterdayStart());
        messageStatisticsDao.insertStatistics(messageStatistics);

        MessageStatisticsEntity ms = new MessageStatisticsEntity();
        ms.setName(EVERY_DAY_QUEUE_MESSAGE_UNSEND);
        ms.setCount(unsendCount);
        ms.setStatisticsTime(yesterdayStart());
        messageStatisticsDao.insertStatistics(ms);


    }

    private void saveMessageCount(int sendMessageCount){

        Map<String, Object> param = new HashMap<>();
        param.put("name", EVERY_DAY_MESSAGE_COUNT);
        param.put("startTime", yesterdayStart());
        param.put("endTime", yesterdayEnd());
        messageStatisticsDao.deleteStatistics(param);


        MessageStatisticsEntity messageStatistics = new MessageStatisticsEntity();
        messageStatistics.setName(EVERY_DAY_MESSAGE_COUNT);
        messageStatistics.setCount(sendMessageCount);
        messageStatistics.setStatisticsTime(yesterdayStart());
        messageStatisticsDao.insertStatistics(messageStatistics);

    }

    private Date yesterdayStart() {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);

        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        return cal.getTime();
    }

    private Date yesterdayEnd(){
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);

        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);

        return cal.getTime();
    }

}
