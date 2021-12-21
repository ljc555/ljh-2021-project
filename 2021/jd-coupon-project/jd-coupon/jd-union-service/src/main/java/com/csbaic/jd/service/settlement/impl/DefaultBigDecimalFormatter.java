package com.csbaic.jd.service.settlement.impl;

import com.csbaic.jd.service.settlement.BigDecimalFormatter;
import com.csbaic.jd.service.settlement.BigDecimalPrecisionStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DefaultBigDecimalFormatter implements BigDecimalFormatter {

    private final BigDecimalPrecisionStrategy precisionStrategy;

    @Autowired
    public DefaultBigDecimalFormatter(BigDecimalPrecisionStrategy precisionStrategy) {
        this.precisionStrategy = precisionStrategy;
    }


    @Override
    public String format(BigDecimal decimal) {
        decimal = precisionStrategy.apply(decimal);
        return decimal.toString();
    }
}
