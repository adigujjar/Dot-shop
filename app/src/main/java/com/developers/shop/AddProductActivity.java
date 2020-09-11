package com.developers.shop;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.developers.shop.Utils.Constants;
import com.developers.shop.Utils.VolleyInitilizer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AddProductActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView Product_Image1, Product_Image2, Product_Image3;
    private Spinner Catagories, made_By_Company, product_conditon;
    private EditText quantity, prize, des_of_product;
    private Button submitt;
    String quantites, prizes_of_pro, descriptions,
            product1_img_str, product2_img_str, product3_img_str,
            Catagory_spin_text, Comapny_spin_text, Condition_spin_text;
    private int image1_code = 00, image2_code = 11, image3_code = 22;
    Bitmap img_one_bitmap, img_two_bitmap, img_three_bitmap;
    Uri img_one_uri, img_two_uri, img_three_uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        getSupportActionBar().setTitle("Add new product");
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        initalizerWidgets();
    }

    public void initalizerWidgets() {
        Product_Image1 = findViewById(R.id.image_1);
        Product_Image2 = findViewById(R.id.image_2);
        Product_Image3 = findViewById(R.id.image_3);

        Product_Image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageOne();
            }
        });
        Product_Image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageTwo();
            }
        });
        Product_Image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageThree();
            }
        });

        Catagories = findViewById(R.id.Catagory_spinner);
        made_By_Company = findViewById(R.id.Company_spinner);
        product_conditon = findViewById(R.id.Condition_spinner);

        arrayadap();

        Catagories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(Shopkeeper_add_item_act.this, adapterView.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                Catagory_spin_text = adapterView.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        made_By_Company.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(Shopkeeper_add_item_act.this, adapterView.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();

                Comapny_spin_text = adapterView.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        product_conditon.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                Condition_spin_text = adapterView.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        quantity = findViewById(R.id.Quantity_text);
        prize = findViewById(R.id.Prize_of_product_text);
        des_of_product = findViewById(R.id.description_text);

        submitt = findViewById(R.id.submit_form);
        submitt.setOnClickListener(this);
    }

    public void convert() {
        quantites = quantity.getText().toString();
        prizes_of_pro = prize.getText().toString();
        descriptions  = des_of_product.getText().toString();

//        Catagory_spin_text = Catagories.toString();
//        Comapny_spin_text = made_By_Company.toString();
//        Condition_spin_text = product_conditon.toString();
    }

    public void arrayadap() {
        ArrayAdapter<String> catgry_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.catagories_product));

        catgry_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<String> cmpny_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.Companies_Name));

        cmpny_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        ArrayAdapter<String> cond_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.Conditon_Product));

        cond_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        Catagories.setAdapter(catgry_adapter);
        made_By_Company.setAdapter(cmpny_adapter);
        product_conditon.setAdapter(cond_adapter);
    }

    @Override
    public void onClick(View view) {

        submittData();
    }


    public void imageOne()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        Intent chooser = Intent.createChooser(intent,"complete action using...");
        startActivityForResult(chooser,image1_code);
    }

    public void imageTwo()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        Intent chooser = Intent.createChooser(intent,"complete action using...");
        startActivityForResult(chooser,image2_code);
    }

    public void imageThree()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        Intent chooser = Intent.createChooser(intent,"complete action using...");
        startActivityForResult(chooser,image3_code);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == image1_code && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            img_one_uri = data.getData();
            try {
                img_one_bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),img_one_uri);
                Product_Image1.setImageBitmap(img_one_bitmap);
                product1_img_str = encodeProductImage(img_one_bitmap);


            } catch (IOException e) {

                e.printStackTrace();
            }
        }

        if (requestCode == image2_code && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            img_two_uri = data.getData();
            try {
                img_two_bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),img_two_uri);
                Product_Image2.setImageBitmap(img_two_bitmap);
                product2_img_str = encodeProductImage(img_two_bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (requestCode == image3_code && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            img_three_uri = data.getData();
            try {
                img_three_bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),img_three_uri);
                Product_Image3.setImageBitmap(img_three_bitmap);
                product3_img_str = encodeProductImage(img_three_bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String encodeProductImage(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,80, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        String imagechangeinstring = Base64.encodeToString(bytes, Base64.DEFAULT);
        return imagechangeinstring;

    }

    public void submittData()
    {
        convert();
//        Toast.makeText(this,"abacaa"+quantites+ prizes_of_pro+ descriptions + product1_img_str+ product2_img_str+
//                product3_img_str+ Catagory_spin_text+Catagory_spin_text+Condition_spin_text,Toast.LENGTH_LONG).show();
        Toast.makeText(AddProductActivity.this," "+product1_img_str+product2_img_str
                +product3_img_str,Toast.LENGTH_SHORT).show();
        if(TextUtils.isEmpty(product1_img_str) || TextUtils.isEmpty(product2_img_str) || TextUtils.isEmpty(product3_img_str))  {

            Toast.makeText(this,"Add Images",Toast.LENGTH_SHORT).show();
        }

        else if (TextUtils.isEmpty(Catagory_spin_text) || TextUtils.isEmpty(Comapny_spin_text) ||
                TextUtils.isEmpty(Condition_spin_text) || TextUtils.isEmpty(quantites) ||
                TextUtils.isEmpty(prizes_of_pro) || TextUtils.isEmpty(descriptions)){
            Toast.makeText(this, "Complete the required fields.", Toast.LENGTH_LONG).show();
        } else {
            storeInDb();
        }

    }

    public void storeInDb()
    {
        submitt.setEnabled(false);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.ADD_PRODUCT_URL, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onResponse(String response) {
                if (response.contains("Successfully")) {

                    Toast.makeText(AddProductActivity.this, response ,Toast.LENGTH_SHORT).show();
                    AddProductActivity.this.finish();


                } else {
                    Toast.makeText(AddProductActivity.this, response, Toast.LENGTH_SHORT).show();
                    submitt.setEnabled(true);

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error:",""+error.toString());
                Toast.makeText(AddProductActivity.this,error.toString()
                        ,Toast.LENGTH_LONG).show();
                submitt.setEnabled(true);

            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("productImageOne", product1_img_str);
                params.put("productImageTwo", product2_img_str);
                params.put("productImageThree", product3_img_str);
                params.put("CatagorySpinner", Catagory_spin_text);
                params.put("CompanySpinner", Comapny_spin_text);
                params.put("ConditionSpinner", Condition_spin_text);
                params.put("QuantityOfProducts", quantites);
                params.put("prizeOfProduct", prizes_of_pro);
                params.put("Description", descriptions);

                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                8000,
                3,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleyInitilizer.getmInstance(AddProductActivity.this).addToRequestQueue(stringRequest);
    }

}
