package com.csbaic.jd.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 发圈必备
 * </p>
 *
 * @author yjwfn
 * @since 2020-02-14
 */
@TableName("jd_timeline_goods")
public class TimelineGoodsEntity implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 商品名称
     */
    private String title;

    /**
     * 商品id
     */
    private Long skuId;

    /**
     * 发布人id
     */
    private Long publisherId;

    /**
     * 分享图
     */
    private String imageUrl;

    /**
     * 发圈内容
     */
    private String content;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public Long getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(Long publisherId) {
        this.publisherId = publisherId;
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

    public static final String SKU_ID = "sku_id";

    public static final String PUBLISHER_ID = "publisher_id";

    public static final String IMAGE_URL = "image_url";

    public static final String CONTENT = "content";

    public static final String CREATE_TIME = "create_time";

    @Override
    public String toString() {
        return "TimelineGoodsEntity{" +
        "id=" + id +
        ", title=" + title +
        ", skuId=" + skuId +
        ", publisherId=" + publisherId +
        ", imageUrl=" + imageUrl +
        ", content=" + content +
        ", createTime=" + createTime +
        "}";
    }
}
