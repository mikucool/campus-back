package com.hzz.campusback.service.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzz.campusback.mapper.TagMapper;
import com.hzz.campusback.mapper.TopicMapper;
import com.hzz.campusback.mapper.UserMapper;
import com.hzz.campusback.model.dto.CreateTopicDTO;
import com.hzz.campusback.model.entity.Post;
import com.hzz.campusback.model.entity.Tag;
import com.hzz.campusback.model.entity.TopicTag;
import com.hzz.campusback.model.entity.User;
import com.hzz.campusback.model.vo.PostVO;
import com.hzz.campusback.model.vo.ProfileVO;
import com.hzz.campusback.service.PostService;
import com.hzz.campusback.service.TagService;
import com.hzz.campusback.service.TopicTagService;
import com.hzz.campusback.service.UserService;
import com.vdurmont.emoji.EmojiParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class PostServiceImpl extends ServiceImpl<TopicMapper, Post> implements PostService {
    @Resource
    private TagMapper tagMapper;
    @Resource
    private UserMapper userMapper;

    @Resource
    private TopicMapper topicMapper;


    @Autowired
    private UserService userService;

    @Autowired
    private TagService tagService;

    @Autowired
    private TopicTagService topicTagService;

    @Override
    public Page<PostVO> getList(Page<PostVO> page, String tab) {
        // 查询话题并封装到 VO 中
        Page<PostVO> iPage = this.baseMapper.selectListAndPage(page, tab);
        // 利用中间表，根据话题 id 查询标签，并设置到话题 vo 对象中
        this.setTopicTags(iPage);
        return iPage;
    }

    @Override
    public Page<PostVO> getListByTag(Page<PostVO> page, String tab) {
        // 思路：
        // 1. 先查询所有记录并封装到 page 对象中
        List<PostVO> postVOs = this.baseMapper.selectListVO();
        // 2. 给相应的记录设置标签
        postVOs.forEach(topic -> {
            // 利用中间表，根据话题 id 查询对应标签 id 的中间对象（TopicTag）表
            // 对帖子内容的 emoji 转码
            if(topic.getContent() != null) {
                topic.setContent(EmojiParser.parseToUnicode(topic.getContent()));
            }
            List<TopicTag> topicTags = topicTagService.selectByTopicId(topic.getId());
            if (!topicTags.isEmpty()) {
                // 从中间对象（TopicTag）表中抽取出标签 id
                List<String> tagIds = topicTags.stream().map(TopicTag::getTagId).collect(Collectors.toList());
                // 根据 tagIds 获取标签集合并设置到 Page 对象中
                List<Tag> tags = tagMapper.selectBatchIds(tagIds);
                // 判断标签集合中标签名是否含有对应 tab 的标签
                Boolean flag = false;
                for (Tag tag : tags) {
                    if (tab.equals(tag.getName())) {

                        flag = true;
                        break;
                    }
                }
                if (flag) {
                    topic.setTags(tags);
                }
            }
        });
        // 到此所有对应的记录被设置了标签，只需将含标签内容的记录过滤出来即可


        // 3. 过滤，取出对应标签
        List<PostVO> filterList = postVOs.stream().filter(rec -> rec.getTags() != null).collect(Collectors.toList());
        List<PostVO> resList = new ArrayList<>();
        // 4. 将记录按分页要求得到对应记录数
        for (int i = 0; i < page.getSize(); i++) {
            int index = (int) ((page.getCurrent() - 1) * page.getSize()) + i;
            if (index > filterList.size() - 1) break;
            PostVO postVO = filterList.get(index);
            resList.add(postVO);
        }
        // 5. 将记录封装到 Page 对象中返回
        page.setRecords(resList);
        page.setTotal(filterList.size());
        return page;
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


    @Override
    @Transactional(rollbackFor = Exception.class) // 使用事务
    public Post create(CreateTopicDTO dto, User user) {
//        Post topic1 = this.baseMapper.selectOne(new LambdaQueryWrapper<Post>().eq(Post::getTitle, dto.getTitle()));
//        Assert.isNull(topic1, "话题已存在，请修改");

        // 封装
        Post topic = Post.builder()
                .userId(user.getId())
                .title(dto.getTitle())
                .content(EmojiParser.parseToAliases(dto.getContent()))
                .createTime(new Date())
                .build();
        this.baseMapper.insert(topic);  // 插入 post 表

        // 用户积分增加
        int newScore = user.getScore() + 5;
        userMapper.updateById(user.setScore(newScore)); // 修改用户表的 score 属性

        // 标签
        if (!ObjectUtils.isEmpty(dto.getTags())) {
            // 保存标签
            List<Tag> tags = tagService.insertTags(dto.getTags()); // 在 tag 表中创建不存在的标签，如果标签存在则增加话题数
            // 处理标签与话题的关联
            topicTagService.createTopicTag(topic.getId(), tags);   // 在帖子和标签的关系表中添加关系数据
        }

        return topic;
    }

    @Override
    public Map<String, Object> viewTopic(String id) {
        Map<String, Object> map = new HashMap<>(16);
        Post topic = this.baseMapper.selectById(id);
        Assert.notNull(topic, "当前话题不存在,或已被作者删除");

        /*当帖子村在时，就对帖子信息进行处理*/
        // 查询话题详情，并更新查看数
        topic.setView(topic.getView() + 1);
        this.baseMapper.updateById(topic);
        // 对帖子内容的 emoji 转码
        topic.setContent(EmojiParser.parseToUnicode(topic.getContent()));
        map.put("topic", topic);
        // 查找帖子对应的标签
        QueryWrapper<TopicTag> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(TopicTag::getTopicId, topic.getId());
        Set<String> set = new HashSet<>();
        // 遍历取出中间表中的标签 id 存放到 set 中
        for (TopicTag articleTag : topicTagService.list(wrapper)) {
            set.add(articleTag.getTagId());
        }
        // 根据 id 集合查询标签对象
        List<Tag> tags = tagService.listByIds(set);
        map.put("tags", tags);

        // 作者信息

        ProfileVO user = userService.getUserProfile(topic.getUserId());
        map.put("user", user);

        return map;
    }

    @Override
    public List<Post> getRecommend(String id) {
        return this.baseMapper.selectRecommend(id);
    }

    @Override
    public Page<PostVO> searchByKey(String keyword, Page<PostVO> page) {
        // 查询话题
        Page<PostVO> iPage = this.baseMapper.searchByKey(page, keyword);
        // 查询话题的标签
        this.setTopicTags(iPage);
        return iPage;
    }

    @Override
    public List<PostVO> listEssence() {
        List<PostVO> postVOList = topicMapper.listEssence();
        return postVOList;
    }
}
