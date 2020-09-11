package com.developers.shop.Utils;

import android.app.Application;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;


public class VolleyInitilizer extends Application {
   private static Context mcontext;
    private static  VolleyInitilizer  mInstance;
    private RequestQueue mRequestQueue;

    public VolleyInitilizer(Context context){
        mcontext = context;
        mRequestQueue = getRequestQueue();
    }
    public static  synchronized   VolleyInitilizer getmInstance(Context context){
        if(mInstance==null){
            mInstance= new VolleyInitilizer(context);
        }
        return mInstance;
    }
    public RequestQueue getRequestQueue(){
        if(mRequestQueue==null){
            mRequestQueue = Volley.newRequestQueue(mcontext.getApplicationContext());
        }
        return  mRequestQueue;
    }
    public <T> void addToRequestQueue(Request<T> req){
        getRequestQueue().add(req);
    }
}
