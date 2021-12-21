package com.csbaic.jd.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 提现信息
 */
@ApiModel
@Data
public class ApplyWithdrawInfo {

    /**
     * 申请提现金额
     */
    private BigDecimal amount;

    /**
     * 收款人微信号
     */
    private String wechatId;

    /**
     * 收款人姓名
     */
    private String payeeName;

    /**
     * 收款人身份证号
     */
    private String payeeCardId;


    /**
     * 短信验证码
     */
    private String code;

}
