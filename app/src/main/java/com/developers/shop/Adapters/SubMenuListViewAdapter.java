package com.developers.shop.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.developers.shop.R;
import com.developers.shop.Utils.Constants;
import com.squareup.picasso.Picasso;

public class SubMenuListViewAdapter  extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] itemname;
    private final String[] itemprice;
    private final String[] itemingre;
    private final String[] imgid;

    public SubMenuListViewAdapter(Activity context, String[] itemname, String[] itemingre, String[] itemprice, String[] imgid) {
        super(context, R.layout.submenu_item_list, itemname);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.itemname=itemname;
        this.itemprice=itemprice;
        this.itemingre=itemingre;
        this.imgid=imgid;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.submenu_item_list, null,true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
        TextView txtIngr = (TextView) rowView.findViewById(R.id.ingre_txt);
        TextView txtPrice = (TextView) rowView.findViewById(R.id.price_txt);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        txtTitle.setText(itemname[position]);
        txtPrice.setText(itemprice[position]);
        txtIngr.setText(itemingre[position]);

//        Picasso.with(context).load(Constants.dishAdrs + imgid[position] + "jpg").into(imageView);

        //imageView.setImageResource(imgid[position]);
        return rowView;
    };
}