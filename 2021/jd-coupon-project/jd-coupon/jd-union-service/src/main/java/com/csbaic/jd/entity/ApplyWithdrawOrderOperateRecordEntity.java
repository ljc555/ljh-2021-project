package com.csbaic.jd.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 提现申请单
 * </p>
 *
 * @author yjwfn
 * @since 2020-02-22
 */
@TableName("jd_apply_withdraw_order_operate_record")
public class ApplyWithdrawOrderOperateRecordEntity implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 提现申请单id
     */
    private Long withdrawOrderId;

    /**
     * 操作人id
     */
    private Long operatorId;

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


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWithdrawOrderId() {
        return withdrawOrderId;
    }

    public void setWithdrawOrderId(Long withdrawOrderId) {
        this.withdrawOrderId = withdrawOrderId;
    }

    public Long getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public LocalDateTime getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(LocalDateTime operateTime) {
        this.operateTime = operateTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public static final String ID = "id";

    public static final String WITHDRAW_ORDER_ID = "withdraw_order_id";

    public static final String OPERATOR_ID = "operator_id";

    public static final String OPERATOR_NAME = "operator_name";

    public static final String OPERATE_TIME = "operate_time";

    public static final String REMARK = "remark";

    @Override
    public String toString() {
        return "ApplyWithdrawOrderOperateRecordEntity{" +
        "id=" + id +
        ", withdrawOrderId=" + withdrawOrderId +
        ", operatorId=" + operatorId +
        ", operatorName=" + operatorName +
        ", operateTime=" + operateTime +
        ", remark=" + remark +
        "}";
    }
}
