package com.csbaic.jd.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.csbaic.jd.entity.TeamMemberEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 团队人员关系表 服务类
 * </p>
 *
 * @author yjwfn
 * @since 2020-02-14
 */
public interface ITeamMemberService extends IService<TeamMemberEntity> {

    /**
     * 为{@code parentId}添加一个成员
     * @param directLeaderId 推荐人
     * @param memberId 被推荐人用户id
     */
    TeamMemberEntity addMember(Long directLeaderId, Long memberId);


    /**
     * 获取{@code userId}的所有成员
     * @param directLeaderId 用记Id
     */
    List<TeamMemberEntity> getMembersOf(Long directLeaderId);

    /**
     * 获取{@code userId}的所有成员
     * @param directLeaderId 用记Id
     */
    List<TeamMemberEntity> getMembersOf(Long directLeaderId, Integer level);

    /**
     * 获取{@code userId}的祖先
     * @param memberId 用户Id
     */
    List<TeamMemberEntity> getLeadersOf(Long memberId);

    /**
     * 获取{@code userId}的祖先
     * @param memberId 用户Id
     */
    TeamMemberEntity getLeadersOf(Long memberId, int level);


    /**
     * 查找最近且身份为{@code identify}的领导人
     * @param memberId
     * @return
     */
    TeamMemberEntity findNearestLeaderOfMemberAndIdentify(Long memberId, int identify);

    /**
     * 查找直接领导人
     * @param memberId
     * @return
     */
    TeamMemberEntity findDirectlyLeaderOfMember(Long memberId);

    /**
     * 根据等级、Level计算成员数量
     *
     * @param leaderId
     * @param identify
     * @param level
     * @return
     */
    int countByLeaderIdAndUserIdentifyAndLevel(Long leaderId, Integer identify, Integer level);

    /**
     * 根据记录时间统计成员人数
     * @param leaderId
     * @return
     */
    int countByLeaderAndCreateTimeBetween(Long leaderId , LocalDateTime begin, LocalDateTime end);

    /**
     * 统计{@code leaderId}的成员数量
     * @param leaderId
     * @return
     */
    int countByLeader(Long leaderId);


    /**
     * 获取团队下人数
     * @param leaderId 用户Id
     * @return
     */
    long getMemberCount(Long leaderId);
}
