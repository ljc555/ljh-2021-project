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
 * @since 2020-03-25
 */
@TableName("jd_order_sku")
public class OrderSkuEntity implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 商品ID
     */
    private Long skuId;

    /**
     * 京东订单id
     */
    private Long orderId;

    /**
     * 推广人id
     */
    private Long ownerId;

    /**
     * 购买人id
     */
    private Long buyerId;

    /**
     * 实际计算佣金的金额。订单完成后，会将误扣除的运费券金额更正。如订单完成后发生退款，此金额会更新。
     */
    private BigDecimal actualCosPrice;

    /**
     * 	推客获得的实际佣金（实际计佣金额*佣金比例*最终比例）。如订单完成后发生退款，此金额会更新。
     */
    private BigDecimal actualFee;

    /**
     * 佣金比例
     */
    private BigDecimal commissionRate;

    /**
     * 预估计佣金额，即用户下单的金额(已扣除优惠券、白条、支付优惠、进口税，未扣除红包和京豆)，有时会误扣除运费券金额，完成结算时会在实际计佣金额中更正。如订单完成前发生退款，此金额也会更新。
     */
    private BigDecimal estimateCosPrice;

    /**
     * 推客的预估佣金（预估计佣金额*佣金比例*最终比例），如订单完成前发生退款，此金额也会更新。
     */
    private BigDecimal estimateFee;

    /**
     * 最终比例（分成比例+补贴比例）
     */
    private BigDecimal finalRate;

    /**
     * 一级类目ID
     */
    private Long cid1;

    /**
     * 商品售后中数量
     */
    private Long frozenSkuNnum;

    /**
     * 联盟子站长身份标识，格式：子站长ID_子站长网站ID_子站长推广位ID
     */
    private String pid;

    /**
     * 推广位ID,0代表无推广位
     */
    private Long positionId;

    /**
     * 网站ID，0：无网站
     */
    private Long siteId;

    /**
     * 商品名称
     */
    private String skuName;

    /**
     * 商品数量
     */
    private Long skuNum;

    /**
     * 商品主图
     */
    private String skuImage;

    /**
     * 商品已退货数量
     */
    private Long skuReturnNum;

    /**
     * 分成比例
     */
    private BigDecimal subSideRate;

    /**
     * 补贴比例
     */
    private BigDecimal subsidyRate;

    /**
     * 三级类目ID
     */
    private Long cid3;

    /**
     * 	PID所属母账号平台名称（原第三方服务商来源）
     */
    private String unionAlias;

    /**
     * 联盟标签数据（整型的二进制字符串，目前返回16位：0000000000000001。数据从右向左进行，每一位为1表示符合联盟的标签特征，第1位：红包，第2位：组合推广，第3位：拼购，第5位：有效首次购（0000000000011XXX表示有效首购，最终奖励活动结算金额会结合订单状态判断，以联盟后台对应活动效果数据报表https://union.jd.com/active为准）,第8位：复购订单，第9位：礼金，第10位：联盟礼金，第11位：推客礼金。例如：0000000000000001:红包订单，0000000000000010:组合推广订单，0000000000000100:拼购订单，0000000000011000:有效首购，0000000000000111：红包+组合推广+拼购等）
     */
    private String unionTag;

    /**
     * 渠道组 1：1号店，其他：京东
     */
    private Integer unionTrafficGroup;

    /**
     * sku维度的有效码（-1：未知,2.无效-拆单,3.无效-取消,4.无效-京东帮帮主订单,5.无效-账号异常,6.无效-赠品类目不返佣,7.无效-校园订单,8.无效-企业订单,9.无效-团购订单,10.无效-开增值税专用发票订单,11.无效-乡村推广员下单,12.无效-自己推广自己下单,13.无效-违规订单,14.无效-来源与备案网址不符,15.待付款,16.已付款,17.已完成,18.已结算（5.9号不再支持结算状态回写展示））
     */
    private Integer validCode;

    /**
     * 子联盟ID(需要联系运营开放白名单才能拿到数据)
     */
    private String subUnionId;

    /**
     * 2：同店；3：跨店
     */
    private Integer traceType;

    /**
     * 订单行维度预估结算时间（格式：yyyyMMdd） ，0：未结算。订单'预估结算时间'仅供参考。账号未通过资质审核或订单发生售后，会影响订单实际结算时间。
     */
    private Integer payMonth;

    /**
     * 	商家ID。'订单行维度'
     */
    private Long popId;

    /**
     * 	推客生成推广链接时传入的扩展字段（需要联系运营开放白名单才能拿到数据）。'订单行维度'
     */
    private String ext1;

    /**
     * 招商团活动id，正整数，为0时表示无活动
     */
    private Long cpActId;

    /**
     * 站长角色，1： 推客、 2： 团长
     */
    private Integer unionRole;

    /**
     * 礼金批次ID
     */
    private String giftCouponKey;

    /**
     * 礼金分摊金额
     */
    private BigDecimal giftCouponOcsAmount;

    /**
     * sku更新时间
     */
    private LocalDateTime updateTime;

    /**
     * sdu创建时间
     */
    private LocalDateTime createTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public BigDecimal getActualCosPrice() {
        return actualCosPrice;
    }

    public void setActualCosPrice(BigDecimal actualCosPrice) {
        this.actualCosPrice = actualCosPrice;
    }

    public BigDecimal getActualFee() {
        return actualFee;
    }

    public void setActualFee(BigDecimal actualFee) {
        this.actualFee = actualFee;
    }

    public BigDecimal getCommissionRate() {
        return commissionRate;
    }

    public void setCommissionRate(BigDecimal commissionRate) {
        this.commissionRate = commissionRate;
    }

    public BigDecimal getEstimateCosPrice() {
        return estimateCosPrice;
    }

    public void setEstimateCosPrice(BigDecimal estimateCosPrice) {
        this.estimateCosPrice = estimateCosPrice;
    }

    public BigDecimal getEstimateFee() {
        return estimateFee;
    }

    public void setEstimateFee(BigDecimal estimateFee) {
        this.estimateFee = estimateFee;
    }

    public BigDecimal getFinalRate() {
        return finalRate;
    }

    public void setFinalRate(BigDecimal finalRate) {
        this.finalRate = finalRate;
    }

    public Long getCid1() {
        return cid1;
    }

    public void setCid1(Long cid1) {
        this.cid1 = cid1;
    }

    public Long getFrozenSkuNnum() {
        return frozenSkuNnum;
    }

    public void setFrozenSkuNnum(Long frozenSkuNnum) {
        this.frozenSkuNnum = frozenSkuNnum;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public Long getPositionId() {
        return positionId;
    }

    public void setPositionId(Long positionId) {
        this.positionId = positionId;
    }

    public Long getSiteId() {
        return siteId;
    }

    public void setSiteId(Long siteId) {
        this.siteId = siteId;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public Long getSkuNum() {
        return skuNum;
    }

    public void setSkuNum(Long skuNum) {
        this.skuNum = skuNum;
    }

    public String getSkuImage() {
        return skuImage;
    }

    public void setSkuImage(String skuImage) {
        this.skuImage = skuImage;
    }

    public Long getSkuReturnNum() {
        return skuReturnNum;
    }

    public void setSkuReturnNum(Long skuReturnNum) {
        this.skuReturnNum = skuReturnNum;
    }

    public BigDecimal getSubSideRate() {
        return subSideRate;
    }

    public void setSubSideRate(BigDecimal subSideRate) {
        this.subSideRate = subSideRate;
    }

    public BigDecimal getSubsidyRate() {
        return subsidyRate;
    }

    public void setSubsidyRate(BigDecimal subsidyRate) {
        this.subsidyRate = subsidyRate;
    }

    public Long getCid3() {
        return cid3;
    }

    public void setCid3(Long cid3) {
        this.cid3 = cid3;
    }

    public String getUnionAlias() {
        return unionAlias;
    }

    public void setUnionAlias(String unionAlias) {
        this.unionAlias = unionAlias;
    }

    public String getUnionTag() {
        return unionTag;
    }

    public void setUnionTag(String unionTag) {
        this.unionTag = unionTag;
    }

    public Integer getUnionTrafficGroup() {
        return unionTrafficGroup;
    }

    public void setUnionTrafficGroup(Integer unionTrafficGroup) {
        this.unionTrafficGroup = unionTrafficGroup;
    }

    public Integer getValidCode() {
        return validCode;
    }

    public void setValidCode(Integer validCode) {
        this.validCode = validCode;
    }

    public String getSubUnionId() {
        return subUnionId;
    }

    public void setSubUnionId(String subUnionId) {
        this.subUnionId = subUnionId;
    }

    public Integer getTraceType() {
        return traceType;
    }

    public void setTraceType(Integer traceType) {
        this.traceType = traceType;
    }

    public Integer getPayMonth() {
        return payMonth;
    }

    public void setPayMonth(Integer payMonth) {
        this.payMonth = payMonth;
    }

    public Long getPopId() {
        return popId;
    }

    public void setPopId(Long popId) {
        this.popId = popId;
    }

    public String getExt1() {
        return ext1;
    }

    public void setExt1(String ext1) {
        this.ext1 = ext1;
    }

    public Long getCpActId() {
        return cpActId;
    }

    public void setCpActId(Long cpActId) {
        this.cpActId = cpActId;
    }

    public Integer getUnionRole() {
        return unionRole;
    }

    public void setUnionRole(Integer unionRole) {
        this.unionRole = unionRole;
    }

    public String getGiftCouponKey() {
        return giftCouponKey;
    }

    public void setGiftCouponKey(String giftCouponKey) {
        this.giftCouponKey = giftCouponKey;
    }

    public BigDecimal getGiftCouponOcsAmount() {
        return giftCouponOcsAmount;
    }

    public void setGiftCouponOcsAmount(BigDecimal giftCouponOcsAmount) {
        this.giftCouponOcsAmount = giftCouponOcsAmount;
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

    public static final String SKU_ID = "sku_id";

    public static final String ORDER_ID = "order_id";

    public static final String OWNER_ID = "owner_id";

    public static final String BUYER_ID = "buyer_id";

    public static final String ACTUAL_COS_PRICE = "actual_cos_price";

    public static final String ACTUAL_FEE = "actual_fee";

    public static final String COMMISSION_RATE = "commission_rate";

    public static final String ESTIMATE_COS_PRICE = "estimate_cos_price";

    public static final String ESTIMATE_FEE = "estimate_fee";

    public static final String FINAL_RATE = "final_rate";

    public static final String CID1 = "cid1";

    public static final String FROZEN_SKU_NNUM = "frozen_sku_nnum";

    public static final String PID = "pid";

    public static final String POSITION_ID = "position_id";

    public static final String SITE_ID = "site_id";

    public static final String SKU_NAME = "sku_name";

    public static final String SKU_NUM = "sku_num";

    public static final String SKU_IMAGE = "sku_image";

    public static final String SKU_RETURN_NUM = "sku_return_num";

    public static final String SUB_SIDE_RATE = "sub_side_rate";

    public static final String SUBSIDY_RATE = "subsidy_rate";

    public static final String CID3 = "cid3";

    public static final String UNION_ALIAS = "union_alias";

    public static final String UNION_TAG = "union_tag";

    public static final String UNION_TRAFFIC_GROUP = "union_traffic_group";

    public static final String VALID_CODE = "valid_code";

    public static final String SUB_UNIONID = "sub_unionId";

    public static final String TRACE_TYPE = "trace_type";

    public static final String PAY_MONTH = "pay_month";

    public static final String POP_ID = "pop_id";

    public static final String EXT1 = "ext1";

    public static final String CP_ACT_ID = "cp_act_id";

    public static final String UNION_ROLE = "union_role";

    public static final String GIFT_COUPON_KEY = "gift_coupon_key";

    public static final String GIFT_COUPON_OCS_AMOUNT = "gift_coupon_ocs_amount";

    public static final String UPDATE_TIME = "update_time";

    public static final String CREATE_TIME = "create_time";

    @Override
    public String toString() {
        return "OrderSkuEntity{" +
        "id=" + id +
        ", skuId=" + skuId +
        ", orderId=" + orderId +
        ", ownerId=" + ownerId +
        ", buyerId=" + buyerId +
        ", actualCosPrice=" + actualCosPrice +
        ", actualFee=" + actualFee +
        ", commissionRate=" + commissionRate +
        ", estimateCosPrice=" + estimateCosPrice +
        ", estimateFee=" + estimateFee +
        ", finalRate=" + finalRate +
        ", cid1=" + cid1 +
        ", frozenSkuNnum=" + frozenSkuNnum +
        ", pid=" + pid +
        ", positionId=" + positionId +
        ", siteId=" + siteId +
        ", skuName=" + skuName +
        ", skuNum=" + skuNum +
        ", skuImage=" + skuImage +
        ", skuReturnNum=" + skuReturnNum +
        ", subSideRate=" + subSideRate +
        ", subsidyRate=" + subsidyRate +
        ", cid3=" + cid3 +
        ", unionAlias=" + unionAlias +
        ", unionTag=" + unionTag +
        ", unionTrafficGroup=" + unionTrafficGroup +
        ", validCode=" + validCode +
        ", subUnionId=" + subUnionId +
        ", traceType=" + traceType +
        ", payMonth=" + payMonth +
        ", popId=" + popId +
        ", ext1=" + ext1 +
        ", cpActId=" + cpActId +
        ", unionRole=" + unionRole +
        ", giftCouponKey=" + giftCouponKey +
        ", giftCouponOcsAmount=" + giftCouponOcsAmount +
        ", updateTime=" + updateTime +
        ", createTime=" + createTime +
        "}";
    }
}
