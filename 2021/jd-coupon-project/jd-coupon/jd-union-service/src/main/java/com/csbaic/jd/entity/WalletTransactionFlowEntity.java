package com.csbaic.jd.entity;

import java.math.BigDecimal;
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
 * @since 2020-03-30
 */
@TableName("jd_wallet_transaction_flow")
public class WalletTransactionFlowEntity implements Serializable {

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
     * 费用数量
     */
    private BigDecimal amount;

    /**
     * 交易id
     */
    private Long orderId;

    /**
     * 交易时间
     */
    private LocalDateTime transactionTime;

    /**
     * 交易类型(0 未知，1收入，2支出） 
     */
    private Integer transactionType;

    /**
     * 交易业务类型 
     */
    private Integer transactionBiz;

    /**
     * 流水备注
     */
    private String remark;

    /**
     * 注册时间
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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public LocalDateTime getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(LocalDateTime transactionTime) {
        this.transactionTime = transactionTime;
    }

    public Integer getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(Integer transactionType) {
        this.transactionType = transactionType;
    }

    public Integer getTransactionBiz() {
        return transactionBiz;
    }

    public void setTransactionBiz(Integer transactionBiz) {
        this.transactionBiz = transactionBiz;
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

    public static final String AMOUNT = "amount";

    public static final String ORDER_ID = "order_id";

    public static final String TRANSACTION_TIME = "transaction_time";

    public static final String TRANSACTION_TYPE = "transaction_type";

    public static final String TRANSACTION_BIZ = "transaction_biz";

    public static final String REMARK = "remark";

    public static final String CREATE_TIME = "create_time";

    @Override
    public String toString() {
        return "WalletTransactionFlowEntity{" +
        "id=" + id +
        ", userId=" + userId +
        ", amount=" + amount +
        ", orderId=" + orderId +
        ", transactionTime=" + transactionTime +
        ", transactionType=" + transactionType +
        ", transactionBiz=" + transactionBiz +
        ", remark=" + remark +
        ", createTime=" + createTime +
        "}";
    }
}
