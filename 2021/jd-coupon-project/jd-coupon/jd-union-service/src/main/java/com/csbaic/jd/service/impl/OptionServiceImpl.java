package com.csbaic.jd.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.csbaic.jd.dto.Option;
import com.csbaic.jd.entity.OptionEntity;
import com.csbaic.common.exception.BizRuntimeException;
import com.csbaic.jd.mapper.OptionMapper;
import com.csbaic.jd.service.IOptionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.csbaic.jd.service.option.OptionUpdateEvent;
import com.csbaic.common.result.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yjwfn
 * @since 2020-03-11
 */
@Service
public class OptionServiceImpl extends ServiceImpl<OptionMapper, OptionEntity> implements IOptionService {

    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    public OptionServiceImpl(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }


    @Override
    public List<Option> getOptions(String... key) {
        if(key == null){
            return Collections.emptyList();
        }

        if(key.length > 20){
            throw BizRuntimeException.from(ResultCode.BAD_REQUEST, "單次最多只能請求 20 個選項");
        }

        return getBaseMapper().getOptions(key);
    }


    @Override
    public List<Option> getAppOptions() {
        List<OptionEntity> options = list(
                Wrappers.<OptionEntity>query()
                .likeRight(OptionEntity.OPTION_NAME, "app")

        );

        return options.stream().map(optionEntity -> {

            Option option = new Option();
            option.setKey(optionEntity.getOptionName());
            option.setValue(optionEntity.getOptionValue());

            return option;
        }).collect(Collectors.toList());
    }

    @Override
    public List<Option> getSysOptions() {
        List<OptionEntity> options = list(
                Wrappers.<OptionEntity>query()
                        .likeRight(OptionEntity.OPTION_NAME, "sys")

        );

        return options.stream().map(optionEntity -> {

            Option option = new Option();
            option.setKey(optionEntity.getOptionName());
            option.setValue(optionEntity.getOptionValue());

            return option;
        }).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void setOptions(Map<String, String> values) {
        if(values == null || values.isEmpty()){
            return;
        }

        Map<String, String> copy = new HashMap<>(values);
        List<OptionEntity> entities = new ArrayList<>();

        for(Map.Entry<String, String> option : copy.entrySet()){
             OptionEntity optionEntity = getOne(
                     Wrappers.<OptionEntity>query()
                     .eq(OptionEntity.OPTION_NAME, option.getKey())
             );

            optionEntity = optionEntity == null ? new OptionEntity() : optionEntity;
            optionEntity.setOptionValue(option.getValue());
            optionEntity.setOptionName(option.getKey());
            entities.add(optionEntity);


        }

        saveOrUpdateBatch(entities);
        eventPublisher.publishEvent(new OptionUpdateEvent(entities));
    }

    @Override
    public void setOption(String key, String value) {
        OptionEntity optionEntity = getOne(
                Wrappers.<OptionEntity>query()
                        .eq(OptionEntity.OPTION_NAME, key)
        );

        optionEntity = optionEntity == null ? new OptionEntity() : optionEntity;
        optionEntity.setOptionName(key);
        optionEntity.setOptionValue(value);
        saveOrUpdate(optionEntity);
        eventPublisher.publishEvent(new OptionUpdateEvent(Collections.singletonList(optionEntity)));
    }

    @Override
    public String getString(String key) {
        OptionEntity optionEntity = getOne(
                Wrappers.<OptionEntity>query()
                        .eq(OptionEntity.OPTION_NAME, key)
        );

        return optionEntity != null ? optionEntity.getOptionValue() : null;
    }

    @Override
    public Integer getInt(String key) {
        String value = getString(key);
        return value != null ? Integer.parseInt(value) : null;
    }

    @Override
    public LocalDateTime getLocalDateTime(String key, String format) {
        String value = getString(key);
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendPattern(format)
                .toFormatter();

        return LocalDateTime.from(formatter.parse(value));
    }
}
