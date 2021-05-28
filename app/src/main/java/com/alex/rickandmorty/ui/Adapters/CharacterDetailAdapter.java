package com.alex.rickandmorty.ui.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alex.rickandmorty.CharacterDetail;
import com.alex.rickandmorty.R;
import com.alex.rickandmorty.ui.Models.CharachterDeatilModel;
import com.alex.rickandmorty.ui.Models.CharacterModel;
import com.sackcentury.shinebuttonlib.ShineButton;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class CharacterDetailAdapter extends  RecyclerView.Adapter<CharacterDetailAdapter.viewHolder> {

    private List<CharachterDeatilModel> modelList;
    private Context context;

    public CharacterDetailAdapter(List<CharachterDeatilModel> modelList, Context context) {
        this.modelList = modelList;
        this.context = context;
    }

    @NonNull
    @Override
    public CharacterDetailAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int pos) {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.chr_d_card, viewGroup, false);
        context = viewGroup.getContext();
        return new CharacterDetailAdapter.viewHolder(view);

    }




    @Override
    public void onBindViewHolder(@NonNull CharacterDetailAdapter.viewHolder holder, int position) {
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