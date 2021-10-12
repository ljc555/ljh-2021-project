package io.renren.modules.travel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.modules.travel.dao.TravelBankDao;
import io.renren.modules.travel.dto.TravelBankDto;
import io.renren.modules.travel.entity.TravelBankEntity;
import io.renren.modules.travel.service.TravelBankService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

/**
 * Description: 银行信息 Service
 *
 * @author jianghong.li
 * @Date 2020-04- 09 15:27
 */
@Service("travelBankService")
public class TravelBankServiceImpl extends ServiceImpl<TravelBankDao, TravelBankEntity> implements TravelBankService {
    @Override
    public TravelBankDto getBankByBank(String bank) {
        TravelBankDto dto = new TravelBankDto();

        if (StringUtils.isEmpty(bank)) {
            return dto;
        }

        TravelBankEntity entity = baseMapper.selectOne(new QueryWrapper<TravelBankEntity>().eq("bank", bank));

        if (entity != null) {
            dto.setBank(entity.getBank());
            dto.setTxt50(entity.getTxt50());
        }

        return dto;
    }
}
