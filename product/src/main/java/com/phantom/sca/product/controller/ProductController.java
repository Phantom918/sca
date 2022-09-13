package com.phantom.sca.product.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO
 *
 * @Author lei.tan
 * @Date 2021/9/21 13:37
 **/
@Slf4j
@RestController
@RequestMapping("/product")
public class ProductController {


    @Value("${server.port}")
    private String port;



    @RequestMapping("/{id}")
    public String get(@PathVariable String id)  {
        log.info("查询商品{} -> ...", id);
        return "查询商品成功!" + ":" + port;
    }

    @RequestMapping("/sendMessage")
    public String sendMessage()  {
        log.info("发送消息 ...");
        return "查询商品成功!" + ":" + port;
    }



}
