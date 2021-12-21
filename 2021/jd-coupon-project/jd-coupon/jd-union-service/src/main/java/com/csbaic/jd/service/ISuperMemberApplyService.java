package com.csbaic.jd.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.csbaic.jd.dto.ApplySuperMember;
import com.csbaic.jd.dto.RejectSuperMemberApply;
import com.csbaic.jd.dto.SuperMemberApplyInfo;
import com.csbaic.jd.dto.SuperMemberApplyOrderInfo;
import com.csbaic.jd.entity.SuperMemberApplyEntity;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 超级会员申请表 服务类
 * </p>
 *
 * @author yjwfn
 * @since 2020-03-24
 */
public interface ISuperMemberApplyService extends IService<SuperMemberApplyEntity> {


    /**
     * 同步成为超级会员
     * @param userId
     * @param applyId
     */
    void  approveSuperMemberApply(Long userId, Long applyId);


    /**
     * 拒绝申请
     * @param userId
     * @param rejectSuperMemberApply
     */
    void rejectSuperMemberApply(Long userId,Long applyId,  RejectSuperMemberApply rejectSuperMemberApply);


    /**
     * 获取申请单
     * @param userId
     * @return
     */
    IPage<SuperMemberApplyOrderInfo> getSuperMemberApplyOrderInfo(Long userId, Integer status, int pageIndex, int pageSize);

    /**
     * 微信群组信息
     * @param userId
     * @return
     */
    SuperMemberApplyInfo getSuperMemberApplyInfo(Long userId);


    /***
     * 申请成为超级会员
     * @param userId
     * @param applySuperMember
     */
    void submitApplySuperMember(Long userId, ApplySuperMember applySuperMember);




}
