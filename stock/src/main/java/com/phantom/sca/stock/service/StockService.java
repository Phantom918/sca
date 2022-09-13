package com.phantom.sca.stock.service;

import com.phantom.sca.stock.model.Stock;

/**
 * 库存操作
 *
 * @Author lei.tan
 * @Date 2021/11/10 23:42
 * @Version 1.0
 */
public interface StockService {

    /**
     * 添加库存
     *
     * @param stock 库存对象
     * @return
     */
    int add(Stock stock);

    /**
     * 更新库存
     *
     * @param stock 库存对象
     * @return
     */
    int updateById(Stock stock);


    /**
     * 根据商品id查询对应商品信息
     * @param id 商品id
     * @return
     */
    Stock selectById(int id);

}
