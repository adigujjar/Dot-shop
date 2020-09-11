package com.developers.shop.Fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.developers.shop.R;
import com.developers.shop.Utils.Constants;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

public class RegisterNewCustomerFragment extends Fragment  {
    @Nullable

    ImageView customer_image;
    EditText customer_name, customer_nickname, customer_email, customer_mobile, customer_password, customer_renterass;
    Button customer_signup_btn;
    RequestQueue requestQueue;
    int PICK_CODE = 111 ;
    Uri muri;
    Bitmap bitmap;
    private String encodeimages;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.register_new_customer_fragment, container, false);
        customer_image = v.findViewById(R.id.image_select_btn);

        customer_name = v.findViewById(R.id.name);
        customer_nickname = v.findViewById(R.id.username);
        customer_email = v.findViewById(R.id.email);
        customer_mobile = v.findViewById(R.id.phone);
        customer_password = v.findViewById(R.id.password);
        customer_renterass = v.findViewById(R.id.confirm_password);

        customer_signup_btn = v.findViewById(R.id.email_sign_in_button);

        requestQueue = new Volley().newRequestQueue(getActivity());
        customer_signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sign_up_button();
            }
        });

        customer_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImage();
            }
        });

        return v;
    }


    public void sign_up_button()
    {
        String url = Constants.CUSTOMER_REGISER_URL;

        final String cstmrname  = customer_name.getText().toString();
        final String cstmrusernme = customer_nickname.getText().toString();
        final String cstmremail = customer_email.getText().toString();
        final String cstmrmobile = customer_mobile.getText().toString();
        final String cstmrpas = customer_password.getText().toString();
        String cstmrentercnfrmpass = customer_renterass.getText().toString();

        if (TextUtils.isEmpty(cstmrname) || TextUtils.isEmpty(cstmrname) || TextUtils.isEmpty(cstmremail) || TextUtils.isEmpty(cstmrmobile)
                || TextUtils.isEmpty(cstmrpas) || TextUtils.isEmpty(cstmrentercnfrmpass)) {
            Toast.makeText(getContext(), "Complete the required fields.", Toast.LENGTH_LONG).show();
            return;
        }

        if (cstmrpas.length() < 5) {
            customer_password.setError("Password should be greater then 5 chars.");
            return;
        }

        if (!cstmrpas.equals(cstmrentercnfrmpass)) {
            customer_renterass.setError("Password not matched.");
            return;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.contains("success")) {
                            Toast.makeText(getContext(), "Registered Successfully.", Toast.LENGTH_SHORT).show();
                            getActivity().finish();
                        } else if (response.contains("email")){
                            customer_email.setError("Email already exists.");
                        } else if (response.contains("username")) {
                            customer_nickname.setError("Username already exists.");
                        } else {
                            Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parameter = new HashMap<>();
                parameter.put("name",cstmrname);
                parameter.put("username",cstmrusernme);
                parameter.put("email",cstmremail);
                parameter.put("mobileNo",cstmrmobile);
                parameter.put("password",cstmrpas);
                parameter.put("profilePic", encodeimages);

                return parameter;
            }
        };
        requestQueue.add(stringRequest);

    }

    public String encodeImage(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        String imginString = Base64.encodeToString(bytes, Base64.DEFAULT);
        return imginString;
    }

    public void pickImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        Intent chooser = Intent.createChooser(intent,"complete action using...");
        startActivityForResult(chooser,PICK_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_CODE && resultCode == RESULT_OK  && data != null && data.getData() != null)
        {
            muri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), muri);
                customer_image.setImageBitmap(bitmap);
                encodeimages = encodeImage(bitmap);

            }
            catch (IOException e) {
                e.printStackTrace();

            }
        }
    }


}
