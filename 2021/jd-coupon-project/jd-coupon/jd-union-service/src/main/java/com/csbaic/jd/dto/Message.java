package com.csbaic.jd.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 新闻
 * </p>
 *
 * @author yjwfn
 * @since 2020-03-26
 */
public class Message implements Serializable {

    private static final long serialVersionUID=1L;

    @JsonSerialize(using = ToStringSerializer.class)
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
     * 消息状态（1: 自动，2：显示，3：未显示）
     */
    @ApiModelProperty("消息状态（1: 自动，2：显示，3：未显示）")
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


    @Override
    public String toString() {
        return "MessageEntity{" +
        "id=" + id +
        ", title=" + title +
        ", content=" + content +
        ", startTime=" + startTime +
        ", endTime=" + endTime +
        ", status=" + status +
        ", createTime=" + createTime +
        "}";
    }
}
