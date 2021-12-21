package com.csbaic.jd.service.user.active;

import com.csbaic.jd.entity.TeamMemberEntity;
import com.csbaic.jd.service.IMemberCommissionService;
import com.csbaic.jd.service.ITeamMemberService;
import com.csbaic.jd.service.settlement.MemberBilling;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Component
public class DefaultActiveScoreCalculator implements ActiveScoreCalculator {

    private final ActiveScoreRepository repository;

    private final ITeamMemberService teamMemberService;

    private final IMemberCommissionService memberCommissionService;



    @Autowired
    public DefaultActiveScoreCalculator(ActiveScoreRepository repository, ITeamMemberService teamMemberService, IMemberCommissionService memberCommissionService) {
        this.repository = repository;
        this.teamMemberService = teamMemberService;
        this.memberCommissionService = memberCommissionService;
    }

    @Override
    public ActiveScore cal(ActiveScoreRequest request) {

        ActiveScore activeScore = repository.get(request);
        if(activeScore != null){
            log.info("Get active score form cache:　{}", request);
            return activeScore;
        }

        activeScore = calActiveScore(request);
        repository.save(request, activeScore);

        return activeScore;
    }




    private ActiveScore calActiveScore(ActiveScoreRequest request){
        log.info("cal active score for {}", request);
        //自己产生的佣金

        MemberBilling commission =  memberCommissionService.getEstimateSettlementFee(request.getUserId(), request.getStart(), request.getEnd());

        BigDecimal maxActiveScorePerUser = new BigDecimal("1000");
        //找出所有的直属成员
        List<TeamMemberEntity> members =  teamMemberService.getMembersOf(request.getUserId() , 1);
        BigDecimal activeScore = commission != null ? maxActiveScorePerUser.min(commission.getActualCommissionFee()) : BigDecimal.ZERO;
        log.info("Active score of self: {}", activeScore);

        for(TeamMemberEntity memberEntity : members){
            MemberBilling commissionOfMember = memberCommissionService.getEstimateSettlementFee( memberEntity.getId(), request.getStart(), request.getEnd());

            if(commissionOfMember != null){
                log.info("Active score of {}}: {}", memberEntity.getMemberId(), activeScore);
                activeScore  = activeScore.add(maxActiveScorePerUser.min(commissionOfMember.getActualRebateFee()));
            }
        }
        log.info("Active score is {}", activeScore);
        ActiveScore obj = new ActiveScore();
        obj.setActiveScore(activeScore);

        return obj;
    }

}
