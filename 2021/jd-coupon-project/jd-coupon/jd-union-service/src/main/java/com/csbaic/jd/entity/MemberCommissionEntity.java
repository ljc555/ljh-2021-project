package com.csbaic.jd.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 佣金商品记录
 * </p>
 *
 * @author yjwfn
 * @since 2020-03-06
 */
@TableName("jd_member_commission")
public class MemberCommissionEntity implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 记录id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户等级
     */
    private Integer identify;

    /**
     * 记录深度
     */
    private Integer level;

    /**
     * 批次Id
     */
    private Long batchId;

    /**
     * 商品ID
     */
    private Long skuId;

    /**
     * 京东订单id
     */
    private Long orderId;

    /**
     * 完成时间
     */
    private LocalDateTime finishTime;

    /**
     * 下单时间(时间戳，毫秒)
     */
    private LocalDateTime orderTime;

    /**
     * 预估返利（卖货）
     */
    private BigDecimal estimateRebateFee;

    /**
     * 实际返利（卖货）
     */
    private BigDecimal actualRebateFee;

    /**
     * 预估奖励
     */
    private BigDecimal estimateAwardFee;

    /**
     * 实际奖励
     */
    private BigDecimal actualAwardFee;

    /**
     * 预估佣金
     */
    private BigDecimal estimateCommissionFee;

    /**
     * 实际佣金
     */
    private BigDecimal actualCommissionFee;

    /**
     * 收益类型
     */
    private Integer feeType;

    /**
     * 商品名称
     */
    private String skuName;

    /**
     * sku维度的有效码（-1：未知,2.无效-拆单,3.无效-取消,4.无效-京东帮帮主订单,5.无效-账号异常,6.无效-赠品类目不返佣,7.无效-校园订单,8.无效-企业订单,9.无效-团购订单,10.无效-开增值税专用发票订单,11.无效-乡村推广员下单,12.无效-自己推广自己下单,13.无效-违规订单,14.无效-来源与备案网址不符,15.待付款,16.已付款,17.已完成,18.已结算（5.9号不再支持结算状态回写展示））
     */
    private Integer validCode;

    /**
     * 佣金备注
     */
    private String remark;

    /**
     * 记录创建时间
     */
    private LocalDateTime createTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getIdentify() {
        return identify;
    }

    public void setIdentify(Integer identify) {
        this.identify = identify;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Long getBatchId() {
        return batchId;
    }

    public void setBatchId(Long batchId) {
        this.batchId = batchId;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public LocalDateTime getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(LocalDateTime finishTime) {
        this.finishTime = finishTime;
    }

    public LocalDateTime getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(LocalDateTime orderTime) {
        this.orderTime = orderTime;
    }

    public BigDecimal getEstimateRebateFee() {
        return estimateRebateFee;
    }

    public void setEstimateRebateFee(BigDecimal estimateRebateFee) {
        this.estimateRebateFee = estimateRebateFee;
    }

    public BigDecimal getActualRebateFee() {
        return actualRebateFee;
    }

    public void setActualRebateFee(BigDecimal actualRebateFee) {
        this.actualRebateFee = actualRebateFee;
    }

    public BigDecimal getEstimateAwardFee() {
        return estimateAwardFee;
    }

    public void setEstimateAwardFee(BigDecimal estimateAwardFee) {
        this.estimateAwardFee = estimateAwardFee;
    }

    public BigDecimal getActualAwardFee() {
        return actualAwardFee;
    }

    public void setActualAwardFee(BigDecimal actualAwardFee) {
        this.actualAwardFee = actualAwardFee;
    }

    public BigDecimal getEstimateCommissionFee() {
        return estimateCommissionFee;
    }

    public void setEstimateCommissionFee(BigDecimal estimateCommissionFee) {
        this.estimateCommissionFee = estimateCommissionFee;
    }

    public BigDecimal getActualCommissionFee() {
        return actualCommissionFee;
    }

    public void setActualCommissionFee(BigDecimal actualCommissionFee) {
        this.actualCommissionFee = actualCommissionFee;
    }

    public Integer getFeeType() {
        return feeType;
    }

    public void setFeeType(Integer feeType) {
        this.feeType = feeType;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public Integer getValidCode() {
        return validCode;
    }

    public void setValidCode(Integer validCode) {
        this.validCode = validCode;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public static final String ID = "id";

    public static final String USER_ID = "user_id";

    public static final String IDENTIFY = "identify";

    public static final String LEVEL = "level";

    public static final String BATCH_ID = "batch_id";

    public static final String SKU_ID = "sku_id";

    public static final String ORDER_ID = "order_id";

    public static final String FINISH_TIME = "finish_time";

    public static final String ORDER_TIME = "order_time";

    public static final String ESTIMATE_REBATE_FEE = "estimate_rebate_fee";

    public static final String ACTUAL_REBATE_FEE = "actual_rebate_fee";

    public static final String ESTIMATE_AWARD_FEE = "estimate_award_fee";

    public static final String ACTUAL_AWARD_FEE = "actual_award_fee";

    public static final String ESTIMATE_COMMISSION_FEE = "estimate_commission_fee";

    public static final String ACTUAL_COMMISSION_FEE = "actual_commission_fee";

    public static final String FEE_TYPE = "fee_type";

    public static final String SKU_NAME = "sku_name";

    public static final String VALID_CODE = "valid_code";

    public static final String REMARK = "remark";

    public static final String CREATE_TIME = "create_time";

    @Override
    public String toString() {
        return "MemberCommissionEntity{" +
        "id=" + id +
        ", userId=" + userId +
        ", identify=" + identify +
        ", level=" + level +
        ", batchId=" + batchId +
        ", skuId=" + skuId +
        ", orderId=" + orderId +
        ", finishTime=" + finishTime +
        ", orderTime=" + orderTime +
        ", estimateRebateFee=" + estimateRebateFee +
        ", actualRebateFee=" + actualRebateFee +
        ", estimateAwardFee=" + estimateAwardFee +
        ", actualAwardFee=" + actualAwardFee +
        ", estimateCommissionFee=" + estimateCommissionFee +
        ", actualCommissionFee=" + actualCommissionFee +
        ", fee_type=" + feeType +
        ", skuName=" + skuName +
        ", validCode=" + validCode +
        ", remark=" + remark +
        ", createTime=" + createTime +
        "}";
    }
}
