package com.csbaic.jd.service.order;


import lombok.Data;

@Data
public class SubUnionId {

    /**
     * 生成推广链接归属人推广码（订单归属人）
     */
    private  long owner;

    /**
     * 购买人
     */
    private  long buyer;


    /**
     * 推广人链接身份
     */
    private int identifyOfOwner;

    /**
     * 购买人身份
     */
    private int identifyOfBuyer;


    /**
     * 是否有返利
     */
    private boolean rebate;

}
