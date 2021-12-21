package com.csbaic.jd.service.team;

/**
 * Created by yjwfn on 2020/2/16.
 */
public interface MemberContainerBuilder {

    /**
     * 创建MemberContainer
     * @param userId
     * @return
     */
    MemberContainer build(Long userId);
}
