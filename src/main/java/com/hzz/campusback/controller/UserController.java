package com.hzz.campusback.controller;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzz.campusback.common.api.ApiResult;
import com.hzz.campusback.model.dto.LoginDTO;
import com.hzz.campusback.model.dto.RegisterDTO;
import com.hzz.campusback.model.entity.Post;
import com.hzz.campusback.model.entity.User;
import com.hzz.campusback.service.PostService;
import com.hzz.campusback.service.UserService;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

import static com.hzz.campusback.jwt.JwtUtil.USER_NAME;

@RestController   // 将返回的 java 对象封装成 json 格式的数据
@RequestMapping("/campus/user")
public class UserController extends BaseController {

    @Resource   // 注入
    private UserService userService;

    @Resource   // 注入
    private PostService postService;

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

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ApiResult<Map<String, String>> login(@Valid @RequestBody LoginDTO dto) {
        String token = userService.executeLogin(dto);
        if (ObjectUtils.isEmpty(token)) {
            return ApiResult.failed("账号密码错误");
        }
        Map<String, String> map = new HashMap<>(16);
        map.put("token", token);
        return ApiResult.success(map, "登录成功");
    }

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public ApiResult<User> getUser(@RequestHeader(value = USER_NAME) String username) { //从请求头中取出名为USER_NAME的变量
        User user = userService.getUserByUsername(username);
        return ApiResult.success(user);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ApiResult<Object> logOut() {
        return ApiResult.success(null, "注销成功");
    }

    @GetMapping("/{username}")
    public ApiResult<Map<String, Object>> getUserByName(@PathVariable("username") String username,
                                                        @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                                        @RequestParam(value = "size", defaultValue = "10") Integer size) {
        Map<String, Object> map = new HashMap<>(16);
        User user = userService.getUserByUsername(username);
        Assert.notNull(user, "用户不存在");
        Page<Post> page = postService.page(new Page<>(pageNo, size),
                new LambdaQueryWrapper<Post>().eq(Post::getUserId, user.getId()));
        map.put("user", user);
        map.put("topics", page);
        return ApiResult.success(map);
    }

    @PostMapping("/update")
    public ApiResult<User> updateUser(@RequestBody User user) {
        userService.updateById(user);
        return ApiResult.success(user);
    }
}
