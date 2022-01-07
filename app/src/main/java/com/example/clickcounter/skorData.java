package com.example.clickcounter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class skorData extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference reference;
    ArrayList<listDB> ListFire;
    RecyclerView layoutskor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skor_data);
        init();
        getdata();

    }
    private void init(){
        database = FirebaseDatabase.getInstance();
        layoutskor = findViewById(R.id.skorLayout);
        reference= database.getReference("jari");
        ListFire = new ArrayList<>();

    }
    public void getdata(){
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot Vsnapshot:snapshot.getChildren()){
                    listDB listdbb = Vsnapshot.getValue(listDB.class);
                    listdbb.setKey(Vsnapshot.getKey());
                    ListFire.add(listdbb);
                }
                layoutskor.setAdapter(new SkorAdapter(ListFire));
                layoutskor.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}