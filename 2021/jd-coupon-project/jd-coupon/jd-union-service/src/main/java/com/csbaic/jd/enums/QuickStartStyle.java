package com.csbaic.jd.enums;

/**
 * 新手上路操作类型
 */
public enum QuickStartStyle {
    /**
     * 默认显示样式，包含标题、文本、操作等元素
     */
    DEFAULT(1),
    /**
     * 单图显示
     */
    IMAGE_ONLY(2);



    private final int type;


    QuickStartStyle(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
