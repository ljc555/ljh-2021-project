package com.csbaic.jd.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 分享海报
 * </p>
 *
 * @author yjwfn
 * @since 2020-03-24
 */
@TableName("jd_wechat_group")
public class WechatGroupEntity implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 群组id
     */
    private Integer groupId;

    /**
     * 组名称
     */
    private String groupName;

    /**
     * 归属人id
     */
    private Long ownerId;

    /**
     * 发布时间
     */
    private LocalDateTime createTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public static final String ID = "id";

    public static final String GROUP_ID = "group_id";

    public static final String GROUP_NAME = "group_name";

    public static final String OWNER_ID = "owner_id";

    public static final String CREATE_TIME = "create_time";

    @Override
    public String toString() {
        return "WechatGroupEntity{" +
        "id=" + id +
        ", groupId=" + groupId +
        ", groupName=" + groupName +
        ", ownerId=" + ownerId +
        ", createTime=" + createTime +
        "}";
    }
}
