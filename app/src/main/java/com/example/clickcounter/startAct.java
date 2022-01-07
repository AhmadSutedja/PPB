package com.example.clickcounter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class startAct extends AppCompatActivity {

    Button next,daftarskor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        next = findViewById(R.id.nextPage);
        daftarskor = findViewById(R.id.skorData);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(startAct.this,MainActivity.class);
                startActivity(intent);
            }
        });
        daftarskor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(startAct.this,skorData.class);
                startActivity(intent);
            }
        });
    }
}