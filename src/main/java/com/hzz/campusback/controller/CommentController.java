package com.hzz.campusback.controller;

import com.hzz.campusback.common.api.ApiResult;
import com.hzz.campusback.model.vo.CommentVO;
import com.hzz.campusback.service.CommentService;
import com.hzz.campusback.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("/comment")
public class CommentController extends BaseController {

    @Resource
    private CommentService commentService;
    @Resource
    private UserService userService;

    @GetMapping("/get_comments")
    public ApiResult<List<CommentVO>> getCommentsByTopicID(@RequestParam(value = "topicid", defaultValue = "1") String topicid) {
        List<CommentVO> lstBmsComment = commentService.getCommentsByTopicID(topicid);
        return ApiResult.success(lstBmsComment);
    }


//    @PostMapping("/add_comment")
//    public ApiResult<Comment> add_comment(@RequestHeader(value = USER_NAME) String userName,
//                                             @RequestBody CommentDTO dto) {
//        User user = userService.getUserByUsername(userName);
//        Comment comment = commentService.create(dto, user);
//        return ApiResult.success(comment);
//    }
}
