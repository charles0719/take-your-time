package com.charles.srewsample.domain;

import lombok.Data;

/**
 * @author charles
 * @date 2021/2/9 8:58
 */
@Data
public class User {
    private String userName;

    private String password;

    private Cat cat;

    public User(Cat cat) {
        this.cat = cat;
    }

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
}
