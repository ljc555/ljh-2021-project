package com.csbaic.jd.service;

import com.csbaic.jd.dto.Option;
import com.csbaic.jd.entity.OptionEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yjwfn
 * @since 2020-03-11
 */
public interface IOptionService extends IService<OptionEntity> {


    /**
     * 獲取選項
     * @param key
     * @return
     */
    List<Option> getOptions(String...key);


    /**
     * 获取App配置
     * @return
     */
    List<Option> getAppOptions();

    /**
     * 获取系统配置项
     * @return
     */
    List<Option> getSysOptions();


    /**
     * 设置选项
     * @param values
     */
    void setOptions(Map<String, String> values);

    /**
     * 设置选项
     * @param key
     */
    void setOption(String key, String value);


    /**
     * 獲取選項
     * @param key
     * @return
     */
    String getString(String key);

    /**
     * 獲取選項
     * @param key
     * @return
     */
    Integer getInt(String key);


    /**
     * 获取时间
     * @param key
     * @param format
     * @return
     */
    LocalDateTime getLocalDateTime(String key, String format);
}
