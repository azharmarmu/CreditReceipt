package com.example.azharuddin.creditreceipt.model;

/**
 * Created by azharuddin on 24/7/17.
 */

public class CustomerModel {
    private String key, name, address;

    public CustomerModel(String key, String name, String address) {
        this.key = key;
        this.name = name;
        this.address = address;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

}
