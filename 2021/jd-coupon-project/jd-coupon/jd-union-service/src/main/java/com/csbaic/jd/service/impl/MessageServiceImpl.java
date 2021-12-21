package com.csbaic.jd.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.csbaic.common.convert.ObjectConvert;
import com.csbaic.common.exception.BizRuntimeException;
import com.csbaic.common.result.ResultCode;
import com.csbaic.jd.dto.CreateMessage;
import com.csbaic.jd.dto.Message;
import com.csbaic.jd.entity.MessageEntity;
import com.csbaic.jd.enums.MessageStatus;
import com.csbaic.jd.enums.MessageType;
import com.csbaic.jd.enums.NewsStatus;
import com.csbaic.jd.mapper.MessageMapper;
import com.csbaic.jd.service.IMessageService;
import com.google.common.base.Strings;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * <p>
 * 新闻 服务实现类
 * </p>
 *
 * @author yjwfn
 * @since 2020-03-26
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, MessageEntity> implements IMessageService {

    @Override
    public Message getValidMessage(Long mid) {

        if(mid == null){
            throw BizRuntimeException.from(ResultCode.BAD_REQUEST, "消息Id不能为空");
        }


        MessageEntity entity = getById(mid);

        if(entity == null){
            throw BizRuntimeException.from(ResultCode.BAD_REQUEST, "消息未找到");
        }

        if(Objects.equals(entity.getStatus(), NewsStatus.INVISIBLE.getStatus())){
            throw BizRuntimeException.from(ResultCode.BAD_REQUEST, "消息状态为不可见");
        }

        if(Objects.equals(entity.getStatus(), NewsStatus.AUTO.getStatus())){
            throw BizRuntimeException.from(ResultCode.BAD_REQUEST, "消息已经过期");
        }


        return ObjectConvert.spring(entity, Message.class);
    }



    @Override
    public Message createMessage(CreateMessage message) {
        if(message == null){
            throw BizRuntimeException.from(ResultCode.BAD_REQUEST);
        }


        if(Strings.isNullOrEmpty(message.getTitle())){
            throw BizRuntimeException.from(ResultCode.BAD_REQUEST, "message.title 不能为空");
        }


        MessageStatus status = MessageStatus.statusOf(message.getStatus());
        if(status == null){
            throw BizRuntimeException.from(ResultCode.BAD_REQUEST, "message.status 值不正确");
        }

        MessageType messageType = MessageType.statusOf(message.getType());
        if (messageType == null) {
            throw BizRuntimeException.from(ResultCode.BAD_REQUEST, "message.type 值不正确");
        }


        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = LocalDateTime.now().plusYears(1);



        if(!Strings.isNullOrEmpty(message.getStartTime())){
            startTime = LocalDateTime.from(dateTimeFormatter.parse(message.getStartTime()));
        }

        if(!Strings.isNullOrEmpty(message.getEndTime())){
            endTime = LocalDateTime.from(dateTimeFormatter.parse(message.getEndTime()));
        }

        if (endTime.isBefore(startTime)) {
            throw BizRuntimeException.from(ResultCode.BAD_REQUEST, "活动时间不正确");
        }


        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setContent(message.getContent());
        messageEntity.setStartTime(startTime);
        messageEntity.setEndTime(endTime);
        messageEntity.setTitle(message.getTitle());
        messageEntity.setStatus(status.getStatus());
        messageEntity.setType(messageType.getType());
        save(messageEntity);



        return ObjectConvert.spring(messageEntity, Message.class);
    }
}
