package com.alex.rickandmorty.ui.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alex.rickandmorty.R;
import com.alex.rickandmorty.ui.Models.CharachterDeatilModel;

import java.util.List;

public class CharacterDetailAdapter extends  RecyclerView.Adapter<CharacterDetailAdapter.viewHolder> {

    private List<CharachterDeatilModel> modelList;
    private Context context;

    public CharacterDetailAdapter(List<CharachterDeatilModel> modelList, Context context) {
        this.modelList = modelList;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int pos) {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.chr_d_card, viewGroup, false);
        context = viewGroup.getContext();
        return new viewHolder(view);

    }




    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        CharachterDeatilModel model = modelList.get(position);

        holder.tvName.setText(model.getName());
        holder.tvEpisode.setText(model.getEpisode());


    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvEpisode;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            //  shineButton = itemView.findViewById(R.id.po_image2);
            tvName = itemView.findViewById(R.id.txtName);
            tvEpisode = itemView.findViewById(R.id.txtEpisode);

        }
    }

}