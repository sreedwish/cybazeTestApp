package com.sreedwish.cybazeapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.sreedwish.cybazeapp.R;
import com.sreedwish.cybazeapp.databinding.FragBtn1Binding;
import com.sreedwish.cybazeapp.databinding.FragBtn3Binding;
import com.sreedwish.cybazeapp.models.Bean_data;
import com.sreedwish.cybazeapp.utils.ValidatorAndFormatter;
import com.sreedwish.cybazeapp.view_models.DataViewModel;

import java.util.ArrayList;
import java.util.List;

public class Frag3Digit extends Fragment implements View.OnClickListener{

    FragBtn3Binding binding;

    AppCompatButton btnSingle, btnRange;

    boolean single_view = true;

    ValidatorAndFormatter vaf = ValidatorAndFormatter.getInstance();

    Context context;

    DataViewModel dataViewModel;

    public void setDataViewModel(DataViewModel dataViewModel) {
        this.dataViewModel = dataViewModel;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.frag_btn_3,container,false);

       btnRange = binding.btniOS;
       btnSingle = binding.btnAndroid;

        binding.btn1.setOnClickListener(this);
        binding.btn2.setOnClickListener(this);
        binding.btn3.setOnClickListener(this);
        binding.btn4.setOnClickListener(this);

        btnSingle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                disableViews();

                single_view = true;

                binding.edtCount.setVisibility(View.VISIBLE);
                binding.edtNumber.setVisibility(View.VISIBLE);
            }
        });


        btnRange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                disableViews();

                single_view = false;

                binding.linEdtContainer.setVisibility(View.VISIBLE);

            }
        });

        vaf.textWatcherNumberCount(binding.edtNumber,binding.edtCount,context,3);
        vaf.textWatcherNumberCount(binding.edtCount,null,context,3);

        vaf.textWatcherNumberCount(binding.edtStart,binding.edtEnd,context,3);

        vaf.textWatcherNumberCount(binding.edtEnd, binding.edtStep , binding.edtCount2 , context,3);

        vaf.textWatcherNumberCount(binding.edtStep,binding.edtCount2,context,2);
        vaf.textWatcherNumberCount(binding.edtCount2,null,context,3);


        return binding.getRoot();
    }


    void disableViews(){
        binding.linEdtContainer.setVisibility(View.GONE);
        binding.edtCount.setVisibility(View.GONE);
        binding.edtNumber.setVisibility(View.GONE);
    }

    void setData(AppCompatButton button){


        if (single_view){

            Bean_data data = new Bean_data();

            data.setButton(3);

            data.setT1(button.getText().toString().toUpperCase());

            int number = Integer.parseInt(binding.edtNumber.getText().toString().trim());
            int count = Integer.parseInt(binding.edtCount.getText().toString().trim());
            int total = number * count;


            data.setNumber(number);

            data.setCount(count);

            data.setTotal(total);

            dataViewModel.insertData(data);




        }else {

            int start = Integer.parseInt(binding.edtStart.getText().toString().trim());
            int end = Integer.parseInt(binding.edtEnd.getText().toString().trim());
            int count = Integer.parseInt(binding.edtCount2.getText().toString().trim());
            int step = Integer.parseInt(binding.edtStep.getText().toString().trim());

            if (start > end){
                clearAllEditText();
                return;
            }

            List<Bean_data> dataList = new ArrayList<>();
            for (int i = start ; i <= end ; i = i+step){

                Bean_data data = new Bean_data();

                data.setButton(3);

                data.setT1(button.getText().toString().toUpperCase());

                data.setNumber(i);

                data.setCount(count);

                int total =  i * count;

                data.setTotal(total);

                dataList.add(data);


            }
            dataViewModel.insertDataList(dataList);

        }



    }

    private void clearAllEditText(){

        if (single_view){
            binding.edtCount.setText("");
            binding.edtNumber.setText("");
        }else {

            binding.edtCount2.setText("");
            binding.edtStart.setText("");
            binding.edtEnd.setText("");
            binding.edtStep.setText("1");

        }

    }


    @Override
    public void onClick(View view) {

        boolean proceed = false;

        if (single_view){

            proceed = isNotEmptyEditText(binding.edtCount, R.string.msg_count_empty);

            if (!proceed){
                return;
            }

            proceed = isNotEmptyEditText(binding.edtNumber, R.string.msg_number_empty);
            if (!proceed){
                return;
            }

        }else {


            proceed = isNotEmptyEditText(binding.edtCount2, R.string.msg_count_empty);
            if (!proceed){
                return;
            }

            proceed = isNotEmptyEditText(binding.edtStart, R.string.msg_start_empty);
            if (!proceed){
                return;
            }

            proceed = isNotEmptyEditText(binding.edtEnd, R.string.msg_end_empty);
            if (!proceed){
                return;
            }

            proceed = isNotEmptyEditText(binding.edtStep, R.string.msg_step_empty);
            if (!proceed){
                return;
            }

        }

        if (proceed){

            if (view.getId() == binding.btn4.getId()){


                setData(binding.btn3);
                setData(binding.btn2);
                setData(binding.btn1);

                clearAllEditText();


            }else {
                setData((AppCompatButton) view);

                clearAllEditText();

            }


        }


    }

    private boolean isNotEmptyEditText(AppCompatEditText editText, int msg_id){

        if (TextUtils.isEmpty(editText.getText().toString())){

            Toast.makeText(context,getString(msg_id),Toast.LENGTH_SHORT).show();
            return false;

        }else {
            return true;
        }

    }


}
