package com.charles.srewsample;

import com.charles.srewsample.domain.User;
import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Locale;

import static cn.smallbun.screw.core.constant.DefaultConstants.*;

/**
 * @author charles
 * @date 2021/2/9 16:15
 */
public class FreemarkerTest {
    @Test
    public void t1() throws Exception{
        Configuration configuration = new Configuration(
                Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        configuration.setTemplateLoader(new ClassTemplateLoader(this.getClass(),"/"));
        configuration.setLocale(new Locale(DEFAULT_LOCALE));
        configuration.setDefaultEncoding(DEFAULT_ENCODING);
        Template template = configuration.getTemplate("hello.ftl");
        File file = new File("D://update//hello.html");
        User user = new User("charles","123456");
        try(Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),DEFAULT_ENCODING))){
            template.process(user,out);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
