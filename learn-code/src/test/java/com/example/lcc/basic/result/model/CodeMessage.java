package com.example.lcc.basic.result.model;

import java.io.Serializable;

/**
 * @date 2018/3/23
 */
public interface CodeMessage<C> {
    C getCode();
    String getMessage();
}
