package com.sarohy.weatho.weatho.View.Adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sarohy.weatho.weatho.Model.DBModel.Location;
import com.sarohy.weatho.weatho.R;
import com.sarohy.weatho.weatho.SharedPreferencesClass;
import com.sarohy.weatho.weatho.Utils;
import com.sarohy.weatho.weatho.WeathoAppliccation;

import java.util.List;

public class CityListRVAdapter extends RecyclerView.Adapter<CityListRVAdapter.MyViewHolder>
{

    private List<Location> dataListAllItems;
    private Activity context;

    public void updateList(List<Location> fetch) {
        dataListAllItems.clear();
        dataListAllItems.addAll(fetch);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView  locationName, temperature;
        ImageButton homeBtn;
        public RelativeLayout viewBackground, viewForeground;
        MyViewHolder(View view) {
            super(view);
            locationName = (TextView) view.findViewById(R.id.tv_name);
            temperature = (TextView) view.findViewById(R.id.tv_current_date);
            viewBackground = view.findViewById(R.id.view_background);
            viewForeground = view.findViewById(R.id.view_foreground);
            homeBtn = view.findViewById(R.id.ib_is_home);
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
        holder.locationName.setText(city.getDataToDisplay());
        String homeKey = WeathoAppliccation.component.getSharedPrefs().getCityKey();
        if (city.getKey().equals(homeKey)){
            holder.homeBtn.setImageResource(R.drawable.ic_home_white_selected_24dp);
        }
        else{
            holder.homeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                WeathoAppliccation.component.getSharedPrefs().setCityKey(city.getKey());
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
