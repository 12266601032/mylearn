package com.example.lcc.basic.spring.properties;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.core.io.support.SpringFactoriesLoader;

public class FactoryLoader {

    @Test
    public void load() {
        SpringFactoriesLoader.loadFactories(null, this.getClass().getClassLoader());
    }
}
