package com.example.azharuddin.creditreceipt.api;

import android.annotation.SuppressLint;

import com.example.azharuddin.creditreceipt.utils.Constants;
import com.google.firebase.database.DatabaseReference;


/**
 * Created by azharuddin on 24/7/17.
 */
@SuppressLint("SimpleDateFormat")
@SuppressWarnings("unchecked")
public class FireBaseAPI {
    static final DatabaseReference ENVIRONMENT = Constants.DATABASE.getReference(Constants.ENV);
}
