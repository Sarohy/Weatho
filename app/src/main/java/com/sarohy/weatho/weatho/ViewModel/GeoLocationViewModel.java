package com.sarohy.weatho.weatho.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.sarohy.weatho.weatho.Model.APIModel.City;
import com.sarohy.weatho.weatho.Model.DBModel.Location;
import com.sarohy.weatho.weatho.ProjectRepository;

import java.util.ArrayList;

public class GeoLocationViewModel extends AndroidViewModel implements ProjectRepository.CallBack {
    ProjectRepository projectRepository;
    MutableLiveData<Location> locationMutableLiveData;
    public GeoLocationViewModel(@NonNull Application application) {
        super(application);
        projectRepository = ProjectRepository.getInstance(application);
    }
    public void fetchLocation(String lat, String log){
        projectRepository.fetchLocationByGeo(lat+","+log, this);
    }

    @Override
    public void onCityFetchedByWord(ArrayList<City> cities) {

    }

    @Override
    public void onLocationFetchedByGeo(Location location) {
        locationMutableLiveData.setValue(location);
    }

    public LiveData<Location> getLocation() {
        return locationMutableLiveData;
    }
}
