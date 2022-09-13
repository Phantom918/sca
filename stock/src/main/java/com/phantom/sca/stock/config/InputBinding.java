package com.phantom.sca.stock.config;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * 消费绑定
 * 通过 @Input 注解，声明了一个名字为 input-common-1 的 Input Binding。
 * <p>
 * 注意，这个名字要和配置文件中的spring.cloud.stream.bindings 配置项对应上。
 * <p>
 * 同时，@Input 注解的方法的返回结果为 SubscribableChannel 类型，可以使用它订阅消息来消费。
 *
 * @Author lei.tan
 * @Date 2022/9/8 23:49
 * @Version 1.0
 */
public interface InputBinding {

    @Input("input-common-1")
    SubscribableChannel inputCommon1();

    @Input("input-common-2")
    SubscribableChannel inputCommon2();

    @Input("input-common-3")
    SubscribableChannel inputCommon3();

    @Input("input-temple-1")
    SubscribableChannel inputTemple1();

}
