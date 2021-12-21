package com.csbaic.jd.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 微信信息表
 * </p>
 *
 * @author yjwfn
 * @since 2020-02-14
 */
@TableName("jd_wechat_user")
public class WechatUserEntity implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 微信号
     */
    private String wechatId;
    /**
     * 会话密钥
     */
    private String sessionKey;

    /**
     * 用户唯一标识
     */
    private String openId;

    /**
     * 用户在开放平台的唯一标识符，在满足 UnionID 下发条件的情况下会返回，详见 UnionID 机制说明。
     */
    private String unionId;

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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getWechatId() {
        return wechatId;
    }

    public void setWechatId(String wechatId) {
        this.wechatId = wechatId;
    }

    public static final String USER_ID = "user_id";

    public static final String WECHAT_ID = "wechat_id";

    public static final String SESSION_KEY = "session_key";

    public static final String OPEN_ID = "open_id";

    public static final String UNION_ID = "union_id";

    public static final String CREATE_TIME = "create_time";

    @Override
    public String toString() {
        return "WechatUserEntity{" +
        "userId=" + userId +
        ", sessionKey=" + sessionKey +
        ", openId=" + openId +
        ", unionId=" + unionId +
        ", createTime=" + createTime +
        "}";
    }
}
