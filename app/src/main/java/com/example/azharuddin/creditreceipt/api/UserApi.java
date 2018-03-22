package com.example.azharuddin.creditreceipt.api;

import android.util.Log;

import com.example.azharuddin.creditreceipt.listeners.CustomerListener;
import com.example.azharuddin.creditreceipt.listeners.UserListener;
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
public class UserApi {
    public static DatabaseReference userDBRef = FireBaseAPI.ENVIRONMENT.child(Constants.USER);
    public static HashMap<String, Object> user = new HashMap<>();

    public void getUser() {
        userDBRef.keepSynced(true);
        userDBRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    if (dataSnapshot.getValue() != null) {
                        user = (HashMap<String, Object>) dataSnapshot.getValue();
                        userListener.getUser(user);
                    } else {
                        user.clear();
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

    private static UserListener userListener;

    public void setUserListener(UserListener userListener) {
        UserApi.userListener = userListener;
    }
}
