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
 * @since 2020-02-22
 */
@TableName("jd_wallet")
public class WalletEntity implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 钱包余额
     */
    private BigDecimal balance;

    /**
     * 已被冻结的金额
     */
    private BigDecimal balanceFreeze;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 钱包状态
     */
    private Integer status;

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

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getBalanceFreeze() {
        return balanceFreeze;
    }

    public void setBalanceFreeze(BigDecimal balanceFreeze) {
        this.balanceFreeze = balanceFreeze;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public static final String ID = "id";

    public static final String BALANCE = "balance";

    public static final String BALANCE_FREEZE = "balance_freeze";

    public static final String USER_ID = "user_id";

    public static final String STATUS = "status";

    public static final String CREATE_TIME = "create_time";

    @Override
    public String toString() {
        return "WalletEntity{" +
        "id=" + id +
        ", balance=" + balance +
        ", balanceFreeze=" + balanceFreeze +
        ", userId=" + userId +
        ", status=" + status +
        ", createTime=" + createTime +
        "}";
    }
}
