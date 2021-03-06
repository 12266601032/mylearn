package com.example.spring.test.demo;

import org.springframework.stereotype.Service;

/**
 * @author liucongcong
 * @date 2018/4/27
 */
@Service("actualSupplier")
public class ActualSupplier implements MessageSupplier {
    @Override
    public String getMessage() {
        return "actual service";
    }
}
