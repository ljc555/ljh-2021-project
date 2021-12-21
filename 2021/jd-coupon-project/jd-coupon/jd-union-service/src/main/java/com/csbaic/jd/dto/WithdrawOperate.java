package com.csbaic.jd.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 提现申请单
 * </p>
 *
 * @author yjwfn
 * @since 2020-02-22
 */
@Data
public class WithdrawOperate implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 操作人名称
     */
    private String operatorName;

    /**
     * 操作时间
     */
    private LocalDateTime operateTime;

    /**
     * 备注
     */
    private String remark;


}
