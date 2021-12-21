package com.csbaic.jd.enums;

public enum  OrderStatus {
    /**
     * 未知,
     */
    UNKNOWN(0, "未知"),
    /**
     * 待付款,
     */
    WAIT_FOR_PAY(2, "待付款"),

    /**
     *已付款,
     */
    PAID(3, "已付款"),
    /**
     * 已完成,
     */
    FINISHED(4, "已完成"),
    /**
     * 无效订单（不计佣金）,
     */
    INVALID_ORDER(5, "无效"),
    /**
     * 已取消
     */
    CANCELED(6, "已取消");

    private final int code;
    private final String desc;


    OrderStatus(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static OrderStatus statusOf(int code){
        for(OrderStatus status : OrderStatus.values()){
            if(status.code == code){
                return status;
            }
        }

        return null;
    }
}
