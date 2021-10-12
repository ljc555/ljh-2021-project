package io.renren.modules.travel.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.travel.entity.TravelApplyEntity;
import io.renren.modules.travel.entity.TravelApplyQueryEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Description:
 *
 * @author jianghong.li
 * @Date 2020-04- 09 15:40
 */
@Mapper
public interface TravelApplyDao extends BaseMapper<TravelApplyEntity> {
    List<TravelApplyQueryEntity> queryList(Map<String, Object> map);

    int queryTotal(Map<String, Object> map);
}
