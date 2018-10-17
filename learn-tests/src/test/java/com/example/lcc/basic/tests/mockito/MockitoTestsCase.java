package com.example.lcc.basic.tests.mockito;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

public class MockitoTestsCase {

    /*注解方式进行mock*/
    @Mock
    MockObject mockObject;
    @InjectMocks
    InjectMockObjects injectMockObjects = new InjectMockObjects();

    /*给目标类或者接口手动创建mock对象*/
    MockObject mockObject2 = Mockito.mock(MockObject.class);

    /*创建一个间谍对象，如果进行stub 则执行实际对象的逻辑*/
    MockObject spyObject = Mockito.spy(new MockObject() {
        @Override
        public void doSomething() {
            System.out.println("the real object did.");
        }

        @Override
        public Result getSomeResult() {
            return () -> {
                System.out.println("the real result.");
            };
        }
    });

    @Before
    public void setup() {
        //初始化本实例中的mock对象，使用mock代理对象代替目标对象
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void name() throws Exception {
        assertNotNull(mockObject);
        assertNotNull(injectMockObjects.mockObject);
        assertNotNull(mockObject2);

        //创建一个与mockObject 调用doSomething()时绑定的stubber
        doNothing().when(mockObject).doSomething();
        //mock对象方法执行
        mockObject.doSomething();
        //验证某个方法的调用次数
        verify(mockObject, times(1)).doSomething();
        //验证后续不会再有mockObject相关的方法调用
        verifyNoMoreInteractions(mockObject);
        verifyZeroInteractions(mockObject);

        spyObject.doSomething();
        doAnswer(invocation -> {
            System.out.println("stubber did.");
            return null;
        }).when(spyObject).doSomething();
        spyObject.doSomething();

    }

    class InjectMockObjects {
        MockObject mockObject;
    }

    interface MockObject {

        void doSomething();

        Result getSomeResult();

    }

    interface Result {
        void inspectionResult();
    }

}
