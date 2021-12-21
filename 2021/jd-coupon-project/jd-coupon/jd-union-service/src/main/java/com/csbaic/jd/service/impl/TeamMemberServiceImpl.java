package com.csbaic.jd.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.csbaic.jd.entity.TeamMemberEntity;
import com.csbaic.common.exception.BizRuntimeException;
import com.csbaic.jd.mapper.TeamMemberMapper;
import com.csbaic.jd.service.ITeamMemberService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 团队人员关系表 服务实现类
 * </p>
 *
 * @author wuhao
 * @since 2019-09-12
 */
@Slf4j
@Service
public class TeamMemberServiceImpl extends ServiceImpl<TeamMemberMapper, TeamMemberEntity> implements ITeamMemberService {

    @Override
    @Transactional
    public TeamMemberEntity addMember(Long leaderId, Long memberId) {

        if(leaderId == null || memberId  == null){
            throw new BizRuntimeException("参数不正确");
        }

        if(Objects.equals(leaderId, memberId)){
            throw new BizRuntimeException("相同成员不能插入");
        }

//        String key = ADD_MEMBER_LOCK_KEY + "_" + leaderId;  //锁住单个用户的操作
//        String timeout = String.valueOf(System.currentTimeMillis() + ADD_MEMBER_LOCK_TIMEOUT);
//        boolean locked = redisLock.lock(key, timeout);
//
//        if(!locked){
//            throw new BizRuntimeException("系统繁忙，请稍后再试~~");
//        }

        try{
            return addMemberInner(leaderId, memberId);
        }finally {
//            redisLock.unlock(key, timeout);
        }
    }


    public TeamMemberEntity addMemberInner(Long leaderId, Long memberId) {

        long count = count(
                Wrappers
                        .<TeamMemberEntity>query()
                        .eq(TeamMemberEntity.LEADER_ID, leaderId)
                        .eq(TeamMemberEntity.MEMBER_ID, memberId)
        );


        if (count > 0) {
            throw new BizRuntimeException("成员已经存在");
        }

        List<TeamMemberEntity> entitiesToInsert = new ArrayList<>();
        List<TeamMemberEntity> allLeaders = new ArrayList<>();

        //插入自己的记录
        TeamMemberEntity selfLeader = new TeamMemberEntity();
        selfLeader.setLeaderId(memberId);
        selfLeader.setMemberId(memberId);
        selfLeader.setLevel(0);
        save(selfLeader);

//        allLeaders.add(selfLeader);

        //找出leaderId的所有领导人，包含level为0的记录
        List<TeamMemberEntity> leadersOfUser =  list(
                Wrappers
                        .<TeamMemberEntity>query()
                        .eq(TeamMemberEntity.MEMBER_ID, leaderId)
        );

        allLeaders.addAll(leadersOfUser);

        TeamMemberEntity indirectRelationEntity;
        TeamMemberEntity directRelationEntity = selfLeader;
        for(TeamMemberEntity leader: allLeaders){
            int level = leader.getLevel() + 1;
            indirectRelationEntity = new TeamMemberEntity();
            indirectRelationEntity.setLevel(level);
            indirectRelationEntity.setLeaderId(leader.getLeaderId());
            indirectRelationEntity.setMemberId(memberId);
            entitiesToInsert.add(indirectRelationEntity);

            if(level == 1){
                directRelationEntity = indirectRelationEntity;
            }
        }

        if(directRelationEntity == null){
            throw new BizRuntimeException("添加成员失败");
        }

        saveBatch(entitiesToInsert);

        return directRelationEntity;
    }





    @Override
    public List<TeamMemberEntity> getMembersOf(Long leaderId ) {
        return list(
                Wrappers
                        .<TeamMemberEntity>query()
                        .eq(TeamMemberEntity.LEADER_ID, leaderId)
        );
    }

    @Override
    public List<TeamMemberEntity> getMembersOf(Long leaderId, Integer level) {

        return list(
                Wrappers
                        .<TeamMemberEntity>query()
                        .eq(TeamMemberEntity.LEADER_ID, leaderId)
                        .eq(TeamMemberEntity.LEVEL, level)
        );
    }

    @Override
    public List<TeamMemberEntity> getLeadersOf(Long memberId ) {
        return list(
                Wrappers
                        .<TeamMemberEntity>query()
                        .eq(TeamMemberEntity.MEMBER_ID, memberId)
                        .orderByAsc(TeamMemberEntity.LEVEL)
        );
    }


    @Override
    public TeamMemberEntity getLeadersOf(Long memberId , int level) {
        return getOne(
                Wrappers
                        .<TeamMemberEntity>query()
                        .eq(TeamMemberEntity.MEMBER_ID, memberId)
                        .eq(TeamMemberEntity.LEVEL, level)
        );
    }
    @Override
    public long getMemberCount(Long leaderId) {
        return count(
                Wrappers
                        .<TeamMemberEntity>query()
                        .eq(TeamMemberEntity.LEADER_ID, leaderId)
                        .ne(TeamMemberEntity.LEVEL, 0) //去掉自己
        );
    }

    @Override
    public TeamMemberEntity findNearestLeaderOfMemberAndIdentify(Long memberId, int identify) {
        return getBaseMapper().findNearestLeaderOfMemberAndIdentify(memberId, identify);
    }

    @Override
    public TeamMemberEntity findDirectlyLeaderOfMember(Long memberId) {
        return getOne(
                Wrappers.<TeamMemberEntity>query()
                .eq(TeamMemberEntity.MEMBER_ID, memberId)
                .eq(TeamMemberEntity.LEVEL , 1)
        );
    }

    @Override
    public int countByLeaderIdAndUserIdentifyAndLevel(Long leaderId, Integer identify, Integer level) {
        return getBaseMapper().countByLeaderIdAndUserIdentifyAndLevel(leaderId, identify, level);
    }

    @Override
    public int countByLeaderAndCreateTimeBetween(Long leaderId, LocalDateTime begin, LocalDateTime end) {
        return count(
                Wrappers.<TeamMemberEntity>query()
                        .eq(TeamMemberEntity.LEADER_ID, leaderId)
                        .ne(TeamMemberEntity.LEVEL , 0)
                        .between(TeamMemberEntity.CREATE_TIME, begin, end)
        );

    }

    @Override
    public int countByLeader(Long leaderId) {
        return count(
                Wrappers.<TeamMemberEntity>query()
                        .eq(TeamMemberEntity.LEADER_ID, leaderId)
                        .ne(TeamMemberEntity.LEVEL , 0)
        );
    }


    //
//    public void recreateTeamRelation(){
//        log.debug("Recreate begin" );
//        build("88888888");
//        log.debug("Recreate finish" );
//
//    }


//    @Override
//    public void recreateTeamRelationForToday() {
//        recreateTeamRelationForDate(LocalDate.now());
//    }
//
//    @Override
//    public void recreateTeamRelationForDate(LocalDate date) {
//        log.debug("recreateTeamRelationForDate begin" );
//        TeamMemberMapper teamRelationMapper =  getBaseMapper();
//        List<UserRelationNode> members = teamRelationMapper.getUserRelationNodeForDate(date);
//
//        for(UserRelationNode node : members){
//            log.debug("Build start for {}" , node.getReferralCode());
//            teamRelationMapper.callNestedValueToClosure(node.getReferralCode());
//            log.debug("Build complete for {}" , node.getReferralCode());
//        }
//
//        log.debug("recreateTeamRelationForDate finish" );
//    }
//
//    @Override
//    public void recreateTeamRelationForLeader(String codeOfLeader) {
//        log.debug("recreateTeamRelationForLeader begin" );
//        build(codeOfLeader);
//        log.debug("recreateTeamRelationForLeader finish" );
//    }
//
//    public void build(String leaderCode){
//        TeamRelationMapper teamRelationMapper =  getBaseMapper();
//        List<UserRelationNode> members = teamRelationMapper.getUserRelationNodeByRecommendCode(leaderCode);
//
//        for(UserRelationNode mem : members){
//            build(mem.getReferralCode());
//        }
//
//        UserRelationNode self = teamRelationMapper.getUserRelationNodeByReferralCode(leaderCode);
//        log.debug("Build start for {}" , leaderCode);
//        teamRelationMapper.callNestedValueToClosure(self.getReferralCode());
//        log.debug("Build complete for {}" , leaderCode);
//
//    }
}
