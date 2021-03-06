package com.sarohy.weatho.weatho.ViewModel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.sarohy.weatho.weatho.Model.DBModel.Location;
import com.sarohy.weatho.weatho.Model.ProjectRepository;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivityViewModel extends AndroidViewModel {
    private MutableLiveData<List<Location>> cities;
    private final ProjectRepository projectRepository;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        cities = new MutableLiveData<>();
        projectRepository = new ProjectRepository();
        loadDataFromDB();
    }

    @SuppressLint("CheckResult")
    private void loadDataFromDB() {
        projectRepository.loadLocationFromDB()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Location>>() {
                    @Override
                    public void accept(List<Location> locations) throws Exception {
                        cities.postValue(locations);
                    }
                });
    }

    public LiveData<List<Location>> getCities(){
        return cities;
    }

}
