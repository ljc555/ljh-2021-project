package com.csbaic.jd.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

@Data
public   class Question {
        @JsonSerialize(using = ToStringSerializer.class)
        private Long id;

        /**
         * 问题标题
         */
        private String title;

        /**
         * 解答
         */
        private String answer;

}
