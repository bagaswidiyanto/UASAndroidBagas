package com.example.uasbagaswidiyanto;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.CardViewHolder> {
    private List<Drink> drinks;
    private Context context;

    public DataAdapter(List<Drink> drinks, Context context){
        this.drinks = drinks;
        this.context = context;
    }

    public List<Drink> getCocktails(){
        return drinks;
    }
    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        CardViewHolder viewHolder = new CardViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        final String nama,image;
        final int pos = position;
        nama  = drinks.get(position).getNama();
        image = drinks.get(position).getImage();
        final int id = drinks.get(position).getId();
        holder.tvNama.setText(nama);
        Glide.with(context).load(image).into(holder.imgImage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                alertDialog.setTitle("Drink");
                alertDialog.setMessage(nama);
                alertDialog.setPositiveButton("BATAL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {dialogInterface.cancel();}
                });
                alertDialog.setNeutralButton("HAPUS", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        MainActivity.db.dataDao().delete(drinks.get(pos));
                        drinks.remove(pos);
                        notifyItemRemoved(pos);
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
            tvNama = (TextView) itemView.findViewById(R.id.tx_drink );
            imgImage = (ImageView) itemView.findViewById(R.id.im_drink);
        }
    }
}
