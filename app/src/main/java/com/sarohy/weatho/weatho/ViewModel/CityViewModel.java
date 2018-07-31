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
    private ArrayList<City>Cities = new ArrayList<>();
    private MutableLiveData<Boolean> fetched = new MutableLiveData<>();
    private final String TAG = "HelloWorld";
    private ProjectRepository projectRepository;
    private LiveData<List<Location>> citiesAdded;

    public CityViewModel(@NonNull Application application) {
        super(application);
        projectRepository = new ProjectRepository(application);
        fetched.setValue(false);
        citiesAdded = projectRepository.loadLocationFromDB();
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

    public boolean checkIsEntered(City c) {
//        for (int i=0;i<citiesAdded.getValue().size();i++) {
//            Location location = citiesAdded.getValue().get(i);
//            if (c.getKey().equals(location.getKey())) {
//                return true;
//            }
//        }
        return false;
    }

    @Override
    public void onCityFetchedByWord(ArrayList<City> cities) {
        this.Cities.clear();
        this.Cities.addAll(cities);
        fetched.setValue(true);
    }

    @Override
    public void onLocationFetchedByGeo(Location location) {

    }
}
