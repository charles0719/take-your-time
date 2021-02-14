package com.charles.domain;

import lombok.Builder;
import lombok.Data;

/**
 * @author charles
 * @date 2021/2/11 17:13
 */
@Data
@Builder
public class FundDto {
    //代码	基金简称	日期	单位净值	累计净值	日增长率	近1周	近1月	近3月	近6月	近1年	近2年	近3年	今年来	成立来
    private String code;
    private String name;
    private String curDate;
    private String val1;
    private String val2;
    private String day;
    private String week;
    private String month;
    private String threeMonth;
    private String sixMonth;
    private String year;
    private String twoYear;
    private String threeYear;
    private String thisYear;
    private String base;
}
