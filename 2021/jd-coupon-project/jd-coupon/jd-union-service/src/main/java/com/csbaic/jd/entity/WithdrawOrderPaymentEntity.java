package com.csbaic.jd.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 提现支付操作记录
 * </p>
 *
 * @author yjwfn
 * @since 2020-03-30
 */
@TableName("jd_withdraw_order_payment")
public class WithdrawOrderPaymentEntity implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 商户订单号，需保持历史全局唯一性(只能是字母或者数字，不能包含有其它字符)
     */
    private String partnerTradeNo;

    /**
     * 企业付款成功，返回的微信付款单号
     */
    private String paymentNo;

    /**
     * 企业付款成功时间
     */
    private String paymentTime;

    /**
     * 支付金额（单位：分）
     */
    private Integer amount;

    /**
     * 微信支付分配的终端设备号
     */
    private String deviceInfo;



    /**
     * 支付备注
     */
    private String paymentDesc;

    /**
     * SUCCESS/FAIL，注意：当状态为FAIL时，存在业务结果未明确的情况。如果状态为FAIL，请务必关注错误代码（err_code字段），通过查询查询接口确认此次付款的结果。
     */
    private String resultCode;

    /**
     * 错误码信息，注意：出现未明确的错误码时（SYSTEMERROR等），请务必用原商户订单号重试，或通过查询接口确认此次付款的结果。
     */
    private String errCode;

    /**
     * 结果信息描述
     */
    private String errCodeDes;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPartnerTradeNo() {
        return partnerTradeNo;
    }

    public void setPartnerTradeNo(String partnerTradeNo) {
        this.partnerTradeNo = partnerTradeNo;
    }

    public String getPaymentNo() {
        return paymentNo;
    }

    public void setPaymentNo(String paymentNo) {
        this.paymentNo = paymentNo;
    }

    public String getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(String paymentTime) {
        this.paymentTime = paymentTime;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
    }


    public String getPaymentDesc() {
        return paymentDesc;
    }

    public void setPaymentDesc(String paymentDesc) {
        this.paymentDesc = paymentDesc;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrCodeDes() {
        return errCodeDes;
    }

    public void setErrCodeDes(String errCodeDes) {
        this.errCodeDes = errCodeDes;
    }

    public static final String ID = "id";

    public static final String PARTNER_TRADE_NO = "partner_trade_no";

    public static final String PAYMENT_NO = "payment_no";

    public static final String PAYMENT_TIME = "payment_time";

    public static final String AMOUNT = "amount";

    public static final String DEVICE_INFO = "device_info";

    public static final String NONCE_STR = "nonce_str";

    public static final String PAYMENT_DESC = "payment_desc";

    public static final String RESULT_CODE = "result_code";

    public static final String ERR_CODE = "err_code";

    public static final String ERR_CODE_DES = "err_code_des";

    @Override
    public String toString() {
        return "WithdrawOrderPaymentEntity{" +
        "id=" + id +
        ", partnerTradeNo=" + partnerTradeNo +
        ", paymentNo=" + paymentNo +
        ", paymentTime=" + paymentTime +
        ", amount=" + amount +
        ", deviceInfo=" + deviceInfo +
        ", paymentDesc=" + paymentDesc +
        ", resultCode=" + resultCode +
        ", errCode=" + errCode +
        ", errCodeDes=" + errCodeDes +
        "}";
    }
}
