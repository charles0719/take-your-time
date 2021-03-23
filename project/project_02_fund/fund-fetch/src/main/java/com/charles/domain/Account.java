package com.charles.domain;

import lombok.Builder;
import lombok.Data;

/**
 * @author charles
 * @date 2021/3/22 11:55
 */
@Data
@Builder
public class Account {

    private double flowAccount;

//    private double freezeAccont;

    private int count;

    private double profit;
}
