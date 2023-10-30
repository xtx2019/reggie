package com.xtx.reggie.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xtx.reggie.common.R;
import com.xtx.reggie.entity.Orders;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.xtx.reggie.service.OrdersService;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/order")

public class OderController {
    @Autowired
    private OrdersService orderService;

    /**
     * 用户下单
     */
    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders orders) {
        orderService.submit(orders);
        return R.success("下单成功");
    }
    /**
     * 用户订单信息
     */
    @GetMapping("/userPage")
    public R<Page> userPage(int page, int pageSize){
        log.info("userPage = {},pageSize = {}",page,pageSize);
        Page pageInfo = new Page(page,pageSize);
        // 构建条件构造器
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        // 添加过滤条件
        queryWrapper.orderByDesc(Orders::getOrderTime);
        // 执行查询
        orderService.page(pageInfo,queryWrapper);
        return R.success(pageInfo);
    }
}
