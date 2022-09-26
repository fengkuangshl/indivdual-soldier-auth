package com.key.win.websocket.interceptor.impl;

import com.key.win.basic.util.JsonUtils;
import com.key.win.basic.web.Result;
import com.key.win.websocket.interceptor.GetMessageSendProcess;
import com.key.win.websocket.interceptor.IMessageSendProcess;

import javax.annotation.PostConstruct;

public abstract class AbstractMessageSendProcessImpl implements IMessageSendProcess {

    public abstract String getId();

    @PostConstruct
    public void init() {
        GetMessageSendProcess.putInstance(getId(), this);
    }
}
