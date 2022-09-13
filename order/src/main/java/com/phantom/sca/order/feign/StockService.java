package com.phantom.sca.order.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 库存接口
 * Feign 远程调用接口定义
 * 注解 @FeignClient 中 name 定义需要调用的[服务名], path 定义调用服务的 Controller 的顶层路径
 * 注意: 如果没有顶层路径，则 patch 可以不写)
 * @FeignClient 注解中 configuration:可以自定义服务类的 Feign 配置，
 * 比如添加 com.phantom.sca.order.config.FeignConfig.java
 * fallback: 熔断降级处理类
 *
 * @Author lei.tan
 * @Date 2021/9/26 00:16
 **/
@FeignClient(name = "stock-app", path = "/stock", fallback = StockServiceFallback.class)
public interface StockService {

    /**
     * 商品库存扣减接口
     *
     * @param id 商品id
     * @return
     */
    @RequestMapping("/reduce/{id}")
    String reduce(@PathVariable("id") String id);

}
