package com.hzz.campusback.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hzz.campusback.model.entity.Tag;

import java.util.List;


public interface TagService extends IService<Tag> {

    Tag insertTag(String tagName);

    /**
     * 插入标签
     *
     * @param tags
     * @return
     */
    List<Tag> insertTags(List<String> tags);
    /**
     * 获取标签关联话题
     *
     * @param topicPage
     * @param id
     * @return
     */
//    Page<Post> selectTopicsByTagId(Page<Post> topicPage, String id);
}
