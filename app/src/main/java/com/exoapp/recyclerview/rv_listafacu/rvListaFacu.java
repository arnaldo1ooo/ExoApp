package com.exoapp.recyclerview.rv_listafacu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.exoapp.R;

import java.util.ArrayList;

public class rvListaFacu extends RecyclerView.Adapter implements View.OnClickListener {
    private Context context;
    private ArrayList<itemListaFacu> listItems; //Aca se cargaran los datos
    private View.OnClickListener listener;


    public rvListaFacu(Context context, ArrayList<itemListaFacu> listItems) {
        this.context = context;
        this.listItems = listItems;
    }

    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View contentView = LayoutInflater.from(context).inflate(R.layout.rv_listafacu, null, false);

        //OnClick
        contentView.setOnClickListener(this);

        return new Holder(contentView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        itemListaFacu item = listItems.get(position);
        Holder Holder = (Holder) holder;
        Holder.ivImagen.setImageResource(item.getImagen());
        Holder.tvFacultad.setText(item.getFacultad() + " (" + item.getSigla() + ")");
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }


    //Onclick
    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if (listener != null) {
            listener.onClick(view);
        }
    }

    public static class Holder extends RecyclerView.ViewHolder {
        ImageView ivImagen;
        TextView tvFacultad;


        public Holder(@NonNull View itemView) {
            super(itemView);
            ivImagen = itemView.findViewById(R.id.ivImagen);
            tvFacultad = itemView.findViewById(R.id.tvFacultad);
        }
    }
}
