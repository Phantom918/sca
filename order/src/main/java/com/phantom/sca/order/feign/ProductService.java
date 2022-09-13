package com.phantom.sca.order.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 商品接口
 *
 * @Author lei.tan
 * @Date 2021/9/26 01:10
 **/
@FeignClient(name = "product-app", path = "/product")
public interface ProductService {

    /**
     * 根据商品 id 查询
     *
     * @param id 商品id
     * @return
     */
    @RequestMapping("/{id}")
    String get(@PathVariable("id") String id);

}
