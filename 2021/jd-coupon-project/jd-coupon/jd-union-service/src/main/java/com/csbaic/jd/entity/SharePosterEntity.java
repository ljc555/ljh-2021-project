package com.csbaic.jd.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 分享海报
 * </p>
 *
 * @author yjwfn
 * @since 2020-03-23
 */
@TableName("jd_share_poster")
public class SharePosterEntity implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 海报名称
     */
    private String title;

    /**
     * 图标Url
     */
    private String iconUrl;

    /**
     * 分享图
     */
    private String imageUrl;

    /**
     * 海报文字内容
     */
    private String content;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public static final String ID = "id";

    public static final String TITLE = "title";

    public static final String ICON_URL = "icon_url";

    public static final String IMAGE_URL = "image_url";

    public static final String CONTENT = "content";

    public static final String CREATE_TIME = "create_time";

    @Override
    public String toString() {
        return "SharePosterEntity{" +
        "id=" + id +
        ", title=" + title +
        ", iconUrl=" + iconUrl +
        ", imageUrl=" + imageUrl +
        ", content=" + content +
        ", createTime=" + createTime +
        "}";
    }
}
