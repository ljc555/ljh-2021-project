package com.csbaic.jd.service.user.active;

/**
 * 保存用户活跃度
 */
public interface ActiveScoreRepository {

    /**
     * 保存用户活跃度
     * @param activeScore
     */
    void save(ActiveScoreRequest request, ActiveScore activeScore);

    /**
     * 移除已经保存的活跃度
     * @param request
     */
    void remove(ActiveScoreRequest request);

    /**
     * 获取活跃度
     * @param request
     * @return
     */
    ActiveScore get(ActiveScoreRequest request);
}
