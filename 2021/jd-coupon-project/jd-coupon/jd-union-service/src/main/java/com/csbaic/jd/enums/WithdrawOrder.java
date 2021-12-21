package com.csbaic.jd.enums;

/**
 * 提现状态
 */
public enum WithdrawOrder {
    /**
     * 申请单已提交
     */
    APPLYING(1, "已提交"),
    /**
     * 正在审核中
     */
    UNDER_REVIEW(2, "审核中"),

    /**
     * 提现申请单已通过
     */
    APPROVED(3, "已审核"),

    /**
     * 提现正在打款
     */
    PENDING_PAYMENT(4, "待收款"),

    /**
     * 提现已经支付
     */
    PAID(5, "已支付"),

    /**
     * 已被驳回
     */
    REJECTED(6, "已被驳回"),

    /**
     * 已经被管理员取消
     */
    CANCEL_ADMIN(7, "已取消"),

    /**
     * 已被用户取消
     */
    CANCEL_APPLIER(8, "已取消"),

    /**
     * 已完成
     */
    FINISHED(9, "已完成");

    private final int status;
    private final String text;


    WithdrawOrder(int status, String text) {
        this.status = status;
        this.text = text;
    }

    public int getStatus() {
        return status;
    }

    public String getText() {
        return text;
    }


    public static WithdrawOrder statusOf(int status){
        for(WithdrawOrder order : values()){
            if(status == order.getStatus()){
                return order;
            }
        }

        return null;
    }
}
