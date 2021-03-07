package com.charles.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * @author charles
 * @date 2021/2/27 9:35
 */
@Builder
@Data
public class AvgFundDto {

    @ExcelProperty("代码")
    private String code;

    @ExcelProperty("名称")
    private String name;

    @ExcelProperty("周日化收益")
    private String week;

    @ExcelProperty("月日化收益")
    private String month;

    @ExcelProperty("3月日化收益")
    private String threemonth;

    @ExcelProperty("6月日化收益")
    private String sixMonth;

    @ExcelProperty("年日化收益")
    private String oneyear;
}
