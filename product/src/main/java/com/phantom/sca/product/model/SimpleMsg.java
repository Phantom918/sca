package com.phantom.sca.product.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * TODO
 *
 * @Author lei.tan
 * @Date 2022/9/8 15:25
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimpleMsg implements Serializable {

    private static final long serialVersionUID = -156318017340521099L;

    private int id;

    private String msg;

}
