package com.phantom.sca.product.service;

import com.phantom.sca.product.config.OutputBinding;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

/**
 * RocketMQ 消息测试
 *
 * @Author lei.tan
 * @Date 2022/9/7 17:51
 * @Version 1.0
 */
@Slf4j
@Service
public class SenderService {

    @Autowired
    private OutputBinding outputBinding;

    /**
     * 发送延迟消息
     *
     * @param msg   消息内容
     * @param tag   标签
     * @param delay 设置延迟级别为x秒后消费
     * @return
     * @throws Exception
     */
    public <T> boolean sendDelayMsg(T msg, String tag, Integer delay) throws Exception {
        Message<T> message = MessageBuilder.withPayload(msg)
                .setHeader(MessageConst.PROPERTY_TAGS, tag)
                .setHeader(MessageConst.PROPERTY_DELAY_TIME_LEVEL, delay)
                .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
                .build();

        return outputBinding.commonGroupChannel().send(message);
    }

    /**
     * 发送事务消息
     *
     * @param msg 消息体
     * @param num 标
     * @param <T> 消息类型
     * @return
     * @throws Exception
     */
    public <T> boolean sendTransactionalMsg(T msg, int num) throws Exception {
        Message<T> message = MessageBuilder.withPayload(msg)
                .setHeader("tx-state", String.valueOf(num))
                .setHeader(MessageConst.PROPERTY_TAGS, "binder")
                .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
                .build();

        return outputBinding.templeGroupChannel().send(message);
    }

}
