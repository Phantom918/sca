package com.phantom.sca.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.phantom.sca.order.model.Order;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单表数据库接口层
 *
 * @Author lei.tan
 * @Data 2021/11/09 01:21:38
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {


}
