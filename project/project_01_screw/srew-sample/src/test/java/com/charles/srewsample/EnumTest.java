package com.charles.srewsample;

import cn.smallbun.screw.core.engine.EngineConfig;
import cn.smallbun.screw.core.engine.EngineFileType;
import cn.smallbun.screw.core.engine.TemplateEngine;
import com.charles.srewsample.domain.Cat;
import com.charles.srewsample.domain.User;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;

/**
 * @author charles
 * @date 2021/2/9 10:21
 */
public class EnumTest {
    @Test
    public void t1() throws Exception{
        EngineFileType fileType = EngineFileType.valueOf("HTML");
        Class<EngineFileType> EngineFileType = fileType.getDeclaringClass();
        Constructor c =  User.class
                .getConstructor(Cat.class);
        Cat cat = new Cat();
        Object o = c.newInstance(cat);
        System.out.println(o);
    }
}
