package com.charles.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import com.charles.mapping.MappingField;
import lombok.Builder;
import lombok.Data;

/**
 * @author charles
 * @date 2021/3/22 22:08
 */
@Data
@Builder
public class HistoryDto {
    @ExcelProperty("日期")
    public String day;

    @ExcelProperty("兴全合润")
    @MappingField(code = "163406")
    public String code0;

    @ExcelProperty("工银瑞信战略转型")
    @MappingField(code = "000991")
    public String code1;

    @ExcelProperty("创金工业")
    @MappingField(code = "005968")
    public String code2;

    @ExcelProperty("招商中证白酒")
    @MappingField(code = "161725")
    public String code3;

    @ExcelProperty("广发制造")
    @MappingField(code = "004997")
    public String code4;

    @ExcelProperty("易方达中小盘")
    @MappingField(code = "110011")
    public String code5;

    @ExcelProperty("兴全趋势混合")
    @MappingField(code = "163402")
    public String code6;

    @ExcelProperty("景顺长城能源基建")
    @MappingField(code = "260112")
    public String code7;

    @ExcelProperty("中欧价值智选汇报")
    @MappingField(code = "166019")
    public String code8;

    @ExcelProperty("中欧价值发现")
    @MappingField(code = "166005")
    public String code9;

    @ExcelProperty("景顺新兴")
    @MappingField(code = "260108")
    public String code10;

    @ExcelProperty("诺德价值")
    @MappingField(code = "570001")
    public String code11;

    @ExcelProperty("工银瑞信金融地产")
    @MappingField(code = "005937")
    public String code12;

    @ExcelProperty("嘉实价值精选")
    @MappingField(code = "005267")
    public String code13;

    @ExcelProperty("中欧医疗")
    @MappingField(code = "003096")
    public String code14;

}
