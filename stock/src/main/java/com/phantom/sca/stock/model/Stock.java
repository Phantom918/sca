package com.phantom.sca.stock.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * 库存
 *
 * @Author lei.tan
 * @Date 2021/11/10 23:18
 * @Version 1.0
 */
@Data
public class Stock implements Serializable {

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 产品id
     */
    private Integer productId;

    /**
     * 产品库存量
     */
    @TableField("`count`")
    private Integer count;
}
