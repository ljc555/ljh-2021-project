package com.csbaic.jd.enums;

import java.util.Objects;

/**
 * sku维度的有效码（
 * -1：未知,
 * 2.无效-拆单,
 * 3.无效-取消,
 * 4.无效-京东帮帮主订单,
 * 5.无效-账号异常,
 * 6.无效-赠品类目不返佣,
 * 7.无效-校园订单,
 * 8.无效-企业订单,
 * 9.无效-团购订单,
 * 10.无效-开增值税专用发票订单,
 * 11.无效-乡村推广员下单,
 * 12.无效-自己推广自己下单,
 * 13.无效-违规订单,
 * 14.无效-来源与备案网址不符,
 * 15.待付款,
 * 16.已付款,
 * 17.已完成,
 * 18.已结算（5.9号不再支持结算状态回写展示））
 * */
public enum OrderValidCode
{
    /**
     * -1：未知,
     */
    UNKNOWN(-1, "未知"),
    /**
     *  2.无效-拆单,
     */
    INVALID_SPLIT(2, "无效-拆单"),
    /**
     * 3.无效-取消,
     */
    INVALID_CANCEL(3, "无效-取消"),
    /**
     * 4.无效-京东帮帮主订单,
     */
    INVALID_JD_MASTER(4, "无效-京东帮帮主订单"),
    /**
     * 5.无效-账号异常,
     */
    INVALID_ACCOUNT_EXCEPTION(5, "无效-账号异常,"),
    /**
     * 6.无效-赠品类目不返佣,
     */
    INVALID_GIFTS_NOT_REBATE(6, "无效-赠品类目不返佣"),
    /**
     * 7.无效-校园订单,
     */
    INVALID_SCHOOL_ORDER(7, "无效-校园订单"),
    /**
     * 8.无效-企业订单,
     */
    INVALID_ENTERPRISE_ORDER(8, "效-企业订单"),
    /**
     * 9.无效-团购订单,
     */
    INVALID_TEAM_ORDER(8, "无效-团购订单"),
    /**
     * 10.无效-开增值税专用发票订单,
     */
    INVALID_INVOICE(10, "无效-开增值税专用发票订单"),
    /**
     * 11.无效-乡村推广员下单,
     */
    INVALID_COUNTRY_PROMOTER(11, "无效-乡村推广员下单"),
    /**
     *  12.无效-自己推广自己下单,
     */
    INVALID_SELF_ORDER(12, "无效-自己推广自己下单"),
    /**
     *  13.无效-违规订单,
     */
    INVALID_ILLEGAL_ORDER(13, "无效-违规订单"),
    /**
     *  14.无效-来源与备案网址不符,
     */
    INVALID_ILLEGAL_SOURCE(14, "无效-来源与备案网址不符"),
    /**
     * 15.待付款,
     */
    WAIT_FOR_PAY(15, "待付款"),
    /**
     *16.已付款,
     */
    PAID(16, "已付款"),
    /**
     * 16.已完成,
     */
    FINISHED(17, "已完成"),
    /**
     * 18.已结算（5.9号不再支持结算状态回写展示））
     */
    SETTLED(18, "已结算") ;

    private final int code;
    private final String desc;

    OrderValidCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static OrderValidCode valueOfCode(Integer code){

        for(OrderValidCode validCode : values()){
            if(Objects.equals(validCode.getCode(), code)){
                return validCode;
            }
        }
        return UNKNOWN;
    }
}
