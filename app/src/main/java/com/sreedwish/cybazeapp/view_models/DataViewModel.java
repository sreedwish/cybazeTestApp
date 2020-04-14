package com.sreedwish.cybazeapp.view_models;

import android.util.AndroidException;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sreedwish.cybazeapp.models.BeanVals;
import com.sreedwish.cybazeapp.models.Bean_data;
import com.sreedwish.cybazeapp.rest.ApiResponse;
import com.sreedwish.cybazeapp.rest.Repository;
import com.sreedwish.cybazeapp.rest_reponses.RespSave;
import com.sreedwish.cybazeapp.utils.Logger;

import org.reactivestreams.Subscriber;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class DataViewModel extends ViewModel {

    int amount_multiplier = 22;

    static final int initial_time_const = 10;

    static int initial_time = initial_time_const;

    Repository repository;


    BeanVals beanVals = new BeanVals();


    List<Bean_data> dataList = new ArrayList<>();

    //Local variables
    private final CompositeDisposable disposables = new CompositeDisposable();
    private final MutableLiveData<Bean_data> liveData = new MutableLiveData<>();
    private final MutableLiveData<BeanVals> liveDataValues = new MutableLiveData<>();
    private final MutableLiveData<ApiResponse> liveDataSaveValues = new MutableLiveData<>();
    private final MutableLiveData<ApiResponse> liveDataTime = new MutableLiveData<>();

    //Getters
    public LiveData<Bean_data> getLiveData() {
        return liveData;
    }
    public List<Bean_data> getDataList() {
        return dataList;
    }

    public LiveData<BeanVals> getLiveDataValues() {
        return liveDataValues;
    }

    public LiveData<ApiResponse> getLiveDataSaveValues() {
        return liveDataSaveValues;
    }

    public LiveData<ApiResponse> getLiveDataTime() {
        return liveDataTime;
    }

    //Constructor
    public DataViewModel(Repository repository) {
        this.repository = repository;
    }

    public void insertData(Bean_data data){


        dataList.add(0,data);
        liveData.setValue(data);

        beanVals.setCount(beanVals.getCount() + data.getCount());
        beanVals.setAmount(amount_multiplier * beanVals.getCount());
        beanVals.setTotal(beanVals.getTotal() + data.getTotal());

        liveDataValues.setValue(beanVals);



    }
    public void insertDataList(List<Bean_data> datas){


        Collections.reverse(datas);
        dataList.addAll(0,datas);
        liveData.setValue(null);

        for (Bean_data data: datas) {

            beanVals.setCount(beanVals.getCount() + data.getCount());
            beanVals.setAmount(amount_multiplier * beanVals.getCount());
            beanVals.setTotal(beanVals.getTotal() + data.getTotal());

        }

        liveDataValues.setValue(beanVals);



    }



    public void changeData(Bean_data data, int pos){


        dataList.set(pos,data);


        if (!data.isChecked()){
            beanVals.setCount(beanVals.getCount() - data.getCount());
            if (beanVals.getCount() != 0) {
                beanVals.setAmount(amount_multiplier / beanVals.getCount());
            }
            beanVals.setTotal(beanVals.getTotal() - data.getTotal());
        }else {
            beanVals.setCount(beanVals.getCount() + data.getCount());
            beanVals.setAmount(amount_multiplier * beanVals.getCount());
            beanVals.setTotal(beanVals.getTotal() + data.getTotal());
        }



        liveDataValues.setValue(beanVals);

    }


    public void apiCallSaveValues(){

        disposables.add(repository.saveData(beanVals.getCount(),beanVals.getAmount(),beanVals.getTotal())
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {

                liveDataSaveValues.setValue(ApiResponse.loading());

            }
        }).subscribe(new Consumer<RespSave>() {
                    @Override
                    public void accept(RespSave respSave) throws Exception {

                        liveDataSaveValues.setValue(ApiResponse.success(respSave));

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                        liveDataSaveValues.setValue(ApiResponse.error(throwable));

                    }
                }));

    }


    public void timeCheck(){

        Observable<Long> observable = Observable.interval(1, TimeUnit.MINUTES);

        disposables.add(observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {

                        liveDataTime.setValue(ApiResponse.success(initial_time));
                    }
                })
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {

                        Logger.getInstance().verbose_log(null, " time " + initial_time);

                        if (initial_time !=0){

                            initial_time = initial_time - 1;
                        } else {
                            initial_time = initial_time_const;
                        }

                        liveDataTime.setValue(ApiResponse.success(initial_time));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                        liveDataTime.setValue(ApiResponse.error(throwable));
                    }
                }));

    }


    @Override
    protected void onCleared() {
        disposables.clear();
        super.onCleared();

    }
}
