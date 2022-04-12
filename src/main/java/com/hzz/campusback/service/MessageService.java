package com.hzz.campusback.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hzz.campusback.model.entity.Message;

import java.util.List;

public interface MessageService extends IService<Message> {
    Message selectByParentId(String patentId);
    // 继承了 mybatis plus 的 IService
}
