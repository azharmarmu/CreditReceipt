package com.example.azharuddin.creditreceipt.api;

import android.util.Log;

import com.example.azharuddin.creditreceipt.utils.Constants;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import com.example.azharuddin.creditreceipt.listeners.CompanyListener;


/**
 * Created by azharuddin on 22/11/17.
 */

@SuppressWarnings("unchecked")
public class CompanyApi {
    public static DatabaseReference companyDBRef = FireBaseAPI.ENVIRONMENT.child(Constants.COMPANY);
    public static HashMap<String, Object> company = new HashMap<>();

    public void getCompany() {
        companyDBRef.keepSynced(true);
        companyDBRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    if (dataSnapshot.getValue() != null) {
                        company = (HashMap<String, Object>) dataSnapshot.getValue();
                        companyListener.getCompany(company);
                    } else {
                        company.clear();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("FireError", databaseError.getMessage());
            }
        });
    }

    private static CompanyListener companyListener;

    public void setCompanyListener(CompanyListener companyListener) {
        CompanyApi.companyListener = companyListener;
    }
}
