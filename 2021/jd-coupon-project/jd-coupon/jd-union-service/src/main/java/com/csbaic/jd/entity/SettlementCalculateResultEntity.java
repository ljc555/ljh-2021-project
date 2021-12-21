package com.csbaic.jd.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 用户账单计算状态记录
 * </p>
 *
 * @author yjwfn
 * @since 2020-03-16
 */
@TableName("jd_settlement_calculate_result")
public class SettlementCalculateResultEntity implements Serializable {

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
     * 开始时间
     */
    private LocalDate startDate;

    /**
     * 开始时间
     */
    private LocalDate endDate;

    /**
     * 返利
     */
    private BigDecimal rebateFee;

    /**
     * 奖励
     */
    private BigDecimal awardFee;

    /**
     * 佣金
     */
    private BigDecimal commissionFee;

    /**
     * 成员状态(0成功，1失败)
     */
    private Integer status;

    /**
     * 记录时间
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

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getRebateFee() {
        return rebateFee;
    }

    public void setRebateFee(BigDecimal rebateFee) {
        this.rebateFee = rebateFee;
    }

    public BigDecimal getAwardFee() {
        return awardFee;
    }

    public void setAwardFee(BigDecimal awardFee) {
        this.awardFee = awardFee;
    }

    public BigDecimal getCommissionFee() {
        return commissionFee;
    }

    public void setCommissionFee(BigDecimal commissionFee) {
        this.commissionFee = commissionFee;
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

    public static final String USER_ID = "user_id";

    public static final String START_DATE = "start_date";

    public static final String END_DATE = "end_date";

    public static final String REBATE_FEE = "rebate_fee";

    public static final String AWARD_FEE = "award_fee";

    public static final String COMMISSION_FEE = "commission_fee";

    public static final String STATUS = "status";

    public static final String CREATE_TIME = "create_time";

    @Override
    public String toString() {
        return "SettlementCalculateResultEntity{" +
        "id=" + id +
        ", userId=" + userId +
        ", startDate=" + startDate +
        ", endDate=" + endDate +
        ", rebateFee=" + rebateFee +
        ", awardFee=" + awardFee +
        ", commissionFee=" + commissionFee +
        ", status=" + status +
        ", createTime=" + createTime +
        "}";
    }
}
