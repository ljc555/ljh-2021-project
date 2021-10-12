package io.renren.modules.travel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.modules.travel.dto.TravelDepartmentDto;
import io.renren.modules.travel.entity.TravelDepartmentEntity;

/**
 * Description:部门编码
 *
 * @author jianghong.li
 * @Date 2020-04- 09 15:26
 */
public interface TravelDepartmentService extends IService<TravelDepartmentEntity> {
    TravelDepartmentDto getDepartmentByDeprs(String deprs);
}
