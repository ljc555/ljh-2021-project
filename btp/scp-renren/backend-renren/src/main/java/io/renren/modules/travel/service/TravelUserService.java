package io.renren.modules.travel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.modules.travel.dto.TravelUserDto;
import io.renren.modules.travel.entity.TravelUserEntity;

/**
 * Description:个人信息
 *
 * @author jianghong.li
 * @Date 2020-04- 09 15:26
 */
public interface TravelUserService extends IService<TravelUserEntity> {
    TravelUserEntity queryByPernr(String pernr);

    TravelUserDto getUserByPernr(String pernr);
}
