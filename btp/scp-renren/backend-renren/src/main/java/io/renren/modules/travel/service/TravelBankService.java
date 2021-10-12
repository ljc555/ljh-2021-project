package io.renren.modules.travel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.modules.travel.dto.TravelBankDto;
import io.renren.modules.travel.entity.TravelBankEntity;

/**
 * Description:银行信息
 *
 * @author jianghong.li
 * @Date 2020-04- 09 15:25
 */
public interface TravelBankService extends IService<TravelBankEntity> {
    TravelBankDto getBankByBank(String bank);
}
