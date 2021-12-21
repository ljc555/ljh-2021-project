package com.csbaic.jd.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 提现信息
 */
@Data
public class WithdrawOrderInfo {


    /**
     * 提现单id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 收款人微信号
     */
    private String wechatId;


    /**
     * 申请提现金额
     */
    private BigDecimal amount;

    /**
     * 收款人姓名
     */
    private String payeeName;


    /**
     * 提现单状态
     */
    private Integer status;

    /**
     * 提现单状态文本
     */
    private String statusDesc;


    /**
     * 提交时间
     */
    private LocalDateTime submitTime;

    /**
     * 提现操作记录
     */
    private List<WithdrawOperate> operations;

}
