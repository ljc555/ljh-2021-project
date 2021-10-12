package io.renren.modules.travel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.modules.travel.dto.TravelCostTypeDto;
import io.renren.modules.travel.entity.TravelCostTypeEntity;

/**
 * Description:费用类别Service
 *
 * @author jianghong.li
 * @Date 2020-04- 09 15:26
 */
public interface TravelCostTypeService extends IService<TravelCostTypeEntity> {
    TravelCostTypeDto getCostTypeByHkont(String hkont);
}
