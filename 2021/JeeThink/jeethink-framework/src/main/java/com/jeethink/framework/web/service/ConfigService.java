package com.jeethink.framework.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jeethink.system.service.ISysConfigService;

/**
 * JeeThink首创 html调用 thymeleaf 实现参数管理
 * 
 * @author jeethink  www.jeethink.vip
 */
@Service("config")
public class ConfigService
{
    @Autowired
    private ISysConfigService configService;

    /**
     * 根据键名查询参数配置信息
     * 
     * @param configName 参数名称
     * @return 参数键值
     */
    public String getKey(String configKey)
    {
        return configService.selectConfigByKey(configKey);
    }
}
