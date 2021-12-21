package com.csbaic.jd.service.settlement.impl;

import com.csbaic.jd.service.settlement.BigDecimalPrecisionStrategy;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;


@Component
public class RoundDownPrecisionStrategy implements BigDecimalPrecisionStrategy {
    @Override
    public BigDecimal apply(BigDecimal bigDecimal) {
        return bigDecimal.setScale(2, BigDecimal.ROUND_DOWN);
    }
}
