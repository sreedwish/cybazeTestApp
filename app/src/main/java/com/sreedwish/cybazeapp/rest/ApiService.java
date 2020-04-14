package com.sreedwish.cybazeapp.rest;


import com.sreedwish.cybazeapp.rest_reponses.RespAuthenticate;
import com.sreedwish.cybazeapp.rest_reponses.RespSave;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiService {


    //Authenticate user
    @FormUrlEncoded
    @POST(IpClass.Authenticate)
    Observable<RespAuthenticate> doAuthentication(@Field("email") String email, @Field("password") String password,
                                                  @Field("key")String key);

    //Authenticate user
    @FormUrlEncoded
    @POST(IpClass.Save_data)
    Observable<RespSave> saveData(@Field("user_id") int user_id, @Field("alldata") int alldata,
                                  @Field("total_bid_amount") int total_bid_amount, @Field("grand_total") int grand_total,
                                  @Field("key")String key);




}
