package com.csbaic.jd.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 佣金商品同步记录
 * </p>
 *
 * @author yjwfn
 * @since 2020-02-20
 */
@TableName("jd_order_sync_record")
public class OrderSyncRecordEntity implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 记录id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 京东订单id
     */
    private Long orderId;

    /**
     * 是否成功（0成功，1失败）
     */
    private Integer status;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public static final String ID = "id";

    public static final String ORDER_ID = "order_id";

    public static final String STATUS = "status";

    @Override
    public String toString() {
        return "OrderSyncRecordEntity{" +
        "id=" + id +
        ", orderId=" + orderId +
        ", status=" + status +
        "}";
    }
}
