package com.xtx.reggie_take_out.service;

import com.xtx.reggie_take_out.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author yuwolianxi
* @description 针对表【category(菜品及套餐分类)】的数据库操作Service
* @createDate 2023-10-12 18:55:15
*/
public interface CategoryService extends IService<Category> {
    public void remove(Long ids);
}
