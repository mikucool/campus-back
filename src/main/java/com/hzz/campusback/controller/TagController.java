package com.hzz.campusback.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzz.campusback.common.api.ApiResult;
import com.hzz.campusback.model.entity.Tag;
import com.hzz.campusback.model.vo.PostVO;
import com.hzz.campusback.service.PostService;
import com.hzz.campusback.service.TagService;
import com.hzz.campusback.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RestController
@RequestMapping("/tag")
public class TagController extends BaseController {


    @Resource
    private TagService tagService;


    // 简单添加一个 tag
    @GetMapping("/insert")
    public ApiResult<Tag> insert(@RequestParam(value = "name") String TagName) {
        Tag tag = tagService.insertTag(TagName);
        return ApiResult.success(tag);
    }
}
