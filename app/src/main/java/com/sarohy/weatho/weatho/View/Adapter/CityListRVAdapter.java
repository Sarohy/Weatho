package com.sarohy.weatho.weatho.View.Adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sarohy.weatho.weatho.Model.DBModel.Location;
import com.sarohy.weatho.weatho.R;
import com.sarohy.weatho.weatho.Utils;
import com.sarohy.weatho.weatho.WeathoApplication;

import java.util.List;

public class CityListRVAdapter extends RecyclerView.Adapter<CityListRVAdapter.MyViewHolder>
{

    private final List<Location> dataListAllItems;
    private final Activity context;

    public void updateList(List<Location> fetch) {
        dataListAllItems.clear();
        dataListAllItems.addAll(fetch);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        final TextView  locationName;
        final TextView temperature;
        final ImageButton homeBtn;
        final RelativeLayout viewBackground;
        public final RelativeLayout viewForeground;
        final TextView countryName;
        final ImageView flagView;
        MyViewHolder(View view) {
            super(view);
            locationName = view.findViewById(R.id.tv_name);
            temperature = view.findViewById(R.id.tv_current_date);
            viewBackground = view.findViewById(R.id.view_background);
            viewForeground = view.findViewById(R.id.view_foreground);
            homeBtn = view.findViewById(R.id.ib_is_home);
            countryName = view.findViewById(R.id.tv_country_name);
            flagView = view.findViewById(R.id.iv_flag);
        }
    }


    public CityListRVAdapter(List<Location> citiesList,Activity context) {
        this.context = context;
        this.dataListAllItems = citiesList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cities_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Location city = dataListAllItems.get(position);
        holder.locationName.setText(city.getLocalizedName());
        holder.countryName.setText(city.getCountry());
        WeathoApplication.component.getGlide().load(Utils.getFlagURL(city.getCountryCode())).into(holder.flagView);
        String homeKey = WeathoApplication.component.getSharedPrefs().getCityKey();
        if (city.getKey().equals(homeKey)){
            holder.homeBtn.setImageResource(R.drawable.ic_home_white_selected_24dp);
        }
        else{
            holder.homeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                WeathoApplication.component.getSharedPrefs().setCityKey(city.getKey());
                    WeathoApplication.component.getSharedPrefs().setCityName(city.getLocalizedName());
                context.finish();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return dataListAllItems.size();
    }

}
