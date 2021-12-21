package com.csbaic.jd.extend.sms.repository;

import java.util.List;
import java.util.Map;

/**
 * 短信存储
 */
public interface SmsRepository {

    /**
     * 保存短信信息
     * @param info 短信信息
     */
    String  save(SmsInfo info);

    /**
     * 获取短信
     * @param key
     * @return
     */
    SmsInfo get(String key);

    /**
     * 移除记录
     * @param key
     */
    void remove(String key);

    /**
     * 通过手机号查找记录
     * @param phoneExt
     * @param phone
     * @return
     */
    Map<String, SmsInfo> getByPhone(String phoneExt, String phone);
}
