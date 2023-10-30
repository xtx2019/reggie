package com.xtx.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xtx.reggie.entity.OrderDetail;
import com.xtx.reggie.service.OrderDetailService;
import com.xtx.reggie.mapper.OrderDetailMapper;
import org.springframework.stereotype.Service;

/**
* @author yuwolianxi
* @description 针对表【order_detail(订单明细表)】的数据库操作Service实现
* @createDate 2023-10-30 09:44:24
*/
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail>
    implements OrderDetailService{

}




