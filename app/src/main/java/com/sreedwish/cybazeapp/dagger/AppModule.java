package com.sreedwish.cybazeapp.dagger;

import android.content.Context;

import androidx.lifecycle.ViewModelProvider;


import com.sreedwish.cybazeapp.rest.ApiClientModule;
import com.sreedwish.cybazeapp.rest.Repository;
import com.sreedwish.cybazeapp.roomDb.DbRepository;
import com.sreedwish.cybazeapp.roomDb.RoomDBModule;
import com.sreedwish.cybazeapp.utils.NetworkAvailability;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(includes = {ApiClientModule.class, RoomDBModule.class})
public class AppModule {

    private Context context;

    public AppModule(Context context){
        this.context = context;
    }

    @Provides
    @Singleton
    Context provideContext(){
        return context;
    }


    @Provides
    @Singleton
    NetworkAvailability provideNetWorkAvailCheck(){
        return new NetworkAvailability(context);
    }



    @Provides
    @Singleton
    ViewModelProvider.Factory getViewModelFactory(Repository repository, DbRepository dbRepository) {
        return new ViewModelFactory(repository,dbRepository);
    }

}
