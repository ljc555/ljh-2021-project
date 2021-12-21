package com.csbaic.jd.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.csbaic.jd.dto.MemberQuickStart;
import com.csbaic.jd.entity.MemberQuickStartEntity;

/**
 * <p>
 * 新人上手 服务类
 * </p>
 *
 * @author yjwfn
 * @since 2020-04-14
 */
public interface IMemberQuickStartService extends IService<MemberQuickStartEntity> {

    /**
     * 保存
     * @param quickStart
     */
    void save(MemberQuickStart quickStart);

    /**
     * 获取新手上路
     * @param pageIndex
     * @param pageSize
     * @return
     */
    IPage<MemberQuickStart> getQuickStart(
             int pageIndex,
             int pageSize
    );
}
