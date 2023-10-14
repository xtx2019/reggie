package com.xtx.reggie_take_out.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xtx.reggie_take_out.common.CustomException;
import com.xtx.reggie_take_out.entity.Category;
import com.xtx.reggie_take_out.entity.Dish;
import com.xtx.reggie_take_out.entity.Setmeal;
import com.xtx.reggie_take_out.service.CategoryService;
import com.xtx.reggie_take_out.mapper.CategoryMapper;
import com.xtx.reggie_take_out.service.DishService;
import com.xtx.reggie_take_out.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;

/**
 * @author yuwolianxi
 * @description 针对表【category(菜品及套餐分类)】的数据库操作Service实现
 * @createDate 2023-10-12 18:55:15
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
        implements CategoryService {
    @Autowired
    private DishService dishService;
    @Autowired
    private SetmealService setmealService;

    /**
     * 根据id删除分类
     **/
    @Override
    public void remove(Long ids) {
        // 查询当前分类是否关联了菜品,如果已经关联了菜品,则不允许删除
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Dish::getCategoryId, ids);
        int count = dishService.count(queryWrapper);
        if (count > 0) {
            throw new CustomException("当前分类已关联菜品,不允许删除");
        }
        // 查询当前分类是否关联了套餐,如果已经关联了套餐,则不允许
        LambdaQueryWrapper<Setmeal> queryWrapperSetmeal = new LambdaQueryWrapper<>();
        queryWrapperSetmeal.eq(Setmeal::getCategoryId, ids);
        int count2 = setmealService.count(queryWrapperSetmeal);
        if (count2 > 0) {
            throw new CustomException("当前分类已关联套餐,不允许删除");
        }
        super.removeById(ids);
    }
}




