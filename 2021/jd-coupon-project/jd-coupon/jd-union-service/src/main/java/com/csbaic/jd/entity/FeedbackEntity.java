package com.csbaic.jd.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author yjwfn
 * @since 2020-03-19
 */
@TableName("jd_feedback")
public class FeedbackEntity implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 提交人
     */
    private Long submitterId;

    /**
     * 選項的值
     */
    private String content;

    /**
     * 反馈状态
     */
    private Integer status;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSubmitterId() {
        return submitterId;
    }

    public void setSubmitterId(Long submitterId) {
        this.submitterId = submitterId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public static final String ID = "id";

    public static final String SUBMITTER_ID = "submitter_id";

    public static final String CONTENT = "content";

    public static final String STATUS = "status";

    @Override
    public String toString() {
        return "FeedbackEntity{" +
        "id=" + id +
        ", submitterId=" + submitterId +
        ", content=" + content +
        ", status=" + status +
        "}";
    }
}
