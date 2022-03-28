package com.hzz.campusback.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzz.campusback.mapper.ItemMapper;
import com.hzz.campusback.model.entity.Item;
import com.hzz.campusback.service.ItemService;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl extends ServiceImpl<ItemMapper, Item> implements ItemService {

    @Override
    public Item getRandomItem() {
        Item item = null;
        item = this.baseMapper.getRandomItem();
        return item;
    }
}
