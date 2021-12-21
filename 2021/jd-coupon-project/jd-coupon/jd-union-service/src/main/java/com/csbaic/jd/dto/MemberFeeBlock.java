package com.csbaic.jd.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * 我的收益
 */
@Data
public class MemberFeeBlock {

    /**
     * 数据项
     */
    private List<Item> items;

    /**
     * 数据段底部说明
     */
    private DataDescription description;


    @AllArgsConstructor
    @Data
    public static class DataDescription {

        /**
         * 数据说明标题
         */
        private String title;
        /**
         * 数据说明动作
         */
        private String text;

        /**
         * 数据说明pop标题
         */
        private String popTitle;


        /**
         * 数据说明pop内容
         */
        private String popText;

    }


    @AllArgsConstructor
    @Data
    public static class Item {
        /**
         * 数据项标题
         */
        private String title;

        /**
         * 数据项值
         */
        private String value;
    }
}
