package com.phantom.sca.stock.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.phantom.sca.stock.model.Stock;
import org.apache.ibatis.annotations.Mapper;

/**
 * 库存的 dao 层
 *
 * @Author lei.tan
 * @Date 2021/11/10 23:38
 * @Version 1.0
 */
@Mapper
public interface StockMapper extends BaseMapper<Stock> {


}
