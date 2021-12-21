package com.csbaic.jd.enums;

public enum FeeType {
    /**
     * 推广人
     */
    COMMISSION(1),
    /**
     * 返利收益
     */
    REBATE(2),
    /**
     * 平台奖励
     */
    AWARD(3);


    private final int type;


    FeeType(int type) {
        this.type = type;
    }


    public int getType() {
        return type;
    }
}
