package com.csbaic.jd.enums;

import java.util.Objects;

public enum NewsStatus {
    /**
     * 自动控制由开始时间和结束时间控制
     */
    AUTO(1, "自动"),

    /**
     * 已显示
     */
    VISIBLE(2, "已显示"),

    /**
     * 未显示
     */
    INVISIBLE(3, "未显示");

    private final int status;

    private final String desc;

    NewsStatus(int status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public int getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }


    public static NewsStatus statusOf(Integer status){
        for(NewsStatus newsStatus : values()){
            if(Objects.equals(status, newsStatus.status)){
                return newsStatus;
            }
        }

        return null;
    }
}
