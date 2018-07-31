package com.sarohy.weatho.weatho.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.sarohy.weatho.weatho.Model.DBModel.Location;
import com.sarohy.weatho.weatho.Model.ProjectRepository;

import java.util.List;

public class CityListViewModel extends AndroidViewModel {
    private LiveData<List<Location>> cities;
    private ProjectRepository projectRepository;


    public CityListViewModel(@NonNull Application application) {
        super(application);
        projectRepository = new ProjectRepository(application);
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
