package com.csbaic.jd.service.login;


import com.csbaic.common.exception.BizRuntimeException;
import com.csbaic.common.result.ResultCode;
import com.google.common.base.Strings;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;


@Component
public class GuavaCacheWechatUserRepository implements WechatUserRepository {


    private final Cache<String, WechatUser> wechatUserCache = CacheBuilder.
            newBuilder()
            .initialCapacity(100)
            .maximumSize(10000)
            .expireAfterWrite(5, TimeUnit.MINUTES) //寫入後五分鐘過期
//            .expireAfterAccess(5, TimeUnit.SECONDS) //讀取後小秒過期
            .build();


    @Override
    public WechatUser get(String openId) {
        return wechatUserCache.getIfPresent(openId);
    }

    @Override
    public void save(WechatUser user) {
        if(user == null || Strings.isNullOrEmpty(user.getOpenId())){
            throw BizRuntimeException.from(ResultCode.BAD_REQUEST, "沒有OpenId無法保存用戶");
        }

        wechatUserCache.put(user.getOpenId(), user);
    }
}
