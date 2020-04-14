package com.sreedwish.cybazeapp.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "data_table")
public class EntityData {

    @PrimaryKey
    int id;

}
