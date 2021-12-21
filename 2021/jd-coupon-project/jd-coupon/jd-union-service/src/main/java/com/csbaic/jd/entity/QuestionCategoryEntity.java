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
@TableName("jd_question_category")
public class QuestionCategoryEntity implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 分类名称
     */
    private String name;

    /**
     * 小图标
     */
    private String icon;

    /**
     * 问分组是否展开
     */
    private Boolean open;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }


    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }

    public static final String ID = "id";

    public static final String NAME = "name";

    public static final String ICON = "icon";

    public static final String CREATE_TIME = "create_time";

    public static final String OPEN = "open";

    @Override
    public String toString() {
        return "QuestionCategoryEntity{" +
        "id=" + id +
        ", name=" + name +
        ", icon=" + icon +
        ", createTime=" + createTime +
        "}";
    }
}
