package com.charles;


import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author charles
 * @date 2020/7/24 10:42
 */
public class PackageUtils {
    /**
     * git show --name-only -x 展示最近的提交记录
     */
    public static void main(String[] args) {
//        String srcPath = "D:\\BusinessCode\\kbaseui-dev\\target\\kbaseui-dev";
        String srcPath = "D:\\BusinessCode\\kbase-core\\target\\kbase-core";

        String trgPath = "D:\\update\\oppo3_0316\\kbase-core";
        exportFiles(srcPath, trgPath);
        /*
        src/com/startling/base/job/WorkInfoSyncJob.java
        src/com/startling/webservice/StartlingServiceImpl.java
        src/config/applicationContext-quratz.xml
        src/sync/manager/key.properties

        src/main/java/com/eastrobot/module/enums/RelationKeyEnum.java
         */
    }

    public static void exportFiles(String srcPath, String trgPath) {
        try {
            //获取需要更新的文件名
            Set<String> fileNames = getFileNames1();
            System.out.println(fileNames);
            //获取目标目录下的所有文件

            List<File> allFiles = getAllFiles(srcPath);
            List<File> resFiles = new ArrayList<>();
            allFiles.stream().forEach(file -> {
                String path = file.getPath();
                String name = file.getName();
                String baseName = "";
                if (name.lastIndexOf('.') != -1) {
                    baseName = name.substring(0, name.lastIndexOf('.'));
                } else {
                    baseName = name;
                }
                String name_ = baseName;
                //            String suffix = name.substring(name.lastIndexOf('.'));
                fileNames.stream().forEach(item -> {
                    if (path.endsWith(item)) {
                        resFiles.add(file);
                        //内部类处理
                    } else {
                        String regex = item.substring(item.lastIndexOf("\\") + 1).replace(".class", "") + "\\$[^\\.]+\\.class";
                        Pattern pattern = Pattern.compile(regex);
                        //RelationKeyEnum$1.class
                        Matcher matcher = pattern.matcher(name);
//                        RegexpMatcher matcher = RegexpUtils.getMatcher(regex);
                        if (matcher.matches()) {
                            resFiles.add(file);
                        }
                    }
                });
            });
//        System.out.println(resFiles);
            for (File file : resFiles) {
                FileUtils.copyFile(file, new File(file.getPath().replace(srcPath, trgPath)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static Set<String> getFileNames() {
        Scanner scanner = new Scanner(System.in);
        Set<String> arrs = new TreeSet<String>();
        String str = scanner.nextLine();
        while (!str.equals("end")) {
            arrs.add(dealWithStr(str));
            str = scanner.nextLine();
        }
        return arrs;
    }

    public static Set<String> getFileNames1() {
        Set<String> set = new HashSet<>();
        try {
            List<String> list = FileUtils.readLines(new File(System.getProperty("user.dir") + "/src/main/resources/update.txt"));
            for (String str : list) {
                if (StringUtils.isNotBlank(str)) {
                    set.add(dealWithStr(str));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return set;
    }

    public static String dealWithStr(String str) {
        str = str.replaceAll("src/main/java/", "");
        str = str.replaceAll("src/main/webapp/", "");
        str = str.replaceAll("src/main/resources/", "");
        //workflow专属
        str = str.replaceAll("src/", "");
        str = str.replaceAll("webapp/", "");
        str = str.replace("/", "\\");
        str = str.replaceAll(".java", ".class");
        return str;
    }

    public static List<File> getAllFiles(String srcPath) {
        //列出该目录下的所有doc文件，递归（扩展名不必带.doc）
        List<File> fileList = (List<File>) FileUtils.listFiles(new File(srcPath), null, true);
//        List<File> fileList = (List<File>)FileUtils.listFiles(dir,null,true);//列出该目录下的所有文件，递归
//        List<File> fileList = (List<File>)FileUtils.listFiles(srcPath,null,false);//列出该目录下的所有文件，不递归
//        fileList.stream().forEach(file -> {
//            System.out.println(file.getName());
//        });
        return fileList;
    }


}
