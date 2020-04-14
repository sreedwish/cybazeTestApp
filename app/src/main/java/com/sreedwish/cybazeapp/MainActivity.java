package com.sreedwish.cybazeapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.sreedwish.cybazeapp.adapters.RecyclerAdapter;
import com.sreedwish.cybazeapp.adapters.ViewPagerAdapter;
import com.sreedwish.cybazeapp.dagger.MyApplication;
import com.sreedwish.cybazeapp.dagger.ViewModelFactory;
import com.sreedwish.cybazeapp.databinding.ActivityMainBinding;
import com.sreedwish.cybazeapp.fragments.Frag1Digit;
import com.sreedwish.cybazeapp.fragments.Frag2Digit;
import com.sreedwish.cybazeapp.fragments.Frag3Digit;
import com.sreedwish.cybazeapp.listeners.RecycleItemClickListener;
import com.sreedwish.cybazeapp.models.BeanVals;
import com.sreedwish.cybazeapp.models.Bean_data;
import com.sreedwish.cybazeapp.rest.ApiResponse;
import com.sreedwish.cybazeapp.rest_reponses.RespSave;
import com.sreedwish.cybazeapp.utils.Custom_Loading_Dialog;
import com.sreedwish.cybazeapp.utils.Logger;
import com.sreedwish.cybazeapp.utils.NonSwipeableViewPager;
import com.sreedwish.cybazeapp.view_models.DataViewModel;

import java.util.List;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

    int time = 0;

    ActivityMainBinding binding;
    Frag1Digit frag1 = new Frag1Digit();
    Frag2Digit frag2 = new Frag2Digit();
    Frag3Digit frag3 = new Frag3Digit();

    NonSwipeableViewPager viewPager;

    ViewPagerAdapter viewPagerAdapter;

    @Inject
    ViewModelFactory viewModelFactory;

    DataViewModel dataViewModel;

    RecyclerAdapter recyclerAdapter;

    Custom_Loading_Dialog loading_dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);

        viewPager = binding.frm;

        MyApplication.getInstance().getAppComponent().doInjection(this);

        dataViewModel = new ViewModelProvider(this,viewModelFactory).get(DataViewModel.class);

        loading_dialog = new Custom_Loading_Dialog(this);

        setUpToolBar();

        setUpViewPager();

        init_live_data();

        init_recycler_view();


    }

    private void setUpToolBar() {

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        getSupportActionBar().setTitle("Home");

    }

    private void init_recycler_view() {

        recyclerAdapter = new RecyclerAdapter(dataViewModel.getDataList(), new RecycleItemClickListener() {
            @Override
            public void onItemCheckClick(Bean_data data, int pos) {

                recyclerAdapter.notifyItemChanged(pos);

                dataViewModel.changeData(data, pos);


            }
        });

        binding.recycler.setLayoutManager(new LinearLayoutManager(this));
        binding.recycler.setAdapter(recyclerAdapter);

    }

    private void init_live_data() {

        dataViewModel.timeCheck();

        dataViewModel.getLiveData().observe(this, new Observer<Bean_data>() {
            @Override
            public void onChanged(Bean_data data) {

                recyclerAdapter.notifyDataSetChanged();


            }
        });

        dataViewModel.getLiveDataValues().observe(this, new Observer<BeanVals>() {
            @Override
            public void onChanged(BeanVals beanVals) {

                binding.tvCount.setText("" + beanVals.getCount());
                binding.tvTotal.setText("" + beanVals.getTotal());
                binding.tvAmount.setText("" + beanVals.getAmount());

            }
        });

        dataViewModel.getLiveDataSaveValues().observe(this, new Observer<ApiResponse>() {
            @Override
            public void onChanged(ApiResponse apiResponse) {

                switch (apiResponse.status){


                    case LOADING:
                        loading_dialog.showDialog(null);
                        break;

                    case SUCCESS:
                        loading_dialog.hideDialog();
                        RespSave respSave = (RespSave) apiResponse.data;
                        Toast.makeText(getApplicationContext(), respSave.getMessage(),Toast.LENGTH_LONG).show();
                        break;

                    case ERROR:
                        loading_dialog.hideDialog();
                        Toast.makeText(getApplicationContext(), apiResponse.error.getMessage(),Toast.LENGTH_LONG).show();
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

                            Toast.makeText(getApplicationContext(),getString(R.string.lbl_game_over), Toast.LENGTH_LONG ).show();

                        }


                        break;
                }


            }
        });

    }

    private void setUpViewPager() {

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        frag1.setDataViewModel(dataViewModel);
        frag2.setDataViewModel(dataViewModel);
        frag3.setDataViewModel(dataViewModel);


        viewPagerAdapter.addFragment(frag1, "frag1");
        viewPagerAdapter.addFragment(frag2, "frag2");
        viewPagerAdapter.addFragment(frag3, "frag3");

        viewPager.setAdapter(viewPagerAdapter);

        binding.btnDigit1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                viewPager.setCurrentItem(0,false);


                btnclickChange(view);

            }
        });

        binding.btnDigit2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                viewPager.setCurrentItem(1,false);


                btnclickChange(view);

            }
        });

        binding.btnDigit3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                viewPager.setCurrentItem(2,false);

                btnclickChange(view);


            }
        });

        viewPager.setCurrentItem(0,false);
        viewPager.setOffscreenPageLimit(3);
        btnclickChange(binding.btnDigit1);

        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dataViewModel.apiCallSaveValues();

            }
        });

    }


    void btnclickChange(View view){

        binding.btnDigit1.setBackgroundDrawable(getDrawable(R.drawable.btn_bg_inactive));
        binding.btnDigit2.setBackgroundDrawable(getDrawable(R.drawable.btn_bg_inactive));
        binding.btnDigit3.setBackgroundDrawable(getDrawable(R.drawable.btn_bg_inactive));

        view.setBackground(getDrawable(R.drawable.btn_bg_active));

    }

}
