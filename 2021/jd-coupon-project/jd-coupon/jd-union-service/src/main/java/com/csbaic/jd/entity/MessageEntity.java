package com.csbaic.jd.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 新闻
 * </p>
 *
 * @author yjwfn
 * @since 2020-03-26
 */
@TableName("jd_message")
public class MessageEntity implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 消息标题
     */
    private String title;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 消息开始时间
     */
    private LocalDateTime startTime;

    /**
     * 消息结束时间
     */
    private LocalDateTime endTime;

    /**
     * 消息类型
     */
    private Integer type;

    /**
     * 消息状态（1: 自动，2：显示，3：未显示）
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    public static final String ID = "id";

    public static final String TITLE = "title";

    public static final String CONTENT = "content";

    public static final String START_TIME = "start_time";

    public static final String END_TIME = "end_time";

    public static final String TYPE = "type";

    public static final String STATUS = "status";

    public static final String CREATE_TIME = "create_time";

    @Override
    public String toString() {
        return "MessageEntity{" +
        "id=" + id +
        ", title=" + title +
        ", content=" + content +
        ", startTime=" + startTime +
        ", endTime=" + endTime +
        ", type=" + type +
        ", status=" + status +
        ", createTime=" + createTime +
        "}";
    }
}
