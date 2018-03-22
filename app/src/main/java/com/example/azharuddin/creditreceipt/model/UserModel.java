package com.example.azharuddin.creditreceipt.model;

/**
 * Created by azharuddin on 24/7/17.
 */

public class UserModel {
    private String key, name, phone;

    public UserModel(String key, String name, String phone) {
        this.key = key;
        this.name = name;
        this.phone = phone;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

}
