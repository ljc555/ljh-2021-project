package com.csbaic.jd.entity;

import java.math.BigDecimal;
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
@TableName("jd_apply_withdraw_order")
public class ApplyWithdrawOrderEntity implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 申请人用户id
     */
    private Long applierId;

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
     * 提现单状态
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

    public Long getApplierId() {
        return applierId;
    }

    public void setApplierId(Long applierId) {
        this.applierId = applierId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getWechatId() {
        return wechatId;
    }

    public void setWechatId(String wechatId) {
        this.wechatId = wechatId;
    }

    public String getPayeeName() {
        return payeeName;
    }

    public void setPayeeName(String payeeName) {
        this.payeeName = payeeName;
    }

    public String getPayeeCardId() {
        return payeeCardId;
    }

    public void setPayeeCardId(String payeeCardId) {
        this.payeeCardId = payeeCardId;
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

    public static final String APPLIER_ID = "applier_id";

    public static final String AMOUNT = "amount";

    public static final String WECHAT_ID = "wechat_id";

    public static final String PAYEE_NAME = "payee_name";

    public static final String PAYEE_CARD_ID = "payee_card_id";

    public static final String STATUS = "status";

    public static final String CREATE_TIME = "create_time";

    @Override
    public String toString() {
        return "ApplyWithdrawOrderEntity{" +
        "id=" + id +
        ", applierId=" + applierId +
        ", balance=" + amount +
        ", wechatId=" + wechatId +
        ", payeeName=" + payeeName +
        ", payeeCardId=" + payeeCardId +
        ", status=" + status +
        ", createTime=" + createTime +
        "}";
    }
}
