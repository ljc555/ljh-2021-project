package com.csbaic.jd.service.settlement;

import java.math.BigDecimal;

/**
 * 金额格式化器（统一输出格式）
 */
public interface BigDecimalFormatter {

    /**
     * 将{@link BigDecimal} 按规定格式化
     * @param decimal
     * @return
     */
    String format(BigDecimal decimal);
}


