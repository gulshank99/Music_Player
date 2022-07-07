package com.example.gmusicplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterClass.Onclick {
    ArrayList<File> list;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);

        Dexter.withContext(this)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        Toast.makeText(MainActivity.this, "Please wait while we are fetching your songs ", Toast.LENGTH_LONG).show();
                        // create Array  and put all songs there
                        list = fetchSong(Environment.getExternalStorageDirectory());
                        String[] items = new String[list.size()];

                        for (int i = 0; i < list.size(); i++) {
                            items[i] = list.get(i).getName().replace(".mp3", "");
                        }

                        AdapterClass ad = new AdapterClass(items, MainActivity.this);

                        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                        recyclerView.setAdapter(ad);

                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();   // Ask again for permission
                    }
                })
                .check();
    }

    // Function to fetch mp3 FILES
    public ArrayList<File> fetchSong(File file) {
        ArrayList<File> list = new ArrayList<File>();
        File[] songs = file.listFiles();

        if (songs != null) {
            for (File myFile : songs) {
                if (!myFile.isHidden() && myFile.isDirectory()) {
                    list.addAll(fetchSong(myFile));
                } else {
                    if (myFile.getName().endsWith(".mp3") && !myFile.getName().startsWith(".")) {
                        list.add(myFile);
                    }
                }

            }
        }
        return list;
    }

    @Override
    public void onclick(int position) {
        Intent intent = new Intent(MainActivity.this, Songs.class);
        String currentSong = list.get(position).toString();
        intent.putExtra("list", list);
        intent.putExtra("currentSong", currentSong);
        intent.putExtra("position", position);

        startActivity(intent);
    }
}