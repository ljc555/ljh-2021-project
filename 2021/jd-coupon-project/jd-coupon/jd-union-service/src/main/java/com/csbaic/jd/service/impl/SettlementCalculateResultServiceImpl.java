package com.csbaic.jd.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.csbaic.jd.dto.SettlementCalculateInfo;
import com.csbaic.jd.entity.SettlementCalculateResultEntity;
import com.csbaic.jd.mapper.SettlementCalculateResultMapper;
import com.csbaic.jd.service.ISettlementCalculateResultService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户账单计算状态记录 服务实现类
 * </p>
 *
 * @author yjwfn
 * @since 2020-03-16
 */
@Service
public class SettlementCalculateResultServiceImpl extends ServiceImpl<SettlementCalculateResultMapper, SettlementCalculateResultEntity> implements ISettlementCalculateResultService {

    @Override
    public IPage<SettlementCalculateInfo> getSettlementCalculateRecords(Long userId, Integer pageIndex, Integer pageSize) {
        pageIndex = pageIndex == null ? 1 : pageIndex;
        pageSize = pageSize == null ? 20 : pageSize;



        return page(
                new Page<>(pageIndex, pageSize),
                Wrappers.<SettlementCalculateResultEntity>query()
                        .eq(SettlementCalculateResultEntity.USER_ID, userId)
        ).convert(SettlementCalculateResultServiceImpl::convert);
    }

    private static SettlementCalculateInfo convert(SettlementCalculateResultEntity entity){
        SettlementCalculateInfo info = new SettlementCalculateInfo();
        info.setAwardFee(entity.getAwardFee());
        info.setRebateFee(entity.getRebateFee());
        info.setCommissionFee(entity.getCommissionFee());
        info.setStartDate(entity.getStartDate());
        info.setEndDate(entity.getEndDate());
        info.setUserId(entity.getUserId());
        return info;
    }
}
