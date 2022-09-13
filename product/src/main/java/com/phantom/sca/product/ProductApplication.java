package com.phantom.sca.product;

import com.phantom.sca.product.config.OutputBinding;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

/**
 * 使用 @EnableBinding注解，声明指定接口开启 Binding 功能，扫描其 @Input 和 @Output 注解。
 *
 * @Author lei.tan
 * @Date 2021/9/26 01:05
 **/
@SpringBootApplication
@EnableBinding({OutputBinding.class})
public class ProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductApplication.class, args);
    }

}
