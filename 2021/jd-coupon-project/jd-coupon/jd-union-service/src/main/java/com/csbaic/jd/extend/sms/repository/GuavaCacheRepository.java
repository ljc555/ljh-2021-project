package com.csbaic.jd.extend.sms.repository;

import com.google.common.base.Strings;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;

public class GuavaCacheRepository implements SmsRepository {

    /**
     * 将短信保存到内存
     */
    private Cache<String, SmsInfo> cache = CacheBuilder.newBuilder()
            .maximumSize(10000)
            .initialCapacity(100)
            .expireAfterWrite(5, TimeUnit.MINUTES)
            .build();


    @Override
    public String save(SmsInfo info) {
        String key = makeKey();
        cache.put(key,info);
        return key;
    }

    @Override
    public SmsInfo get(String key) {
        if(Strings.isNullOrEmpty(key)){
            return null;
        }
        return cache.getIfPresent(key);
    }

    @Override
    public Map<String, SmsInfo> getByPhone(String phoneExt, String phone) {
        final Map<String, SmsInfo> infos = new HashMap<>();
         cache.asMap()
                 .forEach(new BiConsumer<String, SmsInfo>() {
                     @Override
                     public void accept(String s, SmsInfo info) {
                         if(Objects.equals(info.getPhone(), phone) && Objects.equals(info.getPhoneExt(), phoneExt)){
                             infos.put(s, info);
                         }
                     }
                 });

         return infos;
    }

    @Override
    public void remove(String key) {
        cache.invalidate(key);
    }

    public static String makeKey(){
        return UUID.randomUUID().toString();
    }
}
