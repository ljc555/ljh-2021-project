package com.csbaic.jd.service.goods.impl;

import com.beust.jcommander.internal.Lists;
import com.csbaic.common.exception.BizRuntimeException;
import com.csbaic.common.result.ResultCode;
import com.csbaic.jd.dto.CommissionInfo;
import com.csbaic.jd.dto.Coupon;
import com.csbaic.jd.dto.Goods;
import com.csbaic.jd.dto.PriceInfo;
import com.csbaic.jd.entity.UserEntity;
import com.csbaic.jd.enums.UserIdentify;
import com.csbaic.jd.service.IUserService;
import com.csbaic.jd.service.settlement.BigDecimalPrecisionStrategy;
import com.csbaic.jd.utils.UserUtils;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * 根据用户的身份调整用户返利金额
 */
@Slf4j
class AdjustRebateGoodsHandler extends AbstractUserGoodsHandler<Goods>
{



    private final BigDecimalPrecisionStrategy precisionStrategy;

    AdjustRebateGoodsHandler(IUserService userService, BigDecimalPrecisionStrategy precisionStrategy) {
        super(userService);
        this.precisionStrategy = precisionStrategy;
    }



    @Override
    protected void handleImpl(UserEntity userEntity, Goods metadata) {
    preProcessGoods(metadata);
    UserIdentify identify = userEntity != null ? UserUtils.getUserIdentify(userEntity.getIdentify()) : UserIdentify.REGISTERED;

    CommissionInfo commissionInfo = metadata.getCommissionInfo();
//    log.debug("Before adjust: {}", metadata);
    BigDecimal commission = commissionInfo.getCommission() != null  ? precisionStrategy.apply(BigDecimal.valueOf(commissionInfo.getCommission())) : null;
    BigDecimal couponCommission = commissionInfo.getCouponCommission() != null ? precisionStrategy.apply(BigDecimal.valueOf(commissionInfo.getCouponCommission())) : null;
    BigDecimal finalCommission = couponCommission != null ?  couponCommission : commission;

    if(finalCommission == null){
        throw BizRuntimeException.from(ResultCode.ERROR, "佣金错误");
    }
    BigDecimal rebate = precisionStrategy.apply(identify.getRebateRate().multiply(finalCommission));

    //增加返利价
    PriceInfo priceInfo = metadata.getPriceInfo();
    if(priceInfo.getLowestCouponPrice() == null || metadata.getCoupons() == null || metadata.getCoupons().isEmpty()){
        double price = priceInfo.getLowestPrice() != null ? priceInfo.getLowestPrice() : priceInfo.getPrice();
        priceInfo.setLowestRebatePrice(precisionStrategy.apply(BigDecimal.valueOf(price - rebate.doubleValue())).doubleValue());
        commissionInfo.setRebateHint(MessageFormat.format("购买返 ¥{0,number,##.####}", rebate.doubleValue()));
    }

    if(identify == UserIdentify.SUPER || identify == UserIdentify.REGISTERED){
        BigDecimal commissionOfSuperMember =  precisionStrategy.apply(UserIdentify.SUPER.getCommissionRate().multiply(finalCommission));

        if(identify == UserIdentify.SUPER){
            commissionInfo.setCommissionHint(MessageFormat.format("预估返佣 ¥{0,number,##.####}", precisionStrategy.apply(commissionOfSuperMember).doubleValue()));
            commissionInfo.setCommission(commissionOfSuperMember.doubleValue());
            commissionInfo.setCouponCommission(commissionOfSuperMember.doubleValue());
        }else{
            commissionInfo.setCommissionHint(MessageFormat.format("升级超级会员可再省 ¥{0,number,##.####}", precisionStrategy.apply(commissionOfSuperMember).doubleValue()));
            commissionInfo.setCommission(0D);
            commissionInfo.setCouponCommission(0D);
        }

    }else if(identify == UserIdentify.TEACHER){
        BigDecimal commissionOfSuperTeacher =  precisionStrategy.apply(UserIdentify.TEACHER.getCommissionRate().multiply(finalCommission));
        commissionInfo.setCommission(commissionOfSuperTeacher.doubleValue());
        commissionInfo.setCouponCommission(commissionOfSuperTeacher.doubleValue());
        commissionInfo.setCommissionHint(MessageFormat.format("预估返佣 ¥{0,number,##.####}",precisionStrategy.apply(commissionOfSuperTeacher).doubleValue()));
    }


    if(Objects.equals(metadata.getOwner(), "g")){
        metadata.setOwnerDesc("京东自营");
    }else{
        metadata.setOwnerDesc("京东");
    }

//        log.debug("After adjust: {}", metadata);
}

    private static void preProcessGoods(Goods goods){
        //过滤掉无用的卷，只保留一个最好的卷。
        if(goods.getCoupons() != null && !goods.getCoupons().isEmpty()){

            Coupon bestCoupon = goods.getCoupons().stream()
                    .filter(coupon -> coupon.getIsBest() != null && coupon.getIsBest() == 1)
                    .findFirst()
                    .orElse(null);

            if(bestCoupon == null){
                //按折扣最大的排序, 拿第一个
                 goods.getCoupons()
                        .sort((o1, o2) -> BigDecimal.valueOf(o2.getDiscount()).subtract(BigDecimal.valueOf(o1.getDiscount())).intValue());
                bestCoupon = goods.getCoupons().get(0);
            }

            if(bestCoupon == null){
                //没有找到合适的优惠券
                return;
            }

            goods.setCoupons(Lists.newArrayList(bestCoupon));
            List<com.csbaic.jd.dto.Coupon> coupons =  goods.getCoupons();
            PriceInfo priceInfo = goods.getPriceInfo();

            coupons.forEach(new Consumer<Coupon>() {
                @Override
                public void accept(com.csbaic.jd.dto.Coupon coupon) {
                    if(coupon.getIsBest() != null
                            && coupon.getIsBest() == 1
                            && priceInfo.getLowestCouponPrice() == null){

                        double price = priceInfo.getLowestPrice() != null ? priceInfo.getLowestPrice() : priceInfo.getPrice();
                        priceInfo.setLowestCouponPrice(price - coupon.getDiscount());
                    }
                }
            });


        }




    }
}
