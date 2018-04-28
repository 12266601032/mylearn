package com.example.lcc.basic.spring.test;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import java.util.Map;

public class MockitoBeansPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        Map<Class<?>, MockitoBeansTestExecutionListener.MockBeanWrapper> allMockBeans = MockitoBeansTestExecutionListener.resolvedAllMockBeans();
        for (Map.Entry<Class<?>, MockitoBeansTestExecutionListener.MockBeanWrapper> mockBeanWrapperEntry : allMockBeans.entrySet()) {
            beanFactory.registerResolvableDependency(mockBeanWrapperEntry.getKey(), mockBeanWrapperEntry.getValue().getMockObject());
        }
    }

}
