package com.example.clickcounter;

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

import java.util.ArrayList;

public class AdapterJari extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    int view1=0;
    int view2=1;
    int view3=2;
    ArrayList<ListJari> list;
    public AdapterJari(ArrayList<ListJari> list) {
        this.list = list;
    }

    @Override
    public int getItemViewType(int position) {
        if (list.indexOf(list.get(position))==0){
            return  view1;
        }else if (list.indexOf(list.get(position))>0 && list.indexOf(list.get(position))<6) {
            return view2;
        }else{
            return view3;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==view1){
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            return new ViewHolder3(inflater.inflate(R.layout.adapter_jari3,parent,false));
        }
        else if (viewType==view2){
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            return new ViewHolder(inflater.inflate(R.layout.adapter_jari,parent,false));
        }else {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            ViewHolder2 holder = new ViewHolder2(inflater.inflate(R.layout.adapter_jari2,parent,false));
            return holder;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(list.indexOf(list.get(position))==0){
            ViewHolder3 viewHolder3 = (ViewHolder3) holder;
            viewHolder3.bind(position);
        }
        else if(list.indexOf(list.get(position))>0 && list.indexOf(list.get(position))<6){
            ((ViewHolder)holder).bind(position);
        }else {
            ((ViewHolder2)holder).bind(position);
        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public class ViewHolder3 extends RecyclerView.ViewHolder{
        TextView TulisanNav;
        ImageView gambarNav;
        CardView layoutAdapter;
        public ViewHolder3(@NonNull View itemView) {
            super(itemView);
            TulisanNav = itemView.findViewById(R.id.counterJari);
            gambarNav = itemView.findViewById(R.id.gambarCounterJari);
            layoutAdapter = itemView.findViewById(R.id.layoutAdapter3);
        }
        void bind(int position){
            ListJari listJari = list.get(position);
            TulisanNav.setText(listJari.getJari());
            gambarNav.setImageResource(listJari.getGambar());
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView namaJari;
        ImageView gambarJari;
        CardView layoutAdapter;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            namaJari = itemView.findViewById(R.id.namaJari);
            gambarJari = itemView.findViewById(R.id.gambarJari);
            layoutAdapter = itemView.findViewById(R.id.layoutAdapter);

        }
        void bind(int position){
            ListJari listJari = list.get(position);
            namaJari.setText(listJari.getJari());
            gambarJari.setImageResource(listJari.getGambar());

            layoutAdapter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent next = new Intent(view.getContext(),ClickCounter.class);
                    next.putExtra("namaJari",listJari.getJari());
                    next.putExtra("gambarJari",listJari.getGambar());
                    next.putExtra("position",position);
                    view.getContext().startActivity(next);

                }
            });
        }
    }
    public class ViewHolder2 extends RecyclerView.ViewHolder{
        TextView namaJari;
        ImageView gambarJari;
        CardView layoutAdapter;
        public ViewHolder2(@NonNull View itemView) {
            super(itemView);
            namaJari = itemView.findViewById(R.id.namaJari2);
            gambarJari = itemView.findViewById(R.id.gambarJari2);
            layoutAdapter = itemView.findViewById(R.id.layoutAdapter2);
        }
        void bind(int position){
            ListJari listJari = list.get(position);
            namaJari.setText(listJari.getJari());
            gambarJari.setImageResource(listJari.getGambar());

            layoutAdapter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent next = new Intent(view.getContext(),ClickCounter.class);
                    next.putExtra("namaJari",listJari.getJari());
                    next.putExtra("gambarJari",listJari.getGambar());
                    next.putExtra("position",position);
                    view.getContext().startActivity(next);
                }
            });
        }
    }
}
