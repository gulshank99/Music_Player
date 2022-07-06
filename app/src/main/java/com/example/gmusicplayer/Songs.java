package com.example.gmusicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class Songs extends AppCompatActivity {
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
        mediaPlayer.release();
        updateSeek.interrupt();
    }

    TextView textView;
       ImageView play ,next,previous;
       ArrayList<File> songs;
       MediaPlayer mediaPlayer;
       String textContent;
       int position;
       SeekBar seekBar;
       Thread updateSeek;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songs);

        textView=findViewById(R.id.textView);
        play=findViewById(R.id.play);
       // pause=findViewById(R.id.pause);
        next=findViewById(R.id.next);
        previous=findViewById(R.id.previous);
        seekBar=findViewById(R.id.seekBar);

        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();

        songs =(ArrayList) bundle.getParcelableArrayList("list");
        textContent =intent.getStringExtra("currentSong");
        textView.setText(textContent);

        // to play song
       position = intent.getIntExtra("position",0);
       Uri uri = Uri.parse(songs.get(position).toString() );
       mediaPlayer = MediaPlayer.create(this,uri);

       mediaPlayer.start();


       // Seek bar
        seekBar.setMax(mediaPlayer.getDuration());

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });

        updateSeek=new Thread(){
            @Override
            public void run() {
                //super.run();
                int currentPosition =0;
                try{
                    while (currentPosition<mediaPlayer.getDuration()) {
                        currentPosition = mediaPlayer.getCurrentPosition();
                        seekBar.setProgress(currentPosition);
                        sleep(800);
                    }
                }
                 catch (Exception e){
                    e.printStackTrace();
                 }
            }
        };
           updateSeek.start();

           play.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   if(mediaPlayer.isPlaying()) {
                       play.setImageResource(R.drawable.play);
                       mediaPlayer.pause();
                   }
                    else{
                        play.setImageResource(R.drawable.pause);
                        mediaPlayer.start();
                   }

               }
           });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                mediaPlayer.release();
                if(position!=0){
                    position=position-1;
                }
                 else{
                     position= songs.size()-1;
                }
                Uri uri = Uri.parse(songs.get(position).toString() );
                mediaPlayer = MediaPlayer.create(getApplicationContext(),uri);

                mediaPlayer.start();
                play.setImageResource(R.drawable.pause);


                // Seek bar
                seekBar.setMax(mediaPlayer.getDuration());
                textContent=songs.get(position).getName().toString();
                textView.setText(textContent);

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                mediaPlayer.release();
                if(position!=songs.size()-1){
                    position=position+1;
                }
                else{
                    position= 0;
                }
                Uri uri = Uri.parse(songs.get(position).toString() );
                mediaPlayer = MediaPlayer.create(getApplicationContext(),uri);

                mediaPlayer.start();

                play.setImageResource(R.drawable.pause);
                // Seek bar
                seekBar.setMax(mediaPlayer.getDuration());

                textContent=songs.get(position).getName().toString();
                textView.setText(textContent);

            }
        });

    }
}