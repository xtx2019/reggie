package com.xtx.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xtx.reggie.entity.ShoppingCart;
import com.xtx.reggie.service.ShoppingCartService;
import com.xtx.reggie.mapper.ShoppingCartMapper;
import org.springframework.stereotype.Service;

/**
* @author yuwolianxi
* @description 针对表【shopping_cart(购物车)】的数据库操作Service实现
* @createDate 2023-10-25 13:04:55
*/
@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart>
    implements ShoppingCartService{

}




