package com.example.azharuddin.creditreceipt.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.azharuddin.creditreceipt.R;
import com.example.azharuddin.creditreceipt.api.CustomerApi;
import com.example.azharuddin.creditreceipt.api.UserApi;
import com.example.azharuddin.creditreceipt.utils.Constants;
import com.example.azharuddin.creditreceipt.utils.DialogUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.HashMap;


@SuppressWarnings("unchecked")
public class addFragment extends Fragment {
    private static final String ARG_PARAM = "param";

    public addFragment() {
    }

    public static addFragment newInstance(String param) {
        addFragment fragment = new addFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM, param);
        fragment.setArguments(args);
        return fragment;
    }

    private String mParam;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam = getArguments().getString(ARG_PARAM);
        }
    }

    View rootView;
    EditText name, phone, address;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_add, container, false);

        name = rootView.findViewById(R.id.et_name);
        phone = rootView.findViewById(R.id.et_phone);
        address = rootView.findViewById(R.id.et_address);

        if (mParam.equals("Customer")) {
            name.setHint(getString(R.string.customer_name));
            phone.setVisibility(View.GONE);
            address.setVisibility(View.VISIBLE);
            addCustomer();
        } else {
            name.setHint(getString(R.string.user_name));
            phone.setVisibility(View.VISIBLE);
            address.setVisibility(View.GONE);
            addUser();
        }
        return rootView;
    }

    private void addUser() {
        TextView addUser = rootView.findViewById(R.id.btn_add);
        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, Object> user = UserApi.user;


                String userName = name.getText().toString();
                String userPhone = phone.getText().toString();
                HashMap<String, Object> userMap = new HashMap<>();
                if (!userName.isEmpty()
                        && !userPhone.isEmpty()) {
                    if (!user.containsKey(userName)) {
                        userMap.put(Constants.USER_NAME, userName);
                        userMap.put(Constants.USER_PHONE, userPhone);
                        name.setText("");
                        phone.setText("");
                        UserApi.userDBRef.child(userName)
                                .updateChildren(userMap)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            DialogUtils.appToastShort(getContext(),
                                                    "User added");
                                        } else {
                                            DialogUtils.appToastShort(getContext(),
                                                    "User not added");
                                        }
                                    }
                                });
                    } else {
                        name.setError("User already exists");
                        name.requestFocus();
                    }
                }
            }
        });
    }

    private void addCustomer() {
        TextView addCustomer = rootView.findViewById(R.id.btn_add);
        addCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, Object> customer = CustomerApi.customer;

                EditText name = rootView.findViewById(R.id.et_name);
                EditText address = rootView.findViewById(R.id.et_address);

                String customerName = name.getText().toString();
                String customerAddress = address.getText().toString();
                HashMap<String, Object> customerMap = new HashMap<>();
                if (!customerName.isEmpty()
                        && !customerAddress.isEmpty()) {
                    if (!customer.containsKey(customerName)) {
                        customerMap.put("customer_name", customerName);
                        customerMap.put("customer_address", customerAddress);
                        name.setText("");
                        address.setText("");
                        CustomerApi.customerDBRef.child(customerName)
                                .updateChildren(customerMap)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            DialogUtils.appToastShort(getContext(),
                                                    "Customer added");
                                        } else {
                                            DialogUtils.appToastShort(getContext(),
                                                    "Customer not added");
                                        }
                                    }
                                });
                    } else {
                        name.setError("Customer already exists");
                        name.requestFocus();
                    }
                }
            }
        });
    }
}
