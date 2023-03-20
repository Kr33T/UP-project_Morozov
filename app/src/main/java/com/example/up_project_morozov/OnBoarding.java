package com.example.up_project_morozov;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

public class OnBoarding extends AppCompatActivity {

    public static String userImage;
    public static String nickName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        SharedPreferences prefs = this.getSharedPreferences("Date", Context.MODE_PRIVATE);

        if(prefs != null)
        {
            if(!prefs.getString("NickName", "").equals(""))
            {
                userImage = prefs.getString("Avatar", "");
                nickName = prefs.getString("NickName", "");
                startActivity(new Intent(this, Main.class));
            }
        }
    }

    public void navigateToRegistration(View v)
    {
        startActivity(new Intent(this, Register.class));
    }

    public void navigateToLogin(View v)
    {
        startActivity(new Intent(this, Login.class));
    }

}