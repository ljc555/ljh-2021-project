package com.csbaic.jd.service.user.active;


import org.springframework.stereotype.Component;


@Component
public interface ActiveScoreCalculator {


    /**
     * 计算用户的活跃度
     * @param request
     * @return
     */
    ActiveScore cal(ActiveScoreRequest request);


}
