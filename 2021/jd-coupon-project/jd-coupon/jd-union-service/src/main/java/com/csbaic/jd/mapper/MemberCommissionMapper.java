package com.csbaic.jd.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.csbaic.jd.dto.MergedMemberCommission;
import com.csbaic.jd.entity.MemberCommissionEntity;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 佣金商品记录 Mapper 接口
 * </p>
 *
 * @author yjwfn
 * @since 2020-02-15
 */
public interface MemberCommissionMapper extends BaseMapper<MemberCommissionEntity> {


    /**
     * 获取订单的返利金额
     * @param userId
     * @param orderId
     * @return
     */
    MergedMemberCommission getMergedOrderSkuFee(@Param("userId") Long userId, @Param("orderId") Long orderId,@Param("skuId") Long skuId);


    /**
     * 计算30天用户总佣金
     * @param userId
     * @return
     */
     MergedMemberCommission getRebateFeeFor30Days(@Param("userId") Long userId) ;

    /**
     * 计算30天用户总佣金
     * @return
     */
    IPage<MergedMemberCommission> getAllRebateFeeFor30Days(IPage<MergedMemberCommission> page) ;

    /**
     * 根据订单的下单时间，查询用户佣金
     * @param page
     * @param userId
     * @param start
     * @param end
     * @param validCode
     * @return
     */
    IPage<MergedMemberCommission> getBillingForUserIdBetweenFinishTimeAndValidCode(IPage<MergedMemberCommission> page, @Param("userId")  Long userId, @Param("start") LocalDateTime start, @Param("end")  LocalDateTime end, @Param("validCode")  Integer validCode);


    /**
     * 根据订单的完成时间，查询用户佣金
     * @param page
     * @param userId
     * @param start
     * @param end
     * @param validCode
     * @return
     */
    IPage<MergedMemberCommission> getBillingForUserIdBetweenOrderTimeAndValidCode(IPage<MergedMemberCommission> page, @Param("userId")  Long userId, @Param("start") LocalDateTime start, @Param("end")  LocalDateTime end, @Param("validCode")  Integer validCode);

}
