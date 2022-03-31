package com.hzz.campusback.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzz.campusback.mapper.TopicTagMapper;
import com.hzz.campusback.model.entity.TopicTag;
import com.hzz.campusback.service.TopicTagService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(rollbackFor = Exception.class)
public class TopicTagServiceImpl extends ServiceImpl<TopicTagMapper, TopicTag> implements TopicTagService {

    @Override
    public List<TopicTag> selectByTopicId(String topicId) {
        QueryWrapper<TopicTag> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(TopicTag::getTopicId, topicId);
        return this.baseMapper.selectList(wrapper);
    }
}
