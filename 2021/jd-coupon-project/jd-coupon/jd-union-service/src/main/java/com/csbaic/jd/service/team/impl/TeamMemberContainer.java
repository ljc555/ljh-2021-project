package com.csbaic.jd.service.team.impl;

import com.csbaic.jd.enums.UserIdentify;
import com.csbaic.jd.service.team.MemberContainer;
import com.csbaic.jd.service.team.MemberInfo;
import com.csbaic.jd.service.team.bean.MemberNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * Created by yjwfn on 2020/2/16.
 */

public class TeamMemberContainer implements MemberContainer {

    private final MemberNode root;

    public TeamMemberContainer(MemberNode root) {
        this.root = root;
    }

    @Override
    public MemberNode getRoot() {
        return root;
    }

    @Override
    public List<MemberNode> find(Predicate<MemberNode> predicate) {
        MemberNode node = root;
        List<MemberNode> nodes = new ArrayList<>();

        while (node != null){
            if (predicate.test(node)) {
                nodes.add(node);
            }
            node = node.getNext();
        }

        return nodes;
    }

    @Override
    public MemberInfo buildMemberInfo(MemberNode memberNode) {
        MemberNode root = memberNode;
        MemberInfo memberInfo = new MemberInfo();
        memberInfo.setRoot(memberNode);


        if(Objects.equals(root.getIdentify(), UserIdentify.SUPER   )
                || Objects.equals(root.getIdentify(), UserIdentify.REGISTERED   )
            ){
            memberInfo.setDirectlySuperMember(
                    findDirectlyMemberAndIgnore(root, UserIdentify.SUPER , UserIdentify.REGISTERED )
            );   ;

            //找出第一个导师
            MemberNode firstTeacher = find(new Predicate<MemberNode>() {
                @Override
                public boolean test(MemberNode node) {
                    return node != root && Objects.equals(node.getIdentify() ,UserIdentify.TEACHER);
                }
            }).stream().findFirst().orElse(null);

            //判断第一个导师是直属或非直属
            if(firstTeacher == root.getNext()){
                memberInfo.setDirectlyTeacher(firstTeacher);
            }else {
                memberInfo.setIndirectlyTeacher(firstTeacher);
            }

            MemberNode teacherMember = memberInfo.getDirectlyTeacher() != null ? memberInfo.getDirectlyTeacher() : memberInfo.getIndirectlyTeacher();
            //找到第一个导师的直属导师
            memberInfo.setDirectlyTeacherOfTeacher(
                    findDirectlyMemberAndIgnore(teacherMember,  UserIdentify.TEACHER , UserIdentify.SUPER, UserIdentify.REGISTERED)
            );

        }else if(root.getIdentify() == UserIdentify.TEACHER){
            //判断导师的直属导师
            memberInfo.setDirectlyTeacher(findDirectlyMemberAndIgnore(root,  UserIdentify.TEACHER , UserIdentify.SUPER, UserIdentify.REGISTERED)); ;

            //找到 memberInfo.directlyTeacher 的直属导师
            if (memberInfo.getDirectlyTeacher() != null) {
                memberInfo.setDirectlyTeacherOfTeacher(
                        findDirectlyMemberAndIgnore(memberInfo.getDirectlyTeacher(),  UserIdentify.TEACHER , UserIdentify.SUPER, UserIdentify.REGISTERED)
                );
            }
        }

        return memberInfo;
    }

    @Override
    public MemberNode findDirectlyMemberAndIgnore(MemberNode node, UserIdentify identify, UserIdentify... ignoreIdentify) {
        if(node == null){
            return null;
        }

        List<UserIdentify> ignores = new ArrayList<>();
        if(ignoreIdentify != null){
            ignores.addAll(Arrays.asList(ignoreIdentify));
        }
        MemberNode next = node.getNext();
        while (next != null){
            if(Objects.equals(identify , next.getIdentify())){
                return next;
            }else if(!ignores.contains(next.getIdentify())){ //不可跳过的成员
                break;
            }
            next = next.getNext();
        }

        return null;
    }
}
