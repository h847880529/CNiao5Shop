package com.example.hwg.cniao5shop.http;


import android.content.Context;

import dmax.dialog.SpotsDialog;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by hwg on 2017/2/15.
 */

public abstract class SpotsCallBack<T> extends BaseCallback<T>{

    SpotsDialog dialog ;

    public SpotsCallBack(Context context){

        dialog = new SpotsDialog(context) ;
    }

    public void showDialog(){
        dialog.show();
    }
    //关闭dialog
    public void dismissDialog(){
        if (dialog != null){
            dialog.dismiss();
        }
    }

    public void setMessage(String message){
        dialog.setMessage(message);
    }

    @Override
    public void onRequestBefore(Request request) {
        showDialog();
    }

    @Override
    public void onFailure(Request request, Exception e) {
        dismissDialog();
    }

    @Override
    public void onResponse(Response response) {
        dismissDialog();
    }
}
