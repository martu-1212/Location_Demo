package com.example.martinalocationdemo.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.martinalocationdemo.database.model.Locations;

import java.util.List;

@Dao
public interface LocationDao {
        @Query("SELECT * FROM Locations")
        List<Locations> getAll();

        @Insert
        void insert(Locations location);

        @Delete
        void delete(Locations location);

        @Update
        void update(Locations location);

    }



