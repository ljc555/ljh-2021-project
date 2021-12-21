package com.csbaic.jd.enums;

import java.math.BigDecimal;

/***
 * 用户等级
 */
public enum  UserGrade {

    /**
     * 大众会员
     */
    DEFAULT(new BigDecimal("0")),

    /**
     * 黄金会员
     */
    GOLD(new BigDecimal("0.5")),

    /***
     * 铂金会员
     */
    PLATINUM(new BigDecimal("0.6")),

    /**
     * 钻石会员
     */
    DIAMOND(new BigDecimal("0.7"));

    /**
     * 佣金比率
     */
    private BigDecimal ratio;



    UserGrade(BigDecimal ratio) {
        this.ratio = ratio;
    }

    /**
     * 佣金比率
     * @return
     */
    public BigDecimal getRatio() {
        return ratio;
    }
}
