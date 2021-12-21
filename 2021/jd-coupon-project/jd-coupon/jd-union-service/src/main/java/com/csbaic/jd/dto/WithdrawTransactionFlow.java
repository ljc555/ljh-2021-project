package com.csbaic.jd.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class WithdrawTransactionFlow {

    private Long userId;

    private Long txId;

    private BigDecimal amount;

}
