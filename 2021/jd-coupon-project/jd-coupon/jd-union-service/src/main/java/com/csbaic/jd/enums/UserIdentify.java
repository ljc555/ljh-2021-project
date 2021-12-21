package com.csbaic.jd.enums;


import java.math.BigDecimal;
import java.util.Objects;

public enum  UserIdentify {

    /**
     * 注册会员
     */
    REGISTERED(1, "0.1", "0", "注册会员"),

    /**
     * 超级会员
     */
    SUPER(2, "0.1", "0.7", "超级会员"),

    /**
     * 微信导师
     */
    TEACHER(3, "0.1", "0.8", "导师"),

    /**
     * 合伙人
     */
    PARTNER(4, "0.1", "0.8", "合伙人");


    /**
     * 身份代码
     */
    private final int code;
    /**
     * 返利比例
     */
    private final BigDecimal rebateRate;

    /**
     * 佣金比例
     */
    private final BigDecimal commissionRate;

    /**
     * 身份名称
     */
    private final String name;


    UserIdentify(int code, String rate, String commissionRate, String name) {
        this.code = code;
        this.rebateRate = new  BigDecimal(rate);
        this.commissionRate = new BigDecimal(commissionRate);
        this.name = name;
    }

    public BigDecimal getCommissionRate() {
        return commissionRate;
    }

    public BigDecimal getRebateRate() {
        return rebateRate;
    }

    public String getName() {
        return name;
    }

    public int getCode() {
        return code;
    }

    public static UserIdentify of(Integer code){
        for(UserIdentify identify : values()){
            if(Objects.equals(code,identify.getCode())){
                return identify;
            }
        }

        return null;
    }
}
