package com.phantom.sca.order;

import com.phantom.sca.ribbon.MyRule;
import com.phantom.sca.ribbon.RandomRuleConfig;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

/**
 * 1、类注解 @EnableDiscoveryClient 注解默认带有，此处可加可不加
 * 2、@RibbonClient 标示针对哪个服务(name),采用哪种类型的负载均衡策略（configuration）
 * 3、也可以在配置文件中配置负载均衡策略，采用 [服务提供方名称].ribbon.NFLoadBalanceRuleClassName: [具体的策略类的全路径]
 *
 * @Author lei.tan
 * @Date 2021/9/21 14:13
 **/
@Slf4j
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
@MapperScan(basePackages = {"com.phantom.sca.order.mapper"})
/*@RibbonClients({
        @RibbonClient(name = "stock-app", configuration = MyRule.class)
})*/
public class OrderApplication {

    public static void main(String[] args) throws InterruptedException {
        ConfigurableApplicationContext context = SpringApplication.run(OrderApplication.class, args);
        /*while (true) {
            String userName = context.getEnvironment().getProperty("user.name");
            log.info("userName ===> {}", userName);
            String redisDescription = context.getEnvironment().getProperty("redis.description");
            log.info("redisDescription ===> {}", redisDescription);
            TimeUnit.SECONDS.sleep(2);
        }*/


    }

//    @Bean
//    @LoadBalanced
//    public RestTemplate restTemplate(RestTemplateBuilder builder) {
//        // return new RestTemplate(); 这种方式也是可以的
//        return builder.build();
//    }

}



