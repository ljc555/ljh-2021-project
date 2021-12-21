package com.csbaic.jd.service.settlement;

import java.math.BigDecimal;

/**
 * {@link java.math.BigDecimal} 精度策略
 */
public interface BigDecimalPrecisionStrategy {

    /**
     * 重新设置精度
     * @param bigDecimal
     * @return
     */
    BigDecimal apply(BigDecimal bigDecimal);
}


