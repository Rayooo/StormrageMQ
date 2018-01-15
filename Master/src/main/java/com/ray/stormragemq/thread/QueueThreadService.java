package com.ray.stormragemq.thread;

import com.ray.stormragemq.entity.QueueEntity;
import com.ray.stormragemq.entity.QueueMessageEntity;
import com.ray.stormragemq.netty.ClientChannel;
import com.ray.stormragemq.netty.service.GatewayService;
import com.ray.stormragemq.util.JsonUtil;
import com.ray.stormragemq.util.LogUtil;
import com.ray.stormragemq.util.RandomUtil;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

@Service
public class QueueThreadService {

    @Autowired
    private GatewayService gatewayService;

    @Autowired
    private Map<String, QueueEntity> queueMap;

    /**
     * 每个队列启动一个线程发送消息
     * */
    public void start(){
        queueMap.forEach((s, queueEntity) -> {

            startThread(queueEntity);

        });

    }


    /**
     * 开启线程
     * */
    private void startThread(QueueEntity queueEntity){
        new Thread(() -> {
            int modCount = ModCount.getModCount();

            BlockingQueue<QueueMessageEntity> q = queueEntity.getBlockingQueue();
            List<String> consumerUuidList = queueEntity.getConsumerUuidList();

            while (true){
                QueueMessageEntity queueMessage = null;

                //检查消费者是否存在
                if(consumerUuidList.size() > 0){
                    try {
                        queueMessage = q.poll(5, TimeUnit.SECONDS);
                        if(queueMessage != null){
                            String messageString = JsonUtil.toJson(queueMessage.getMessage());
                            ClientChannel clientChannel = gatewayService.getGatewayChannel(consumerUuidList.get(RandomUtil.getIntRandom(0, consumerUuidList.size() - 1)));
                            clientChannel.getSocketChannel().writeAndFlush(Unpooled.copiedBuffer(messageString, CharsetUtil.UTF_8));
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    //消费者不存在，暂停5秒
                    try {
                        TimeUnit.SECONDS.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                //检查modCount
                if(modCount != ModCount.getModCount()){
                    LogUtil.logInfo("ModCount被修改，线程结束，modCount = " + ModCount.getModCount());
                    break;
                }

            }
        }).start();

    }






}
