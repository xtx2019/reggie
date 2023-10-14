package com.xtx.reggie_take_out.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xtx.reggie_take_out.common.R;
import com.xtx.reggie_take_out.entity.Employee;
import com.xtx.reggie_take_out.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

/*
    @GetMapping("/test")
    public Object test(){
        return R.success(employeeService.list());
    }
*/


    /**
     * 员工登录
     *
     * @param request
     * @param employee
     * @return
     */
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee) {

        //1、将页面提交的密码password进行md5加密处理
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        //2、根据页面提交的用户名username查询数据库
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, employee.getUsername());
        Employee emp = employeeService.getOne(queryWrapper);

        //3、如果没有查询到则返回登录失败结果
        if (emp == null) {
            return R.error("登录失败");
        }

        //4、密码比对，如果不一致则返回登录失败结果
        if (!emp.getPassword().equals(password)) {
            return R.error("登录失败");
        }

        //5、查看员工状态，如果为已禁用状态，则返回员工已禁用结果
        if (emp.getStatus() == 0) {
            return R.error("账号已禁用");
        }

        //6、登录成功，将员工id存入Session并返回登录成功结果
        long id = Thread.currentThread().getId();
        log.info("线程ID:{}", id);
        request.getSession().setAttribute("employee", emp.getId());
        return R.success(emp);
    }

    /**
     * 员工退出
     *
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request) {
        //清理Session中保存的当前登录员工的id
        request.getSession().removeAttribute("employee");
        return R.success("退出成功");
    }

    /**
     * 新增员工
     *
     * @param employee
     * @return
     */
    @PostMapping
    public R<String> save(HttpServletRequest request, @RequestBody Employee employee) {
        log.info("新增员工:{}", JSON.toJSONString(employee));
        // 设置初始密码，MD5加密
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));

        // 设置创建时间
        //LocalDateTime localDateTime = LocalDateTime.now();
        // employee.setCreateTime(localDateTime);
        // 设置修改时间
        // employee.setUpdateTime(localDateTime);
        // 获取当前用户ID
        //Long empId = (Long) request.getSession().getAttribute("employee");

        //employee.setCreateUser(empId);
        //employee.setUpdateUser(empId);

        employeeService.save(employee);
        // 新增员工
        return R.success("新增成功");
    }

    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name) {
        log.info("分页查询员工:{},{},{}", page, pageSize, name);
        // 构造分页构造器
        Page<Employee> pageParam = new Page<>(page, pageSize);
        // 构造查询条件
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        if (name != null && !name.isEmpty()) {
            queryWrapper.like(Employee::getName, name);
        }
        // 添加排序条件
        queryWrapper.orderByDesc(Employee::getCreateTime);
        // 执行分页查询
        Page<Employee> pageResult = employeeService.page(pageParam, queryWrapper);
        return R.success(pageResult);

    }

    @PutMapping
    public R<String> update(HttpServletRequest request, @RequestBody Employee employee) {
        log.info("修改员工:{}", JSON.toJSONString(employee));
        Object empId = request.getSession().getAttribute("employee");
        employee.setUpdateTime(LocalDateTime.now());
        employee.setUpdateUser((Long) empId);
        employeeService.updateById(employee);
        long id = Thread.currentThread().getId();
        log.info("线程ID:{}", id);
        return R.success("修改成功");

    }

    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable Long id) {
        log.info("根据id查询员工:{}", id);
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getId, id);
        Employee employee = employeeService.getOne(queryWrapper);
        if (employee == null) {
            return R.error("员工不存在");
        }
        return R.success(employee);
    }
}

