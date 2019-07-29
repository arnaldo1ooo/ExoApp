package com.exoapp.recyclerview.correlatividad;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.exoapp.R;

import java.util.ArrayList;

public class rv_correlatividad extends RecyclerView.Adapter implements View.OnClickListener{
   private Context context;
   private ArrayList<item_correlatividad> listItems; //Aca se cargaran los datos
    private View.OnClickListener listener;


    public rv_correlatividad(Context context, ArrayList<item_correlatividad> listItems){
        this.context = context;
        this.listItems = listItems;
    }

    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View contentView = LayoutInflater.from(context).inflate(R.layout.rv_correlatividad,null,false);

        //OnClick
        contentView.setOnClickListener(this);


        System.out.println("Se crea la vista CREATE VIEW HOLDER: " + viewType);
        return new Holder(contentView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        item_correlatividad item = listItems.get(position);
        Holder Holder = (Holder) holder;
        Holder.cbMateria.setText(item.getcbMateria());
        Holder.tvCorrelatividad.setText(item.gettvCorrelatividad());

        System.out.println("Se muestra el item   BING VIEW HODLER: " + position);
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }


    //Onclick
    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }
    @Override
    public void onClick(View view) {
        if(listener != null){
            listener.onClick(view);
        }
    }

    public  static  class  Holder extends RecyclerView.ViewHolder{
        CheckBox cbMateria;
        TextView tvCorrelatividad;

        public Holder(@NonNull View itemView) {
            super(itemView);
            cbMateria = itemView.findViewById(R.id.cbMateria);
            tvCorrelatividad = itemView.findViewById(R.id.tvCorrelatividad);
        }
    }
}
