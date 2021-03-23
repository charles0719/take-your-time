package com.charles.common;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author charles
 * @date 2021/3/23 16:59
 */
public class Tools {

    public static Map<String, String> readFocus() throws Exception {
        Map<String, String> codeMap = new HashMap<>();
        String path = System.getProperty("user.dir") + "/src/main/resources/focus.txt";
        File file = new File(path);
        List<String> list = FileUtils.readLines(file, "utf-8");
        for (String item : list) {
            if (item.startsWith("#")) {
                continue;
            }
            String[] splits = item.split(",");
            String code = splits[0];
            String name = splits[1];
            codeMap.put(code, name);
        }
        return codeMap;
    }
}
