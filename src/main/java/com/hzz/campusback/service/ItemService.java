package com.hzz.campusback.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hzz.campusback.model.entity.Item;

public interface ItemService extends IService<Item> {
    // 继承了 mybatis plus 的 IService
    Item getRandomItem();
}
