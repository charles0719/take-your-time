package com.charles.srewsample;

import org.junit.jupiter.api.Test;

/**
 * @author charles
 * @date 2021/2/9 18:54
 */
public class OtherTest {
    @Test
    public void t1(){
        Package pkg = ReflectionTest.class.getPackage();
        Package pkg1 = Package.getPackage("java.util");
        System.out.println(pkg);
        System.out.println(pkg1);
    }

    @Test
    public void t2(){
        String property = System.getProperty("user.dir");
        System.out.println(property);
    }
}
