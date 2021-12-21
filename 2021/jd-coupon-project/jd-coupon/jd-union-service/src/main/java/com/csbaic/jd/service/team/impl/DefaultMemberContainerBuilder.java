package com.csbaic.jd.service.team.impl;

import com.csbaic.jd.entity.TeamMemberEntity;
import com.csbaic.jd.entity.UserEntity;
import com.csbaic.jd.service.ITeamMemberService;
import com.csbaic.jd.service.IUserService;
import com.csbaic.jd.service.team.MemberContainer;
import com.csbaic.jd.service.team.MemberContainerBuilder;
import com.csbaic.jd.service.team.bean.MemberNode;
import com.csbaic.jd.utils.UserUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yjwfn on 2020/2/16.
 */
@Component
public class DefaultMemberContainerBuilder implements MemberContainerBuilder {

    private final ITeamMemberService teamMemberService;

    private final IUserService userService;


    public DefaultMemberContainerBuilder(ITeamMemberService teamMemberService, IUserService userService) {
        this.teamMemberService = teamMemberService;
        this.userService = userService;
    }


    public MemberContainer build(Long memberId){

        List<TeamMemberEntity> entityList = teamMemberService.getLeadersOf(memberId);
        List<MemberNode> nodes  = new ArrayList<>();
        //转换成MemberNode
        for(TeamMemberEntity entity : entityList){
            //拿出用户信息
            UserEntity userEntity = userService.getById(entity.getLeaderId());


            MemberNode node  = new MemberNode();
            node.setUserId(userEntity.getId());
            node.setIdentify(UserUtils.getUserIdentify(userEntity.getIdentify()));
            node.setLevel(entity.getLevel());
            nodes.add(node);
        }

        for(int index = 1 ; index < nodes.size() ; index++){
            MemberNode pre = nodes.get(index - 1);
            MemberNode node = nodes.get(index);


            pre.setNext(node);
            node.setPre(pre);
        }

        MemberNode memberNode = !nodes.isEmpty() ? nodes.get(0) : null;
        return new TeamMemberContainer(memberNode) ;
    }
}
