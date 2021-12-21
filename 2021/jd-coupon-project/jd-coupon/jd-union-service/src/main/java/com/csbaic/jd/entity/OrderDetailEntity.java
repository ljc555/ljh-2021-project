package com.csbaic.jd.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 订单记录
 * </p>
 *
 * @author yjwfn
 * @since 2020-03-25
 */
@TableName("jd_order_detail")
public class OrderDetailEntity implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 订单ID
     */
    private Long orderId;

    /**
     * 完成时间
     */
    private LocalDateTime finishTime;

    /**
     * 下单设备(1:PC,2:无线)
     */
    private Integer orderEmt;

    /**
     * 下单时间(时间戳，毫秒)
     */
    private LocalDateTime orderTime;

    /**
     * 推广人id
     */
    private Long ownerId;

    /**
     * 购买人id
     */
    private Long buyerId;

    /**
     * 父单的订单ID，仅当发生订单拆分时返回， 0：未拆分，有值则表示此订单为子订单
     */
    private Long parentId;

    /**
     * 订单维度预估结算时间,不建议使用，可以用订单行sku维度paymonth字段参考，（格式：yyyyMMdd），0：未结算，订单'预估结算时间'仅供参考。账号未通过资质审核或订单发生售后，会影响订单实际结算时间。
     */
    private String payMonth;

    /**
     * 下单用户是否为PLUS会员 0：否，1：是
     */
    private Integer plus;

    /**
     * 订单维度商家ID，不建议使用，可以用订单行sku维度popId参考
     */
    private Long popId;

    /**
     * 推客的联盟ID
     */
    private Long unionId;

    /**
     * 订单维度的推客生成推广链接时传入的扩展字段，不建议使用，可以用订单行sku维度ext1参考,（需要联系运营开放白名单才能拿到数据）
     */
    private String ext1;

    /**
     * 订单维度的有效码，不建议使用，可以用订单行sku维度validCode参考,（-1：未知,2.无效-拆单,3.无效-取消,4.无效-京东帮帮主订单,5.无效-账号异常,6.无效-赠品类目不返佣,7.无效-校园订单,8.无效-企业订单,9.无效-团购订单,10.无效-开增值税专用发票订单,11.无效-乡村推广员下单,12.无效-自己推广自己下单,13.无效-违规订单,14.无效-来源与备案网址不符,15.待付款,16.已付款,17.已完成,18.已结算（5.9号不再支持结算状态回写展示））
     */
    private Integer validCode;

    /**
     * 订单更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 订单创建时间
     */
    private LocalDateTime createTime;


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

    public LocalDateTime getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(LocalDateTime finishTime) {
        this.finishTime = finishTime;
    }

    public Integer getOrderEmt() {
        return orderEmt;
    }

    public void setOrderEmt(Integer orderEmt) {
        this.orderEmt = orderEmt;
    }

    public LocalDateTime getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(LocalDateTime orderTime) {
        this.orderTime = orderTime;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public Long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getPayMonth() {
        return payMonth;
    }

    public void setPayMonth(String payMonth) {
        this.payMonth = payMonth;
    }

    public Integer getPlus() {
        return plus;
    }

    public void setPlus(Integer plus) {
        this.plus = plus;
    }

    public Long getPopId() {
        return popId;
    }

    public void setPopId(Long popId) {
        this.popId = popId;
    }

    public Long getUnionId() {
        return unionId;
    }

    public void setUnionId(Long unionId) {
        this.unionId = unionId;
    }

    public String getExt1() {
        return ext1;
    }

    public void setExt1(String ext1) {
        this.ext1 = ext1;
    }

    public Integer getValidCode() {
        return validCode;
    }

    public void setValidCode(Integer validCode) {
        this.validCode = validCode;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public static final String ID = "id";

    public static final String ORDER_ID = "order_id";

    public static final String FINISH_TIME = "finish_time";

    public static final String ORDER_EMT = "order_emt";

    public static final String ORDER_TIME = "order_time";

    public static final String OWNER_ID = "owner_id";

    public static final String BUYER_ID = "buyer_id";

    public static final String PARENT_ID = "parent_id";

    public static final String PAY_MONTH = "pay_month";

    public static final String PLUS = "plus";

    public static final String POP_ID = "pop_id";

    public static final String UNION_ID = "union_id";

    public static final String EXT1 = "ext1";

    public static final String VALID_CODE = "valid_code";

    public static final String UPDATE_TIME = "update_time";

    public static final String CREATE_TIME = "create_time";

    @Override
    public String toString() {
        return "OrderDetailEntity{" +
        "id=" + id +
        ", orderId=" + orderId +
        ", finishTime=" + finishTime +
        ", orderEmt=" + orderEmt +
        ", orderTime=" + orderTime +
        ", ownerId=" + ownerId +
        ", buyerId=" + buyerId +
        ", parentId=" + parentId +
        ", payMonth=" + payMonth +
        ", plus=" + plus +
        ", popId=" + popId +
        ", unionId=" + unionId +
        ", ext1=" + ext1 +
        ", validCode=" + validCode +
        ", updateTime=" + updateTime +
        ", createTime=" + createTime +
        "}";
    }
}
