package com.example.clickcounter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;


public class ClickCounter extends AppCompatActivity {

    NumberPicker NumPickerMinute,NumberPickerSecond;
    TextView Counter,TextMasukkan,judulMenit,judulDetik,judulJari,waktuhabis;
    Button button,pauseButton,restartButton,upload;
    LinearLayout container;
    ImageView judulgambar;
    ArrayList<Long> listwaktu,listJumlahKlik,waktuAwal;
    CountDownTimer countDownTimer;
    SharedPreferences preferences;
    Gson gson;
    FirebaseDatabase database;
    DatabaseReference reference;

    int gambarjari,position,jumlahklik;
    String namajari;
    boolean TimeIsRunning;
    long timeInMiliS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_click_counter);
        init();
        addList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(listwaktu.get(position)!=0) {
            timeInMiliS = listwaktu.get(position);
            container.setVisibility(View.INVISIBLE);
            TextMasukkan.setText("Waktu Tersisa :");
            Counter.setVisibility(View.VISIBLE);
            judulMenit.setVisibility(View.INVISIBLE);
            judulDetik.setVisibility(View.INVISIBLE);
            pauseButton.setVisibility(View.VISIBLE);
            restartButton.setVisibility(View.VISIBLE);
            button.setText(listJumlahKlik.get(position).toString());
            jumlahklik = (int) (long) listJumlahKlik.get(position);
            updateTime();
        }
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listwaktu.get(position)!=0) {
                        if (TimeIsRunning == true) {
                            jumlahklik++;
                            button.setText("" + jumlahklik);
                        } else {
                            container.setVisibility(View.INVISIBLE);
                            TextMasukkan.setText("Waktu Tersisa :");
                            Counter.setVisibility(View.VISIBLE);
                            judulMenit.setVisibility(View.INVISIBLE);
                            judulDetik.setVisibility(View.INVISIBLE);
                            pauseButton.setVisibility(View.VISIBLE);
                            restartButton.setVisibility(View.VISIBLE);

                            startTime();
                            jumlahklik++;
                            button.setText("" + jumlahklik);
                        }
                    }else{
                        Button(view);
                    }
                }
            });
            pauseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pauseButton(view);
                }
            });
            restartButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    resetButton(view);
                }
            });
    }

    public void Button(View view) {
        if(TimeIsRunning==false) {
            container.setVisibility(View.INVISIBLE);
            TextMasukkan.setText("Waktu Tersisa :");
            Counter.setVisibility(View.VISIBLE);
            judulMenit.setVisibility(View.INVISIBLE);
            judulDetik.setVisibility(View.INVISIBLE);
            pauseButton.setVisibility(View.VISIBLE);
            restartButton.setVisibility(View.VISIBLE);

            int Minute = NumPickerMinute.getValue();
            int Second = NumberPickerSecond.getValue();
            timeInMiliS = ((Minute * 60) + Second) * 1000;
            if(waktuAwal.get(position)==0) {
                String penampung = preferences.getString("waktu_awal", "[]");
                waktuAwal = gson.fromJson(penampung, new TypeToken<ArrayList<Long>>() {
                }.getType());
                waktuAwal.set(position, timeInMiliS);
                penampung = gson.toJson(waktuAwal);
                preferences.edit().putString("waktu_awal", penampung).apply();
            }
            startTime();
        }else {
            jumlahklik++;
            button.setText(""+jumlahklik);
        }
    }

    public void pauseButton(View view) {
        if(TimeIsRunning==false){
            Toast.makeText(getApplicationContext(),"Timer sudah berhenti",Toast.LENGTH_SHORT).show();
        }else{
            stopTime();
            String penampung = preferences.getString("key_jari", "[]");
            listwaktu = gson.fromJson(penampung, new TypeToken<ArrayList<Long>>() {
            }.getType());
            listwaktu.set(position, timeInMiliS);
            penampung = gson.toJson(listwaktu);
            preferences.edit().putString("key_jari", penampung).apply();

            penampung = preferences.getString("jumlah_klik", "[]");
            listJumlahKlik = gson.fromJson(penampung, new TypeToken<ArrayList<Long>>() {
            }.getType());
            listJumlahKlik.set(position, (long) jumlahklik);
            penampung = gson.toJson(listJumlahKlik);
            preferences.edit().putString("jumlah_klik", penampung).apply();
        }
    }



    public void resetButton(View view) {
        container.setVisibility(View.VISIBLE);
        TextMasukkan.setText("Masukkan Waktu :");
        Counter.setVisibility(View.INVISIBLE);
        judulMenit.setVisibility(View.VISIBLE);
        judulDetik.setVisibility(View.VISIBLE);
        pauseButton.setVisibility(View.INVISIBLE);
        restartButton.setVisibility(View.INVISIBLE);
        button.setText("Tekan Disini Untuk Memulai");

        if(TimeIsRunning==true){
            stopTime();
            setValuePref();
        }else{
            setValuePref();
        }
    }

    private void startTime() {
        countDownTimer = new CountDownTimer(timeInMiliS,1) {
            @Override
            public void onTick(long l) {
                timeInMiliS = l;
                updateTime();
            }

            @Override
            public void onFinish() {
                timeInMiliS = 0;
                Counter.setText("00:00:0");
                button.setVisibility(View.INVISIBLE);
                pauseButton.setVisibility(View.INVISIBLE);
                restartButton.setVisibility(View.INVISIBLE);
                waktuhabis.setVisibility(View.VISIBLE);
                upload.setVisibility(View.VISIBLE);
                upload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String key = reference.push().getKey();
                        listDB List = new listDB(waktuAwal.get(position),jumlahklik,gambarjari,namajari,key);
                        reference.child(key).setValue(List);

                        Intent intent = new Intent(ClickCounter.this,skorData.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        finish();
                        startActivity(intent);
                        setValuePref();
                    }
                });
            }
        }.start();
        TimeIsRunning = true;
    }

    private void stopTime() {
        countDownTimer.cancel();
        TimeIsRunning =false;
    }

    private void updateTime() {
        int minute = (int) timeInMiliS/60000;
        int second = (int) timeInMiliS%60000/1000;
        int milis = (int) timeInMiliS%1000;
        String teks;
        teks = ""+minute+":";
        if(second < 10)teks+=" 0";
            teks+=second;
        teks+=":"+milis;
        Counter.setText(teks);
    }

    private void setValuePref(){
        String penampung = preferences.getString("key_jari","[]");
        listwaktu=gson.fromJson(penampung,new TypeToken<ArrayList<Long>>(){}.getType());
        listwaktu.set(position,(long)0);
        penampung = gson.toJson(listwaktu);
        preferences.edit().putString("key_jari",penampung).apply();

        jumlahklik = 0;
        penampung = preferences.getString("jumlah_klik", "[]");
        listJumlahKlik = gson.fromJson(penampung, new TypeToken<ArrayList<Long>>() {
        }.getType());
        listJumlahKlik.set(position, (long) 0);
        penampung = gson.toJson(listJumlahKlik);
        preferences.edit().putString("jumlah_klik", penampung).apply();

        penampung = preferences.getString("waktu_awal", "[]");
        waktuAwal = gson.fromJson(penampung, new TypeToken<ArrayList<Long>>() {
        }.getType());
        waktuAwal.set(position,(long) 0);
        penampung = gson.toJson(waktuAwal);
        preferences.edit().putString("waktu_awal", penampung).apply();
    }

    public void addList(){

        String penampung = preferences.getString("key_jari","[]");
        listwaktu=gson.fromJson(penampung,new TypeToken<ArrayList<Long>>(){}.getType());
        if(listwaktu.isEmpty()){
        for (int i=0;i<10;i++) {
            listwaktu.add((long) 0);
        }}
        penampung = gson.toJson(listwaktu);
        preferences.edit().putString("key_jari",penampung).apply();

        penampung = preferences.getString("jumlah_klik","[]");
        listJumlahKlik=gson.fromJson(penampung,new TypeToken<ArrayList<Long>>(){}.getType());
        if(listJumlahKlik.isEmpty()){
            for (int i=0;i<10;i++) {
                listJumlahKlik.add((long) 0);
            }}
        penampung = gson.toJson(listJumlahKlik);
        preferences.edit().putString("jumlah_klik",penampung).apply();

        penampung = preferences.getString("waktu_awal","[]");
        waktuAwal = gson.fromJson(penampung,new TypeToken<ArrayList<Long>>(){}.getType());
        if(waktuAwal.isEmpty()){
        for (int i=0;i<10;i++) {
            waktuAwal.add((long) 0);
        }}
        penampung = gson.toJson(waktuAwal);
        preferences.edit().putString("waktu_awal",penampung).apply();
    }

    public  void init(){
        NumPickerMinute = findViewById(R.id.numberPicker);
        NumberPickerSecond = findViewById(R.id.numberPicker2);
        pauseButton = findViewById(R.id.pauseButton);
        restartButton = findViewById(R.id.resetButton);
        Counter = findViewById(R.id.counter);
        button = findViewById(R.id.startButton);
        judulDetik = findViewById(R.id.textDetik);
        judulMenit = findViewById(R.id.textMenit);
        container = findViewById(R.id.numberContainer);
        TextMasukkan = findViewById(R.id.textMasukkanWaktu);
        judulJari = findViewById(R.id.counterJari);
        judulgambar = findViewById(R.id.gambarCounterJari);
        upload = findViewById(R.id.uploadData);
        waktuhabis = findViewById(R.id.waktuHabis);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("jari");

        NumPickerMinute.setMinValue(0);
        NumPickerMinute.setMaxValue(9);
        NumberPickerSecond.setMinValue(0);
        NumberPickerSecond.setMaxValue(59);

        namajari = getIntent().getStringExtra("namaJari");
        gambarjari = getIntent().getIntExtra("gambarJari",0);
        position = getIntent().getIntExtra("position",1)-1;
        judulJari.setText(namajari);
        judulgambar.setImageResource(gambarjari);

        preferences = this.getSharedPreferences("shared_key", Context.MODE_PRIVATE);
        gson = new GsonBuilder().create();
        listwaktu = new ArrayList<>();
        listJumlahKlik = new ArrayList<>();
        waktuAwal = new ArrayList<>();
    }
}