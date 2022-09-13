package com.phantom.sca.order.service;

/**
 * 订单接口
 *
 * @Author lei.tan
 * @Data 2021/11/09 01:24
 */
public interface OrderService {

    /**
     * 下订单
     *
     * @param productId 产品id
     * @param count     产品数量
     * @return
     */
    int add(Integer productId, Integer count);

}
