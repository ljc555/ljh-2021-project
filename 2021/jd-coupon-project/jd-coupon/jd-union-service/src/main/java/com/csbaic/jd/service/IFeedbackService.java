package com.csbaic.jd.service;

import com.csbaic.jd.dto.CreateFeedback;
import com.csbaic.jd.entity.FeedbackEntity;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yjwfn
 * @since 2020-03-19
 */
public interface IFeedbackService extends IService<FeedbackEntity> {


    void createFeedback(Long userId, CreateFeedback feedback);

}
