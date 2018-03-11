package com.ray.stormragemq.schedule;

import com.ray.stormragemq.dao.MessageDao;
import com.ray.stormragemq.dao.MessageStatisticsDao;
import com.ray.stormragemq.entity.MessageStatisticsEntity;
import com.ray.stormragemq.util.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@EnableScheduling
@Component
public class MessageStatisticsSchedule {

    @Autowired
    private MessageDao messageDao;

    @Autowired
    private MessageStatisticsDao messageStatisticsDao;

    public static final String EVERY_DAY_MESSAGE_COUNT = "EVERY_DAY_MESSAGE_COUNT";

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

        LogUtil.logInfo("统计消息的任务结束");
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
