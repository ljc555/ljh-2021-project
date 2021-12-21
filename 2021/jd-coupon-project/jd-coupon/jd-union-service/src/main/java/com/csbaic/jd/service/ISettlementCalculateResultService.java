package com.csbaic.jd.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.csbaic.jd.dto.SettlementCalculateInfo;
import com.csbaic.jd.entity.SettlementCalculateResultEntity;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户账单计算状态记录 服务类
 * </p>
 *
 * @author yjwfn
 * @since 2020-03-16
 */
public interface ISettlementCalculateResultService extends IService<SettlementCalculateResultEntity> {

    IPage<SettlementCalculateInfo> getSettlementCalculateRecords(Long userId, Integer pageIndex, Integer pageSize);
}
