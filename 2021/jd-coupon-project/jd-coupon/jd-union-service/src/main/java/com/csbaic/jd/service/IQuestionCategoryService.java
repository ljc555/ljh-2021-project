package com.csbaic.jd.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.csbaic.jd.dto.CreateQuesCategory;
import com.csbaic.jd.dto.GroupQuestion;
import com.csbaic.jd.entity.QuestionCategoryEntity;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yjwfn
 * @since 2020-03-20
 */
public interface IQuestionCategoryService extends IService<QuestionCategoryEntity> {


    void addCategory( CreateQuesCategory createQuesCategory);
    /**
     * 获取分组后的问题
     * @return
     */
    List<GroupQuestion> getGroupQuestion(Long cid);
}
