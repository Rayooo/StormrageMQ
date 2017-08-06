package com.ray.stormragemq.util;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

public class MatchQueueUtil {

    public static boolean match(String strA, String strB){
        if("*".equals(strA) || "*".equals(strB)){
            return true;
        }
        strA = StringUtils.deleteWhitespace(strA);
        strB = StringUtils.deleteWhitespace(strB);

        List<String> listA = Arrays.asList(strA.split("\\."));
        List<String> listB = Arrays.asList(strB.split("\\."));

        if(listA.size() != listB.size()){         //两个长度需要一样
            return false;
        }
        
        for(int i = 0; i < listA.size(); i++){
            if("*".equals(listA.get(i)) || "*".equals(listB.get(i))){
                continue;
            }

            if(!StringUtils.equals(listA.get(i), listB.get(i))){
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args){
        System.out.println(match("com.ray.a", "com.ray.a.c"));
        System.out.println();
        System.out.println(match("com.ray.a", "com.*.a"));
        System.out.println();
        System.out.println(match("com.ray.a", "*"));
        System.out.println();
        System.out.println(match("com.ray.a", "*.*.*"));
        System.out.println();
        System.out.println(match("com.ray.a", "*.*.a"));
        System.out.println();
        System.out.println(match("com.ray.a", "*.a"));
        System.out.println();


    }


}





