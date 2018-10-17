package com.example.java.reflect.javabean;

//import com.sun.beans.editors.IntegerEditor;
import org.junit.Test;

import java.beans.*;

/**
 * @date 2018/4/24
 */
/*public class PropertyEditorsDemo {

    Integer val = null;

    @Test
    public void editor() throws IntrospectionException {

        BeanInfo beanInfo = Introspector.getBeanInfo(this.getClass());
        PropertyDescriptor[] propertyDescriptors =
                beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
        }

        IntegerEditor integerEditor = new IntegerEditor();
        integerEditor.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                PropertyEditorsDemo.this.val = (Integer) ((PropertyEditor) evt.getSource()).getValue();
            }
        });
        integerEditor.setAsText("1");
        System.out.println(val);
        System.out.println(int.class.isAssignableFrom(int.class));
    }
}*/
