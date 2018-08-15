package com.example.lcc.basic.tests.mockito;

import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class MockDefaultMethod {

    @Test
    public void test() {

        InterfaceA obj = new InterfaceA() {
        };
        InterfaceA spiedObj = spy(obj);
        when(spiedObj.defMethod()).thenCallRealMethod();
        spiedObj.defMethod();
    }

    @Test
    public void test2(){
        InterfaceA obj = new InterfaceA() {
        };
        InterfaceA spiedObj = spy(obj);
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                if(invocation.getMethod().isDefault()){
                    invocation.callRealMethod();
                    return invocation.getMethod().invoke(obj, invocation.getArguments());
                }
                return invocation.callRealMethod();
            }
        }).when(spiedObj).defMethod();
        spiedObj.defMethod();
    }


    interface InterfaceA {

        default String defMethod() {
            System.out.println("output from default method.");
            return "";
        }
    }
}
