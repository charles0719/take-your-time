package com.charles.domain;

import lombok.Data;

@Data
public class HistoryModel {
    public String day;
    public String close;

    public HistoryModel(String day, String close) {
        this.day = day;
        this.close = close;
    }
}