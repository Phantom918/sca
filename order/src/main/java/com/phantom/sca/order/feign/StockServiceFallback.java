package com.phantom.sca.order.feign;

import org.springframework.stereotype.Component;

/**
 * 服务降级处理实现类
 *
 * @Author lei.tan
 * @Date 2021/10/25 00:31
 **/
@Component
public class StockServiceFallback implements StockService{
    @Override
    public String reduce(String id) {
        return "服务降级啦！！！";
    }
}
