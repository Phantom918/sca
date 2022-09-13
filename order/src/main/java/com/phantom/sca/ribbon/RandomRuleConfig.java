package com.phantom.sca.ribbon;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Ribbon 的附在负载均衡策略不能放在能被 SpringBoot 的 @ComponentScan 注解能够扫描到的地方，
 * 否则这个均衡策略会被所有的 @RibbonClient 共享，从而就达不到每个消费端能够自定义不同的策略的
 * 目的
 *
 * @Author lei.tan
 * @Date 2021/9/22 23:48
 **/
@Configuration
public class RandomRuleConfig {

    /**
     * 自定义负载均衡策略
     * @return
     */
    @Bean
    public IRule iRule() {
        return new RandomRule();
    }


}
