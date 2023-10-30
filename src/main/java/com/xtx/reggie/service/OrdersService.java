package com.xtx.reggie.service;

import com.xtx.reggie.entity.Orders;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.core.annotation.Order;
import org.springframework.transaction.annotation.Transactional;

/**
* @author yuwolianxi
* @description 针对表【orders(订单表)】的数据库操作Service
* @createDate 2023-10-30 09:44:14
*/
public interface OrdersService extends IService<Orders> {


    @Transactional
    void submit(Orders orders);
}
