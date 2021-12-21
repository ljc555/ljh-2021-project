package com.csbaic.jd.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 活动表
 * </p>
 *
 * @author yjwfn
 * @since 2020-03-16
 */
@TableName("jd_activity")
public class ActivityEntity implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 活动标题
     */
    private String title;

    /**
     * 活动分享图
     */
    private String shareImage;

    /**
     * 活动logo图
     */
    private String logoImage;

    /**
     * 活动分享文本
     */
    private String content;

    /**
     * 活动开始时间
     */
    private LocalDateTime startTime;

    /**
     * 活动结束时间
     */
    private LocalDateTime endTime;

    /**
     * 活动状态
     */
    private Integer status;


    /**
     * 创建时间
     */
    private LocalDateTime createTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShareImage() {
        return shareImage;
    }

    public void setShareImage(String shareImage) {
        this.shareImage = shareImage;
    }

    public String getLogoImage() {
        return logoImage;
    }

    public void setLogoImage(String logoImage) {
        this.logoImage = logoImage;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public static final String ID = "id";

    public static final String TITLE = "title";

    public static final String SHARE_IMAGE = "share_image";

    public static final String LOGO_IMAGE = "logo_image";

    public static final String CONTENT = "content";

    public static final String START_TIME = "start_time";

    public static final String END_TIME = "end_time";

    public static final String STATUS = "status";

    public static final String CREATE_TIME = "create_time";

    @Override
    public String toString() {
        return "ActivityEntity{" +
        "id=" + id +
        ", title=" + title +
        ", shareImage=" + shareImage +
        ", logoImage=" + logoImage +
        ", content=" + content +
        ", startTime=" + startTime +
        ", endTime=" + endTime +
        ", createTime=" + createTime +
        "}";
    }
}
