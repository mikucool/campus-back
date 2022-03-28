package com.hzz.campusback.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hzz.campusback.model.entity.Promotion;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionMapper extends BaseMapper<Promotion> {
    // 继承了 BaseMapper 类，该类时 mybatis plus 的，它提供了一些基础的增删改功能
}
