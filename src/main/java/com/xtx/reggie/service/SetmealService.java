package com.xtx.reggie.service;

import com.xtx.reggie.dto.SetmealDto;
import com.xtx.reggie.entity.Setmeal;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author yuwolianxi
* @description 针对表【setmeal(套餐)】的数据库操作Service
* @createDate 2023-10-14 17:09:25
*/
public interface SetmealService extends IService<Setmeal> {
    // 新增套餐，需要操作两张表：setmeal、setmeal_dish
    public void saveWithSetMeal(SetmealDto setmealDto);

    // 根据id查询套餐信息和对应的菜品信息
    public SetmealDto getByIdWithSetMeal(Long id);

    // 更新套餐信息
    public void updateWithSetMeal(SetmealDto setmealDto);
}
