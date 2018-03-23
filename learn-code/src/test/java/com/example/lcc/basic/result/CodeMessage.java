package com.example.lcc.basic.result;

import java.io.Serializable;

/**
 * @date 2018/3/23
 */
public interface CodeMessage<C extends Serializable> extends Serializable{
    C getCode();
    String getMessage();
}
