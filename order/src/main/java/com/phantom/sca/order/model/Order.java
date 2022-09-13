package com.phantom.sca.order.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 *  定单模型， 如果表名或者字段名和数据库关键字冲突需要加反引号 `` 包裹
 *
 *  @Author lei.tan
 *  @Date 2021/11/9 01:18
 */
@Data
@TableName("`Order`")
public class Order implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer productId;

    private Integer totalAmount;

    @TableField("`status`")
    private Integer status ;

}
