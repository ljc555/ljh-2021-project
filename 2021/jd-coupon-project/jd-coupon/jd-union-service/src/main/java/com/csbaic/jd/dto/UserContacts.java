package com.csbaic.jd.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserContacts {

    /**
     * 我的
     */
    private SimpleMemberUserInfo mine;

    /**
     * 邀请人
     */
    private SimpleMemberUserInfo inviter;

    /**
     * 导师
     */
    private SimpleMemberUserInfo teacher;

    /**
     * 粉丝
     */
    private List<SimpleMemberUserInfo> fans;



}
