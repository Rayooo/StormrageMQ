package com.ray.stormragemq.config;

import com.ray.stormragemq.constant.ExchangerEnum;
import com.ray.stormragemq.dao.ExchangerDao;
import com.ray.stormragemq.dao.QueueDao;
import com.ray.stormragemq.domain.ExchangerEntity;
import com.ray.stormragemq.domain.QueueEntity;
import com.ray.stormragemq.util.MatchQueueUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
import java.util.*;

@Component
public class Refresh {

    private Map<String, ExchangerEntity> exchangerMap;

    private final ExchangerDao exchangerDao;

    private final QueueDao queueDao;

    private Map<String, QueueEntity> queueMap;

    private List<QueueEntity> queueEntityList;

    @Autowired
    public Refresh(Map<String, ExchangerEntity> exchangerMap, ExchangerDao exchangerDao, QueueDao queueDao, Map<String, QueueEntity> queueMap) {
        this.exchangerMap = exchangerMap;
        this.exchangerDao = exchangerDao;
        this.queueDao = queueDao;
        this.queueMap = queueMap;
    }

    @EventListener(ContextRefreshedEvent.class)
    public void contextRefreshEvent(){
        initExchanger();
        initQueue();
        initExchangerQueueList();
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
                String[] a = exchangerEntity.getContent().split(",");
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

        System.out.println("结束匹配队列");

    }


}
