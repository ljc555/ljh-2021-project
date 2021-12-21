package com.csbaic.jd.service.order.handle.filter;

import com.csbaic.jd.service.order.handle.OrderMetadata;
import com.csbaic.jd.service.team.MemberContainer;

/**
 * Created by yjwfn on 2020/2/15.
 */
public interface MemberCommissionSaver {


    /**
     * 保存佣金信息
     */
     void save(MemberContainer memberContainer, OrderMetadata orderMetadata, OrderMetadata.SkuMetadata skuMetadata);
}
