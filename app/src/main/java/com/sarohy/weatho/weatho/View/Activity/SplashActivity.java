package com.sarohy.weatho.weatho.View.Activity;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.sarohy.weatho.weatho.WeathoApplication;

public class SplashActivity extends AppCompatActivity {

//    @BindView(R.id.tv_logo)
//    TextView tvLogo;
//    @BindView(R.id.tv_footer)
//    TextView tvFooter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Activity activity = this;
        String value = WeathoApplication.component.getSharedPrefs().getCityKey();
        Bundle bundle=ActivityOptions.makeSceneTransitionAnimation(activity).toBundle();
        if (value.equals("-1"))
            startActivity(new Intent(this, GeoLocationActivity.class),bundle);
        else
            startActivity(new Intent(this, MainActivity.class),bundle);
        finish();
    }
}
