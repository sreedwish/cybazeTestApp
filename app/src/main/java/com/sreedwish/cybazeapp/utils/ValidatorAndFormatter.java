package com.sreedwish.cybazeapp.utils;

import android.content.Context;
import android.content.res.Resources;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;


import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ValidatorAndFormatter {

    private static final String TAG = "~~VFormatter";

    public static ValidatorAndFormatter instance;

    public static Resources resources;



    SimpleDateFormat common_sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
    private String timeString;

    public static ValidatorAndFormatter getInstance() {

        if (instance == null) {
            instance = new ValidatorAndFormatter();
        }

        return instance;
    }



    public boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }



    public String timeToInputFormat(String time_val) {

        String return_string = time_val;

        String format = "HH:mm:ss";

        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.ENGLISH);

        Date date = new Date();
        try {
            date = sdf.parse(time_val);
            return_string = sdf.format(date);
        }catch (Exception e)
        {

        }

        //Commons.logger.verbose_log(TAG, " return time vaf " + return_string);

        return return_string;

    }


    public String timeDisplayTrainBusSnippet(String inputVal) {
        String return_string = inputVal;

        try {

            SimpleDateFormat s = new SimpleDateFormat("HH:mm:ss", Locale.US);

            Date formatted_date = s.parse(inputVal);

            SimpleDateFormat out_date = new SimpleDateFormat("hh:mm a", Locale.US);

            return_string = out_date.format(formatted_date);

        } catch (Exception e) {
            e.printStackTrace();
        }



        return return_string;
    }



    public int getDateDifference(String startDt, String endDt) {

        int diffInDays = 0;

        try {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

            Date dt1 = sdf.parse(startDt);
            Date dt2 = sdf.parse(endDt);

            long diff = dt2.getTime() - dt1.getTime();
            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000);

            diffInDays = (int) ((dt2.getTime() - dt1.getTime()) / (1000 * 60 * 60 * 24));



        } catch (Exception e) {
            e.printStackTrace();
        }

        diffInDays++;

        return diffInDays;
    }

    public void textWatcherNumberCount(AppCompatEditText edt, @Nullable AppCompatEditText edtNext, Context context, int count){

        edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (edt.getText().toString().length() == count){
                    if (edtNext !=null) {
                        edtNext.requestFocus();
                    }else {
                        edt.clearFocus();
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    public void textWatcherNumberCount(AppCompatEditText edt, @Nullable AppCompatEditText edtNext,@Nullable AppCompatEditText edtAlt,
                                       Context context, int count){

        edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (edt.getText().toString().length() == count){
                    if (edtNext !=null){

                        if (!TextUtils.isEmpty(edtNext.getText().toString())){
                            if (edtAlt != null){
                                edtAlt.requestFocus();
                            }else {
                                edt.clearFocus();
                            }
                        }else {
                            edtNext.requestFocus();
                        }


                    }

                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }






}
