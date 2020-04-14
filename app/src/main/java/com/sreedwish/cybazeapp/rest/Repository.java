package com.sreedwish.cybazeapp.rest;

import android.content.Context;


import com.sreedwish.cybazeapp.dagger.MyApplication;
import com.sreedwish.cybazeapp.rest_reponses.RespAuthenticate;
import com.sreedwish.cybazeapp.rest_reponses.RespSave;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

@Singleton
public class Repository {

    @Inject
    Context context;

    String key = "OEZOOXo5djVoUDZ0ekVkRzN0T3hmUT09";


    private ApiService apiService;

    public Repository(ApiService apiService) {
        this.apiService = apiService;

        MyApplication.getInstance().getAppComponent().doInjection(this);

    }

    /**
     * Authenticate user , this call is using for both login and registration
     *
     * @param email
     * @param password
     *
     */
    public Observable<RespAuthenticate> doAuthentication(final String email, final String password) {

        return apiService.doAuthentication(email,password,key);
    }

    /**
     * Save the data in server
     * @param alldata
     * @param total_bid_amount
     * @param grand_total
     * @return
     */
    public Observable<RespSave> saveData( int alldata, int total_bid_amount, int grand_total ){
        int user_id = 169;

        return apiService.saveData(user_id,alldata,total_bid_amount,grand_total,key);

    }





}
