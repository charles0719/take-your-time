package com.charles.mapping;

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
 * @date 2021/3/23 11:24
 */
public class Mapping {

    public static <T> List<FieldMethod> getFieldMethods(Class<T> clazz) throws IntrospectionException,
            NoSuchFieldException {
        //结果集合
        List<FieldMethod> fieldMethods = new ArrayList<>();
        //BeanInfo
        BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
        PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
        //循环处理值
        for (PropertyDescriptor pd : pds) {
            Method writeMethod = pd.getWriteMethod();
            if (writeMethod == null) {
                continue;
            }
            //获取字段
            Field field = clazz.getDeclaredField(pd.getName());
            //获取只写方法
            FieldMethod fieldMethod = new FieldMethod();
            fieldMethod.setField(field);
            fieldMethod.setMethod(writeMethod);
            //放入集合
            fieldMethods.add(fieldMethod);
        }
        return fieldMethods;
    }

    public static <T> Map<String, Method> getAnnoMethods(Class<T> clazz) throws IntrospectionException,
            NoSuchFieldException {
        //BeanInfo
        BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
        PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
        Map<String, Method> map = new HashMap<>();
        //循环处理值
        for (PropertyDescriptor pd : pds) {
            Method writeMethod = pd.getWriteMethod();
            if (writeMethod == null) {
                continue;
            }
            //获取字段
            Field field = clazz.getDeclaredField(pd.getName());
            MappingField mappingField = field.getAnnotation(MappingField.class);
            if (mappingField != null) {
                map.put(mappingField.code(), writeMethod);
            }
        }
        return map;
    }
}
