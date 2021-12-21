package com.csbaic.jd.service.group;

/**
 * 微信群ID生成器
 */
public interface GroupIdGenerator {

    /**
     * 生成一个群id
     * @return
     */
    Integer create(Long uniqueId);

}
