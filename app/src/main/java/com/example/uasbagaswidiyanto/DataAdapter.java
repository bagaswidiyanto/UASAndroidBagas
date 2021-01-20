package com.example.uasbagaswidiyanto;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONObject;

import java.util.List;

public class DataAdapter  extends RecyclerView.Adapter<DataAdapter.CardViewHolder> {
    private List<Drink> drinks;
    private Context context;

    public DataAdapter(List<Drink> movies,Context context){
        this.drinks = movies;
        this.context = context;
    }
    public List<Drink> getMovies(){
        return drinks;
    }
    @NonNull
    @Override
    public DataAdapter.CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        CardViewHolder viewHolder = new CardViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DataAdapter.CardViewHolder holder, int position) {
        final String nama,image;
        final int pos = position;
        nama  = drinks.get(position).getNama();
        image = drinks.get(position).getImage();
        final int id = drinks.get(position).getId();
        holder.tvNama.setText(nama);
        Glide.with(context).load(image).into(holder.imgImage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                alertDialog.setTitle("Drink");
                alertDialog.setMessage(nama);
                alertDialog.setPositiveButton("BATAL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                alertDialog.setNeutralButton("HAPUS", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.db.dataDao().delete(drinks.get(pos));
                        drinks.remove(pos); //hapus data
                        notifyItemRemoved(pos); //refresh data
                        notifyDataSetChanged();
                    }
                });


                AlertDialog dialog = alertDialog.create();
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return drinks.size();
    }

    public class CardViewHolder extends RecyclerView.ViewHolder {
        TextView tvNama;
        ImageView imgImage;
        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNama = (TextView) itemView.findViewById(R.id.tx_drink);
            imgImage = (ImageView) itemView.findViewById(R.id.im_drink);
        }
    }
}