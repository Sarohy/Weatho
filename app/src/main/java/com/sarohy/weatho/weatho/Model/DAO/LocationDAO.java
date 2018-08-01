package com.sarohy.weatho.weatho.Model.DAO;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.sarohy.weatho.weatho.Model.DBModel.Location;

import java.util.ArrayList;
import java.util.List;
@Dao
public interface LocationDAO {
    @Query("SELECT * FROM Location")
    LiveData<List<Location>> getAll();

    @Query("SELECT * FROM Location")
    List<Location> getAllList();

    @Insert(onConflict= OnConflictStrategy.REPLACE)
    void insertAll(Location... locations);

    @Delete
    void delete(Location location);

    @Query("DELETE FROM Location")
    void deleteAll();
}
