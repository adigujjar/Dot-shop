package com.developers.shop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.developers.shop.Utils.Content;
import com.developers.shop.Utils.DatabaseHandler;

public class ItemViewActivity extends AppCompatActivity implements View.OnClickListener {

    ListView listView;
    TextView itemName, makeText, conditionText, descriptionText;
    ImageView imageView;
    Button addToCart;
    RelativeLayout progressLayout;
    LinearLayout itemViewLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_view);

        String title = getIntent().getStringExtra("item");
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        progressLayout = findViewById(R.id.progressBarLayout);
        itemViewLayout = findViewById(R.id.item_view_layout);

        init();
        reterieveFromDatabase();
        showSpinner(false);
    }

    public void init(){
        itemName = findViewById(R.id.item_name);
        makeText = findViewById(R.id.maketxt);
        conditionText = findViewById(R.id.conditiontxt);
        descriptionText = findViewById(R.id.descriptiontxt);
        imageView = (ImageView) findViewById(R.id.c_item_photo);

        addToCart = findViewById(R.id.add_to_cart_button);
        addToCart.setOnClickListener(this);
    }

    private void showSpinner(boolean isVisible) {
        if (isVisible) {
            progressLayout.setVisibility(View.VISIBLE);
            itemViewLayout.setVisibility(View.GONE);
        } else {
            progressLayout.setVisibility(View.GONE);
            itemViewLayout.setVisibility(View.VISIBLE);
        }
    }

    public void reterieveFromDatabase(){
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.add_to_cart_button:
                DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                // Inserting Contacts
                Log.e("Insert: ", "Inserting ..");
                db.addContact(new Content("My Mobile 210", "1", "2000"));
                Toast.makeText(this, "Successfully add to cart", Toast.LENGTH_SHORT).show();
            break;
            default:
            break;
        }
    }

}
