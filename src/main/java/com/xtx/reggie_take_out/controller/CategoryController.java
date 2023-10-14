package com.xtx.reggie_take_out.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xtx.reggie_take_out.common.R;
import com.xtx.reggie_take_out.entity.Category;
import com.xtx.reggie_take_out.entity.Employee;
import com.xtx.reggie_take_out.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    // 分页查询
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name) {
        log.info("分页查询员工:{},{},{}", page, pageSize, name);
        // 构造分页构造器
        Page<Category> pageParam = new Page<>(page, pageSize);
        // 构造查询条件
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        if (name != null && !name.isEmpty()) {
            queryWrapper.like(Category::getName, name);
        }
        // 添加排序条件
        queryWrapper.orderByAsc(Category::getSort);
        // 执行分页查询
        Page<Category> pageResult = categoryService.page(pageParam, queryWrapper);
        return R.success(pageResult);

    }

    // 添加分类
    @PostMapping
    public R<String> save(HttpServletRequest request, @RequestBody Category category) {
        log.info("新增分类:{}", JSON.toJSONString(category));
        long id = Thread.currentThread().getId();
        log.info("线程id为：{}", id);
        categoryService.save(category);
        return R.success("新增成功");
    }

    // 删除分类
    @DeleteMapping
    public R<String> delete(Long ids) {
        log.info("删除分类:{}", ids);
        // categoryService.removeById(ids);
        categoryService.remove(ids);
        return R.success("删除成功");
    }
    // 修改分类
    @PutMapping
    public R<String> update(HttpServletRequest request, @RequestBody Category category) {
        log.info("修改分类:{}", JSON.toJSONString(category));
        categoryService.updateById(category);
        return R.success("修改成功");
    }
}
