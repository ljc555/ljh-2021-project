package com.csbaic.jd.service.impl;

import com.csbaic.common.convert.ObjectConvert;
import com.csbaic.common.exception.BizRuntimeException;
import com.csbaic.common.result.ResultCode;
import com.csbaic.jd.dto.CreateQuesCategory;
import com.csbaic.jd.dto.GroupQuestion;
import com.csbaic.jd.entity.QuestionCategoryEntity;
import com.csbaic.jd.mapper.QuestionCategoryMapper;
import com.csbaic.jd.service.IQuestionCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.Strings;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yjwfn
 * @since 2020-03-20
 */
@Service
public class QuestionCategoryServiceImpl extends ServiceImpl<QuestionCategoryMapper, QuestionCategoryEntity> implements IQuestionCategoryService {


    @Override
    public void addCategory(CreateQuesCategory createQuesCategory) {

        if(Strings.isNullOrEmpty(createQuesCategory.getName())){
            throw BizRuntimeException.from(ResultCode.ERROR, "分类名称不能为空");
        }

        if(Strings.isNullOrEmpty(createQuesCategory.getIcon())){
            throw BizRuntimeException.from(ResultCode.ERROR, "分类图标不能为空");
        }


        QuestionCategoryEntity categoryEntity  = ObjectConvert.spring(QuestionCategoryEntity.class)
                .convert(createQuesCategory);

        save(categoryEntity);
    }

    @Override
    public List<GroupQuestion> getGroupQuestion(Long cid) {
        return getBaseMapper().getGroupQuestion(cid);
    }
}
