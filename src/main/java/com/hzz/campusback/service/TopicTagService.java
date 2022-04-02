package com.hzz.campusback.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hzz.campusback.model.entity.Tag;
import com.hzz.campusback.model.entity.TopicTag;

import java.util.List;
import java.util.Set;


public interface TopicTagService extends IService<TopicTag> {

    /**
     * 获取Topic Tag 关联记录
     *
     * @param topicId TopicId
     * @return
     */
    List<TopicTag> selectByTopicId(String topicId);

    /**
     * 创建中间关系
     *
     * @param id
     * @param tags
     * @return
     */
    void createTopicTag(String id, List<Tag> tags);

    Set<String> selectTopicIdsByTagId(String id);
}
