package com.csbaic.jd.service.team;

/***
 * 邀请码生成器
 */
public interface InvitationCodeGenerator {


    /***
     * 生成邀请码
     * @return
     */
    String generate(Long uniqueId);

}
