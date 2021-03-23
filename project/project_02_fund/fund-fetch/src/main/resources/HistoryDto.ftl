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

<#list codeMap?keys as key>
    @ExcelProperty("${codeMap['${key}']}")
    @MappingField(code = "${key}")
    public String code${key_index};

</#list>
}
