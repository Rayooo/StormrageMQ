# StormrageMQ
Master主节点 ：服务端口号3000

MQ节点：服务端口号3001，Netty端口号4001

Web服务节点：服务端口号8080

redis端口号6379

mysql端口号3306

## 坑点

Netty启动服务后会阻塞进程，所以无法执行到.start()方法后的register()代码。

@EventListener(ContextRefreshedEvent.class)这句话在spring启动的时候会调用，但是启用netty时并不好用。我最终的解决办法是开启两个线程，一个调用.start()方法启动netty服务，另一个调用register()方法在master上注册MQ。