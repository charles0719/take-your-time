package com.charles;

import com.charles.common.Tools;
import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.junit.Test;
import java.io.*;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author charles
 * @date 2021/2/9 16:15
 */
public class FreemarkerTest {
    @Test
    public void t1() throws Exception {
        Configuration configuration = new Configuration(
                Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        configuration.setTemplateLoader(new ClassTemplateLoader(this.getClass(), "/"));
        configuration.setLocale(new Locale("zh_CN"));
        configuration.setDefaultEncoding("UTF-8");
        Template template = configuration.getTemplate("HistoryDto.ftl");
        File file = new File(System.getProperty("user.dir") + "/src/main/resources/HistoryDto_update.txt");
        Map<String, Object> map = new HashMap<>();
        map.put("codeMap", Tools.readFocus());
        try (Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"))) {
            template.process(map, out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
