package com.xtx.reggie_take_out.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xtx.reggie_take_out.entity.Employee;
import com.xtx.reggie_take_out.service.EmployeeService;
import com.xtx.reggie_take_out.mapper.EmployeeMapper;
import org.springframework.stereotype.Service;

/**
* @author yuwolianxi
* @description 针对表【employee(员工信息)】的数据库操作Service实现
* @createDate 2023-10-10 23:51:11
*/
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee>
    implements EmployeeService{

}




