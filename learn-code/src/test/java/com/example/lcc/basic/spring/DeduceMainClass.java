package com.example.lcc.basic.spring;

import org.junit.Test;
import org.springframework.core.io.support.SpringFactoriesLoader;

/**
 * @date 2018/4/9
 */
public class DeduceMainClass {

    public static void main(String[] args) {
    }

    @Test
    public void deduceMainClass() throws Exception {
        /**
         * 利用异常中的堆栈信息来推断main方法
         * 参考自SpringApplication
         */
        try {
            StackTraceElement[] stackTrace = new RuntimeException().getStackTrace();
            for (StackTraceElement stackTraceElement : stackTrace) {
                if ("main".equals(stackTraceElement.getMethodName())) {
                    Class.forName(stackTraceElement.getClassName());
                    System.out.println(stackTraceElement.getClassName());
                }
            }
        }
        catch (ClassNotFoundException ex) {
            // Swallow and continue
        }
    }

}
