package com.developers.shop.Adapters;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.developers.shop.R;
import com.developers.shop.Utils.DatabaseHandler;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CartListAdapter extends BaseAdapter implements ListAdapter {

    private ArrayList<String> list = new ArrayList<String>();
    private ArrayList<String> size = new ArrayList<String>();
    private ArrayList<String> pricelist = new ArrayList<String>();

    private Context context;
    int i=1;
    String subtotal, totalTax,totalPr;
    TextView subtotaltxt, taxtxt, totaltxt;
    double count = 0;
    float count_tax;

    public CartListAdapter(ArrayList<String> list, ArrayList<String> size, ArrayList<String> pricelist,String subtotal ,
                           Context context, TextView subtotaltxt, TextView taxtxt, TextView totaltxt) {
        this.list = list;
        this.size = size;
        this.pricelist = pricelist;
        this.subtotal = subtotal;
        this.context = context;
        this.subtotaltxt = subtotaltxt;
        this.taxtxt = taxtxt;
        this.totaltxt = totaltxt;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return 0;
        //just return 0 if your list items do not have an Id variable.
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.cart_list_view, null);
        }

        //Handle TextView and display string from your list
        final TextView listItemText = (TextView)view.findViewById(R.id.itemName);
        final TextView sizeTxt = (TextView) view.findViewById(R.id.itemV);
        final TextView nmbr = (TextView) view.findViewById(R.id.nmbrTxt);
        final TextView itemp = (TextView) view.findViewById(R.id.itemP);
        listItemText.setText(list.get(position));
        sizeTxt.setText(size.get(position));
        itemp.setText(""+pricelist.get(position));

        //Handle buttons and add onClickListeners
        ImageButton deleteBtn = (ImageButton) view.findViewById(R.id.trashBtn);
        Button addBtn = (Button)view.findViewById(R.id.addBtn);
        final Button minusBtn = (Button) view.findViewById(R.id.minuBtn);

        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //do something
                list.remove(position); //or some other task
                DatabaseHandler db = new DatabaseHandler(context);
                db.deleteContact(listItemText.getText().toString());
                notifyDataSetChanged();
            }
        });
        addBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //do something

                i = Integer.parseInt(nmbr.getText().toString());
                float x = Float.parseFloat(itemp.getText().toString());
                i++;
                nmbr.setText(String.valueOf(i));
                if (i > 1){
                    minusBtn.setEnabled(true);
                    double sub1 = Double.parseDouble(subtotaltxt.getText().toString());
                    sub1 = sub1 +  x;
                    count_tax = (float) (0.14 * sub1);
                    totalTax = String.valueOf(count_tax);
                    totalPr = String.valueOf(sub1 + count_tax);
                    subtotaltxt.setText(String.valueOf(sub1));
                    totaltxt.setText(totalPr);
                    taxtxt.setText(totalTax);
                    sub1 = 00.00;
                }
                else if(i <= 1 ){
                    minusBtn.setEnabled(false);
                }
                notifyDataSetChanged();
            }
        });
        minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = Integer.parseInt(nmbr.getText().toString());
                if (i > 1){
                    i--;
                    nmbr.setText(String.valueOf(i));
                    minusBtn.setEnabled(true);
                    float b = Float.parseFloat(itemp.getText().toString());
                    float sub1 = Float.parseFloat(subtotaltxt.getText().toString());

                    sub1 = sub1 - b;
                    count_tax = (float) (0.14 * sub1);

                    totalTax = String.valueOf(count_tax);
                    totalPr = String.valueOf(sub1 + count_tax);
                    subtotaltxt.setText(String.valueOf(sub1));
                    totaltxt.setText(totalPr);
                    taxtxt.setText(totalTax);
                }
                else if (i <= 1 ){
                    minusBtn.setEnabled(false);
                }
                notifyDataSetChanged();
            }
        });
        return view;
    }

}