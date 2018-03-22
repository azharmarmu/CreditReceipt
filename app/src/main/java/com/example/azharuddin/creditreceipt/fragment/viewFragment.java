package com.example.azharuddin.creditreceipt.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.azharuddin.creditreceipt.R;
import com.example.azharuddin.creditreceipt.adapter.CustomerAdapter;
import com.example.azharuddin.creditreceipt.adapter.UserAdapter;
import com.example.azharuddin.creditreceipt.api.CustomerApi;
import com.example.azharuddin.creditreceipt.api.UserApi;
import com.example.azharuddin.creditreceipt.model.CustomerModel;
import com.example.azharuddin.creditreceipt.model.UserModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@SuppressWarnings("unchecked")
public class viewFragment extends Fragment {
    private static final String ARG_PARAM = "param";

    public viewFragment() {
    }

    public static viewFragment newInstance(String param) {
        viewFragment fragment = new viewFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_view, container, false);
        if (mParam.equals("Customer")) {
            changeMapToListCustomer(CustomerApi.customer);
        } else {
            changeMapToListUser(UserApi.user);
        }
        return rootView;
    }

    private void changeMapToListCustomer(HashMap<String, Object> customer) {
        List<CustomerModel> customerList = new ArrayList<>();
        if (customer != null) {
            for (String key : customer.keySet()) {
                HashMap<String, Object> customerDetails = (HashMap<String, Object>) customer.get(key);
                customerList.add(new CustomerModel(key,
                        (String) customerDetails.get("customer_name"),
                        (String) customerDetails.get("customer_address")));
            }
        }
        populateCustomer(customerList);
    }

    private void populateCustomer(List<CustomerModel> customerList) {
        CustomerAdapter adapter = new CustomerAdapter(getContext(), customerList);
        RecyclerView customerView = rootView.findViewById(R.id.rv_customer);
        customerView.removeAllViews();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        customerView.setLayoutManager(layoutManager);
        customerView.setItemAnimator(new DefaultItemAnimator());
        customerView.setAdapter(adapter);
    }


    private void changeMapToListUser(HashMap<String, Object> user) {
        List<UserModel> userList = new ArrayList<>();
        if (user != null) {
            for (String key : user.keySet()) {
                HashMap<String, Object> userDetails = (HashMap<String, Object>) user.get(key);
                userList.add(new UserModel(key,
                        (String) userDetails.get("user_name"),
                        (String) userDetails.get("user_phone")));
            }
        }
        populateUser(userList);
    }

    private void populateUser(List<UserModel> userList) {
        UserAdapter adapter = new UserAdapter(getContext(), userList);
        RecyclerView userView = rootView.findViewById(R.id.rv_customer);
        userView.removeAllViews();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        userView.setLayoutManager(layoutManager);
        userView.setItemAnimator(new DefaultItemAnimator());
        userView.setAdapter(adapter);
    }
}
