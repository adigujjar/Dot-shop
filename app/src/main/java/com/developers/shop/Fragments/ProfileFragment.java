package com.developers.shop.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.developers.shop.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {

    CircleImageView profileImage;
    TextView name, email, mob, cnic;
    SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.profile_fragment, container, false);

        sharedPreferences = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);

        init(v);

        return v;
    }

    void init(View v) {

        profileImage = v.findViewById(R.id.profile_image);
        name = v.findViewById(R.id.user_name);
        email = v.findViewById(R.id.user_email);
        mob = v.findViewById(R.id.user_mob);
        cnic = v.findViewById(R.id.user_cnic);

        profileImage.setImageBitmap(decodeImage(sharedPreferences.getString("picture", "")));
        name.setText(sharedPreferences.getString("name", ""));
        email.setText(sharedPreferences.getString("email", ""));
        mob.setText(sharedPreferences.getString("mobile", ""));
        if (!TextUtils.isEmpty(sharedPreferences.getString("cnic", null))) {
            cnic.setVisibility(View.VISIBLE);
            cnic.setText(sharedPreferences.getString("cnic", ""));
        }

    }

    public Bitmap decodeImage(String img)
    {
        byte[] decode = Base64.decode(img, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(decode, 0, decode.length);
        return bitmap;
    }

}
