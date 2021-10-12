package io.renren.modules.travel.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.travel.entity.TravelBankEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * Description:
 *
 * @author jianghong.li
 * @Date 2020-04- 09 15:40
 */
@Mapper
public interface TravelBankDao extends BaseMapper<TravelBankEntity> {
}
