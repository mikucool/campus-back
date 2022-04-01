package com.hzz.campusback.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzz.campusback.mapper.FollowMapper;
import com.hzz.campusback.model.entity.Follow;
import com.hzz.campusback.service.FollowService;
import org.springframework.stereotype.Service;


@Service
public class FollowServiceImpl extends ServiceImpl<FollowMapper, Follow> implements FollowService {
}
