package com.charles.easycode.entity;

import java.io.Serializable;

/**
 * (User)实体类
 *
 * @author charles
 * @since 2021-03-02 16:16:48
 */
public class User implements Serializable {
    private static final long serialVersionUID = 853556413907245461L;

    private Integer id;

    private String username;

    private String sex;

    private Object birthday;

    private String address;

    private String password;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Object getBirthday() {
        return birthday;
    }

    public void setBirthday(Object birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
