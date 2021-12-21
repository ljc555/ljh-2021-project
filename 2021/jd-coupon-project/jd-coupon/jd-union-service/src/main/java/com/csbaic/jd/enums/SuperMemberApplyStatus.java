package com.csbaic.jd.enums;


public enum  SuperMemberApplyStatus {
    WAIT_FOR_APPLY(1, "待提交"),
    /**
     * 已提交
     */
    SUBMITTED(2, "已提交"),
    /**
     * 申请单已通过
     */
    APPROVED(3, "已通过"),

    /**
     * 已被驳回
     */
    REJECTED(4, "已拒绝");


    private final int status;

    private final String name;

    SuperMemberApplyStatus(int status, String name) {
        this.status = status;
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public static SuperMemberApplyStatus statusOf(int status){
        for(SuperMemberApplyStatus order : values()){
            if(status == order.getStatus()){
                return order;
            }
        }

        return null;
    }
}
