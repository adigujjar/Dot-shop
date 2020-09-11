package com.developers.shop.Fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.developers.shop.LoginActivity;
import com.developers.shop.R;
import com.developers.shop.ShopKeeperDashboardActivity;
import com.developers.shop.Utils.Constants;
import com.developers.shop.Utils.VolleyInitilizer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

public class RegisterShopKeeperFragment extends Fragment implements View.OnClickListener {

    // UI references.
    private EditText mName, mShopName, mEmail, mCNIC, mMobileNo;
    private ImageView shopkeeper_image, shop_image;
    private Button shopkeeper_signup_btn;
    private int PROFILE_CODE = 000 , SHOP_CODE = 1111;
    private Uri uri1;
    private Uri uri2;
    private Bitmap bit1, bit2;
    private String encodeshopimagge, encodeshopkeeperimage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.register_shop_keeper_fragment, container, false);

        shopkeeper_image = v.findViewById(R.id.image_select_btn);
        shop_image = v.findViewById(R.id.shop_select_btn);

        mName = v.findViewById(R.id.name);
        mShopName = v.findViewById(R.id.shopname);
        mEmail = v.findViewById(R.id.email);
        mCNIC = v.findViewById(R.id.cnic);
        mMobileNo = v.findViewById(R.id.phone);

        shopkeeper_signup_btn = v.findViewById(R.id.email_sign_in_button);

        shopkeeper_signup_btn.setOnClickListener(this);

        shopkeeper_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadYourShopImage();
            }
        });

        shop_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadProfile();
            }
        });


        return v;
    }

    public void uploadYourShopImage()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        Intent chooser = Intent.createChooser(intent,"complete action using...");
        startActivityForResult(chooser,SHOP_CODE);
    }

    public void uploadProfile()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        Intent chooser = Intent.createChooser(intent,"complete action using...");
        startActivityForResult(chooser,PROFILE_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SHOP_CODE && resultCode == RESULT_OK && data != null && data.getData() !=null)
        {
            uri1 = data.getData();
            try {
                bit1 = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),uri1);
                shopkeeper_image.setImageBitmap(bit1);
                encodeshopkeeperimage = encodeshopImage(bit1);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (requestCode == PROFILE_CODE && resultCode == RESULT_OK && data != null && data.getData()!=null)
        {
            uri2 = data.getData();
            try {
                bit2 = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),uri2);
                shop_image.setImageBitmap(bit2);
                encodeshopimagge = encodeshopImage(bit2);
            }

            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.email_sign_in_button:
                //Log.e("check", "" +mName.getText().toString()+mShopName.getText().toString()+mEmail.getText().toString()+
                // mCNIC.getText().toString()+mMobileNo.getText().toString());

                validateFields();
                break;
            default:
                break;
        }
    }

    public void validateFields() {
        if (TextUtils.isEmpty(encodeshopimagge) || TextUtils.isEmpty(encodeshopimagge)) {
            Toast.makeText(getContext(), "Add images.", Toast.LENGTH_LONG).show();
            return;
        } else if (TextUtils.isEmpty(mName.getText().toString()) || TextUtils.isEmpty(mShopName.getText().toString()) ||
                TextUtils.isEmpty(mEmail.getText().toString()) || TextUtils.isEmpty(mCNIC.getText().toString()) ||
                TextUtils.isEmpty(mMobileNo.getText().toString())){
            Toast.makeText(getContext(), "Complete the required fields.", Toast.LENGTH_LONG).show();
            return;
        } else {
            registerUser();
        }
    }

    //Stored in database after athentication
    public void registerUser(){

        shopkeeper_signup_btn.setEnabled(false);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.REGISTER_URL, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onResponse(String response) {
                if (response.contains("success")){

                    Toast.makeText(getContext(), response ,Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getContext(), LoginActivity.class));
                    getActivity().finishAffinity();
                }else {
                    Toast.makeText(getContext(),response, Toast.LENGTH_SHORT).show();
                    shopkeeper_signup_btn.setEnabled(true);

//                    mProgressDialog.dismiss();
//                    mEmailView.setError(getString(R.string.error_incorrect_email));
//                    Toast.makeText(getApplicationContext(),"Email is already exist! Please re-enter.",Toast.LENGTH_LONG).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("error:",""+error.toString());
                        Toast.makeText(getContext(),error.toString()
                                ,Toast.LENGTH_LONG).show();
                        shopkeeper_signup_btn.setEnabled(true);
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("name", mName.getText().toString());
                params.put("shopName", mShopName.getText().toString());
                params.put("email", mEmail.getText().toString());
                params.put("cnic", mCNIC.getText().toString());
                params.put("mobileNo", mMobileNo.getText().toString());
                params.put("profilePic", encodeshopkeeperimage);
                params.put("shopPic", encodeshopimagge);
                params.put("userType", "shop keeper");
                params.put("long", "");
                params.put("lat", "");

                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                8000,
                3,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleyInitilizer.getmInstance(getContext()).addToRequestQueue(stringRequest);

    }

    public String encodeshopImage(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,80, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        String imagechangeinstring = Base64.encodeToString(bytes, Base64.DEFAULT);
        return imagechangeinstring;

    }

}
