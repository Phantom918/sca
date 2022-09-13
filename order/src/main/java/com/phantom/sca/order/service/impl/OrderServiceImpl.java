package com.phantom.sca.order.service.impl;

import com.phantom.sca.order.feign.ProductService;
import com.phantom.sca.order.feign.StockService;
import com.phantom.sca.order.mapper.OrderMapper;
import com.phantom.sca.order.model.Order;
import com.phantom.sca.order.service.OrderService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 订单接口
 *
 * @Author lei.tan
 * @Data 2021/11/09 01:24
 */
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderMapper orderMapper;

    private final StockService stockService;

    private final ProductService productService;

    public OrderServiceImpl(OrderMapper orderMapper, @Qualifier("com.phantom.sca.order.feign.StockService") StockService stockService, ProductService productService) {
        this.orderMapper = orderMapper;
        this.stockService = stockService;
        this.productService = productService;
    }


    /**
     * 分布式事务案例
     *
     * @param productId 产品id
     * @param count     产品数量
     * @return
     */
    @Override
//    @Transactional(rollbackFor = Exception.class)
    @GlobalTransactional
    public int add(Integer productId, Integer count) {
        Order order = new Order();
        order.setProductId(productId);
        order.setTotalAmount(count);
        int num = orderMapper.insert(order);
        log.info("订单插入[{}]完毕....", num);
        String productMessage = productService.get("2222");
        log.info("商品[{}]查询完毕.....", productMessage);
        String stockMessage = stockService.reduce("1");
        log.info("商品[{}]库存更新完毕....", stockMessage);
        // 发生异常....
        int m = num / 0;
        return num;
    }
}
