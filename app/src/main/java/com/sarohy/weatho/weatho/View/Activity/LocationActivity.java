package com.sarohy.weatho.weatho.View.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityOptions;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Toast;

import com.sarohy.weatho.weatho.Model.APIModel.City;
import com.sarohy.weatho.weatho.Model.DBModel.Location;
import com.sarohy.weatho.weatho.R;
import com.sarohy.weatho.weatho.RecyclerItemTouchHelper;
import com.sarohy.weatho.weatho.Utils;
import com.sarohy.weatho.weatho.View.Adapter.CityListRVAdapter;
import com.sarohy.weatho.weatho.ViewModel.CityListViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LocationActivity extends AppCompatActivity implements View.OnClickListener, RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {

    @BindView(R.id.fab_add_location)
    FloatingActionButton fabAddLocation;
    @BindView(R.id.rv_cities)
    RecyclerView rvCities;


    private
    Activity activity;
    private final int REQUEST_CODE = 2;
    private CityListViewModel viewModel;
    private Context context;
    private CityListRVAdapter cityListRVAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        context = this;
        activity = this;
        viewModel = ViewModelProviders.of((FragmentActivity) context).get(CityListViewModel.class);
        cityListRVAdapter = new CityListRVAdapter(new ArrayList<Location>(),this);
        setup();
    }

    private void setup() {
        fabAddLocation.setOnClickListener(this);
        rvCities.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvCities.setLayoutManager(mLayoutManager);
        rvCities.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        rvCities.setItemAnimator(new DefaultItemAnimator());
        rvCities.setAdapter(cityListRVAdapter);
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT,this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(rvCities);
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback1 = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.UP) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        new ItemTouchHelper(itemTouchHelperCallback1).attachToRecyclerView(rvCities);
        viewModel.getCities().observe((LifecycleOwner) context, new Observer<List<Location>>() {
            @Override
            public void onChanged(@Nullable List<Location> fetch) {
                if (fetch!=null) {
                    cityListRVAdapter.updateList(fetch);
                    cityListRVAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if(resultCode == RESULT_OK) {
                City c = (City) Objects.requireNonNull(data.getExtras()).get("city");
                assert c != null;
                viewModel.insertCity(Utils.cityAPItoDB(c));
            }
        }
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab_add_location:
                Bundle bundle= ActivityOptions.makeSceneTransitionAnimation(activity).toBundle();
                startActivityForResult(new Intent(getApplication(), AddLocationActivity.class),REQUEST_CODE,bundle);
                break;
        }
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        Toast.makeText(this,String.valueOf(position),Toast.LENGTH_SHORT).show();
        Location city = Objects.requireNonNull(viewModel.getCities().getValue()).get(position);
        viewModel.deleteCity(city);
    }
}
