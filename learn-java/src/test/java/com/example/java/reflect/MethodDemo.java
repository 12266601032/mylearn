package com.example.java.reflect;

import org.junit.Test;

/**
 * ClassName: MethodDemo<br/>
 * Function:  TODO: ADD FUNCTION. <br/>
 * Reason:	  TODO: ADD REASON. <br/>
 * Date:     2018年09月13日 15:19 <br/>
 *
 * @author lcc
 * @see
 * @since
 */
public class MethodDemo {

    @Test
    public void testGetThisMethod() {
        //方式1
        System.out.println(new RuntimeException().getStackTrace()[0].getMethodName());
        //方法2
        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
    }
}
