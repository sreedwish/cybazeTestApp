package com.sreedwish.cybazeapp.roomDb;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.sreedwish.cybazeapp.models.EntityData;


@Database(entities = { EntityData.class}, version = 2)
public abstract class AppDataBase extends RoomDatabase {

    static String DB_NAME = "CybazeApp_DB";

    public abstract DataDao getDataDao();

}
