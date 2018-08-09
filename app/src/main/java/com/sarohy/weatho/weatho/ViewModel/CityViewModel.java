package com.sarohy.weatho.weatho.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.sarohy.weatho.weatho.Model.APIModel.City;
import com.sarohy.weatho.weatho.Model.DBModel.Location;
import com.sarohy.weatho.weatho.Model.ProjectRepository;

import java.util.ArrayList;
import java.util.List;

public class CityViewModel extends AndroidViewModel implements ProjectRepository.CallBack {
    private final ArrayList<City>Cities = new ArrayList<>();
    private final MutableLiveData<Boolean> fetched = new MutableLiveData<>();
    private final ProjectRepository projectRepository;

    public CityViewModel(@NonNull Application application) {
        super(application);
        projectRepository = new ProjectRepository();
        fetched.setValue(false);
    }
    public void getData(String str){
        fetched.setValue(false);
        projectRepository.fetchLocationByLetter(str,this);
    }

    /**
     * Expose the LiveData Projects query so the UI can observe it.
     */
    public List<City> getCities() {
        return Cities;
    }

    public LiveData<Boolean> dataFetched() {
        return fetched;
    }


    @Override
    public void onCityFetchedByWord(ArrayList<City> cities) {
        this.Cities.clear();
        this.Cities.addAll(cities);
        fetched.postValue(true);
    }

    @Override
    public void onLocationFetchedByGeo(Location location) {

    }
}
