package com.charles.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * @author charles
 * @date 2021/2/27 8:29
 */
@Data
@Builder
public class FundPeriod {

    @ExcelProperty("基金code")
    private String code;

    @ExcelProperty("基金名称")
    private String name;

    @ExcelProperty("0218-0226")
    private float sum1;

    @ExcelProperty("0208-0210")
    private float sum2;

    @ExcelProperty("0201-0226")
    private float sum3;

}
