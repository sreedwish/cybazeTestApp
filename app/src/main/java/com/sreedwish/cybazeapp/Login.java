package com.sreedwish.cybazeapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.sreedwish.cybazeapp.adapters.RecyclerAdapter;
import com.sreedwish.cybazeapp.dagger.MyApplication;
import com.sreedwish.cybazeapp.dagger.ViewModelFactory;
import com.sreedwish.cybazeapp.databinding.ActivityLoginBinding;
import com.sreedwish.cybazeapp.rest.ApiResponse;
import com.sreedwish.cybazeapp.utils.Custom_Loading_Dialog;
import com.sreedwish.cybazeapp.utils.ValidatorAndFormatter;
import com.sreedwish.cybazeapp.view_models.AuthentiCationVM;
import com.sreedwish.cybazeapp.view_models.DataViewModel;

import javax.inject.Inject;

public class Login extends AppCompatActivity {

    ActivityLoginBinding binding;

    Context context;

    @Inject
    ViewModelFactory viewModelFactory;

    AuthentiCationVM viewModel;

    DataViewModel dataViewModel;

    Custom_Loading_Dialog loading_dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_login);

        context =this;

        MyApplication.getInstance().getAppComponent().doInjection(this);

        viewModel = new ViewModelProvider(this,viewModelFactory).get(AuthentiCationVM.class);
        dataViewModel = new ViewModelProvider(this,viewModelFactory).get(DataViewModel.class);

        loading_dialog = new Custom_Loading_Dialog(this);



        binding.btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                String email = binding.edtEmail.getText().toString();
                String password = binding.edtPassword.getText().toString();

                if (!ValidatorAndFormatter.getInstance().isValidEmail(email)){
                    Toast.makeText(context, getString(R.string.msg_invalid_email),Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)){
                    Toast.makeText(context, getString(R.string.msg_invalid_password),Toast.LENGTH_SHORT).show();
                    return;
                }



                viewModel.doAuthentication(email,password);



            }
        });

        init_live_data();




    }



    private void init_live_data() {

        dataViewModel.timeCheck();

        viewModel.getLiveDataAuthenticate().observe(this, new Observer<ApiResponse>() {
            @Override
            public void onChanged(ApiResponse apiResponse) {

                switch (apiResponse.status){
                    case LOADING:
                        loading_dialog.showDialog(null);
                        break;

                    case SUCCESS:
                        loading_dialog.hideDialog();
                        if (apiResponse.data == null){
                            //Assumption = login success
                            gotoNextActivity();

                        }else {

                            String msg = (String) apiResponse.data;
                            Toast.makeText(context,msg,Toast.LENGTH_LONG).show();

                        }
                        break;

                    case ERROR:
                        loading_dialog.hideDialog();
                        Toast.makeText(context,apiResponse.error.getMessage(), Toast.LENGTH_LONG).show();
                        break;

                }

            }
        });


        dataViewModel.getLiveDataTime().observe(this, new Observer<ApiResponse>() {
            @Override
            public void onChanged(ApiResponse apiResponse) {

                switch (apiResponse.status){
                    case SUCCESS:
                        int time = (int) apiResponse.data;
                        if (time == 0){

                            binding.tvTimerMsg.setText(time + " " + getString(R.string.lbl_game_over));

                        }else {
                            binding.tvTimerMsg.setText(time + " " + getString(R.string.lbl_bottom_msg));
                        }


                        break;
                }

            }
        });

    }


    private void gotoNextActivity(){
        Intent intent = new Intent(context,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        finish();
        startActivity(intent);
    }

}
