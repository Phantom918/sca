package com.phantom.sca.stock.controller;

import com.phantom.sca.stock.model.Stock;
import com.phantom.sca.stock.service.StockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;

/**
 * 库存 Controller
 *
 * @Author lei.tan
 * @Date 2021/9/21 13:37
 **/
@Slf4j
@RestController
@RequestMapping("/stock")
public class StockController {

    /**
     * 库存操作类
     */
    private final StockService stockService;


    @Value("${server.port}")
    private String port;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }


    /**
     * 扣减库存
     *
     * @param id
     * @return
     */
    @RequestMapping("/reduce/{id}")
    public String reduce(@PathVariable String id) {
        log.info("商品 {} -> 扣减库存....", id);
        Stock stock = stockService.selectById(Integer.valueOf(id));
        Integer count = stock.getCount();
        if (stock != null && count > 0) {
            stock.setCount(count - 1);
            stockService.updateById(stock);
            log.info("商品[{}]库存更新成功[{} -> {}]！", stock.getProductId(), count, stock.getCount());
        }

        return "扣减库存成功!" + ":" + port;
    }

    /**
     * 添加商品
     *
     * @param stock
     * @return
     */
    @RequestMapping("/add")
    public String addStock(@RequestBody Stock stock) {
        log.info("商品{} -> 新增....", stock);
        int num = stockService.add(stock);
        log.info("商品新增成功 {} 条!", num);
        return "添加商品成功!";
    }

    /**
     * 更新库存
     *
     * @param stock
     * @return
     */
    @RequestMapping("/update")
    public String updateStock(@RequestBody Stock stock) {
        log.info("商品{} -> 更新库存....", stock);
        int num = stockService.updateById(stock);
        log.info("商品库存更新成功 {} 条!", num);
        return "商品库存更新成功!";
    }

}
