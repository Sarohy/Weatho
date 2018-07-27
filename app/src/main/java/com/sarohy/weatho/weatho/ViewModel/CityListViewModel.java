package com.sarohy.weatho.weatho.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.sarohy.weatho.weatho.Model.DBModel.Location;
import com.sarohy.weatho.weatho.ProjectRepository;
import com.sarohy.weatho.weatho.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CityListViewModel extends AndroidViewModel {
    private LiveData<List<Location>> cities;
    private ProjectRepository projectRepository;


    public CityListViewModel(@NonNull Application application) {
        super(application);
        projectRepository = ProjectRepository.getInstance(application);
        loadData();
    }

    private void loadData(){
        cities = projectRepository.loadLocationFromDB();
    }

    public LiveData<List<Location>> getCities(){
        return cities;
    }

    public void insertCity(Location city){
        projectRepository.addLocation(city);
        projectRepository.loadAllData(city.getKey());
    }

    public void deleteCity(Location city){
        projectRepository.deleteLocation(city);
        loadData();
    }
}
