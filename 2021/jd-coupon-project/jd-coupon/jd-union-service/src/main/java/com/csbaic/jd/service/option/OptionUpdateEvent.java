package com.csbaic.jd.service.option;

import org.springframework.context.ApplicationEvent;

/**
 * 选项更新事件
 */
public class OptionUpdateEvent extends ApplicationEvent {

    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public OptionUpdateEvent(Object source) {
        super(source);
    }


}
