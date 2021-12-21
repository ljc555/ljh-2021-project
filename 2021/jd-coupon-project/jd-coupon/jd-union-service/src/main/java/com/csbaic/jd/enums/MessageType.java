package com.csbaic.jd.enums;

import java.util.Objects;

public enum MessageType {

    /**
     * 促销活动
     */
    PROMOTION(1, "促销活动"),

    /**
     * 系统消息
     */
    SYS(2, "系统消息");

    private final int type;
    private final String desc;

    MessageType(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public int getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    public static MessageType statusOf(Integer status){
        for(MessageType newsStatus : values()){
            if(Objects.equals(status, newsStatus.type)){
                return newsStatus;
            }
        }

        return null;
    }
}
