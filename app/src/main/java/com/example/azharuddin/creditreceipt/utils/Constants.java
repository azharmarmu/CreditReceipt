package com.example.azharuddin.creditreceipt.utils;

import android.annotation.SuppressLint;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by azharuddin on 26/5/17.
 */

public class Constants {
    public static final FirebaseAuth AUTH = FirebaseAuth.getInstance();
    public static final FirebaseDatabase DATABASE = FirebaseDatabase.getInstance();
    public static final String ENV = "development";
    //public static final String ENV = "production";

    public static final String KEY = "key";

    /*RealTime Table Name*/
    public static final String USERS = "users";
    public static final String COMPANY = "company";
    public static final String ADMIN = "admin";
    public static final String PRODUCTS = "products";
    public static final String SALES_MAN = "sales_man";
    public static final String CUSTOMER = "customer";
    public static final String USER = "user";

    /*FireStore Table Name*/
    public static final String BILLING = ENV + "_" + "billing";
    public static final String TAKEN = ENV + "_" + "taken";
    public static final String ORDER = ENV + "_" + "order";

    public static final String EDIT = "edit";
    public static final String CHECK = "check";

    public static final int SALES_MAN_CODE = 123;

    public static final String PRODUCT = "Product";
    public static final String PARTY = "Party";

    public static final String ALL = "ALL";
    public static final String ROUTE = "Route";

    public static final String CLOSED = "close";
    public static final String START = "start";
    public static final String STARTED = "started";

    public static final String UPDATE = "update";

    /*Company Detailsw-Table*/
    public static final String COMPANY_NAME = "name";
    public static final String COMPANY_PHONE = "phone";
    public static final String COMPANY_EMAIL = "email";


    /*SalesMan-Table*/
    public static final String SALES_MAN_NAME = "sales_man_name";
    public static final String SALES_MAN_PHONE = "sales_man_phone";


    /*Balance-Table*/
    public static final String CUSTOMER_NAME = "customer_name";
    public static final String CUSTOMER_ADDRESS = "customer_address";

    public static final String USER_NAME = "user_name";
    public static final String USER_PHONE = "user_phone";

    /*Fire-Store*/
    public static final String BALANCE = "balance";


    @SuppressLint("SimpleDateFormat")
    public static String currentDate() throws ParseException {
        return new SimpleDateFormat("dd/MM/yyyy")
                .format(new Date(System.currentTimeMillis()));
    }
}
