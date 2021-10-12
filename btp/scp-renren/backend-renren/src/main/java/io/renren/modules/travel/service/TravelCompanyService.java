package io.renren.modules.travel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.modules.travel.dto.TravelCompanyDto;
import io.renren.modules.travel.entity.TravelCompanyEntity;

/**
 * Description:公司代码
 *
 * @author jianghong.li
 * @Date 2020-04- 09 15:26
 */
public interface TravelCompanyService extends IService<TravelCompanyEntity> {
    TravelCompanyDto getCompanyByBukrs(String bukrs);
}
