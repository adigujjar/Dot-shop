package com.developers.shop;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class SplashScreen extends AppCompatActivity {

    Handler handler;
    Runnable runnable;
    TextView check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        check = findViewById(R.id.check);

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
//                if (!isOnline()){
                startActivity(new Intent(SplashScreen.this, LoginActivity.class));
                finish();
//                }

            }
        };

        handler.postDelayed(runnable, 3000);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!isOnline()){
            Toast.makeText(this,"No Internet Connection!", Toast.LENGTH_LONG).show();
        }
    }

    public boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

}
