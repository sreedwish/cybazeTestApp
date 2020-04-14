package com.sreedwish.cybazeapp.utils;

import android.app.Activity;
import android.app.Dialog;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;


import com.sreedwish.cybazeapp.R;


public class Custom_Loading_Dialog {



    AppCompatTextView textView;
    Activity activity;
    Dialog dialog;


    public Custom_Loading_Dialog(Activity activity) {
        this.activity = activity;


    }


    public void showDialog(@Nullable String text){

        dialog = null;
        dialog  = new Dialog(activity);


        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //...set cancelable false so that it's never get hidden
        dialog.setCancelable(false);
        //...that's the layout i told you will inflate later
        dialog.setContentView(R.layout.custom_dialog_loading);

        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        textView = (AppCompatTextView) dialog.findViewById(R.id.tv);

        if (!TextUtils.isEmpty(text)){
            textView.setText(text);
        }
        textView.setVisibility(View.INVISIBLE);

        ProgressBar progressBar = (ProgressBar) dialog.findViewById(R.id.progress);
        progressBar.setVisibility(View.VISIBLE);


        dialog.show();


    }




    //..also create a method which will hide the dialog when some work is done
    public void hideDialog(){
        dialog.dismiss();
    }



}
