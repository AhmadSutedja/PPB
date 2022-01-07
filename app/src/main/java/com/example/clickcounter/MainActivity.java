package com.example.clickcounter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<ListJari> list;
    RecyclerView RecView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecView = findViewById(R.id.RecListKiri);
        list = new ArrayList<>();
        addData();
        RecView.setAdapter(new AdapterJari(list));
        RecView.setLayoutManager(new LinearLayoutManager(this));


    }

    @Override
    protected void onResume() {
        super.onResume();
        //list.add(new ListJari("Jari Kiri",R.drawable.));
    }
    public void addData(){
        list.add(new ListJari("Pilih Jari Yang Akan Dihitung",R.drawable.nav));
        list.add(new ListJari("Jari Telunjuk Kiri",R.drawable.telunjuk_kiri));
        list.add(new ListJari("Jari Jempol Kiri",R.drawable.jempol_kiri));
        list.add(new ListJari("Jari Tengah Kiri",R.drawable.tengah_kiri));
        list.add(new ListJari("Jari Manis Kiri",R.drawable.manis_kiri));
        list.add(new ListJari("Jari Kelingking Kiri",R.drawable.kelingking_kiri));

        list.add(new ListJari("Jari Telunjuk Kanan",R.drawable.telunjuk_kanan));
        list.add(new ListJari("Jari Jempol Kanan",R.drawable.jempol_kanan));
        list.add(new ListJari("Jari Tengah Kanan",R.drawable.tengah_kanan));
        list.add(new ListJari("Jari Manis Kanan",R.drawable.manis_kanan));
        list.add(new ListJari("Jari Kelingking Kanan",R.drawable.kelingking_kanan));
    }
}