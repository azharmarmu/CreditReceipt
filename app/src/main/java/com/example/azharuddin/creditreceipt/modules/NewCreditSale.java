package com.example.azharuddin.creditreceipt.modules;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.azharuddin.creditreceipt.R;
import com.example.azharuddin.creditreceipt.activity.LandingActivity;
import com.example.azharuddin.creditreceipt.api.CustomerApi;
import com.example.azharuddin.creditreceipt.utils.Constants;
import com.example.azharuddin.creditreceipt.utils.DateUtils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by azharuddin on 24/7/17.
 */

@SuppressWarnings("unchecked")
public class NewCreditSale {
    private static HashMap<String, Object> balance = new HashMap<>();

    private Activity activity;
    private View itemView;
    private TextView datePicker;
    private AutoCompleteTextView customerName;
    private EditText customerAmount;
    private TextView save;

    public void evaluate(LandingActivity activity, View itemView) {
        try {
            this.activity = activity;
            this.itemView = itemView;

            initViews();

            new DateUtils().currentDateSet(datePicker);
            new DateUtils().datePicker(activity, datePicker);

            getCustomerDetails();
            saveCredit();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private List<String> custName;

    private void getCustomerDetails() {
        custName = new ArrayList<>();
        HashMap<String, Object> customer = CustomerApi.customer;
        if (customer.size() > 0) {
            for (String customerKey : customer.keySet()) {
                HashMap<String, Object> customerDetails = (HashMap<String, Object>) customer.get(customerKey);
                custName.add(customerDetails.get(Constants.CUSTOMER_NAME).toString());
            }
        }
        ArrayAdapter<List<String>> adapter = new ArrayAdapter(
                activity,
                android.R.layout.select_dialog_item,
                custName
        );
        customerName.setAdapter(adapter);
    }

    private final String TAG = "NewCreditSale";

    private void saveCredit() {
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = customerName.getText().toString();
                String date = datePicker.getText().toString();
                int amount = Integer.parseInt(customerAmount.getText().toString());

                HashMap<String, Object> details = new HashMap<>();
                details.put("bill_date", date);
                details.put("bill_amount", amount);
                details.put("entry_date", new DateUtils().currentDate());
                details.put("status", false);
                details.put("admin", "ADMIN-1"); // TODO: 19/3/18 --> need to change admin as per login

                if (custName.contains(name) && amount > 0) {
                    FirebaseFirestore.getInstance()
                            .collection(Constants.ENV)
                            .document(Constants.BALANCE)
                            .collection(name)
                            .add(details)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                                    Toast.makeText(activity, "Entry successful", Toast.LENGTH_SHORT).show();

                                    customerName.setText("");
                                    new DateUtils().datePicker(activity, datePicker);
                                    customerAmount.setText("");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w(TAG, "Error adding document", e);
                                }
                            });
                } else {
                    if (!custName.contains(name)) {
                        customerName.setError("Customer not exists");
                        customerName.requestFocus();
                    } else {
                        customerAmount.setError("Amount cannot be zero or less");
                        customerAmount.requestFocus();
                    }
                }

            }
        });
    }

    private void initViews() {
        customerName = itemView.findViewById(R.id.et_customer_name);
        datePicker = itemView.findViewById(R.id.et_date_picker);
        customerAmount = itemView.findViewById(R.id.et_amount);
        save = itemView.findViewById(R.id.btn_save);
    }

}
