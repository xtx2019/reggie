package com.xtx.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xtx.reggie.entity.User;
import com.xtx.reggie.service.UserService;
import com.xtx.reggie.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author yuwolianxi
* @description 针对表【user(用户信息)】的数据库操作Service实现
* @createDate 2023-10-25 11:05:16
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}




