package com.xtx.reggie_take_out.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xtx.reggie_take_out.common.R;
import com.xtx.reggie_take_out.entity.Dish;
import com.xtx.reggie_take_out.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/dish")
public class DishController {
    @Autowired
    private DishService dishService;

    // 分页查询菜品
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name) {
        log.info("分页查询菜品:{},{},{}", page, pageSize, name);
        // 构造分页构造器
        Page<Dish> pageParam = new Page<>(page, pageSize);
        // 构造查询条件
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        if (name != null && !name.isEmpty()) {
            queryWrapper.like(Dish::getName, name);
        }
        // 添加排序条件
        queryWrapper.orderByAsc(Dish::getSort);
        // 执行分页查询
        Page<Dish> pageResult = dishService.page(pageParam, queryWrapper);
        return R.success(pageResult);
    }

    // 添加菜品


}
