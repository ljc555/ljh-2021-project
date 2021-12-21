package com.csbaic.jd.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@ApiModel("等级会员信息")
@Data
public class GradeMemberInfo {


    /**
     * 用户信息
     */
    @ApiModelProperty("用户信息")
    private SimpleMemberUserInfo userInfo;

    /**
     * 导师用户信息
     */
    @ApiModelProperty("导师用户信息")
    private SimpleMemberUserInfo teacherInfo;

    /**
     * 数据项
     */
    @ApiModelProperty("数据项")
    List<MemberInfoBlock> blocks ;


    @Data
    public static class MemberInfoBlock {

        /**
         * 标题
         */
        private String title;

        /**
         * 是否可以隐藏
         */
        private Boolean hideable;

        /**
         * 更多页面
         */
        private String more;

        /**
         * 数据项
         */
        private List<MemberInfoBlockItem> items;
    }


    @AllArgsConstructor
    @Data
    public static class MemberInfoBlockItem {
        private String key;
        private String value;


    }

}
