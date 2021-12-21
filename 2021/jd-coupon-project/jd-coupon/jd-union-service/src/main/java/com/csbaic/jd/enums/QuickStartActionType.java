package com.csbaic.jd.enums;

/**
 * 新手上路操作类型
 */
public enum  QuickStartActionType {
    /**
     * 跳转到某一页
     */
    PAGE(1),
    /**
     * 复制数据
     */
    COPY(2),
    /**
     * 显示图片
     */
    SHOW_IMAGE(3),

    /**
     * 分享APP
     */
    SHARE_APP(4);


    private final int type;


    QuickStartActionType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
