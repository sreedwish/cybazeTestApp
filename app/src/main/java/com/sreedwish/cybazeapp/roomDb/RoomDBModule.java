package com.sreedwish.cybazeapp.roomDb;

import android.content.Context;

import androidx.room.Room;


import com.sreedwish.cybazeapp.dagger.AppModule;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(includes = AppModule.class)
public class RoomDBModule {

    private AppDataBase appDataBase;

    public RoomDBModule(Context context) {
        appDataBase = Room.databaseBuilder(context,AppDataBase.class,AppDataBase.DB_NAME)
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
    }




    @Singleton
    @Provides
    AppDataBase provideAppDataBase(){
        return appDataBase;
    }




    @Singleton
    @Provides
    DataDao provideBusDao(AppDataBase appDataBase){
        return appDataBase.getDataDao();
    }



    @Singleton
    @Provides
    DbRepository provideDbRepository( DataDao dataDao){
        return new DbRepository(dataDao);
    }


}
