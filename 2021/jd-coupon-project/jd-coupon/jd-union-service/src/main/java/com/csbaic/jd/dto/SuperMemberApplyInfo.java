package com.csbaic.jd.dto;

import lombok.Data;

import java.util.List;

@Data
public class SuperMemberApplyInfo {

    /**
     * 分配的组id
     */
    private Integer groupId;

    /**
     * 分配的组名称
     */
    private String groupName;


    /**
     * 导师微信号
     */
    private String teacherWechatId;

    /**
     * 建群例子
     */
    private List<Example> examples;

    /**
     * 已提交的图片
     */
    private List<String> images;

    /**
     * 要求成员数量
     */
    private Integer memberCount;

    /**
     * 申请状态
     */
    private Integer status;

    /**
     * 描述
     */
    private String statusDesc;

    /**
     * 备注信息
     */
    private String remark;

    @Data
    public static class Example {

        /**
         * 步骤
         */
        private String step;

        /**
         * 图片
         */
        private String imageUrl;

    }

}
