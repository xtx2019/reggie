package com.xtx.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xtx.reggie.entity.AddressBook;
import com.xtx.reggie.service.AddressBookService;
import com.xtx.reggie.mapper.AddressBookMapper;
import org.springframework.stereotype.Service;

/**
* @author yuwolianxi
* @description 针对表【address_book(地址管理)】的数据库操作Service实现
* @createDate 2023-10-25 10:36:04
*/
@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook>
    implements AddressBookService{

}




