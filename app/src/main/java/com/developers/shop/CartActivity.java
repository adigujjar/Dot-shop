 package com.developers.shop;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.developers.shop.Adapters.CartListAdapter;
import com.developers.shop.Utils.Constants;
import com.developers.shop.Utils.Content;
import com.developers.shop.Utils.DatabaseHandler;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    CartListAdapter adapter;
    ListView listView;
    String user = "123" , email = "sad";
    String line = null, size = null, price = null, subtotal, totalTax, totalPr;
    ArrayList<String> list = new ArrayList<String>();
    ArrayList<String> sizelist = new ArrayList<String>();
    ArrayList<String> pricelist = new ArrayList<String>();
    double count = 0;
    float count_tax;
    TextView subtotaltxt, taxtxt, totaltxt;
    NumberFormat formatter = NumberFormat.getNumberInstance();
    DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        getSupportActionBar().setDisplayShowHomeEnabled(true);

        init();
    }

    public void init(){
//        SharedPreferences sharedPreferences = getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE);
//        user = sharedPreferences.getString(Constants.USER_SHARED_PREF, null);
//        email = sharedPreferences.getString(Constants.EMAIL_SHARED_PREF, null);

        subtotaltxt = (TextView) findViewById(R.id.subtotalTxt);
        taxtxt = (TextView) findViewById(R.id.taxTxt);
        totaltxt = (TextView) findViewById(R.id.totaltxt);
        listView = (ListView) findViewById(R.id.c_list);
        db = new DatabaseHandler(this);
        Log.e("Reading: ", "Reading all contacts..");
        List<Content> contacts = db.getAllContacts();

        if (contacts.isEmpty()){
            Toast.makeText(this,"Cart is empty!!!", Toast.LENGTH_SHORT).show();
            line = "n";
        }
        else {
//            formatter.setMinimumFractionDigits(2);
//            formatter.setMaximumFractionDigits(2);

            for (Content cn : contacts) {
                line = cn.getName();
                size = cn.get_quantity();
                price = cn.getPrice();
                list.add(line);
                sizelist.add(size);
                pricelist.add(price);
                count = count + Double.parseDouble(price);
            }
            subtotal = "" + count;//formatter.format(count);
            count_tax = (float) (0.14 * Float.parseFloat(subtotal));
            totalTax = "" + count_tax;//formatter.format(count_tax);
            Double totalPrice = Double.parseDouble(subtotal) + Double.parseDouble(totalTax);
            totalPr = "" + totalPrice;//formatter.format(totalPrice);

            subtotaltxt.setText(""+subtotal);
            taxtxt.setText(""+totalTax);
            totaltxt.setText(""+totalPr);

            adapter = new CartListAdapter(list, sizelist, pricelist, subtotal, CartActivity.this, subtotaltxt, taxtxt , totaltxt );
            listView.setAdapter(adapter);
        }
    }

    public void continueBtn(View v){
        //In onresume fetching value from sharedpreference
        List<Content> contacts = db.getAllContacts();
        String dishes = " ";
        for (Content cn : contacts) {
            dishes = dishes + cn.getName() + ",";
        }
        Log.e("dishz:",""+dishes);
        String time = getCurrentTimeStamp();
        Log.e("time",""+time);
//        new SaveOrderToDatabase(this).execute(user,email,dishes,totaltxt.getText().toString(),time.toString());
//        Intent toPesapal = new Intent(CartActivity.this,PesaPal.class);
//        toPesapal.putExtra("totalPrice",""+totaltxt.getText().toString());
//        startActivity(toPesapal);
    }

    public static String getCurrentTimeStamp(){
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentDateTime = dateFormat.format(new Date()); // Find todays date
            return currentDateTime;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}

