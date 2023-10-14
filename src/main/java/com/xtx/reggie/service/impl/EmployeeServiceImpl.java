package com.xtx.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xtx.reggie.entity.Employee;
import com.xtx.reggie.mapper.EmployeeMapper;
import com.xtx.reggie.service.EmployeeService;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService{
}
