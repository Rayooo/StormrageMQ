package com.ray.demo.loginconsumer2.util;

import java.util.concurrent.ThreadLocalRandom;

public class RandomUtil {

    /**
     * min max 都可以取到
     * */
    public static int getIntRandom(int min, int max){
        int randomNum = ThreadLocalRandom.current().nextInt(min, max + 1);
        return randomNum;
    }

    public static void main(String[] args){
        System.out.println(getIntRandom(0, 0));
    }

}
