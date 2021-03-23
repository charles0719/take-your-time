package com.charles.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HistoryModel {
    @ExcelProperty("日期")
    public String day;
    @ExcelProperty("涨跌幅")
    public String close;

    public HistoryModel(String day, String close) {
        this.day = day;
        this.close = close;
    }
}