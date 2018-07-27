package com.sarohy.weatho.weatho.View.Activity;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.sarohy.weatho.weatho.Model.APIModel.City;
import com.sarohy.weatho.weatho.R;
import com.sarohy.weatho.weatho.Utils;
import com.sarohy.weatho.weatho.View.Adapter.CityRVAdapter;
import com.sarohy.weatho.weatho.RecyclerTouchListener;
import com.sarohy.weatho.weatho.ViewModel.CityViewModel;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddLocationActivity extends AppCompatActivity implements View.OnClickListener,TextWatcher{

    @BindView(R.id.et_city_search)
    EditText etCitySearch;
    @BindView(R.id.ibtn_back)
    ImageButton ibtnBack;
    @BindView(R.id.pb_search)
    ProgressBar pbSearch;
    @BindView(R.id.rv_cities)
    RecyclerView rvCities;

    CityViewModel viewModel;
    private Context context;
    private CityRVAdapter cityListAdapter;
    private static final String LOG_TAG = AddLocationActivity.class.getSimpleName()+"Test: In AddLocationActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);
        ButterKnife.bind(this);
        init();

    }

    private void setup() {
        ibtnBack.setOnClickListener(this);
        etCitySearch.addTextChangedListener(this);
        cityListAdapter = new CityRVAdapter((List<City>) viewModel.getCities());
        rvCities.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvCities.setLayoutManager(mLayoutManager);
        rvCities.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        rvCities.setItemAnimator(new DefaultItemAnimator());
        rvCities.setAdapter(cityListAdapter);
        rvCities.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rvCities, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                City city = cityListAdapter.getList().get(position);
                if (!viewModel.checkIsEntered(city)) {
                    Intent intent = new Intent();
                    intent.putExtra("city", city);
                    setResult(RESULT_OK, intent);
                    finish();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Already Added!!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        viewModel.dataFetched().observe((LifecycleOwner) context, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean fetch) {
                if (fetch) {
                    pbSearch.setVisibility(View.GONE);
                    cityListAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    void init(){
        context = this;
        pbSearch.setVisibility(View.GONE);
        viewModel = ViewModelProviders.of((FragmentActivity) context).get(CityViewModel.class);
        setup();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibtn_back:
                finish();
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @SuppressLint("LongLogTag")
    @Override
    public void onTextChanged(final CharSequence charSequence, int start, int before, int count) {
        Log.d(LOG_TAG, String.valueOf(charSequence));
        pbSearch.setVisibility(View.VISIBLE);
        viewModel.getData((String.valueOf(charSequence)));
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
