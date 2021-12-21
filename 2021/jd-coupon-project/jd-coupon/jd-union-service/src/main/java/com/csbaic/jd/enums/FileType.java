package com.csbaic.jd.enums;

import java.util.Objects;

public enum  FileType {

    SUPER_MEMBER_APPLY(1, "超级会员申请审核图片");

    /**
     * 文件类型
     */
    private final int type;

    /**
     * 文件描述
     */
    private final String desc;


    FileType(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public static FileType typeOf(Integer type){

        for(FileType t : values()){
            if(Objects.equals(t.type, type)){
                return t;
            }
        }

        return null;
    }

    public String getDesc() {
        return desc;
    }

    public int getType() {
        return type;
    }
}
