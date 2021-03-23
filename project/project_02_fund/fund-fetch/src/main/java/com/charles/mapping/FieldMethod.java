package com.charles.mapping;

import lombok.Data;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

@Data
public class FieldMethod {

    /**
     * 属性
     */
    private Field field;

    /**
     * 方法
     */
    private Method method;
}