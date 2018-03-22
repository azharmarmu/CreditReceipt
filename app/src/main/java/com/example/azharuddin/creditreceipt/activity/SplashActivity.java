package com.example.azharuddin.creditreceipt.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.example.azharuddin.creditreceipt.api.AdminApi;
import com.example.azharuddin.creditreceipt.api.CompanyApi;
import com.example.azharuddin.creditreceipt.api.CustomerApi;
import com.example.azharuddin.creditreceipt.api.UserApi;
import com.example.azharuddin.creditreceipt.utils.Constants;


public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RelativeLayout layout = new RelativeLayout(this);
        ProgressBar progressBar = new ProgressBar(SplashActivity.this, null, android.R.attr.progressBarStyleLarge);
        progressBar.setIndeterminate(true);
        progressBar.setVisibility(View.VISIBLE);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(100, 100);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        layout.addView(progressBar, params);

        setContentView(layout);

        new CompanyApi().getCompany(); //calling company details Api
        new AdminApi().getAdmin(); //calling admin details Api
        new CustomerApi().getCustomer(); //calling customer details Api
        new UserApi().getUser(); //calling user details Api

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent activity;
                if (isLoggedIn()) {
                    activity = new Intent(SplashActivity.this, LandingActivity.class);
                } else {
                    activity = new Intent(SplashActivity.this, LoginActivity.class);
                }
                activity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                activity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(activity);
                finish();
            }
        }, 1000);

    }

    private boolean isLoggedIn() {
        return Constants.AUTH.getCurrentUser() != null;
    }
}
