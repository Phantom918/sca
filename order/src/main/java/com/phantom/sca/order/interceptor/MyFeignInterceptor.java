package com.phantom.sca.order.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

/**
 * 一般 springmvc 的拦截器是指服务端在接受到请求前做拦截，此处的 feign 拦截器是在 服务端调用服务端之间，
 * 远程请求发起前做拦截，这样我们可以用来做自己想做的事，比如 日志打印、授权，但是一般来说为辅的授权认证是在
 * 网关层做的
 * 此处 如果使用  Configuration 注解会变成全局拦截器
 *
 * @Author lei.tan
 * @Date 2021/9/26 02:24
 **/
@Slf4j
@Configuration
public class MyFeignInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        log.info("feign 拦截器....");
        // 添加请求头
        template.header("authority", "jwt....");
        // 添加参数
        template.query("id", "555");


    }
}
