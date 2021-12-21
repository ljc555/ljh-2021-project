package com.csbaic.jd.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.csbaic.common.convert.ObjectConvert;
import com.csbaic.common.exception.BizRuntimeException;
import com.csbaic.common.result.ResultCode;
import com.csbaic.jd.dto.CreateQuestion;
import com.csbaic.jd.entity.QuestionCategoryEntity;
import com.csbaic.jd.entity.QuestionEntity;
import com.csbaic.jd.mapper.QuestionMapper;
import com.csbaic.jd.service.IQuestionCategoryService;
import com.csbaic.jd.service.IQuestionService;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yjwfn
 * @since 2020-03-20
 */
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, QuestionEntity> implements IQuestionService {

    @Autowired
    private IQuestionCategoryService questionCategoryService;

    public QuestionServiceImpl() {
    }


    @Override
    public void create(CreateQuestion question) {
        if(question == null){
            throw BizRuntimeException.from(ResultCode.BAD_REQUEST, "参数错误");
        }

        if(question.getCid() == null){
            throw BizRuntimeException.from(ResultCode.BAD_REQUEST, "分类Id不能为空");
        }

        if(Strings.isNullOrEmpty(question.getTitle())){
            throw BizRuntimeException.from(ResultCode.BAD_REQUEST, "问题标题不能为空");
        }

        if(Strings.isNullOrEmpty(question.getAnswer())){
            throw BizRuntimeException.from(ResultCode.BAD_REQUEST, "问题问题不能为空");
        }

        QuestionCategoryEntity categoryEntity  = questionCategoryService.getById(question.getCid());
        if(categoryEntity == null){
            throw BizRuntimeException.from(ResultCode.NOT_FOUND, "没有找到分类");
        }


        QuestionEntity questionEntity = ObjectConvert.spring(QuestionEntity.class)
                .convert(question);

        questionEntity.setCid(question.getCid());
        save(questionEntity);
    }

}
