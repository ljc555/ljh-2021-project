package com.csbaic.jd.service.team.bean;

import com.csbaic.jd.enums.UserIdentify;
import com.csbaic.common.exception.BizRuntimeException;
import com.csbaic.jd.service.commission.MemberCommissionInfo;
import com.csbaic.common.result.ResultCode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by yjwfn on 2020/2/16.
 */
@Getter
@Setter
public class MemberNode {

    /**
     * 成员用户id
     */
    private Long userId;

    /**
     * 成员等级
     */
    private UserIdentify identify;

    /**
     * 深度
     */
    private Integer level;

    /**
     * 直接下级
     */
    private MemberNode pre;

    /**
     * 直接上级
     */
    private MemberNode next;

    /**
     * 成员对象信息
     */
    private List<MemberCommissionInfo> commissions;

    /**
     * 添加佣金信息
     * @param memberCommissionInfo
     */
    public void add(MemberCommissionInfo memberCommissionInfo){
        if(commissions == null){
            commissions = new ArrayList<>();
        }

        commissions.add(memberCommissionInfo);
    }

    /**
     * 获取第一条佣金记录
     * @return
     */
    public Optional<MemberCommissionInfo> findFirst(){
        return commissions != null ? commissions.stream().findFirst() : Optional.empty();
    }



    @Override
    public String toString() {
        return "MemberNode{" +
                "userId=" + userId +
                ", identify=" + identify +
                ", level=" + level +
                ", object=" + commissions +
                '}';
    }
}
