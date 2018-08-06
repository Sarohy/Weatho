package com.sarohy.weatho.weatho.Model.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.sarohy.weatho.weatho.Model.DBModel.Location;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface LocationDAO {
    @Query("SELECT * FROM Location")
    Flowable<List<Location>> getAll();

    @Query("SELECT * FROM Location")
    List<Location> getAllList();

    @Insert(onConflict= OnConflictStrategy.REPLACE)
    void insertAll(Location... locations);

    @Delete
    void delete(Location location);

}
