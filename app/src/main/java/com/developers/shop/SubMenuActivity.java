package com.developers.shop;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.developers.shop.Adapters.SubMenuListViewAdapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

public class SubMenuActivity extends AppCompatActivity {

    String []list_items = new String[] {
            "Item Menu",
    };

    String []list_itemsP = new String[] {
            "212"
    };
    String []list_itemsI = new String[] {
            "Apple"
    };
    SubMenuListViewAdapter adapter;
    ListView listView;
    Intent intent1;
    String itemSelected = null;
    RelativeLayout progressLayout;
    LinearLayout itemViewLayout;
    TextView mainTitle;
    String[] imgid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_menu);

        String title = getIntent().getStringExtra("main_menu");
        Toast.makeText(this, title, Toast.LENGTH_LONG).show();
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        init();
        reterieveFromDatabase();
        showSpinner(false);
    }

    public void init(){
        progressLayout = findViewById(R.id.progressBarLayout);
        itemViewLayout = findViewById(R.id.item_view_layout);

        listView = (ListView) findViewById(android.R.id.list);
        adapter=new SubMenuListViewAdapter(SubMenuActivity.this, list_items, list_itemsI,list_itemsP, imgid);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Object o = listView.getItemAtPosition(position);
                String str = (String) o;//As you are using Default String Adapter
                BufferedReader bufferedReader = new BufferedReader(new StringReader(str));
                try {
                    Intent intent = new Intent(SubMenuActivity.this, ItemViewActivity.class);
                    intent.putExtra("item",""+bufferedReader.readLine());
                    startActivity(intent);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
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

}
