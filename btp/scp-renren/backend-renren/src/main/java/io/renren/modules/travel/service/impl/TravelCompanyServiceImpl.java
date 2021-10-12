package io.renren.modules.travel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.modules.travel.dao.TravelCompanyDao;
import io.renren.modules.travel.dto.TravelCompanyDto;
import io.renren.modules.travel.entity.TravelCompanyEntity;
import io.renren.modules.travel.service.TravelCompanyService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

/**
 * Description: 公司代码 Service
 *
 * @author jianghong.li
 * @Date 2020-04- 09 15:28
 */
@Service("travelCompanyService")
public class TravelCompanyServiceImpl extends ServiceImpl<TravelCompanyDao, TravelCompanyEntity> implements TravelCompanyService {
    @Override
    public TravelCompanyDto getCompanyByBukrs(String bukrs) {
        TravelCompanyDto dto = new TravelCompanyDto();

        if (StringUtils.isEmpty(bukrs)) {
            return dto;
        }

        TravelCompanyEntity entity = baseMapper.selectOne(new QueryWrapper<TravelCompanyEntity>().eq("bukrs", bukrs));

        if (entity != null) {
            dto.setBukrs(entity.getBukrs());
            dto.setButxt(entity.getButxt());
        }

        return dto;
    }
}
