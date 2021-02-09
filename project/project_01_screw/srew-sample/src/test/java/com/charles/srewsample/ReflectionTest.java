package com.charles.srewsample;

import cn.smallbun.screw.core.mapping.FieldMethod;
import com.charles.srewsample.domain.User;
import org.junit.jupiter.api.Test;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author charles
 * @date 2021/2/9 9:06
 */
public class ReflectionTest {
    @Test
    public void t1() throws Exception{
        Map<String,String> map = new HashMap<>();
        map.put("userName","charles");
        map.put("password","123456");
        List<FieldMethod> fieldMethods = getFields(User.class);
        System.out.println(fieldMethods.size());
    }

    private List<FieldMethod> getFields(Class<User> userClass) throws IntrospectionException, NoSuchFieldException {
        BeanInfo beanInfo = Introspector.getBeanInfo(userClass);
        PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
        List<FieldMethod> fieldMethods = new ArrayList<>();
        for (PropertyDescriptor descriptor : descriptors) {
            System.out.println(descriptor.getName());
            Method writeMethod = descriptor.getWriteMethod();
            if (writeMethod == null) {
                continue;
            }
            //获取字段
            Field field = userClass.getDeclaredField(descriptor.getName());
            //获取只写方法
            FieldMethod fieldMethod = new FieldMethod();
            fieldMethod.setField(field);
            fieldMethod.setMethod(writeMethod);
            //放入集合
            fieldMethods.add(fieldMethod);
        }
        return fieldMethods;
    }

}
