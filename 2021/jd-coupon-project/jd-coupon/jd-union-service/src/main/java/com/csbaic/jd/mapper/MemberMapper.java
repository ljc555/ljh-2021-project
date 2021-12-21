package com.csbaic.jd.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.csbaic.jd.dto.SimpleMemberUserInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 成员mapper
 */

public interface MemberMapper {


    /**
     * 查询用户的成员
     * @param page 分页
     * @param userId 用户id
     * @param identify 按身份
     * @return
     */
    IPage<SimpleMemberUserInfo> getMembersOfUserIdAndIdentify(Page<?> page, @Param("userId") Long userId, @Param("identify")  Integer identify);

    /**
     * 获取用户的联系人信息
     * @param userId 用户id
     * @return
     */
    SimpleMemberUserInfo getMemberInfoOfUserId(@Param("userId") Long userId);


    /**
     * 获取用户的联系人（公显示填写了微信账号的人）
     * @param page  分页
     * @param userId 用户id
     * @param excluded 排除列表
     * @return
     */
    IPage<SimpleMemberUserInfo> getMembersInfoOfUserIdAndNotIn(IPage<?> page, @Param("userId") Long userId,@Param("excluded") List<Long> excluded);



}
