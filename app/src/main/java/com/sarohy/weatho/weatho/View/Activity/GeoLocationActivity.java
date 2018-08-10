package com.sarohy.weatho.weatho.View.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.IntentSender;
import android.content.IntentSender.SendIntentException;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.sarohy.weatho.weatho.R;
import com.sarohy.weatho.weatho.ViewModel.GeoLocationViewModel;
import com.sarohy.weatho.weatho.WeathoApplication;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GeoLocationActivity extends AppCompatActivity implements
        ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener, View.OnClickListener {

    @BindView(R.id.tv_latitude)
    TextView tvLatitude;
    @BindView(R.id.tv_longitude)
    TextView tvLongitude;
    @BindView(R.id.tv_city)
    TextView tvCityName;
    @BindView(R.id.pb_load)
    ProgressBar pbLoad;
    @BindView(R.id.fab_done)
    FloatingActionButton fabDone;
    @BindView(R.id.fab_refresh)
    FloatingActionButton fabRefresh;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private LocationRequest mLocationRequest;
    private GeoLocationViewModel viewModel;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 150:
                if (grantResults[0]==0) {
                    Log.d("Tested", String.valueOf(grantResults[0]));
                    mGoogleApiClient = new GoogleApiClient.Builder(this)
                            .addConnectionCallbacks(this)
                            .addOnConnectionFailedListener(this)
                            .addApi(LocationServices.API)
                            .build();

                    mGoogleApiClient.connect();
                }
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geo_location);
        ButterKnife.bind(this);
        viewModel = ViewModelProviders.of( this).get(GeoLocationViewModel.class);

        fabDone.setOnClickListener(this);
        fabRefresh.setOnClickListener(this);
        fabRefresh.setVisibility(View.GONE);
        fabDone.setVisibility(View.GONE);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        150);
            }
        } else {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();

            mGoogleApiClient.connect();
        }

    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        settingRequest();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(this, "Connection Suspended!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "Connection Failed!", Toast.LENGTH_SHORT).show();
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, 90000);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        } else {
            Log.i("Current Location", "Location services connection failed with code " + connectionResult.getErrorCode());
        }
    }

    private void settingRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);    // 10 seconds, in milliseconds
        mLocationRequest.setFastestInterval(1000);   // 1 second, in milliseconds
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient,
                        builder.build());

        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {

            @Override
            public void onResult(@NonNull LocationSettingsResult result) {
                final Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        getLocation();
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            status.startResolutionForResult(GeoLocationActivity.this, 1000);
                        } catch (SendIntentException e) {
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        break;
                }
            }

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1000:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        // All required changes were successfully made
                        getLocation();
                        break;
                    case Activity.RESULT_CANCELED:
                        // The user was asked to change settings, but chose not to
                        Toast.makeText(this, "Location Service not Enabled", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
                break;

        }
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        } else {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                    mGoogleApiClient);

            if (mLastLocation != null) {
                updateUI();
            } else {
                Log.i("Current Location", "No data for location found");
                if (!mGoogleApiClient.isConnected())
                    mGoogleApiClient.connect();
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
            }
        }
    }
    private com.sarohy.weatho.weatho.Model.DBModel.Location[] locationDB;
    @SuppressLint("SetTextI18n")
    private void updateUI() {
        locationDB = new com.sarohy.weatho.weatho.Model.DBModel.Location[1];
        viewModel.fetchLocation(String.valueOf(mLastLocation.getLatitude()),String.valueOf(mLastLocation.getLongitude()));
        viewModel.getLocation().observe(this, new Observer<com.sarohy.weatho.weatho.Model.DBModel.Location>() {
            @Override
            public void onChanged(@Nullable com.sarohy.weatho.weatho.Model.DBModel.Location location) {
                locationDB[0] = location;
                assert location != null;
                fabRefresh.setVisibility(View.VISIBLE);
                fabDone.setVisibility(View.VISIBLE);
                tvCityName.setText(location.getDataToDisplay());
            }
        });
        pbLoad.setVisibility(View.INVISIBLE);
        tvLatitude.setText("Latitude: " + String.valueOf(mLastLocation.getLatitude()));
        tvLongitude.setText("Longitude: " + String.valueOf(mLastLocation.getLongitude()));
    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        updateUI();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab_done:
                viewModel.addLocation(locationDB[0]);
                WeathoApplication.component.getSharedPrefs().setCityKey(locationDB[0].getKey());
                WeathoApplication.component.getSharedPrefs().setCityName(locationDB[0].getLocalizedName());
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
                break;
            case R.id.fab_refresh:
                pbLoad.setVisibility(View.VISIBLE);
                fabRefresh.setVisibility(View.INVISIBLE);
                fabDone.setVisibility(View.INVISIBLE);
                tvLatitude.setText("");
                tvLongitude.setText("");
                getLocation();
                break;
        }
    }
}
