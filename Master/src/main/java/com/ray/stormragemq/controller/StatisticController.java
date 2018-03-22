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
        cal.add(Calendar.DATE, -9);

        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        return cal.getTime();
    }

}
