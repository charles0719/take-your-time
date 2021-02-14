package com.charles;

import com.charles.domain.FundModel;
import com.charles.util.PropertiesUtil;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.util.List;

/**
 * @author charles
 * @date 2021/2/14 11:27
 */
public class FileReadTest {

    @Test
    public void t1(){
        System.out.println(PropertiesUtil.getString("third.code"));
    }

    @Test
    public void t2() throws Exception{
        String path = System.getProperty("user.dir")+"/src/main/resources/fund.txt";
        System.out.println(path);
        File file = new File(path);
        List<String> list = FileUtils.readLines(file, "utf-8");
        for (String str : list) {
            String[] items = str.split(",");
            FundModel fundModel = FundModel.builder().code(items[0]).val(items[1]).count(items[2]).desc(items[3]).build();
            System.out.println(fundModel);
        }
    }

}
