package com.phantom.sca.stock.service.impl;

import com.phantom.sca.stock.model.SimpleMsg;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.context.IntegrationContextUtils;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.support.ErrorMessage;
import org.springframework.stereotype.Service;

/**
 * 消费消息处理
 * <p>
 * 使用 @EnableBinding 注解，声明指定接口开启 Binding 功能，扫描其 @Input 和 @Output 注解
 *
 * @Author lei.tan
 * @Date 2022/9/9 00:11
 * @Version 1.0
 */
@Slf4j
@Service
public class ReceiveService {


    @StreamListener("input-common-1")
    public void receiveInput1(@Payload SimpleMsg receiveMsg) {
        log.info("[onMessage][input-common-1 线程编号: {} 消息内容: {}]", Thread.currentThread().getId(), receiveMsg);
    }

    @StreamListener("input-common-2")
    public void receiveInput2(@Payload SimpleMsg receiveMsg) {
        log.info("[onMessage][input-common-2 线程编号: {}} 消息内容: {}]", Thread.currentThread().getId(), receiveMsg);
    }

    @StreamListener("input-common-3")
    public void receiveInput3(@Payload SimpleMsg receiveMsg) {
        log.info("[onMessage][input-common-3 线程编号: {} 消息内容: {}]", Thread.currentThread().getId(), receiveMsg);
        // 此处抛出一个 RuntimeException 异常，模拟消费失败
        throw new RuntimeException("我就是故意抛出一个异常");
    }

    @StreamListener("input-temple-1")
    public void receiveTransactionalMsg(String transactionMsg) {
        log.info("[onMessage][input-temple-3 线程编号: {} 消息内容: {}]", Thread.currentThread().getId(), transactionMsg);
    }

    /**
     * 局部的异常处理：通过订阅指定错误的 Channel - [destination].[group].errors
     * 注意: 如果异常处理方法成功，没有重新抛出异常，会认定为该消息被消费成功，所以就不会进行消费重试。
     *
     * @param errorMessage
     */
    @ServiceActivator(inputChannel = "topic-common-01.group-common-3.errors")
    public void handleError(ErrorMessage errorMessage) {
        log.info("[handleError][payload: {}]", ExceptionUtils.getRootCauseMessage(errorMessage.getPayload()));
        log.info("[handleError][originalMessage: {}]", errorMessage.getOriginalMessage());
        log.info("[handleError][headers: {}]", errorMessage.getHeaders());
    }

    /**
     * 全局错误 errorChannel
     *
     * @param errorMessage
     */
    @StreamListener(IntegrationContextUtils.ERROR_CHANNEL_BEAN_NAME)
    public void globalHandleError(ErrorMessage errorMessage) {
        log.info("[globalHandleError][payload: {}]", ExceptionUtils.getRootCauseMessage(errorMessage.getPayload()));
        log.info("[globalHandleError][originalMessage: {}]", errorMessage.getOriginalMessage());
        log.info("[globalHandleError][headers: {}]", errorMessage.getHeaders());
    }

}
