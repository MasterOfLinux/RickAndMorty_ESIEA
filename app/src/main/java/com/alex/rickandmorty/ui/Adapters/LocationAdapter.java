package com.alex.rickandmorty.ui.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alex.rickandmorty.R;
import com.alex.rickandmorty.ui.Models.LocationModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class LocationAdapter  extends RecyclerView.Adapter<LocationAdapter.viewHolder> {

    private List<LocationModel> modelList;
    private Context context;

    public LocationAdapter(List<LocationModel> modelList, Context context) {
        this.modelList = modelList;
        this.context = context;
    }

    @NonNull
    @Override
    public LocationAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int pos) {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.location_row, viewGroup, false);
        context  = viewGroup.getContext();
        return new LocationAdapter.viewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull LocationAdapter.viewHolder holder, int position) {
        LocationModel model = modelList.get(position);

        holder.tvName.setText(model.getName());
        holder.tvType.setText(model.getType());
        holder.tvDim.setText(model.getDimension());

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }



    public class viewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvType,tvDim;



        public viewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.loc_name);
            tvType = itemView.findViewById(R.id.loc_type);
            tvDim = itemView.findViewById(R.id.loc_dim);

        }
    }

}

