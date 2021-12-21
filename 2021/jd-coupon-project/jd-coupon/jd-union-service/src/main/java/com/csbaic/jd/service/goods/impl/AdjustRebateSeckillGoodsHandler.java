package com.csbaic.jd.service.goods.impl;

import com.csbaic.jd.dto.CommissionInfo;
import com.csbaic.jd.dto.SecKillGoods;
import com.csbaic.jd.dto.StuGoods;
import com.csbaic.jd.entity.UserEntity;
import com.csbaic.jd.enums.UserIdentify;
import com.csbaic.jd.service.IUserService;
import com.csbaic.jd.utils.UserUtils;

import java.math.BigDecimal;
import java.text.MessageFormat;

/**
 * 根据用户的身份调整用户返利金额
 */
class AdjustRebateSeckillGoodsHandler extends AbstractUserGoodsHandler<SecKillGoods>
{



    AdjustRebateSeckillGoodsHandler(IUserService userService) {
        super(userService);
    }



    @Override
    protected void handleImpl(UserEntity userEntity, SecKillGoods metadata) {

        UserIdentify identify = userEntity != null ? UserUtils.getUserIdentify(userEntity.getIdentify()) : UserIdentify.REGISTERED;



        Double availableCommission = metadata.getCommission();

        if(identify == UserIdentify.REGISTERED){
            metadata.setSuperMemberCommissionHint(MessageFormat.format("升级超级会员可再省 {0,number,##.####}", UserIdentify.SUPER.getRebateRate().subtract(UserIdentify.REGISTERED.getRebateRate()).multiply(BigDecimal.valueOf(availableCommission)).doubleValue()));
        }


        metadata.setCommission(null);
        metadata.setCommissionHint(MessageFormat.format("购买返 {0,number,##.####}", identify.getRebateRate().multiply(BigDecimal.valueOf(availableCommission)).doubleValue()));

    }
}
