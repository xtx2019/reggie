package com.xtx.reggie.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xtx.reggie.common.R;
import com.xtx.reggie.dto.SetmealDto;
import com.xtx.reggie.entity.Setmeal;
import com.xtx.reggie.entity.SetmealDish;
import com.xtx.reggie.service.SetmealDishService;
import com.xtx.reggie.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/setmeal")
public class SetMealController {

    @Autowired
    private SetmealService setmealService;

    @Autowired
    private SetmealDishService setmealDishService;

    /**
     * 分页查询
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name) {
        log.info("page = {},pageSize = {},name = {}", page, pageSize, name);

        //构造分页构造器
        Page pageInfo = new Page(page, pageSize);

        //构造条件构造器
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        //添加过滤条件
        queryWrapper.like(StringUtils.isNotEmpty(name), Setmeal::getName, name);
        //添加排序条件
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);

        //执行查询
        setmealService.page(pageInfo, queryWrapper);

        return R.success(pageInfo);
    }

    /**
     * 添加套餐
     */
    @PostMapping
    public R<String> save(@RequestBody SetmealDto setmealDto) {
        log.info(setmealDto.toString());

        setmealService.saveWithSetMeal(setmealDto);

        return R.success("新增套餐成功");
    }

    /**
     * 批量起售
     */
    @PostMapping("/status/{status}")
    public R<String> status(@PathVariable Integer status, Long[] ids) {
        for (Long dishId : ids) {
            Setmeal setmeal = new Setmeal();
            setmeal.setId(dishId);
            setmeal.setStatus(status);
            setmealService.updateById(setmeal);
        }
        return R.success("套餐信息更新成功");
    }

    /**
     * 删除套餐
     */
    @DeleteMapping
    public R<String> delete(Long[] ids) {
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        for (Long dishId : ids) {
            queryWrapper.in(Setmeal::getId, dishId);
            setmealService.remove(queryWrapper);
        }


        LambdaQueryWrapper<SetmealDish> queryWrapper1 = new LambdaQueryWrapper<>();
        for (Long dishFlavorId : ids) {
            queryWrapper1.in(SetmealDish::getSetmealId, dishFlavorId);
            setmealDishService.remove(queryWrapper1);
        }
        return R.success("删除成功");
    }

    /**
     * 查询指定套餐
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<SetmealDto> getOneById(@PathVariable Long id) {
        SetmealDto setmealDto = new SetmealDto();

        // 查询套餐基本信息
        Setmeal setmeal = setmealService.getById(id);
        // 对象拷贝
        BeanUtils.copyProperties(setmeal, setmealDto);

        // 条件构造器
        LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SetmealDish::getSetmealId, setmealDto.getId());

        // 查询套餐与菜品的关联关系
        List<SetmealDish> list = setmealDishService.list(queryWrapper);
        setmealDto.setSetmealDishes(list);

        return R.success(setmealDto);
    }

    /**
     * 保存修改
     */
    @PutMapping
    public R<String> update(@RequestBody SetmealDto setmealDto) {
        log.info(setmealDto.toString());

        setmealService.updateWithSetMeal(setmealDto);

        return R.success("修改成功");
    }

}
