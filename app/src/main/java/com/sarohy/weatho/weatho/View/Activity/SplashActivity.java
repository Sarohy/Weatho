package com.sarohy.weatho.weatho.View.Activity;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.sarohy.weatho.weatho.R;
import com.sarohy.weatho.weatho.Utils;

import butterknife.BindView;

public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.tv_logo)
    TextView tvLogo;
    @BindView(R.id.tv_footer)
    TextView tvFooter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Activity activity = this;
        SharedPreferences sharedPreferences = getSharedPreferences(Utils.City,
                Context.MODE_PRIVATE);
        String value = sharedPreferences.getString(Utils.CityKey,"-1");
        Bundle bundle=ActivityOptions.makeSceneTransitionAnimation(activity).toBundle();
        if (value.equals("-1"))
            startActivity(new Intent(this, GeoLocationActivity.class),bundle);
        else
            startActivity(new Intent(this, MainActivity.class),bundle);
        finish();
    }
}
