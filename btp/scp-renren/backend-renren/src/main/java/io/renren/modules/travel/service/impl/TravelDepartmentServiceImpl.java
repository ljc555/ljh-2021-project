package io.renren.modules.travel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.modules.travel.dao.TravelDepartmentDao;
import io.renren.modules.travel.dto.TravelDepartmentDto;
import io.renren.modules.travel.entity.TravelDepartmentEntity;
import io.renren.modules.travel.service.TravelDepartmentService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

/**
 * Description: 部门编码 Service
 *
 * @author jianghong.li
 * @Date 2020-04- 09 15:28
 */
@Service("travelDepartmentService")
public class TravelDepartmentServiceImpl extends ServiceImpl<TravelDepartmentDao, TravelDepartmentEntity> implements TravelDepartmentService {
    @Override
    public TravelDepartmentDto getDepartmentByDeprs(String deprs) {
        TravelDepartmentDto dto = new TravelDepartmentDto();

        if (StringUtils.isEmpty(deprs)) {
            return dto;
        }

        TravelDepartmentEntity entity = baseMapper.selectOne(new QueryWrapper<TravelDepartmentEntity>().eq("deprs", deprs));

        if (entity != null) {
            dto.setDeprs(entity.getDeprs());
            dto.setDepxt(entity.getDepxt());
        }

        return dto;
    }
}
