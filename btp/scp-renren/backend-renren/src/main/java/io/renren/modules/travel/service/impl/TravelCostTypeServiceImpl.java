package io.renren.modules.travel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.modules.travel.dao.TravelCostTypeDao;
import io.renren.modules.travel.dto.TravelCostTypeDto;
import io.renren.modules.travel.entity.TravelCostTypeEntity;
import io.renren.modules.travel.service.TravelCostTypeService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

/**
 * Description: 费用类别Service
 *
 * @author jianghong.li
 * @Date 2020-04- 09 15:28
 */
@Service("travelCostTypeService")
public class TravelCostTypeServiceImpl extends ServiceImpl<TravelCostTypeDao, TravelCostTypeEntity> implements TravelCostTypeService {
    @Override
    public TravelCostTypeDto getCostTypeByHkont(String hkont) {
        TravelCostTypeDto dto = new TravelCostTypeDto();

        if (StringUtils.isEmpty(hkont)) {
            return dto;
        }

        TravelCostTypeEntity entity = baseMapper.selectOne(new QueryWrapper<TravelCostTypeEntity>().eq("hkont", hkont));

        if (entity != null) {
            dto.setHkont(entity.getHkont());
            dto.setTxt20(entity.getTxt20());
        }

        return dto;
    }
}
