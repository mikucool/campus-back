package com.hzz.campusback.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzz.campusback.mapper.TagMapper;
import com.hzz.campusback.mapper.TopicMapper;
import com.hzz.campusback.mapper.UserMapper;
import com.hzz.campusback.model.entity.Post;
import com.hzz.campusback.model.entity.Tag;
import com.hzz.campusback.model.entity.TopicTag;
import com.hzz.campusback.model.vo.PostVO;
import com.hzz.campusback.service.PostService;
import com.hzz.campusback.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class PostServiceImpl extends ServiceImpl<TopicMapper, Post> implements PostService {
    @Resource
    private TagMapper tagMapper;
    @Resource
    private UserMapper userMapper;


    @Autowired
    private UserService userService;

    @Autowired
    private com.hzz.campusback.service.TopicTagService topicTagService;

    @Override
    public Page<PostVO> getList(Page<PostVO> page, String tab) {
        // 查询话题
        Page<PostVO> iPage = this.baseMapper.selectListAndPage(page, tab);
        // 利用中间表，根据话题 id 查询标签，并设置到话题 vo 对象中
        setTopicTags(iPage);
        return iPage;
    }


    private void setTopicTags(Page<PostVO> iPage) {
        // 获取页面记录并遍历
        iPage.getRecords().forEach(topic -> {
            // 利用中间表，根据话题 id 查询对应标签 id 的中间对象（TopicTag）表
            List<TopicTag> topicTags = topicTagService.selectByTopicId(topic.getId());
            if (!topicTags.isEmpty()) {
                // 从中间对象（TopicTag）表中抽取出标签 id
                List<String> tagIds = topicTags.stream().map(TopicTag::getTagId).collect(Collectors.toList());
                // 根据 tagIds 获取标签集合并设置到 Page 对象中
                List<Tag> tags = tagMapper.selectBatchIds(tagIds);
                topic.setTags(tags);
            }
        });
    }
}
