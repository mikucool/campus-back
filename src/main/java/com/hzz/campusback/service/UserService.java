package com.hzz.campusback.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hzz.campusback.model.dto.LoginDTO;
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

    /**
     * 获取用户信息
     * @param username
     * @return
     */
    User getUserByUsername(String username);

    /**
     * 用户登录，当用户登录成功后生成 token 字符串返回给客户端，用于下次使用
     * @param dto
     * @return  生成的 jwt 的 token
     */
    String executeLogin(LoginDTO dto);
}
