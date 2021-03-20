package com.charles.autopoi.domain;

import lombok.Builder;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;

import java.util.Date;

@Data
@Builder
public class StudentEntity implements java.io.Serializable {
    /**
     * id
     */
    private String        id;
    /**
     * 学生姓名
     */
    @Excel(name = "学生姓名", height = 20, width = 30)
    private String        name;
    /**
     * 学生性别
     */
    @Excel(name = "学生性别", replace = { "男_1", "女_2" }, suffix = "生")
    private int           sex;

    @Excel(name = "出生日期", databaseFormat = "yyyyMMddHHmmss", format = "yyyy-MM-dd", width = 20)
    private Date birthday;

    @Excel(name = "进校日期", databaseFormat = "yyyyMMddHHmmss", format = "yyyy-MM-dd")
    private Date registrationDate;

 }