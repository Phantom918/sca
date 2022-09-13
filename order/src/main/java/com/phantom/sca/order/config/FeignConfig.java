package com.phantom.sca.order.config;

import feign.Logger;
import feign.Request;
import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * Feign 全局配置
 *
 * @Author lei.tan
 * @Date 2021/9/26 00:43
 **/
@Configuration
public class FeignConfig {

    /**
     * Feign 调用时日志打印级别
     *
     * @return
     */
    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    /**
     * 配置请求重试
     */
    @Bean
    public Retryer feignRetryer() {
        return new Retryer.Default(200, 2000, 0);
    }


    /**
     * 设置请求超时时间
     * connectTimeout(链接超时时间，默认10秒)
     * connectTimeoutUnit(时间单位)
     * readTimeout(请求超时时间，默认60秒)
     * readTimeoutUnit(时间单位)
     * followRedirects(是否允许重试，默认 true)
     *
     * @return
     */
    @Bean
    public Request.Options feignOptions() {
        return new Request.Options(10000, TimeUnit.SECONDS, 5000, TimeUnit.SECONDS, true);
    }

}
