package com.sarohy.weatho.weatho;

import android.annotation.SuppressLint;
import android.util.Log;

import com.sarohy.weatho.weatho.Model.APIModel.City;
import com.sarohy.weatho.weatho.Model.APIModel.CurrentWeather;
import com.sarohy.weatho.weatho.Model.APIModel.Day;
import com.sarohy.weatho.weatho.Model.APIModel.DayForecast;
import com.sarohy.weatho.weatho.Model.APIModel.HourForecast;
import com.sarohy.weatho.weatho.Model.APIModel.Maximum;
import com.sarohy.weatho.weatho.Model.APIModel.Minimum;
import com.sarohy.weatho.weatho.Model.APIModel.Night;
import com.sarohy.weatho.weatho.Model.APIModel.Temperature;
import com.sarohy.weatho.weatho.Model.DBModel.WeatherCurrent;
import com.sarohy.weatho.weatho.Model.DBModel.Location;
import com.sarohy.weatho.weatho.Model.DBModel.WeatherHour;
import com.sarohy.weatho.weatho.Model.DBModel.WeatherDay;

import org.jetbrains.annotations.Nullable;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Utils {
    public static final String BASE_URL = "http://dataservice.accuweather.com/";
    public static final String API_KEY1 = "0B2ulCEWdRYvelMurc54xcaAPpbyarMw";
    public static final String API_KEY2 = "ADaTpwFUasrct7lU8w15pt8ye04bO19k";
    public static final String API_KEY3 = "9rCSj6Cgvbx4T6EtCX96bAkC4wufd1Wm";
    public static final String API_KEY4 = "LgyKJC9NlRb0lQVpEuUVDuSKCq74sQn3";
    public static final String SETTING = "Settings";
    public static String SP_HOME_CITY = "HomeCityID";
    private static final int [] backgroundImages = {R.drawable.day_8,R.drawable.day_10,
            R.drawable.day_3,R.drawable.day_5,R.drawable.night_9,R.drawable.night_12};

    private static final int [] weatherImages = {
            R.drawable.w1,R.drawable.w1,
            R.drawable.w4,R.drawable.w4,
            R.drawable.w11,R.drawable.w4,
            R.drawable.w7,R.drawable.w8,
            R.drawable.ic_info_black_24dp,R.drawable.ic_info_black_24dp,
            R.drawable.w11,R.drawable.w12,
            R.drawable.w13,R.drawable.w14,
            R.drawable.w15,R.drawable.w17,
            R.drawable.w17,R.drawable.w18,
            R.drawable.w19,R.drawable.w20,
            R.drawable.w22,R.drawable.w22,
            R.drawable.w23,R.drawable.ic_info_black_24dp,
            R.drawable.w25, R.drawable.w26,
            R.drawable.ic_info_black_24dp,R.drawable.ic_info_black_24dp,
            R.drawable.w29,R.drawable.ic_info_black_24dp,
            R.drawable.ic_info_black_24dp,R.drawable.w32,
            R.drawable.w33,R.drawable.w33,
            R.drawable.w36, R.drawable.w36,
            R.drawable.w11,R.drawable.w36,
            R.drawable.w39,R.drawable.w40,
            R.drawable.w41,R.drawable.w41,
            R.drawable.w43,R.drawable.w44
    };
    private static final int [] weatherAnimation = {
            4,4,
            4,5,
            7,5,
            5,5,
            7,7,
            1,2,
            2,2,
            3,3,
            3,2,
            0,0,
            0,0,
            0,0,
            6,2,
            7,7,
            6,5,
            7,1,
            5,5,
            6,6,
            7,6,
            2,2,
            3,3,
            0,0
    };

    public static String [] weatherIconLink={
            "https://www.developer.accuweather.com/sites/default/files/01-s.png",
            "https://www.developer.accuweather.com/sites/default/files/02-s.png",
            "https://www.developer.accuweather.com/sites/default/files/03-s.png",
            "https://www.developer.accuweather.com/sites/default/files/04-s.png",
            "https://www.developer.accuweather.com/sites/default/files/05-s.png",
            "https://www.developer.accuweather.com/sites/default/files/06-s.png",
            "https://www.developer.accuweather.com/sites/default/files/07-s.png",
            "https://www.developer.accuweather.com/sites/default/files/08-s.png",
            "https://www.developer.accuweather.com/sites/default/files/09-s.png",
            "https://www.developer.accuweather.com/sites/default/files/10-s.png",
            "https://www.developer.accuweather.com/sites/default/files/11-s.png",
            "https://www.developer.accuweather.com/sites/default/files/12-s.png",
            "https://www.developer.accuweather.com/sites/default/files/13-s.png",
            "https://www.developer.accuweather.com/sites/default/files/14-s.png",
            "https://www.developer.accuweather.com/sites/default/files/15-s.png",
            "https://www.developer.accuweather.com/sites/default/files/16-s.png",
            "https://www.developer.accuweather.com/sites/default/files/17-s.png",
            "https://www.developer.accuweather.com/sites/default/files/18-s.png",
            "https://www.developer.accuweather.com/sites/default/files/19-s.png",
            "https://www.developer.accuweather.com/sites/default/files/20-s.png",
            "https://www.developer.accuweather.com/sites/default/files/21-s.png",
            "https://www.developer.accuweather.com/sites/default/files/22s.png",
            "https://www.developer.accuweather.com/sites/default/files/23-s.png",
            "https://www.developer.accuweather.com/sites/default/files/24-s.png",
            "https://www.developer.accuweather.com/sites/default/files/25-s.png",
            "https://www.developer.accuweather.com/sites/default/files/26-s.png",
            "https://www.developer.accuweather.com/sites/default/files/27-s.png",
            "https://www.developer.accuweather.com/sites/default/files/28-s.png",
            "https://www.developer.accuweather.com/sites/default/files/29-s.png",
            "https://www.developer.accuweather.com/sites/default/files/30-s.png",
            "https://www.developer.accuweather.com/sites/default/files/31-s.png",
            "https://www.developer.accuweather.com/sites/default/files/32-s.png",
            "https://www.developer.accuweather.com/sites/default/files/33-s.png",
            "https://www.developer.accuweather.com/sites/default/files/34-s.png",
            "https://www.developer.accuweather.com/sites/default/files/35-s.png",
            "https://www.developer.accuweather.com/sites/default/files/36-s.png",
            "https://www.developer.accuweather.com/sites/default/files/37-s.png",
            "https://www.developer.accuweather.com/sites/default/files/38-s.png",
            "https://www.developer.accuweather.com/sites/default/files/39-s.png",
            "https://www.developer.accuweather.com/sites/default/files/40-s.png",
            "https://www.developer.accuweather.com/sites/default/files/41-s.png",
            "https://www.developer.accuweather.com/sites/default/files/42-s.png",
            "https://www.developer.accuweather.com/sites/default/files/43-s.png",
            "https://www.developer.accuweather.com/sites/default/files/44-s.png"
    };
    public static String City = "WeatherInfo";
    public static String CityKey = "CityKey";


    public static Location cityAPItoDB(City c){
        return new Location(c.getKey(),c.getAdministrativeArea().getLocalizedName(),
                c.getLocalizedName(),c.getCountry().getLocalizedName());
    }

    public static WeatherDay weatherAPItoDB(String cityKey,DayForecast d) {
        WeatherDay weatherDay = new WeatherDay();
        Day day = d.getDay();
        Night night = d.getNight();
        Temperature temperature = d.getTemperature();
        Maximum maximum = temperature.getMaximum();
        Minimum minimum = temperature.getMinimum();
        Date date = StringToDate(d.getDate()) ;
        weatherDay.setCityKey(cityKey);
        weatherDay.setDate(date);
        weatherDay.setIconDay(Integer.parseInt(day.getIcon()));
        weatherDay.setIconNight(Integer.parseInt(night.getIcon()));
        weatherDay.setIconPhraseDay(day.getIconPhrase());
        weatherDay.setIconPhraseNight(night.getIconPhrase());
        weatherDay.setTemperatureMax(maximum.getValue());
        weatherDay.setTemperatureMin(minimum.getValue());
        weatherDay.setTemperatureUnit(maximum.getUnit());
        weatherDay.setLink(d.getMobileLink());
        return weatherDay;
    }
    @SuppressLint("SimpleDateFormat")
    private static Date StringToDate(String date){
        Date d = null;
        try {
            d = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return d;
    }

    public static String DateToString(@Nullable Date date) {
        @SuppressLint("SimpleDateFormat") DateFormat df = new SimpleDateFormat("dd/MM/yyyy EEEE");
        return df.format(date);
    }

    public static WeatherCurrent currentWeatherAPItoDB(String cityKey,
                                                       CurrentWeather currentWeather) {
        WeatherCurrent currentWeatherUpdate = new WeatherCurrent();
        currentWeatherUpdate.setCityKey(cityKey);
        currentWeatherUpdate.setWeatherIcon(currentWeather.getWeatherIcon());
        currentWeatherUpdate.setWeatherText(currentWeather.getWeatherText());
        currentWeatherUpdate.setMobileLink(currentWeather.getMobileLink());
        currentWeatherUpdate.setTemperature(currentWeather.getTemperature()
                .getMetric().getValue());
        currentWeatherUpdate.setTemperatureUnit(currentWeather.getTemperature()
                .getMetric().getUnit());
        currentWeatherUpdate.setLocalObservationDateTime(StringToDate(currentWeather
                .getLocalObservationDateTime()));
        currentWeatherUpdate.setIsDayTime(currentWeather.getIsDayTime());
        return currentWeatherUpdate;
    }

    public static WeatherHour weatherOf12HoursAPItoDB(String cityKey,
                                                      HourForecast dayWeatherForecast) {
        WeatherHour weather12Hour = new WeatherHour();
        weather12Hour.setCityKey(cityKey);
        weather12Hour.setWeatherIcon(dayWeatherForecast.getWeatherIcon());
        weather12Hour.setWeatherText(dayWeatherForecast.getIconPhrase());
        weather12Hour.setMobileLink(dayWeatherForecast.getMobileLink());
        weather12Hour.setTemperature(dayWeatherForecast.getTemperature()
                .getValue());
        weather12Hour.setTemperatureUnit(dayWeatherForecast.getTemperature()
                .getUnit());
        weather12Hour.setLocalObservationDateTime(StringToDate(dayWeatherForecast
                .getDateTime()));
        weather12Hour.setIsDayTime(dayWeatherForecast.getIsDaylight());
        weather12Hour.setProbability(dayWeatherForecast.getPrecipitationProbability());
        return weather12Hour;
    }
    public static String UnitCovertFToC(String temp){
        float t = Float.parseFloat(temp);
        float ans = (float) (t-32)*5/9;
        return new DecimalFormat("##").format(Math.floor(ans));
    }
    public static String UnitCovertCToF(String temp){
        float t = Float.parseFloat(temp);
        float ans = (float) ((1.8 * t) +32);
        return new DecimalFormat("##").format(Math.floor(ans));
    }
    public static String displayValue(String str){
        return new DecimalFormat("##").format(Math.floor(Float.parseFloat(str)));
    }

    public static int getHeaderImage() {
        Date d = Calendar.getInstance().getTime();
        Log.d("Hours", String.valueOf(d.getHours()));
        int hours = d.getHours();
        if (hours<5){
            return backgroundImages[5];
        }
        else if (hours<9)
            return backgroundImages[0];
        else if (hours<13)
            return backgroundImages[1];
        else if (hours<17)
            return backgroundImages[2];
        else if (hours<21)
            return backgroundImages[3];
        else
            return backgroundImages[4];
    };

    public static int getWeatherIcon(String weatherIcon) {
        return weatherImages[Integer.parseInt(weatherIcon)];
    }

    public static String getWeatherIconLink(String weatherIcon) {
        return weatherIconLink[Integer.parseInt(weatherIcon)];
    }

    public static CharSequence showHiLowWeather(int temperatureUnit, String temperatureMax,
                                                String temperatureMin, String currentTempUnit) {
        if (temperatureUnit==1) {
            if (currentTempUnit.equals("F"))
                return Utils.UnitCovertFToC(temperatureMax)+"°C / " + Utils.UnitCovertFToC(temperatureMin)+"°C";
            else
                return Utils.displayValue(temperatureMax)+"°C / " + Utils.displayValue(temperatureMin)+"°C";
        }
        else {
            if (currentTempUnit.equals("C"))
                return Utils.UnitCovertCToF(temperatureMax)+"°F / " + Utils.UnitCovertCToF(temperatureMin)+"°F";
            else
                return Utils.displayValue(temperatureMax)+"°F / " + Utils.displayValue(temperatureMin)+"°F";
        }
    }
    public static CharSequence showCurrentWeather(int temperatureUnit, String temperature,
                                                 String currentTempUnit) {
        if (temperatureUnit==1) {
            if (currentTempUnit.equals("F"))
                return Utils.UnitCovertFToC(temperature)+"°C";
            else
                return Utils.displayValue(temperature)+"°C";
        }
        else {
            if (currentTempUnit.equals("C"))
                return Utils.UnitCovertCToF(temperature)+"°F";
            else
                return Utils.displayValue(temperature)+"°F";
        }
    }

    @Nullable
    public static CharSequence DateToTime(@Nullable Date date) {
        @SuppressLint("SimpleDateFormat") DateFormat df = new SimpleDateFormat("HH:mm");
        return df.format(date);
    }

    @Nullable
    public static int mapOfWeather(@Nullable String weatherIcon) {
        return weatherAnimation[Integer.parseInt(weatherIcon)];
    }


    //val animation1 = AnimationUtils.loadAnimation(activity!!, R.animator.home_slide_up)
    //rootView.infoLayout!!.animation = animation1
//        val animation = ObjectAnimator.ofFloat(rootView.infoLayout, "translationY", -500f)
//        animation.duration = 2000
//        animation.start()

}
