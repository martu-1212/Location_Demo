package com.example.martinalocationdemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.martinalocationdemo.R;
import com.example.martinalocationdemo.database.model.Locations;

import java.util.List;

public class LocationListAdapter extends RecyclerView.Adapter<LocationListAdapter.LocationsViewHolder> {

private Context mCtx;
private List<Locations> locationsList;

public LocationListAdapter(Context mCtx, List<Locations> locationsList) {
        this.mCtx = mCtx;
        this.locationsList = locationsList;
        }

@Override
public LocationsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.locations_list, parent, false);
        return new LocationsViewHolder(view);
        }

@Override
public void onBindViewHolder(LocationsViewHolder holder, int position) {
    holder.tvName.setText(locationsList.get(position).getName());

}

@Override
public int getItemCount() {
        return locationsList.size();
        }

class LocationsViewHolder extends RecyclerView.ViewHolder{

    AppCompatTextView tvName;

    public LocationsViewHolder(View itemView) {
        super(itemView);
        tvName = itemView.findViewById(R.id.tvName);
    }


}
}

