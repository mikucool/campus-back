package com.hzz.campusback.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzz.campusback.mapper.MessageMapper;
import com.hzz.campusback.model.entity.Message;
import com.hzz.campusback.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Slf4j
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {

    @Autowired
    private MessageMapper messageMapper;

    @Override
    public Message selectByParentId(String parentId) {
        Message message = messageMapper.selectByParentId(parentId);
        return message;
    }
}
