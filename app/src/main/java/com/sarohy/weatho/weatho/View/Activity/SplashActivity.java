package com.sarohy.weatho.weatho.View.Activity;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.sarohy.weatho.weatho.R;

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
        Bundle bundle=ActivityOptions.makeSceneTransitionAnimation(activity).toBundle();
        startActivity(new Intent(this, MainActivity.class),bundle);
        finish();
    }
}
