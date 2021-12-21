package com.csbaic.jd.mapper;

import com.csbaic.jd.dto.GroupQuestion;
import com.csbaic.jd.entity.QuestionCategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yjwfn
 * @since 2020-03-20
 */
public interface QuestionCategoryMapper extends BaseMapper<QuestionCategoryEntity> {


    List<GroupQuestion> getGroupQuestion(Long cid);

}
