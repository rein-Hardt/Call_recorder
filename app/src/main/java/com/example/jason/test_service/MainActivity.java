package com.example.jason.test_service;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button btn_start,btn_stop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_start = (Button) findViewById(R.id.button);
        btn_stop = (Button) findViewById(R.id.button2);
    }
    public void startNewService(View view){
        startService(new Intent(this,PhoneService.class));
    }
    public void stopNewService(View view){
        stopService(new Intent(this,PhoneService.class));
    }



}
