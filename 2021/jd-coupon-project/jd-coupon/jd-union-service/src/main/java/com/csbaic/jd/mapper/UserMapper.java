package com.csbaic.jd.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.csbaic.jd.dto.SimpleMemberUserInfo;
import com.csbaic.jd.entity.UserEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author yjwfn
 * @since 2020-02-14
 */
public interface UserMapper extends BaseMapper<UserEntity> {

    /**
     * 更新用户信息
     * @param userId
     * @param values 需要更新的字段的值
     */
    void updateUserInfoById(@Param("userId") Long userId,@Param("values") Map<String, Object> values);

    /**
     * 更新用户信息
     * @param userId
     * @param wechatId 需要更新的字段的值
     */
    void updateWechatIdById(@Param("userId") Long userId,@Param("wechatId") String wechatId);
}
