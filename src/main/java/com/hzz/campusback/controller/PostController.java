package com.hzz.campusback.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzz.campusback.common.api.ApiResult;
import com.hzz.campusback.model.entity.Tag;
import com.hzz.campusback.model.vo.PostVO;
import com.hzz.campusback.service.PostService;
import com.hzz.campusback.service.TagService;
import com.hzz.campusback.service.UserService;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;


@RestController
@RequestMapping("/post")
public class PostController extends BaseController {

    @Resource
    private PostService postService;
    @Resource
    private UserService userService;


    /**
     *
     * @param tab 类型：最新或最热
     * @param pageNo 页码
     * @param pageSize  一页显示多少
     * @return  Page<PostVO>，Page 为 mp 提供的类；PostVO 为自定义 bean，有帖子的各种信息
     */
    @GetMapping("/list")
    public ApiResult<Page<PostVO>> list(@RequestParam(value = "tab", defaultValue = "latest") String tab,
                                        @RequestParam(value = "pageNo", defaultValue = "1")  Integer pageNo,
                                        @RequestParam(value = "size", defaultValue = "10") Integer pageSize) {
        Page<PostVO> list = postService.getList(new Page<>(pageNo, pageSize), tab);
        return ApiResult.success(list);
    }


}
