package com.csbaic.jd.service.user;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.csbaic.jd.dto.*;

public interface MemberService {




    /**
     * 获取成员信息
     * @param userId
     * @return
     */
    GradeMemberInfo getGradeMemberInfo(Long userId);


    /**
     * 获取用户的导师
     * @param userId
     * @return
     */
    SimpleMemberUserInfo getFirstTeacherOf(Long userId);


    /**
     * 获取用户成员信息
     * @param userId
     * @return
     */
    SimpleMemberUserInfo getMemberInfoOfUser(Long userId);

    /**
     * 获取用户成员列表
     * @param userId
     * @return
     */
    IPage<SimpleMemberUserInfo> getMembersOf(Long userId, QueryMember queryMember);


    /**
     * 查询联系人
     * @param userId
     * @param contact
     * @return
     */
    UserContacts getUserContacts(Long userId, QueryContacts contact);

    /**
     * 我的收益
     * @param userId
     * @return
     */
    MemberFeeSegments getMemberFeeBlocks(Long userId);


}
