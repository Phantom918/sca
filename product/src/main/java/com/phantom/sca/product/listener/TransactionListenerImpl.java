package com.phantom.sca.product.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.messaging.Message;

/**
 * rocketmq 事务消息监听处理
 *
 * @Author lei.tan
 * @Date 2022/9/9 00:00
 * @Version 1.0
 */
@Slf4j
@RocketMQTransactionListener(txProducerGroup = "group-temple", corePoolSize = 5, maximumPoolSize = 10)
public class TransactionListenerImpl implements RocketMQLocalTransactionListener {


    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object o) {
        Object num = message.getHeaders().get("tx-state");
        // ... local transaction process, return rollback, commit or unknown
        if ("1".equals(num)) {
            log.info("executor: {} unknown", new String((byte[]) message.getPayload()));
            return RocketMQLocalTransactionState.UNKNOWN;
        } else if ("2".equals(num)) {
            log.info("executor: {} rollback", new String((byte[]) message.getPayload()));
            return RocketMQLocalTransactionState.ROLLBACK;
        } else {
            log.info("executor: {} commit", new String((byte[]) message.getPayload()));
            return RocketMQLocalTransactionState.COMMIT;
        }
    }

    /**
     * 在事务消息长事件未被提交或回滚时
     * <p>
     * RocketMQ 会回查事务消息对应的生产者分组下的 Producer 获得事务消息的状态。此时，该方法就会被调用
     *
     * @param message
     * @return
     */
    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
        // ... check local transaction status and return rollback, commit or unknown
        log.info("check: {}", new String((byte[]) message.getPayload()));
        return RocketMQLocalTransactionState.COMMIT;
    }
}
