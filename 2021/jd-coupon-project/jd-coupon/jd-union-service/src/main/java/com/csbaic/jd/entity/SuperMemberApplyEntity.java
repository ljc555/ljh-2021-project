package com.csbaic.jd.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 超级会员申请表
 * </p>
 *
 * @author yjwfn
 * @since 2020-03-24
 */
@TableName("jd_super_member_apply")
public class SuperMemberApplyEntity implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 申请人
     */
    private Long ownerId;

    /**
     * 群组id
     */
    private Integer groupId;

    /**
     * 申请图
     */
    private String imageUrls;

    /**
     * 申请状态
     */
    private Integer status;

    /**
     * 申请备注
     */
    private String remark;

    /**
     * 申请时间
     */
    private LocalDateTime createTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }


    public String getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(String imageUrls) {
        this.imageUrls = imageUrls;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public static final String ID = "id";

    public static final String OWNER_ID = "owner_id";

    public static final String GROUP_ID = "group_id";

    public static final String IMAGE_URLS = "image_urls";

    public static final String STATUS = "status";



    public static final String CREATE_TIME = "create_time";

    @Override
    public String toString() {
        return "SuperMemberApplyEntity{" +
        "id=" + id +
        ", ownerId=" + ownerId +
        ", groupId=" + groupId +
        ", imageUrl=" + imageUrls +
        ", status=" + status +
        ", createTime=" + createTime +
        "}";
    }
}
