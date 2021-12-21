package com.csbaic.jd.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author yjwfn
 * @since 2020-02-15
 */
@TableName("jd_user")
public class UserEntity implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 用户名
     */
    private String nickName;

    /**
     * 活动分享图
     */
    private String avatarUrl;

    /**
     * 手机号
     */
    private String phone;


    /**
     * 用户身份（导师，合伙人）
     */
    private Integer identify;

    /**W
     * 邀请码
     */
    private String invitationCode;

    /**
     * 邀请人用户id
     */
    private Long inviterId;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 注册时间
     */
    private LocalDateTime createTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }



    public Integer getIdentify() {
        return identify;
    }

    public void setIdentify(Integer identify) {
        this.identify = identify;
    }

    public String getInvitationCode() {
        return invitationCode;
    }

    public void setInvitationCode(String invitationCode) {
        this.invitationCode = invitationCode;
    }

    public Long getInviterId() {
        return inviterId;
    }

    public void setInviterId(Long inviterId) {
        this.inviterId = inviterId;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public static final String ID = "id";

    public static final String NICKNAME = "nick_name";

    public static final String AVATAR_URL = "avatar_url";

    public static final String PHONE = "phone";

    public static final String ACTIVE_SCORE = "active_score";

    public static final String IDENTIFY = "identify";

    public static final String INVITATION_CODE = "invitation_code";

    public static final String INVITER_ID = "inviter_id";

    public static final String CREATE_TIME = "create_time";

    public static final String ROLE_NAME = "role_name";


    @Override
    public String toString() {
        return "UserEntity{" +
        "id=" + id +
        ", nickName=" + nickName +
        ", avatarUrl=" + avatarUrl +
        ", phone=" + phone +
        ", identify=" + identify +
        ", invitationCode=" + invitationCode +
        ", inviterId=" + inviterId +
        ", createTime=" + createTime +
        "}";
    }
}
