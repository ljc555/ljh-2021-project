package com.csbaic.jd.service.group;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.csbaic.jd.entity.OptionEntity;
import com.csbaic.jd.service.IOptionService;
import com.csbaic.jd.service.option.Options;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/***
 * 邀请码生成器
 */
@Component
public class DefaultGroupIdGenerator implements GroupIdGenerator {

    private static final Long GROUP_ID_START = 10000L;

    @Autowired
    private IOptionService optionService;




    @Override
    public Integer create(Long uniqueId) {

        OptionEntity nextGroupId = optionService.getOne(
                Wrappers
                .<OptionEntity>query()
                .eq(OptionEntity.OPTION_NAME, Options.OPT_SYS_NEXT_GROUP_ID)
        );

        if(nextGroupId == null){
            nextGroupId = new OptionEntity();
            nextGroupId.setOptionName(Options.OPT_SYS_NEXT_GROUP_ID);
            nextGroupId.setOptionValue(String.valueOf(GROUP_ID_START));
            optionService.save(nextGroupId);
        }


        Integer nextId = Integer.parseInt(nextGroupId.getOptionValue());
        nextGroupId.setOptionValue(String.valueOf(nextId + 1));

        //乐观锁更新
        optionService.update(
                nextGroupId,
                Wrappers.<OptionEntity>update()
                .eq(OptionEntity.OPTION_VALUE, String.valueOf(nextId))
                );

        return  nextId;
    }

}
