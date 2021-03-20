package com.charles;

import org.junit.Test;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author charles
 * @date 2021/3/16 18:20
 */
public class PkgTest {

    @Test
    public void t1() {
//        String srcPath = "D:\\BusinessCode\\kbase-report\\target\\kbase-report";
        String srcPath = "D:\\BusinessCode\\kbase-core\\target\\kbase-core";
//        String srcPath = "D:\\BusinessCode\\kbase-core\\out\\artifacts\\kbase_workflow";
//        String srcPath = "D:\\BusinessCode\\kbaseui-std\\target\\kbaseui-std";
        String appName = "oppo";
        String updatePath = "D:\\update";
        String trgPath = generatePath(srcPath, appName, updatePath);
//        String trgPath = "D:\\update\\oppo_0316\\kbase-core";
        PackageUtils.exportFiles(srcPath, trgPath);
    }

    private String generatePath(String srcPath, String appName, String updatePath) {
        String projectName = new File(srcPath).getName();
        String trgPath = updatePath + File.separator + appName + File.separator + appName + "_" + LocalDate.now().format(DateTimeFormatter.ofPattern("MMdd")) + File.separator + projectName;
        System.out.println(trgPath);
        return trgPath;
    }


    @Test
    public void t10() {
//        String regex = "RelationKeyEnum\\$[^\\d]+\\.class";
//        String regex = "RelationKeyEnum\\$[^\\.]+\\.class";
        String regex = "RelationKeyEnum\\$\\d+\\.class";
        Pattern pattern = Pattern.compile(regex);
        String item = "RelationKeyEnum$1.class";
        Matcher matcher = pattern.matcher(item);
//                        RegexpMatcher matcher = RegexpUtils.getMatcher(regex);
        if (matcher.matches()) {
            System.out.println("match ok");
        }
    }

    @Test
    public void t11() {
//        System.out.println(PackageUtils.getFileNames());
        System.out.println(PackageUtils.getFileNames1());
    }
}
