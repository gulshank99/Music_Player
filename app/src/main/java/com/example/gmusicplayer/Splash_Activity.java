package com.example.gmusicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Splash_Activity extends AppCompatActivity {
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        imageView=findViewById(R.id.imageView);



        Thread thread = new Thread(){
            public void run(){
                 getSupportActionBar().hide();
                try{
                    sleep(2500);
                }
                catch(Exception e){
                   e.getCause();
                }
                finally{
                    Intent intent = new Intent(Splash_Activity.this,MainActivity.class);
                    startActivity(intent);
                     finish();
                }
            }
        };    thread.start();

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Splash_Activity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}