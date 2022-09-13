package com.phantom.sca.stock.service.impl;

import com.phantom.sca.stock.mapper.StockMapper;
import com.phantom.sca.stock.model.Stock;
import com.phantom.sca.stock.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 库存操作实现层
 *
 * @Author lei.tan
 * @Date 2021/11/10 23:46
 * @Version 1.0
 */
@Service
public class StockServiceImpl implements StockService {

    /**
     * 库存 mapper
     */
    private final StockMapper stockMapper;

    public StockServiceImpl(StockMapper stockMapper) {
        this.stockMapper = stockMapper;
    }

    @Override
    public int add(Stock stock) {
        return stockMapper.insert(stock);
    }

    @Override
    public int updateById(Stock stock) {
        return stockMapper.updateById(stock);
    }

    @Override
    public Stock selectById(int id) {
        return stockMapper.selectById(id);
    }
}
