package com.example.lcc.basic.spring.test.demo;

import com.example.lcc.basic.spring.test.MockitoBeansPostProcessor;
import com.example.lcc.basic.spring.test.MockitoBeansTestExecutionListener;
import com.example.lcc.basic.spring.test.annotation.ImportMockBeans;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import static org.mockito.Mockito.doReturn;


@TestExecutionListeners({MockitoBeansTestExecutionListener.class})
@ContextConfiguration(classes = {SimpleTestCaseUsingAnnontation.class})
@ComponentScan(basePackageClasses = {SimpleTestCaseUsingAnnontation.class})
@ImportMockBeans({MessageSupplier.class})
public class SimpleTestCaseUsingAnnontation extends AbstractJUnit4SpringContextTests {

    @Autowired
    private SomeService someService;
    @Autowired
    MessageSupplier messageSupplier;


    @Test
    public void test() {

        doReturn("this is mock message.")
                .when(messageSupplier)
                .getMessage();
        someService.printMessage();

    }

    @Test
    public void test2(){
        someService.printMessage();
    }

    @Bean
    public BeanFactoryPostProcessor mockBeansPostProcessor(){
        return new MockitoBeansPostProcessor();
    }

}
