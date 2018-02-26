package com.ray.stormragemq.config;

import com.ray.stormragemq.constant.ConstantVariable;
import com.ray.stormragemq.constant.ExchangerEnum;
import com.ray.stormragemq.dao.ExchangerDao;
import com.ray.stormragemq.dao.QueueDao;
import com.ray.stormragemq.dao.QueueMessageDao;
import com.ray.stormragemq.entity.ExchangerEntity;
import com.ray.stormragemq.entity.QueueEntity;
import com.ray.stormragemq.entity.QueueMessageEntity;
import com.ray.stormragemq.thread.ModCount;
import com.ray.stormragemq.thread.QueueThreadService;
import com.ray.stormragemq.util.LogUtil;
import com.ray.stormragemq.util.MatchQueueUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Component
public class Refresh {

    @Autowired
    private Map<String, ExchangerEntity> exchangerMap;

    @Autowired
    private ExchangerDao exchangerDao;

    @Autowired
    private QueueDao queueDao;

    @Autowired
    private Map<String, QueueEntity> queueMap;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private QueueThreadService queueThreadService;

    @Autowired
    private QueueMessageDao queueMessageDao;

    private List<QueueEntity> queueEntityList;

    @EventListener(ContextRefreshedEvent.class)
    public void contextRefreshEvent(){
        ModCount.addModCount();
        initExchanger();
        initQueue();
        initExchangerQueueList();
        initQueueMessage();
        queueThreadService.start();
    }

    private void initExchanger(){
        exchangerMap.clear();
        List<ExchangerEntity> list = exchangerDao.getAllExchanger();
        list.forEach(e -> exchangerMap.put(e.getName(), e));
    }

    private void initQueue(){
        queueMap.clear();
        queueEntityList = queueDao.getAllQueue();
        queueEntityList.forEach(q -> queueMap.put(q.getName(), q));
    }


    //将Exchanger中的queueList初始化
    private void initExchangerQueueList(){

        exchangerMap.forEach((s, exchangerEntity) -> {

            if(exchangerEntity.getType() == ExchangerEnum.DIRECT.getType()){
                for (QueueEntity queue : queueEntityList) {
                    if(StringUtils.equals(queue.getName(), exchangerEntity.getContent()) && queue.getCreateUserId().equals(exchangerEntity.getCreateUserId())){
                        exchangerEntity.setQueueList(new ArrayList<>(Collections.singletonList(queue.getName())));
                        break;
                    }
                }
            }
            else if(exchangerEntity.getType() == ExchangerEnum.FANOUT.getType()){
                String[] a = exchangerEntity.getContent().split(ConstantVariable.SEPARATOR);
                ArrayList<String> needQueue = new ArrayList<>(Arrays.asList(a));
                ArrayList<String> add = new ArrayList<>();
                for (QueueEntity queue : queueEntityList) {
                    for (String ss : needQueue) {
                        if(queue.getCreateUserId().equals(exchangerEntity.getCreateUserId()) && StringUtils.equals(queue.getName(), ss)){
                            add.add(ss);
                        }
                    }
                }
                exchangerEntity.setQueueList(add);
            }
            else if(exchangerEntity.getType() == ExchangerEnum.TOPIC.getType()){
                ArrayList<String> add = new ArrayList<>();
                for (QueueEntity queue : queueEntityList) {
                    if(queue.getCreateUserId().equals(exchangerEntity.getCreateUserId()) && MatchQueueUtil.match(queue.getName(), exchangerEntity.getContent())){
                        add.add(queue.getName());
                    }
                }
                exchangerEntity.setQueueList(add);
            }


        });

        LogUtil.logInfo("结束匹配队列");

    }

    //从redis中和数据库中初始化queueMessage
    private void initQueueMessage(){

        //redis
        List list = redisTemplate.opsForHash().values(ConstantVariable.MESSAGE_QUEUE_KEY);
        if(!CollectionUtils.isEmpty(list)){
            for (Object o : list) {
                QueueMessageEntity qm = QueueMessageEntity.parseJson(o);
                if(qm != null){
                    QueueEntity queue = queueMap.get(qm.getQueueName());
                    queue.getBlockingQueue().offer(qm);
                }
            }
        }

        List<QueueMessageEntity> listInDB = queueMessageDao.getAllQueueMessageNotReceived();
        if(!CollectionUtils.isEmpty(listInDB)){
            for (QueueMessageEntity qm : listInDB) {
                if(qm != null){
                    QueueEntity queue = queueMap.get(qm.getQueueName());
                    queue.getBlockingQueue().offer(qm);
                }
            }
        }

    }

}
