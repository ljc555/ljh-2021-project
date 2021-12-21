package com.csbaic.jd.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.csbaic.jd.dto.OrderInfo;
import com.csbaic.jd.entity.OrderDetailEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 订单记录 Mapper 接口
 * </p>
 *
 * @author yjwfn
 * @since 2020-02-14
 */
public interface OrderDetailMapper extends BaseMapper<OrderDetailEntity> {


    /**
     * 查找指定时间区间中，已完成用户的订单id
     * @param begin
     * @param end
     * @return
     */
    List<Long> findUsersByFinishedOrder(@Param("begin") LocalDateTime begin,@Param("end")  LocalDateTime end);
}
