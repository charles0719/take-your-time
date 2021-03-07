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
public class TinyFundDto {

    @ExcelProperty("标签")
    private String tag;

    @ExcelProperty("周收益")
    private String week;

    @ExcelProperty("月收益")
    private String month;

    @ExcelProperty("3月收益")
    private String threemonth;

    @ExcelProperty("6月收益")
    private String sixMonth;

    @ExcelProperty("年收益")
    private String oneyear;
}
