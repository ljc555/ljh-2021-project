package com.csbaic.jd.service.team;

import com.csbaic.jd.enums.UserIdentify;
import com.csbaic.jd.service.team.bean.MemberNode;

import java.util.List;
import java.util.function.Predicate;

/**
 * Created by yjwfn on 2020/2/16.
 */
public interface MemberContainer {

    /**
     * 获取根结点
     * @return
     */
    MemberNode getRoot();

    /**
     * 获取节点
     * @param predicate
     * @return
     */
    List<MemberNode> find(Predicate<MemberNode>  predicate);

    /**
     * 生成成员信息
     * @param memberNode
     * @return
     */
    MemberInfo buildMemberInfo(MemberNode memberNode);
    /**
     * 查找直属有效成员
     * 1. S1  -  R1 - S2 : S1为S2的直属超级会员
     * 2. T1 - S1 - T2 ： T1为T2的直属导师
     * 1. S1  -  S2 : S1为S2的直属超级会员
     * 2. T1 -  T2 ： T1为T2的直属导师
     *
     * @param node
     * @param identify
     * @param ignoreIdentify
     * @return
     */
      MemberNode findDirectlyMemberAndIgnore(MemberNode node, UserIdentify identify, UserIdentify... ignoreIdentify);

}
