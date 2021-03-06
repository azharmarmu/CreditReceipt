package com.example.azharuddin.creditreceipt.api;

import android.util.Log;

import com.example.azharuddin.creditreceipt.listeners.CustomerListener;
import com.example.azharuddin.creditreceipt.utils.Constants;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;


/**
 * Created by azharuddin on 22/11/17.
 */

@SuppressWarnings("unchecked")
public class CustomerApi {
    public static DatabaseReference customerDBRef = FireBaseAPI.ENVIRONMENT.child(Constants.CUSTOMER);
    public static HashMap<String, Object> customer = new HashMap<>();

    public void getCustomer() {
        customerDBRef.keepSynced(true);
        customerDBRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    if (dataSnapshot.getValue() != null) {
                        customer = (HashMap<String, Object>) dataSnapshot.getValue();
                        customerListener.getCustomer(customer);
                    } else {
                        customer.clear();
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

    private static CustomerListener customerListener;

    public void setCustomerListener(CustomerListener customerListener) {
        CustomerApi.customerListener = customerListener;
    }
}
