package com.sarohy.weatho.weatho.View.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.sarohy.weatho.weatho.Model.APIModel.City;
import com.sarohy.weatho.weatho.R;

import java.util.ArrayList;
import java.util.List;

public class CityRVAdapter extends RecyclerView.Adapter<CityRVAdapter.MyViewHolder> implements Filterable{

    private List<City> dataListAllItems;
    private List<City> dataListFilter;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView  name;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.tv_text);
        }
    }


    public CityRVAdapter(List<City> citiesList) {
        this.dataListAllItems = citiesList;
        this.dataListFilter = (citiesList);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_city_picker_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        City city = dataListFilter.get(position);
        holder.name.setText(city.getDataToDisplay());
    }

    @Override
    public int getItemCount() {
        return dataListFilter.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    dataListFilter = dataListFilter;
                } else {
                    List<City> filteredList = new ArrayList<>();
                    for (City row : dataListAllItems) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getLocalizedName().toLowerCase().startsWith(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    dataListFilter = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = dataListFilter;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                dataListFilter = (ArrayList<City>) filterResults.values;

                // refresh the list with filtered data
                notifyDataSetChanged();
            }
        };
    }

    public List<City> getList(){
        return dataListFilter;
    }

}
