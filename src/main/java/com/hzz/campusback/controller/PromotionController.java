package com.hzz.campusback.controller;

import com.hzz.campusback.common.api.ApiResult;
import com.hzz.campusback.model.entity.Promotion;
import com.hzz.campusback.service.PromotionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController   // 将返回的 java 对象封装成 json 格式的数据
@RequestMapping("/promotion")
public class PromotionController extends BaseController{

    @Resource   // 注入
    private PromotionService promotionService;

    // 利用 mybatis plus 进行查询
    @GetMapping("/show") // @GetMapping是一个组合注解，是@RequestMapping(method = RequestMethod.GET)的缩写。
    public ApiResult<List<Promotion>> getDescription(){
        List<Promotion> list = promotionService.list();
        return ApiResult.success(list); // 返回最后一条记录
    }
}
