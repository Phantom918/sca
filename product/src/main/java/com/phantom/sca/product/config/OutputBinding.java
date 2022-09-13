package com.phantom.sca.product.config;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * 生产者绑定
 * <p>
 * 通过 @Output 注解，声明了一个名字为 output-common 的 Output Binding。
 * 注意 这个名字要和配置文件中的 spring.cloud.stream.bindings 配置项对应上
 * 同时，@Output 注解的方法的返回结果为 MessageChannel 类型，可以使用它发送消息。
 * </p>
 *
 * @Author lei.tan
 * @Date 2022/9/8 18:29
 * @Version 1.0
 */
public interface OutputBinding {

    /**
     * commonGroup 发送消息组
     *
     * @return 消息管道
     */
    @Output("output-common")
    MessageChannel commonGroupChannel();


    /**
     * templeGroup 发送消息组
     *
     * @return 消息管道
     */
    @Output("output-temple")
    MessageChannel templeGroupChannel();

}
