package com.csbaic.jd.service;

import com.csbaic.jd.dto.CreateMessage;
import com.csbaic.jd.dto.Message;
import com.csbaic.jd.entity.MessageEntity;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 新闻 服务类
 * </p>
 *
 * @author yjwfn
 * @since 2020-03-26
 */
public interface IMessageService extends IService<MessageEntity> {

    /**
     * 获取可用的消息信息
     * @param mid
     * @return
     */
    Message getValidMessage(Long mid);


    /**
     * 创建一条消息
     * @param message
     * @return
     */
    Message createMessage(CreateMessage message);


}
