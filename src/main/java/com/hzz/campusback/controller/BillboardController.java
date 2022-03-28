package com.hzz.campusback.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hzz.campusback.common.api.ApiResult;
import com.hzz.campusback.model.entity.Billboard;
import com.hzz.campusback.service.BillboardService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController   // 将返回的 java 对象封装成 json 格式的数据
@RequestMapping("/billboard")
public class BillboardController extends BaseController{

    @Resource   // 注入
    private BillboardService billboardService;

    // 利用 mybatis plus 进行查询
    @RequestMapping("/show")
    public ApiResult<Billboard> getDescription(){
        List<Billboard> list = billboardService.list(
                new LambdaQueryWrapper<Billboard>().eq(Billboard::isShow, true)
        ); // 查询 show 为 true 的字段
        return ApiResult.success(list.get(list.size()-1)); // 返回最后一条记录
    }
}
