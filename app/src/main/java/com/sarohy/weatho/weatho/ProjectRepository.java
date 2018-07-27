package com.sarohy.weatho.weatho;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.Toast;

import com.sarohy.weatho.weatho.API.APIClient;
import com.sarohy.weatho.weatho.API.APIInterface;
import com.sarohy.weatho.weatho.Database.AppDatabase;
import com.sarohy.weatho.weatho.Model.APIModel.City;
import com.sarohy.weatho.weatho.Model.APIModel.CurrentWeather;
import com.sarohy.weatho.weatho.Model.APIModel.DayForecast;
import com.sarohy.weatho.weatho.Model.APIModel.HourForecast;
import com.sarohy.weatho.weatho.Model.APIModel.DayForecastList;
import com.sarohy.weatho.weatho.Model.DBModel.WeatherCurrent;
import com.sarohy.weatho.weatho.Model.DBModel.Location;
import com.sarohy.weatho.weatho.Model.DBModel.WeatherHour;
import com.sarohy.weatho.weatho.Model.DBModel.WeatherDay;

import org.jetbrains.annotations.NotNull;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProjectRepository {
    private static final String LOG_TAG = ProjectRepository.class.getSimpleName();

    // For Singleton instantiation
    private static final Object LOCK = new Object();
    @SuppressLint("StaticFieldLeak")
    private static ProjectRepository projectRepository;
    private final AppDatabase appDatabase;
    private APIInterface apiService;
    private String API_KEY;
    private Context context;
    private String toastMessage = "Request Overflowed!!";

    private ProjectRepository(Context context) {
        this.API_KEY = Utils.API_KEY4;
        this.context = context;
        apiService = APIClient.getClient().create(APIInterface.class);
        appDatabase = AppDatabase.getAppDatabase(context);

    }

    public synchronized static ProjectRepository getInstance(Context context) {
        if (projectRepository == null) {
            projectRepository = new ProjectRepository(context);
        }
        return projectRepository;
    }

    public LiveData<List<Location>> loadLocationFromDB() {
        return appDatabase.locationDAO().getAll();
    }

    public void deleteLocation(final Location city) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                appDatabase.locationDAO().delete(city);
            }
        });
        t.start();

    }

    public void addLocation(final Location city) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                appDatabase.locationDAO().insertAll(city);
            }
        });
        t.start();

    }

    public void fetchLocationByLetter(String str, final CallBack callBack) {

        Call<ArrayList<City>> call = apiService.getCities(API_KEY, str);
        call.enqueue(new Callback<ArrayList<City>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<City>> call, @NonNull Response<ArrayList<City>> response) {
                if (response.body() != null && !response.message().toLowerCase().equals("unauthorized")) {
                    callBack.onCityFetchedByWord(response.body());
                    Log.d("Getting cities result", "Number of movies received: " + response.body().size());
                }
                else {
                    Toast.makeText(context,toastMessage,Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<City>> call, @NonNull Throwable t) {

            }
        });
    }

    private void fetchDayForecast(final String cityKey) {
        Call<DayForecastList> call = apiService.getForecastOf5Day(cityKey, API_KEY);
        call.enqueue(new Callback<DayForecastList>() {
            @Override
            public void onResponse(@NonNull Call<DayForecastList> call, @NonNull Response<DayForecastList> response) {
                if (response.body() != null && !response.message().toLowerCase().equals("unauthorized")) {
                    final DayForecastList weatherOf5Day = response.body();
                    Thread t = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            appDatabase.weatherDayDAO().deleteByCity(cityKey);
                            assert weatherOf5Day != null;
                            Log.d(LOG_TAG, "Inserting Day Weather Forecast");
                            for (final DayForecast d : weatherOf5Day.getDayForecasts()) {
                                appDatabase.weatherDayDAO().insertAll(Utils.weatherAPItoDB(cityKey, d));
                            }
                        }
                    });
                    t.run();
                }
                else {
                    Toast.makeText(context,toastMessage,Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(@NonNull Call<DayForecastList> call, @NonNull Throwable t) {

            }
        });
    }

    public void fetchHourlyData(final String cityKey, final MutableLiveData<ArrayList<WeatherHour>> liveData) {
        Call<ArrayList<HourForecast>> call = apiService.get12HoursForecast(cityKey, API_KEY);
        call.enqueue(new Callback<ArrayList<HourForecast>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<HourForecast>> call, @NonNull Response<ArrayList<HourForecast>> response) {
                if (response.body() != null && !response.message().toLowerCase().equals("unauthorized")) {
                    final List<HourForecast> dayWeatherForecasts = response.body();
                    if (dayWeatherForecasts != null && dayWeatherForecasts.size() >= 5) {
                        final Thread t = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                appDatabase.weatherOf12HoursDAO().deleteByCity(cityKey);
                                Log.d(LOG_TAG, "Inserting Hour Weather Forecast");
                                ArrayList<WeatherHour> array = new ArrayList<>();
                                for (final HourForecast d : dayWeatherForecasts) {
                                    WeatherHour weather12Hour = Utils.weatherOf12HoursAPItoDB(cityKey, d);
                                    array.add(weather12Hour);
                                    appDatabase.weatherOf12HoursDAO().insertAll(weather12Hour);
                                }
                                liveData.postValue(array);
                            }
                        });
                        t.start();
                    }
                }
                else {
                    Toast.makeText(context,toastMessage,Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<HourForecast>> call, @NonNull Throwable t) {
                Log.d(LOG_TAG, "Fail");
            }
        });
    }

    public void fetchCurrentWeather(final String cityKey) {
        Call<ArrayList<CurrentWeather>> call = apiService.getCurrentUpdate(cityKey, API_KEY);
        call.enqueue(new Callback<ArrayList<CurrentWeather>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<CurrentWeather>> call, @NonNull Response<ArrayList<CurrentWeather>> response) {
                final ArrayList<CurrentWeather> currentWeather = (ArrayList<CurrentWeather>) response.body();
                if (currentWeather != null && !response.message().toLowerCase().equals("unauthorized")) {
                    Thread t = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d(LOG_TAG, "Inserting Current Weather");
                            appDatabase.currentWeatherDAO().deleteByCity(cityKey);
                            appDatabase.currentWeatherDAO().insertAll(Utils.currentWeatherAPItoDB(cityKey, currentWeather.get(0)));
                        }
                    });
                    t.start();
                }
                else {
                    Toast.makeText(context,toastMessage,Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<CurrentWeather>> call, @NonNull Throwable t) {
                Log.d(LOG_TAG, "Failed");
            }
        });
    }

    public void fetchLocationByGeo(final String location, final MutableLiveData<Location> locationMutableLiveData) {

        Call<City> call = apiService.getCity(API_KEY, location);
        call.enqueue(new Callback<City>() {
            @Override
            public void onResponse(@NonNull Call<City> call, @NonNull final Response<City> response) {
                if (response.body() != null && !response.message().toLowerCase().equals("unauthorized")) {
                    locationMutableLiveData.setValue(Utils.cityAPItoDB(response.body()));
                }
                else {
                    Toast.makeText(context,toastMessage,Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<City> call, @NonNull Throwable t) {

            }
        });
    }

    public LiveData<List<WeatherDay>> loadDayForecastFromDB(@NotNull final String cityKey) {
        return appDatabase.weatherDayDAO().forecastByCity(cityKey);
    }


    public LiveData<WeatherCurrent> loadCurrentWeatherFromDB(final String cityKey) {
        return(appDatabase.currentWeatherDAO().forecastByCity(cityKey));
    }

    public void loadHourlyDataFromDB(@NotNull final String cityKey, @NotNull final MutableLiveData<ArrayList<WeatherHour>> weatherDay) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                ArrayList<WeatherHour> arrayList = (ArrayList<WeatherHour>) appDatabase.weatherOf12HoursDAO().forecastByCity(cityKey);
                weatherDay.postValue(arrayList);
            }
        });
        t.start();
    }

    public void loadAllData(String key) {
        fetchDayForecast(key);
        fetchCurrentWeather(key);
        fetchHourlyData(key, new MutableLiveData<ArrayList<WeatherHour>>());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void loadDataASynchronous(ArrayList<Location> locations, NotificationManager notificationManager, PendingIntent pIntent, String cId, Context context) {
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = 0; i<locations.size(); i++) {
            final String cityKey = locations.get(i).getKey();
            Log.d(LOG_TAG, "Doing Fetching");
            Call<ArrayList<HourForecast>> call1 = apiService.get12HoursForecast(cityKey, API_KEY);
            final List<HourForecast> dayWeatherForecasts;
            try {
                dayWeatherForecasts = call1.execute().body();
                if (dayWeatherForecasts != null) {
                    if (dayWeatherForecasts.size() >= 5) {
                        appDatabase.weatherOf12HoursDAO().deleteAll();
                        for (final HourForecast d : dayWeatherForecasts) {
                            appDatabase.weatherOf12HoursDAO().insertAll(Utils.weatherOf12HoursAPItoDB(cityKey, d));
                        }

                        Log.d(LOG_TAG, "Added to DB - hourly");
                    }
                } else {
                    Toast.makeText(context, toastMessage, Toast.LENGTH_LONG).show();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            Call<DayForecastList> call2 = apiService.getForecastOf5Day(cityKey, API_KEY);
            DayForecastList weatherOf5Day = null;
            try {
                weatherOf5Day = call2.execute().body();
                if (weatherOf5Day != null) {
                    appDatabase.weatherDayDAO().deleteAll();
                    for (final DayForecast d : weatherOf5Day.getDayForecasts()) {
                        WeatherDay w = Utils.weatherAPItoDB(cityKey, d);
                        appDatabase.weatherDayDAO().insertAll(w);
                    }
                    WeatherDay w = Utils.weatherAPItoDB(cityKey, weatherOf5Day.getDayForecasts().get(0));
                    arrayList.add(locations.get(i).getLocalizedName() + " " + w.getIconPhraseDay());
                    Log.d(LOG_TAG, "Added to DB - daily");
                } else {
                    Toast.makeText(context, toastMessage, Toast.LENGTH_LONG).show();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            Call<ArrayList<CurrentWeather>> call3 = apiService.getCurrentUpdate(cityKey, API_KEY);
            final ArrayList<CurrentWeather> currentWeather;
            try {
                currentWeather = (ArrayList<CurrentWeather>) call3.execute().body();
                if (currentWeather != null) {
                    appDatabase.currentWeatherDAO().deleteAll();
                    appDatabase.currentWeatherDAO().insertAll(Utils.currentWeatherAPItoDB(
                            cityKey, currentWeather.get(0)));
                    Log.d(LOG_TAG, "Added to DB - current");
                } else {
                    Toast.makeText(context, toastMessage, Toast.LENGTH_LONG).show();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Log.d(LOG_TAG,"Done");
        StringBuilder str= new StringBuilder();
        for (String s: arrayList){
            str.append(s).append("\n");
        }
        Notification n  = new Notification.Builder(context)
                .setContentTitle("Weather Syncing Completed")
                .setContentText("Updated at " + (Utils.DateToTime(new Date())))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pIntent)
                .setChannelId(cId)
                .setStyle(new Notification.BigTextStyle().bigText(str.toString()))
                .setAutoCancel(true)
                .build();
        assert notificationManager != null;
        notificationManager.notify(6, n);

    }

    public void loadLocationFromDB(ArrayList<Location> arrayList) {
        arrayList.addAll(appDatabase.locationDAO().getAll().getValue());
    }
    public interface CallBack{
        void onCityFetchedByWord(ArrayList<City> cities);
    }
}
