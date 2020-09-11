package com.developers.shop;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.developers.shop.Fragments.RegisterNewCustomerFragment;
import com.developers.shop.Fragments.RegisterShopKeeperFragment;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    String selectUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        selectUser = getIntent().getStringExtra("selectedUser");

        if (!TextUtils.isEmpty(selectUser)) {
            if (selectUser.equals("customer")) {
                RegisterNewCustomerFragment frag = new RegisterNewCustomerFragment();
                loadFragment(frag);
            } else if (selectUser.equals("shop keeper")) {
                RegisterShopKeeperFragment frag = new RegisterShopKeeperFragment();
                loadFragment(frag);
            }
        }

    }

    @SuppressLint("ResourceType")
    public void loadFragment(Fragment fragment){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        ft.addToBackStack(null);

        ft.replace(R.id.frame, fragment,"Fragment");
        ft.commitAllowingStateLoss();
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id){
            case android.R.id.home:
                this.finish();
            break;
            default:

            break;
        }
    }
}
