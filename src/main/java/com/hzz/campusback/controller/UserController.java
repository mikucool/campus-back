package com.hzz.campusback.controller;

import com.hzz.campusback.common.api.ApiResult;
import com.hzz.campusback.model.dto.RegisterDTO;
import com.hzz.campusback.model.entity.Promotion;
import com.hzz.campusback.model.entity.User;
import com.hzz.campusback.service.UserService;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController   // 将返回的 java 对象封装成 json 格式的数据
@RequestMapping("/campus/user")
public class UserController extends BaseController {

    @Resource   // 注入
    private UserService userService;

    // 利用 mybatis plus 进行查询
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ApiResult<Map<String, Object>> register(@Valid @RequestBody RegisterDTO dto) {

        User user = userService.executeRegister(dto);
        if (ObjectUtils.isEmpty(user)) {
            return ApiResult.failed("账号注册失败");
        }
        Map<String, Object> map = new HashMap<>(16);
        map.put("user", user);  // 20220329 暂时没有用，因为客户端验证完后并没有用到 map，此处为提高拓展性
        return ApiResult.success(map);
    }
}
