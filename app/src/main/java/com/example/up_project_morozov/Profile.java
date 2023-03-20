package com.example.up_project_morozov;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class Profile extends AppCompatActivity {

    ImageView userPhoto;
    TextView userName;

    private AdapterUserPhotos pAdapter;
    private List<UserPhotoModel> list = new ArrayList<>();

    public static UserPhotoModel photoModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        userPhoto = findViewById(R.id.userPhotoIV);
        new AdapterQuotes.DownloadImageTask((ImageView) userPhoto).execute(OnBoarding.userImage);

        userName = findViewById(R.id.userNameTV);
        userName.setText(OnBoarding.nickName);

        GridView photoGV = findViewById(R.id.photoGV);
        pAdapter = new AdapterUserPhotos(this, list);
        photoGV.setAdapter(pAdapter);

        photoGV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                photoModel = list.get(i);
                startActivity(new Intent(Profile.this, Photo.class));
            }
        });
    }

    public void navigateToLogin(View v) { startActivity(new Intent(this, Login.class)); }

    public void navigateToMain (View v) { startActivity(new Intent(this, Main.class)); }

    public void navigateToListen (View v) { startActivity(new Intent(this, Listen.class)); }

    public void navigateToMenu (View v) { startActivity(new Intent(this, Menu.class)); }
}