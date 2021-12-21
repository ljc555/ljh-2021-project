package com.csbaic.jd.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 新人上手
 * </p>
 *
 * @author yjwfn
 * @since 2020-04-14
 */
@TableName("jd_member_quick_start")
public class MemberQuickStartEntity implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 记录id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 新人上路标题
     */
    private String title;

    /**
     * 新人上路说明
     */
    private String content;

    /**
     * 视频地址
     */
    private String videoUrl;

    /**
     * 跳转页面、复制、显示图片
     */
    private Integer type;

    /**
     * 显示样式
     */
    private Integer style;

    /**
     * 操作提示
     */
    private String action;

    /**
     * 操作内容
     */
    private String data;

    /**
     * 显示顺序
     */
    private Integer sort;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStyle() {
        return style;
    }

    public void setStyle(Integer style) {
        this.style = style;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public static final String ID = "id";

    public static final String TITLE = "title";

    public static final String CONTENT = "content";

    public static final String VIDEO_URL = "video_url";

    public static final String TYPE = "type";

    public static final String STYLE = "style";

    public static final String ACTION = "action";

    public static final String DATA = "data";

    public static final String SORT = "sort";

    public static final String CREATE_TIME = "create_time";

    @Override
    public String toString() {
        return "MemberQuickStartEntity{" +
        "id=" + id +
        ", title=" + title +
        ", content=" + content +
        ", videoUrl=" + videoUrl +
        ", type=" + type +
        ", style=" + style +
        ", action=" + action +
        ", data=" + data +
        ", sort=" + sort +
        ", createTime=" + createTime +
        "}";
    }
}
