package com.csbaic.jd.service.settlement.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.csbaic.jd.dto.SettlementTransactionFlow;
import com.csbaic.jd.entity.OrderDetailEntity;
import com.csbaic.jd.entity.OrderSkuEntity;
import com.csbaic.jd.entity.SettlementCalculateResultEntity;
import com.csbaic.jd.enums.SuccessOrFailureStatus;
import com.csbaic.jd.service.IOrderDetailService;
import com.csbaic.jd.service.ISettlementCalculateResultService;
import com.csbaic.jd.service.IWalletTransactionFlowService;
import com.csbaic.jd.service.settlement.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Slf4j
@Service
public class SettlementServiceImpl implements SettlementService {


    private final MemberBillingCalculator billingCalculator;

    private final ISettlementCalculateResultService calculateResultService;

    private final IWalletTransactionFlowService transactionFlowService;

    private final IOrderDetailService orderDetailService;

    @Autowired
    public SettlementServiceImpl(@Qualifier(BillingCalculators.ACTUAL_SETTLEMENT) MemberBillingCalculator billingCalculator, ISettlementCalculateResultService memberBillingCalculateResultService, IWalletTransactionFlowService transactionFlowService, IOrderDetailService orderDetailService) {
        this.billingCalculator = billingCalculator;
        this.calculateResultService = memberBillingCalculateResultService;
        this.transactionFlowService = transactionFlowService;
        this.orderDetailService = orderDetailService;
    }


    @Override
    public void settlementMonthly(int witch) {
        log.debug("Start settle for {} 月",  witch);
        LocalDate withMonth = LocalDate.now().withMonth(witch);
        LocalDateTime firstDay = LocalDateTime.of(withMonth.with(TemporalAdjusters.firstDayOfMonth()), LocalTime.MIN);
        LocalDateTime lastDay = LocalDateTime.of(withMonth.with(TemporalAdjusters.lastDayOfMonth()), LocalTime.MAX);

        //找出订单时间为上个月的订单
        List<Long> users =  orderDetailService.findUsersByFinishedOrder(firstDay, lastDay);
        for(Long userId : users){
            calculateOnce(new CalculateRequest(firstDay, lastDay, userId));
        }
    }

    @Override
    public void settlementForLashMonth() {
        LocalDate withMonth = LocalDate.now().minusMonths(1);
        LocalDateTime firstDay = LocalDateTime.of(withMonth.with(TemporalAdjusters.firstDayOfMonth()), LocalTime.MIN);
        LocalDateTime lastDay = LocalDateTime.of(withMonth.with(TemporalAdjusters.lastDayOfMonth()), LocalTime.MAX);


        //找出订单时间为上个月的订单
        List<Long> users =  orderDetailService.findUsersByFinishedOrder(firstDay, lastDay);
        for(Long userId : users){
            calculateOnce(new CalculateRequest(firstDay, lastDay, userId));
        }

    }


    /**
     * 结算
     * @param request
     */
    private void calculateOnce(CalculateRequest request){

        long count = calculateResultService.count(
                Wrappers.<SettlementCalculateResultEntity>query()
                        .eq(SettlementCalculateResultEntity.USER_ID, request.getUserId())
                        .eq(SettlementCalculateResultEntity.START_DATE, request.getStart().toLocalDate())
                        .eq(SettlementCalculateResultEntity.END_DATE, request.getEnd().toLocalDate())

        );

        if (count > 0) {
            log.info("Calculate record exists already ：{}", request);
            return;
        }

        log.info("Calculate settlement ：{}", request);
        SettlementCalculateResultEntity calculateResultEntity = new SettlementCalculateResultEntity();
        calculateResultEntity.setStartDate(LocalDate.from(request.getStart()) );
        calculateResultEntity.setEndDate(LocalDate.from(request.getEnd()));
        calculateResultEntity.setStatus(SuccessOrFailureStatus.FAILURE.ordinal());
        calculateResultEntity.setUserId(request.getUserId());
        calculateResultService.save(calculateResultEntity);

        try{
            MemberBilling billing = billingCalculator.cal(request);
            saveResultToWallet(calculateResultEntity.getId(), request, billing);
            log.info("settlement {}", billing);

            calculateResultEntity.setRebateFee(billing.getActualRebateFee());
            calculateResultEntity.setAwardFee(billing.getActualAwardFee());
            calculateResultEntity.setCommissionFee(billing.getActualCommissionFee());
            calculateResultEntity.setStatus(SuccessOrFailureStatus.SUCCESS.ordinal());
        }catch (Exception e){
            log.error("cal fail", e);
            calculateResultEntity.setStatus(SuccessOrFailureStatus.FAILURE.ordinal());
        }finally {
            //保存同步记录
            calculateResultService.saveOrUpdate(calculateResultEntity);
        }
    }

    /**
     * 保存流水信息
     * @param request
     * @param billing
     */
    public void saveResultToWallet(Long txId, CalculateRequest request, MemberBilling billing){
        if(billing.getActualRebateFee() == null
                && billing.getActualAwardFee() == null
                && billing.getActualCommissionFee() == null){

            log.info("Zero result, not save");
            return;
        }

        SettlementTransactionFlow settlementTransactionFlow = new SettlementTransactionFlow();
        BeanUtils.copyProperties(billing, settlementTransactionFlow);
        BeanUtils.copyProperties(request, settlementTransactionFlow);
        settlementTransactionFlow.setTxId(txId);
        settlementTransactionFlow.setStart(request.getStart().toLocalDate());
        settlementTransactionFlow.setEnd(request.getEnd().toLocalDate());
        settlementTransactionFlow.setRebateFee(billing.getActualRebateFee());
        settlementTransactionFlow.setAwardFee(billing.getActualAwardFee());
        settlementTransactionFlow.setCommissionFee(billing.getActualCommissionFee());
        transactionFlowService.createTransactionFlow(settlementTransactionFlow);
    }

}
