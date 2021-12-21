package com.csbaic.jd.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * banner表
 * </p>
 *
 * @author yjwfn
 * @since 2020-02-14
 */
@TableName("jd_banner")
public class BannerEntity implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * banner跳转页面
     */
    private String location;

    /**
     * banner封面图
     */
    private String coverUrl;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 标题
     */
    private String title;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public static final String ID = "id";

    public static final String LOCATION = "location";

    public static final String COVER_URL = "cover_url";

    public static final String END_TIME = "end_time";

    public static final String START_TIME = "start_time";

    public static final String STATUS = "status";

    public static final String TITLE = "title";

    @Override
    public String toString() {
        return "BannerEntity{" +
        "id=" + id +
        ", location=" + location +
        ", coverUrl=" + coverUrl +
        ", endTime=" + endTime +
        ", startTime=" + startTime +
        ", status=" + status +
        ", title=" + title +
        "}";
    }
}
