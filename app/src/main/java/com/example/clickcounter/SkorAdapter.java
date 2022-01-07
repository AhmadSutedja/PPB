package com.example.clickcounter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class SkorAdapter extends RecyclerView.Adapter<SkorAdapter.ViewHolder> {
    private ArrayList<listDB> listt;
    FirebaseDatabase database;
    DatabaseReference reference;
    public SkorAdapter(ArrayList<listDB> listt) {
        this.listt = listt;
    }

    @NonNull
    @Override
    public SkorAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        ViewHolder holder = new ViewHolder(inflater.inflate(R.layout.adapter_skor,parent,false));

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull SkorAdapter.ViewHolder holder, int position) {
        listDB listdbb = listt.get(position);

        long timeInMiliS = listdbb.getWaktuTotal();
        int minute = (int) timeInMiliS/60000;
        int second = (int) timeInMiliS%60000/1000;
        String teks;
        teks = ""+minute+":";
        if(second < 10)teks+=" 0";
        teks+=second;

        holder.waktu.setText("0"+teks);
        holder.nama.setText(listdbb.getNamajari());
        holder.jumlahklik.setText(Integer.toString(listdbb.getJumlahklik()));
        holder.gambar.setImageResource(listdbb.getGambarjari());
        holder.layoutAdapter.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(view.getContext());
                dialog.setMessage("Apakah Anda Yakin Ingin Menghapus Data "+listdbb.getNamajari()+" Ini ?");
                dialog.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listt.clear();
                        reference.child("jari").child(listdbb.getKey()).removeValue();

                    }
                });
                dialog.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });dialog.show();
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return listt.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView waktu,nama,jumlahklik;
        ImageView gambar;
        CardView layoutAdapter;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            database = FirebaseDatabase.getInstance();
            reference = database.getReference();
            waktu = itemView.findViewById(R.id.waktuSkor);
            nama = itemView.findViewById(R.id.namaSkor);
            jumlahklik = itemView.findViewById(R.id.jumlahKlik);
            gambar= itemView.findViewById(R.id.gambarSkor);
            layoutAdapter = itemView.findViewById(R.id.skorAdapter);
        }
    }

}
