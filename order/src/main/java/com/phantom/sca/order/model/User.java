package com.phantom.sca.order.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * TODO
 *
 * @Author lei.tan
 * @Date 2022/8/30 01:41
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = 3896571485276972341L;

    private Long id;

    private String username;

    private String password;

}
