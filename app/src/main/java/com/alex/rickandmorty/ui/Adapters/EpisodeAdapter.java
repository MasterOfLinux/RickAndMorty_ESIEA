package com.alex.rickandmorty.ui.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alex.rickandmorty.R;
import com.alex.rickandmorty.ui.Models.EpisodeModel;


import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class EpisodeAdapter extends RecyclerView.Adapter<EpisodeAdapter.viewHolder> {

    private List<EpisodeModel> modelList;
    private Context context;

    public EpisodeAdapter(List<EpisodeModel> modelList, Context context) {
        this.modelList = modelList;
        this.context = context;
    }

    @NonNull
    @Override
    public EpisodeAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int pos) {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.episode_row, viewGroup, false);
        context  = viewGroup.getContext();
        return new EpisodeAdapter.viewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull EpisodeAdapter.viewHolder holder, int position) {
        EpisodeModel model = modelList.get(position);

        holder.tvName.setText(model.getName());
        holder.tvAirDate.setText(model.getAirDate());
        holder.tvEpisode.setText(model.getEpisdoe());

}

    @Override
    public int getItemCount() {
        return modelList.size();
    }



    public class viewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvAirDate,tvEpisode;



        public viewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.ep_name);
            tvAirDate = itemView.findViewById(R.id.ep_date);
            tvEpisode = itemView.findViewById(R.id.ep_episode);

        }
    }

}


