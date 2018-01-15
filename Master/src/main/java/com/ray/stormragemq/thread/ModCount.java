package com.ray.stormragemq.thread;

import java.util.concurrent.atomic.AtomicInteger;

public class ModCount {

    private ModCount(){};

    /**
     * 队列更改次数，当子线程中的modCount != 这里的modCount时，停止线程
     * */
    private static volatile AtomicInteger modCount = new AtomicInteger(0);


    public static void addModCount(){
        modCount.incrementAndGet();
    }

    public static int getModCount(){
        return modCount.get();
    }


}
