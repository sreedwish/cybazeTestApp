package com.sreedwish.cybazeapp.roomDb;



import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DbRepository {

    private final String TAG = "~~DbRepository";



    DataDao dataDao;


    @Inject
    public DbRepository( DataDao dataDao) {
        this.dataDao = dataDao;
    }





}
