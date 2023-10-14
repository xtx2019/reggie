package com.xtx.reggie_take_out.mapper;

import com.xtx.reggie_take_out.entity.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author yuwolianxi
* @description 针对表【category(菜品及套餐分类)】的数据库操作Mapper
* @createDate 2023-10-12 18:55:15
* @Entity com.xtx.reggie_take_out.entity.Category
*/
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {

}




