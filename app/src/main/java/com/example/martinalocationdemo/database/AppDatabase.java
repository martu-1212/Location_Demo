package com.example.martinalocationdemo.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.martinalocationdemo.database.dao.LocationDao;
import com.example.martinalocationdemo.database.model.Locations;

@Database(entities = {Locations.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract LocationDao locationDao();
}
