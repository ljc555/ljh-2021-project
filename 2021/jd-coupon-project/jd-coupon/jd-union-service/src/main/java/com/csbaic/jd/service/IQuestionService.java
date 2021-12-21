package com.csbaic.jd.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.csbaic.jd.dto.CreateQuestion;
import com.csbaic.jd.entity.QuestionEntity;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yjwfn
 * @since 2020-03-20
 */
public interface IQuestionService extends IService<QuestionEntity> {



    void  create(CreateQuestion question);


}
