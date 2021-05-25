package com.alex.rickandmorty.ui.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.hassan3217.alexgolf.rickandmorty.CharacterDetail;
import com.hassan3217.alexgolf.rickandmorty.R;
import com.hassan3217.alexgolf.rickandmorty.ui.Models.CharacterModel;
import com.sackcentury.shinebuttonlib.ShineButton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.viewHolder> {
    private List<CharacterModel> modelList;
    private Context context;

    public CharacterAdapter(List<CharacterModel> modelList, Context context) {
        this.modelList = modelList;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int pos) {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.character_card, viewGroup, false);
        context  = viewGroup.getContext();
        return new viewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        CharacterModel model = modelList.get(position);

        holder.tvName.setText(model.getName());
        holder.tvStatus.setText(model.getStatus());
        holder.tvSpecie.setText(model.getSpecie());
        holder.tvId.setText(model.getId());

        Picasso.get().load(model.getImage()).into(holder.char_img);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toasty.success(context,  "\n" + model.getCurUrl() + "\n" + model.getI(), Toast.LENGTH_SHORT, true).show();
                Intent i=  new Intent(context, CharacterDetail.class);
                i.putExtra("curUrlKey",model.getCurUrl());
                i.putExtra("curIdKey",model.getI());
                context.startActivity(i);

                Activity activity = (Activity) context;
                activity.overridePendingTransition(R.anim.fadein,R.anim.fadeout);

            }
        });



    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public void filterList(ArrayList<CharacterModel> filteredList) {
        modelList = filteredList;
        notifyDataSetChanged();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvStatus, tvSpecie,tvId;
        CardView cardView;
        ImageView char_img;
        ShineButton shineButton;


        public viewHolder(@NonNull View itemView) {
            super(itemView);
            //  shineButton = itemView.findViewById(R.id.po_image2);
            tvName = itemView.findViewById(R.id.char_name);
            tvStatus = itemView.findViewById(R.id.char_status);
            tvSpecie = itemView.findViewById(R.id.char_specie);
            tvId = itemView.findViewById(R.id.char_id);
            char_img = itemView.findViewById(R.id.char_img);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }

}

