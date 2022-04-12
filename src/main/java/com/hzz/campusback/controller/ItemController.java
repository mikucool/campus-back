package com.hzz.campusback.controller;

import com.hzz.campusback.common.api.ApiResult;
import com.hzz.campusback.service.ItemService;
import com.hzz.campusback.model.entity.Item;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController   // 将返回的 java 对象封装成 json 格式的数据
@RequestMapping("/activity")
public class ItemController extends BaseController{

    @Resource   // 注入
    private ItemService itemService;

    @RequestMapping("/show")
    public ApiResult<Item> getDescription(){
        Item item = itemService.getRandomItem();
        return ApiResult.success(item); // 返回最后一条记录
    }
}
