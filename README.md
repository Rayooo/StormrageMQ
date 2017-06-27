# StormrageMQ
Master主节点 ：服务端口号3000

MQ节点：服务端口号3001，Netty端口号4001

Web服务节点：服务端口号8080

redis端口号6379

mysql端口号3306

前端Vue + Element UI

## 流程

1.启动master节点

2.启动mq节点，一个或者两个，mq节点自动发送自身节点名字和ip给master注册

3.master根据他的Hashmap上的mq节点列表，发送心跳，若不成功则会被剔除，mq注册会根据名称，若同名，会使得前一个无效。



## 坑点

Netty启动服务后会阻塞进程，所以无法执行到.start()方法后的register()代码。

@EventListener(ContextRefreshedEvent.class)这句话在spring启动的时候会调用，但是启用netty时并不好用。我最终的解决办法是开启两个线程，一个调用.start()方法启动netty服务，另一个调用register()方法在master上注册MQ。