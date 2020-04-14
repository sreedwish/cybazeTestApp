package com.sreedwish.cybazeapp.view_models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sreedwish.cybazeapp.rest.ApiResponse;
import com.sreedwish.cybazeapp.rest.Repository;
import com.sreedwish.cybazeapp.rest_reponses.RespAuthenticate;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class AuthentiCationVM extends ViewModel {

    Repository repository;

    public AuthentiCationVM(Repository repository) {
        this.repository = repository;
    }

    //Local data
    private final MutableLiveData<ApiResponse> liveDataAuthenticate = new MutableLiveData<>();
    private final MutableLiveData<ApiResponse> liveDataGetInitData = new MutableLiveData<>();
    private final CompositeDisposable disposables = new CompositeDisposable();

    //Getter
    public LiveData<ApiResponse> getLiveDataAuthenticate() {
        return liveDataAuthenticate;
    }
    public LiveData<ApiResponse> getLiveDataGetInitData() {
        return liveDataGetInitData;
    }

    //Api call
    public void doAuthentication(String email, String password){

        disposables.add(repository.doAuthentication(email, password)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {

                liveDataAuthenticate.setValue(ApiResponse.loading());

            }
        })
        .subscribe(new Consumer<RespAuthenticate>() {
            @Override
            public void accept(RespAuthenticate respAuthenticate) throws Exception {

                if (!respAuthenticate.getError()){

                    liveDataAuthenticate.setValue(ApiResponse.success(null));

                }else {

                    liveDataAuthenticate.setValue(ApiResponse.success(respAuthenticate.getMessage()));
                }


            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {


                liveDataAuthenticate.setValue(ApiResponse.error(throwable));
            }
        }));

    }

    @Override
    protected void onCleared() {
        disposables.clear();
        super.onCleared();

    }
}
