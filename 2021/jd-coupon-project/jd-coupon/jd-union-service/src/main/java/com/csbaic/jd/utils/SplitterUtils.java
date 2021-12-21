package com.csbaic.jd.utils;

import com.google.common.base.Splitter;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * 字符分割工具
 */
public final class SplitterUtils {

    /**
     * 将字符串分割成集合
     * @param str
     * @param separator
     * @return
     */
    public static List<String> split(String str, String separator){
        return StreamSupport.stream(
                Splitter.on(separator)
                        .split(str)
                        .spliterator(), false
        ).collect(Collectors.toList());
    }


    /**
     * 将集合连接成字符串
     * @param items
     * @param separator
     * @return
     */
    public static String stringfiy(List<String> items, String separator){
        if(items == null || items.isEmpty()){
            return "";
        }

        return String.join(separator, items);
    }
}
