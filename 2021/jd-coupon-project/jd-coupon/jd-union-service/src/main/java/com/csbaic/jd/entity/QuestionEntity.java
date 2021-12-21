package com.csbaic.jd.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author yjwfn
 * @since 2020-03-20
 */
@TableName("jd_question")
public class QuestionEntity implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 问题分类ID
     */
    private Long cid;

    /**
     * 问题标题
     */
    private String title;

    /**
     * 问题回答
     */
    private String answer;

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

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public static final String ID = "id";

    public static final String CID = "cid";

    public static final String TITLE = "title";

    public static final String ANSWER = "answer";

    public static final String CREATE_TIME = "create_time";

    @Override
    public String toString() {
        return "QuestionEntity{" +
        "id=" + id +
        ", cid=" + cid +
        ", title=" + title +
        ", answer=" + answer +
        ", createTime=" + createTime +
        "}";
    }
}
