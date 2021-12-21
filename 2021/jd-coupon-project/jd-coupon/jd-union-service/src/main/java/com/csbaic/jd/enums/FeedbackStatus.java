package com.csbaic.jd.enums;

public enum FeedbackStatus {
    /**
     * 已提交
     */
    SUBMITTED(1),
    /**
     * 正在处理中
     */
    PROCESSING(2),

    /**
     * 已处理
     */
    PROCESSED(3);


    private final int status;

    FeedbackStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
