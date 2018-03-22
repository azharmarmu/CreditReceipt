package com.example.azharuddin.creditreceipt.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.azharuddin.creditreceipt.R;
import com.example.azharuddin.creditreceipt.api.CompanyApi;
import com.example.azharuddin.creditreceipt.api.CustomerApi;
import com.example.azharuddin.creditreceipt.api.UserApi;
import com.example.azharuddin.creditreceipt.listeners.CustomerListener;
import com.example.azharuddin.creditreceipt.listeners.UserListener;
import com.example.azharuddin.creditreceipt.modules.Balance;
import com.example.azharuddin.creditreceipt.modules.CreditSale;
import com.example.azharuddin.creditreceipt.modules.Customer;
import com.example.azharuddin.creditreceipt.modules.NewCreditSale;
import com.example.azharuddin.creditreceipt.modules.Receipt;
import com.example.azharuddin.creditreceipt.modules.User;
import com.example.azharuddin.creditreceipt.utils.Constants;

import java.util.HashMap;

import com.example.azharuddin.creditreceipt.listeners.CompanyListener;

public class LandingActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        CompanyListener,
        CustomerListener,
        UserListener {


    TextView companyName, companyPhone, companyMail;

    public static int whereIam = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        // TODO: 15/3/18 Call relevant Listener
        new CompanyApi().setCompanyListener(this);
        new CustomerApi().setCustomerListener(this);
        new UserApi().setUserListener(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.balance));
        }

        balance = findViewById(R.id.balance_holder);
        creditSale = findViewById(R.id.credit_sale_holder);
        receipt = findViewById(R.id.receipt_holder);
        customer = findViewById(R.id.customer_holder);
        newCreditSale = findViewById(R.id.nav_new_credit_sale_holder);
        user = findViewById(R.id.user_holder);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        navSetup();

        switchScreen();
    }

    private void navSetup() {
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        try {
            companyName = headerView.findViewById(R.id.admin_company);
            companyPhone = headerView.findViewById(R.id.admin_phone);
            companyMail = headerView.findViewById(R.id.admin_email);

            HashMap<String, Object> company = CompanyApi.company;

            final String name = company.get("name").toString();
            final String number = company.get("phone").toString();
            final String mail = company.get("email").toString();

            companyName.setText(name.toUpperCase());
            companyPhone.setText(number);
            companyMail.setText(mail.toLowerCase());

            RelativeLayout nav = headerView.findViewById(R.id.company_name);
            nav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    editCustomerDialog(name, number, mail);
                }
            });

        } catch (NullPointerException e) {
            Log.e("Error", e.getMessage());
        }

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);
    }

    private void editCustomerDialog(String name, String number, String mail) {
        final Dialog dialog = new Dialog(LandingActivity.this, R.style.DialogTheme);
        dialog.setContentView(R.layout.dialog_company);

        Window window = dialog.getWindow();
        assert window != null;
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.TOP;

        final EditText etName = dialog.findViewById(R.id.et_company_name);
        final EditText etPhone = dialog.findViewById(R.id.et_company_phone);
        final EditText etMail = dialog.findViewById(R.id.et_company_mail);

        etName.setText(name);
        etPhone.setText(number);
        etMail.setText(mail);

        TextView setAdminDetails = dialog.findViewById(R.id.btn_set_admin_details);
        setAdminDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String compName = etName.getText().toString();
                String compPhone = etPhone.getText().toString();
                String compMail = etMail.getText().toString();
                if (!compName.isEmpty() && !compPhone.isEmpty() && !compMail.isEmpty()) {
                    CompanyApi.companyDBRef.removeValue();
                    HashMap<String, Object> companyMap = new HashMap<>();
                    companyMap.put(Constants.COMPANY_NAME, compName);
                    companyMap.put(Constants.COMPANY_PHONE, compPhone);
                    companyMap.put(Constants.COMPANY_EMAIL, compMail);
                    CompanyApi.companyDBRef.updateChildren(companyMap);
                    companyName.setText(compName.toUpperCase());
                    companyPhone.setText(compPhone);
                    companyMail.setText(compMail);

                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_balance) {
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(getString(R.string.balance));
            }
            whereIam = 0;
        } else if (id == R.id.nav_credit_sale) {
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(getString(R.string.credit_sale));
            }
            whereIam = 1;
        } else if (id == R.id.nav_receipts) {
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(getString(R.string.receipts));
            }
            whereIam = 2;
        } else if (id == R.id.nav_customer) {
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(getString(R.string.customer));
            }
            whereIam = 3;
        } else if (id == R.id.nav_new_credit_sale) {
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(getString(R.string.new_credit_sale));
            }
            whereIam = 4;
        } else if (id == R.id.nav_user) {
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(getString(R.string.user));
            }
            whereIam = 5;
        }
        switchScreen();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    View balance, creditSale, receipt, customer, newCreditSale, user;

    private void switchScreen() {
        balance.setVisibility(View.GONE);
        creditSale.setVisibility(View.GONE);
        receipt.setVisibility(View.GONE);
        customer.setVisibility(View.GONE);
        newCreditSale.setVisibility(View.GONE);
        user.setVisibility(View.GONE);

        switch (whereIam) {
            case 0:
                balance.setVisibility(View.VISIBLE);
                new Balance().evaluate(this, balance);
                break;
            case 1:
                creditSale.setVisibility(View.VISIBLE);
                new CreditSale().evaluate(this, creditSale);
                break;
            case 2:
                receipt.setVisibility(View.VISIBLE);
                new Receipt().evaluate(this, receipt);
                break;
            case 3:
                customer.setVisibility(View.VISIBLE);
                new Customer().evaluate(this, customer);
                break;
            case 4:
                newCreditSale.setVisibility(View.VISIBLE);
                new NewCreditSale().evaluate(this, newCreditSale);
                break;
            case 5:
                user.setVisibility(View.VISIBLE);
                new User().evaluate(this, user);
                break;
        }
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
    }

    @Override
    public void getCompany(HashMap<String, Object> customer) {
        navSetup();
    }

    @Override
    public void getCustomer(HashMap<String, Object> customer) {
        switchScreen();
    }

    @Override
    public void getUser(HashMap<String, Object> user) {
        switchScreen();
    }
}
