package com.csbaic.jd.service.impl;

import com.csbaic.jd.dto.CreateFeedback;
import com.csbaic.jd.entity.FeedbackEntity;
import com.csbaic.jd.enums.FeedbackStatus;
import com.csbaic.common.exception.BizRuntimeException;
import com.csbaic.jd.mapper.FeedbackMapper;
import com.csbaic.jd.service.IFeedbackService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.csbaic.common.result.ResultCode;
import com.google.common.base.Strings;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yjwfn
 * @since 2020-03-19
 */
@Service
public class FeedbackServiceImpl extends ServiceImpl<FeedbackMapper, FeedbackEntity> implements IFeedbackService {


    @Override
    public void createFeedback(Long userId, CreateFeedback feedback) {
        if (userId == null) {
            throw BizRuntimeException.from(ResultCode.ERROR, "用户Id不能为空");
        }

        FeedbackEntity feedbackEntity = new FeedbackEntity();
        feedbackEntity.setContent(feedback.getContent());
        feedbackEntity.setSubmitterId(userId);
        feedbackEntity.setStatus(FeedbackStatus.SUBMITTED.getStatus());
        save(feedbackEntity);
    }
}
