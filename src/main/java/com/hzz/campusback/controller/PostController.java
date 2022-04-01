package com.hzz.campusback.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzz.campusback.common.api.ApiResult;
import com.hzz.campusback.model.dto.CreateTopicDTO;
import com.hzz.campusback.model.entity.Post;
import com.hzz.campusback.model.entity.Tag;
import com.hzz.campusback.model.entity.User;
import com.hzz.campusback.model.vo.PostVO;
import com.hzz.campusback.service.PostService;
import com.hzz.campusback.service.TagService;
import com.hzz.campusback.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import java.util.List;
import java.util.Map;

import static com.hzz.campusback.jwt.JwtUtil.USER_NAME;


@RestController
@RequestMapping("/post")
public class PostController extends BaseController {

    @Resource
    private PostService postService;
    @Resource
    private UserService userService;


    /**
     * @param tab      类型：最新或最热
     * @param pageNo   页码
     * @param pageSize 一页显示多少
     * @return Page<PostVO>，Page 为 mp 提供的类；PostVO 为自定义 bean，有帖子的各种信息
     */
    @GetMapping("/list")
    public ApiResult<Page<PostVO>> list(@RequestParam(value = "tab", defaultValue = "latest") String tab,
                                        @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                        @RequestParam(value = "size", defaultValue = "10") Integer pageSize) {
        Page<PostVO> list = postService.getList(new Page<>(pageNo, pageSize), tab);
        return ApiResult.success(list);
    }

    /**
     *
     * @param userName USER_NAME 从客户端返回的 token 中取出的用户名
     * @param dto
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ApiResult<Post> create(@RequestHeader(value = USER_NAME) String userName
            , @RequestBody CreateTopicDTO dto) {
        User user = userService.getUserByUsername(userName); // 根据用户名查询用户
        Post topic = postService.create(dto, user); // 创建帖子并进行各种关系设置
        return ApiResult.success(topic);
    }

    // 帖子信息
    @GetMapping()
    public ApiResult<Map<String, Object>> view(@RequestParam("id") String id) {
        Map<String, Object> map = postService.viewTopic(id);
        return ApiResult.success(map);
    }

    // 推荐
    @GetMapping("/recommend")
    public ApiResult<List<Post>> getRecommend(@RequestParam("topicId") String id) {
        List<Post> topics = postService.getRecommend(id);
        return ApiResult.success(topics);
    }
}
