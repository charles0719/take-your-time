package com.charles.autopoi;

import com.charles.autopoi.domain.StudentEntity;
import org.apache.poi.ss.usermodel.Workbook;
import org.jeecgframework.poi.excel.ExcelExportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.junit.jupiter.api.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//@SpringBootTest
class AutopoiDemoApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    public void t1(){
        List<StudentEntity> list = new ArrayList<>();
        StudentEntity studentEntity = StudentEntity.builder().id("123").name("charles").birthday(new Date()).build();
        list.add(studentEntity);
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("计算机一班学生","学生"),
                StudentEntity.class, list);
//        writeExcelFile(workbook);
    }

    private void writeExcelFile(Workbook workbook) {
        try {
            FileOutputStream output = new FileOutputStream("d:\\workbook.xls");
            workbook.write(output);
            output.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
