package com.sarohy.weatho.weatho.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.sarohy.weatho.weatho.Model.DBModel.WeatherCurrent;
import com.sarohy.weatho.weatho.Model.DBModel.Location;
import com.sarohy.weatho.weatho.ProjectRepository;
import com.sarohy.weatho.weatho.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivityViewModel extends AndroidViewModel {
    private LiveData<List<Location>> cities;
    public ProjectRepository projectRepository;
    private Application application;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        projectRepository = ProjectRepository.getInstance(application);
        loadDataFromDB();
    }

    private void loadDataFromDB() {
        cities = projectRepository.loadLocationFromDB();
    }

    public LiveData<List<Location>> getCities(){
        return cities;
    }

    public void load() {
        loadDataFromDB();
    }
}
