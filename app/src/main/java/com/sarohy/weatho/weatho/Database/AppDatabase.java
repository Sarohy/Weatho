package com.sarohy.weatho.weatho.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import com.sarohy.weatho.weatho.Converter;
import com.sarohy.weatho.weatho.Model.DAO.CurrentWeatherDAO;
import com.sarohy.weatho.weatho.Model.DAO.LocationDAO;
import com.sarohy.weatho.weatho.Model.DAO.WeatherDayDAO;
import com.sarohy.weatho.weatho.Model.DAO.WeatherHoursDAO;
import com.sarohy.weatho.weatho.Model.DBModel.WeatherCurrent;
import com.sarohy.weatho.weatho.Model.DBModel.Location;
import com.sarohy.weatho.weatho.Model.DBModel.WeatherHour;
import com.sarohy.weatho.weatho.Model.DBModel.WeatherDay;

@Database(entities = {Location.class, WeatherDay.class, WeatherCurrent.class, WeatherHour.class}, version = 1)
@TypeConverters(Converter.class)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;
    public abstract LocationDAO locationDAO();
    public abstract WeatherDayDAO weatherDayDAO();
    public abstract CurrentWeatherDAO currentWeatherDAO();
    public abstract WeatherHoursDAO weatherOf12HoursDAO();

    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "user-database")
                            .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
