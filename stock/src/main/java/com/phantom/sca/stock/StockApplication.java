package com.phantom.sca.stock;

import com.phantom.sca.stock.config.InputBinding;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.stream.annotation.EnableBinding;

/**
 * TODO
 *
 * @Author lei.tan
 * @Date 2021/9/21 13:19
 **/
@SpringBootApplication
@EnableDiscoveryClient
@EnableBinding({InputBinding.class})
public class StockApplication {

    public static void main(String[] args) {
        SpringApplication.run(StockApplication.class, args);
    }

}
