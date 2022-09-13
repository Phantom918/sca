package com.phantom.sca.product.controller;

import com.phantom.sca.product.model.SimpleMsg;
import com.phantom.sca.product.service.SenderService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.SecureRandom;

/**
 * 消息发送
 *
 * @Author lei.tan
 * @Date 2022/9/8 18:47
 * @Version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/produce")
public class ProduceController {

    @Autowired
    private SenderService senderService;

    @GetMapping("/sendDelayMsg")
    public boolean sendDelayMsg(@RequestParam("msg") String msg) throws Exception {
        int msgId = new SecureRandom().nextInt();
        return senderService.sendDelayMsg(new SimpleMsg(msgId, msg), "tagObj", 0);
    }

    @GetMapping("/sendTransactionalMsg")
    public boolean sendTransactionalMsg() throws Exception {
        // unknown message
        senderService.sendTransactionalMsg("transactional-msg1", 1);
        // rollback message
        senderService.sendTransactionalMsg("transactional-msg2", 2);
        // commit message
        senderService.sendTransactionalMsg("transactional-msg3", 3);

        return true;
    }


}
