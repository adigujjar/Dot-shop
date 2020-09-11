package com.developers.shop.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.developers.shop.R;

public class MainMenuListViewAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] itemname;
    private final int[] img;

    public MainMenuListViewAdapter(Activity context, String[] itemname, int[] img) {
        super(context, R.layout.main_menu_list_view, itemname);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.itemname=itemname;
        this.img=img;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.main_menu_list_view, null,true);
        TextView name = (TextView) rowView.findViewById(R.id.menu_item_name);
        ImageView pic = (ImageView) rowView.findViewById(R.id.menu_item_pic);
        name.setText(itemname[position]);
        pic.setImageResource(img[position]);
        return rowView;
    };
}
