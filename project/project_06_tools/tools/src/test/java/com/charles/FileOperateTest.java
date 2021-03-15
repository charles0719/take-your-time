package com.charles;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

/**
 * @author charles
 * @date 2021/3/15 15:13
 */
public class FileOperateTest {

    @Test
    public void splitFile() {
        // 原文件(待拆分的文件)
        File resFile = new File("D:\\update\\test\\AIA AI2.0 iVoice运维手册_V1.0_20200429.docx");
        // 拆分后的目录
        File splitDir = new File("D:\\update\\output");

//        splitFile(resFile, splitDir);

        mergeFile(splitDir);
    }

    private void mergeFile(File splitDir) {
        try {
            List<File> files = (List<File>) FileUtils.listFiles(splitDir, new String[]{"part"}, false);
            List<FileInputStream> inputs = new ArrayList<>();
            for (int i = 0; i < files.size(); i++) {
                inputs.add(new FileInputStream(files.get(i)));
            }
            Enumeration<FileInputStream> en = Collections.enumeration(inputs);
            // 多个流 ->1个流
            SequenceInputStream sin = new SequenceInputStream(en);
//            OutputStream out = new FileOutputStream(new File(splitDir,"hello.docx"));
            OutputStream out = new FileOutputStream("D:\\update\\output\\a.docx");
            byte[] bytes = new byte[1024 * 1024];
            int len = -1;
            while ((len = sin.read(bytes)) != -1) {
                out.write(bytes, 0, len);
            }
            sin.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void splitFile(File resFile, File splitDir) {
        try {
            if (!splitDir.exists()) {
                splitDir.createNewFile();
            }
            FileInputStream in = new FileInputStream(resFile);
            FileOutputStream out = null;
            byte[] bytes = new byte[1024 * 1024];
            int length = -1;
            int count = 0;
            while ((length = in.read(bytes)) != -1) {
                out = new FileOutputStream(new File(splitDir, count++ + ".part"));
                out.write(bytes, 0, length);
                out.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
