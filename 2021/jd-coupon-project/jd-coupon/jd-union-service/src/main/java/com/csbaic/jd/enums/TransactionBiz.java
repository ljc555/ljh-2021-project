package com.csbaic.jd.enums;

public enum TransactionBiz {
    /**
     * -1：未知,
     */
    UNKNOWN(0, "未知"),
    MONTH_BILLING(1, "月度结算"),
    WITHDRAW(1, "提现");

    private final int code;
    private final String remark;


    TransactionBiz(int code, String remark) {
        this.code = code;
        this.remark = remark;
    }

    public int getCode() {
        return code;
    }

    public String getRemark() {
        return remark;
    }
}
