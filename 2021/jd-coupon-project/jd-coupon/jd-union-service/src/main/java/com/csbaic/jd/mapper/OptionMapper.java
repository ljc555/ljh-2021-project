package com.csbaic.jd.mapper;

import com.csbaic.jd.dto.Option;
import com.csbaic.jd.entity.OptionEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yjwfn
 * @since 2020-03-11
 */
public interface OptionMapper extends BaseMapper<OptionEntity> {

    /**
     * 獲取選項
     * @param key
     * @return
     */
    List<Option> getOptions(@Param("keys") String...key);

    /**
     * 设置选项
     * @param value
     */
    void setOption(@Param("name") String name, @Param("value") String value);
}
