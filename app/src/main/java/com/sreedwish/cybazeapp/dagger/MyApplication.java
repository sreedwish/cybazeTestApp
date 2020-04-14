package com.sreedwish.cybazeapp.dagger;

import android.app.Application;
import android.content.Context;

import com.sreedwish.cybazeapp.rest.ApiClientModule;
import com.sreedwish.cybazeapp.roomDb.RoomDBModule;
import com.sreedwish.cybazeapp.utils.NetworkAvailability;


public class MyApplication extends Application {

    AppComponent appComponent;

    Context context;



    RoomDBModule roomDBModule;

    ApiClientModule apiClientModule;

    NetworkAvailability networkAvailability;

    protected static MyApplication instance;

    public static MyApplication getInstance(){
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        context = this;

        instance = this;

        roomDBModule = new RoomDBModule(context);

        networkAvailability = new NetworkAvailability(context);

        apiClientModule = new ApiClientModule(networkAvailability);

        appComponent = DaggerAppComponent.builder().appModule(new AppModule(this))
                .apiClientModule(apiClientModule)
                .roomDBModule(roomDBModule).build();
    }

    public AppComponent getAppComponent(){
        return appComponent;
    }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
    }
}
