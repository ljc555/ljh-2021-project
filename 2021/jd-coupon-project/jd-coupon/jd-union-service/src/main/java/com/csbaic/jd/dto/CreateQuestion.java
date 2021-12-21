package com.csbaic.jd.dto;

import lombok.Data;

@Data
public   class CreateQuestion {

        /**
         * 分类名称
         */
        private Long cid;


        /**
         * 问题标题
         */
        private String title;

        /**
         * 解答
         */
        private String answer;

}
