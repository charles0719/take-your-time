package com.charles.domain;

import com.alibaba.excel.annotation.ExcelProperty;
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
    @ExcelProperty("涨跌幅1")
    public String code1;

    @ExcelProperty("涨跌幅2")
    public String code2;

    @ExcelProperty("涨跌幅3")
    public String code3;

    @ExcelProperty("涨跌幅3")
    public String code4;

    @ExcelProperty("涨跌幅3")
    public String code5;

    @ExcelProperty("涨跌幅3")
    public String code6;

    @ExcelProperty("涨跌幅3")
    public String code7;

}
