package com.csbaic.jd.commission;

import com.csbaic.jd.enums.UserIdentify;
import com.csbaic.jd.service.team.MemberContainer;
import com.csbaic.jd.service.team.MemberContainerBuilder;
import com.csbaic.jd.service.team.bean.MemberNode;
import com.csbaic.jd.service.team.impl.TeamMemberContainer;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by yjwfn on 2020/2/16.
 */

public class MockMemberContainerBuilder implements MemberContainerBuilder {



    private final List<MemberNode> nodes;

    public MockMemberContainerBuilder(List<MemberNode> nodes) {
        this.nodes = nodes;
    }


    public MemberContainer build(Long memberId){

        List<Long> ids  = nodes.stream()
                .map(node -> node.getUserId())
                .collect(Collectors.toList());

        int index, startIndex;
        index = startIndex = ids.indexOf(memberId) ;

        for(; index < nodes.size() ; index++){
            MemberNode pre = index > 0 ? nodes.get(index - 1) : null;
            MemberNode node = nodes.get(index);

            if(pre != null){
                pre.setNext(node);
            }

            node.setPre(pre);
        }

        MemberNode memberNode = !nodes.isEmpty() ? nodes.get(startIndex) : null;
        return new TeamMemberContainer(memberNode) ;
    }


}
