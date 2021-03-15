package com.charles;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.util.List;

/**
 * @author charles
 * @date 2021/3/15 16:19
 */
public class FileCompareTest {
    @Test
    public void t3() throws Exception{
        String path1 = System.getProperty("user.dir")+"/src/main/resources/1.txt";
        String path2 = System.getProperty("user.dir")+"/src/main/resources/2.txt";
        List<String> list1 = FileUtils.readLines(new File(path1), "utf-8");
        List<String> list2 = FileUtils.readLines(new File(path2), "utf-8");
        list1.removeAll(list2);
        System.out.println(list1.size());
    }
}
