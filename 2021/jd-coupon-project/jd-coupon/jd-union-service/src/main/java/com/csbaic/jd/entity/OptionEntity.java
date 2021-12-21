package com.csbaic.jd.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author yjwfn
 * @since 2020-03-11
 */
@TableName("jd_option")
public class OptionEntity implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 選項key
     */
    private String optionName;

    /**
     * 選項的值
     */
    private String optionValue;

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

    public String getOptionName() {
        return optionName;
    }

    public void setOptionName(String optionName) {
        this.optionName = optionName;
    }

    public String getOptionValue() {
        return optionValue;
    }

    public void setOptionValue(String optionValue) {
        this.optionValue = optionValue;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public static final String ID = "id";

    public static final String OPTION_NAME = "option_name";

    public static final String OPTION_VALUE = "option_value";

    public static final String CREATE_TIME = "create_time";

    @Override
    public String toString() {
        return "OptionEntity{" +
        "id=" + id +
        ", optionName=" + optionName +
        ", optionValue=" + optionValue +
        ", createTime=" + createTime +
        "}";
    }
}
