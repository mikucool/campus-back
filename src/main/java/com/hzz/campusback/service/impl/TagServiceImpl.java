package com.hzz.campusback.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzz.campusback.common.exception.ApiAsserts;
import com.hzz.campusback.jwt.JwtUtil;
import com.hzz.campusback.mapper.TagMapper;
import com.hzz.campusback.mapper.UserMapper;
import com.hzz.campusback.model.dto.LoginDTO;
import com.hzz.campusback.model.dto.RegisterDTO;
import com.hzz.campusback.model.entity.Tag;
import com.hzz.campusback.model.entity.User;
import com.hzz.campusback.service.TagService;
import com.hzz.campusback.service.UserService;
import com.hzz.campusback.utils.MD5Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    /**
     * 添加一个 tag
     * @param tagName
     * @return
     */
    @Override
    public Tag insertTag(String tagName) {
        Tag tag = Tag.builder().name(tagName).build();

        System.out.println(tag);
        baseMapper.insert(tag);

        return tag;
    }
}
