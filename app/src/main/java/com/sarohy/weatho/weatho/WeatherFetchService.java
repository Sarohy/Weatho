package com.sarohy.weatho.weatho;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.sarohy.weatho.weatho.Model.DBModel.Location;
import com.sarohy.weatho.weatho.Model.ProjectRepository;
import com.sarohy.weatho.weatho.View.Activity.MainActivity;

import java.util.ArrayList;
import java.util.Date;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class WeatherFetchService extends IntentService {

    ProjectRepository projectRepository;
    ArrayList<Location> locations = new ArrayList<>();

    public WeatherFetchService() {
        super("WeatherFetchService");
    }

    NotificationManager notificationManager;
    PendingIntent pIntent;
    String cId= "2";
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onHandleIntent(Intent intent) {
       // Thread.currentThread().getId() == Looper.getMainLooper().getThread().getId()
        if (intent != null) {
            Log.d("Tested", "In service");
            projectRepository = new ProjectRepository(getApplicationContext());
             String cName = "Notification";
            int importance = 0;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                importance = NotificationManager.IMPORTANCE_DEFAULT;
            }
            Intent i = new Intent(this, MainActivity.class);
            pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), i, 0);
            notificationManager =
                    (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                NotificationChannel notificationChannel = new NotificationChannel(cId,cName,importance);
                assert notificationManager != null;
                notificationManager.createNotificationChannel(notificationChannel);
            }
            projectRepository.loadLocationFromDB(locations);
            String str =projectRepository.loadDataASynchronous(locations);

            Notification n  = new Notification.Builder(this)
                    .setContentTitle("Weather Syncing Completed")
                    .setContentText("Updated at " + (Utils.DateToTime(new Date())))
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentIntent(pIntent)
                    .setChannelId(cId)
                    .setStyle(new Notification.BigTextStyle().bigText(str))
                    .setAutoCancel(true)
                    .build();
            assert notificationManager != null;
            notificationManager.notify(6, n);

        }
    }
}
