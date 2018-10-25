package com.example.guava;

import com.google.common.cache.*;
import com.google.common.util.concurrent.RateLimiter;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 *
 */
public class CachesDemo {

    @Test
    public void cache() {
        Cache<String, String> cache = CacheBuilder.newBuilder().maximumSize(1000)
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .removalListener(new RemovalListener<String, String>() {
                    @Override
                    public void onRemoval(RemovalNotification<String, String> notification) {

                    }
                })
                .build();

        cache.put("aaa","cxxxx");
        System.out.println(cache.getIfPresent("aaa"));


    }
}
