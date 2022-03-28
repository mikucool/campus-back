package com.hzz.campusback.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hzz.campusback.model.dto.RegisterDTO;
import com.hzz.campusback.model.entity.User;

public interface UserService extends IService<User> {
    // 继承了 mybatis plus 的 IService


    /**
     * 注册功能
     * @param dto
     * @return 注册对象
     */
    User executeRegister(RegisterDTO dto);

}
