package com.charles.domain;

import lombok.Builder;
import lombok.Data;

/**
 * @author charles
 * @date 2021/2/14 11:33
 */
@Data
@Builder
public class FundModel {

    private String code;

    private String val;

    private String count;

    private String desc;

    private String dp;

    private String day;

    private String base;

    private String money;

}
