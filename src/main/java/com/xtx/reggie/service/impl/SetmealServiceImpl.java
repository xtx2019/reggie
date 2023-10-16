package com.xtx.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xtx.reggie.dto.SetmealDto;
import com.xtx.reggie.entity.Setmeal;
import com.xtx.reggie.entity.SetmealDish;
import com.xtx.reggie.service.SetmealDishService;
import com.xtx.reggie.service.SetmealService;
import com.xtx.reggie.mapper.SetmealMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yuwolianxi
 * @description 针对表【setmeal(套餐)】的数据库操作Service实现
 * @createDate 2023-10-14 17:09:25
 */
@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal>
        implements SetmealService {
    @Autowired
    private SetmealDishService setmealDishService;

    @Override
    public void saveWithSetMeal(SetmealDto setmealDto) {
        // 保存套餐基本信息
        this.save(setmealDto);
        Long setmealId = setmealDto.getId();
        // 保存套餐菜品信息
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();

        setmealDishes = setmealDishes.stream().map((item) -> {
            item.setSetmealId(String.valueOf(setmealId));
            return item;
        }).collect(Collectors.toList());

        setmealDishService.saveBatch(setmealDishes);
    }

    @Override
    public SetmealDto getByIdWithSetMeal(Long id) {
        // 查询套餐基本信息，从setmeal表查询
        Setmeal setmeal = this.getById(id);

        SetmealDto setmealDto = new SetmealDto();
        BeanUtils.copyProperties(setmeal,setmealDto);

        // 查询套餐菜品信息，从setmeal_dish表查询
        LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SetmealDish::getSetmealId,setmeal.getId());
        List<SetmealDish> setmealDishes = setmealDishService.list(queryWrapper);
        setmealDto.setSetmealDishes(setmealDishes);

        return setmealDto;
    }

    @Override
    public void updateWithSetMeal(SetmealDto setmealDto) {
        // 修改套餐
        this.updateById(setmealDto);

        // 清理当前套餐对应的菜品信息---setmeal_dish表的delete操作
        LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SetmealDish::getSetmealId,setmealDto.getId());

        setmealDishService.remove(queryWrapper);

        // 添加当前提交过来的菜品信息---setmeal_dish表的insert操作
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();

        setmealDishes = setmealDishes.stream().map((item) -> {
            item.setSetmealId(String.valueOf(setmealDto.getId()));
            return item;
        }).collect(Collectors.toList());

        setmealDishService.saveBatch(setmealDishes);
    }
}




