package com.example.up_project_morozov;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login extends AppCompatActivity {

    EditText emailET, passwordET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emailET = findViewById(R.id.emailET);
        passwordET = findViewById(R.id.passwordET);

        SharedPreferences prefs = this.getSharedPreferences(
                "Date", Context.MODE_PRIVATE);

        if(prefs!=null)
        {
            emailET.setText(prefs.getString("Email", ""));
            passwordET.requestFocus();
        }
    }

    public void navigateToMain(View v)
    {
        if(emailET.getText().toString().equals("") || passwordET.getText().toString().equals(""))
        {
            Toast.makeText(Login.this, "Все поля должны быть заполнены!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            if(emailET.getText().toString().contains("@"))
            {
                String email = String.valueOf(emailET.getText());
                String password = String.valueOf(passwordET.getText());
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://mskko2021.mad.hakta.pro/api/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                com.example.up_project_morozov.Retrofit retrofitAPI = retrofit.create(com.example.up_project_morozov.Retrofit.class);

                UserCheck userCheck = new UserCheck(email, password);
                Call<UserTableModel> call = retrofitAPI.createUser(userCheck);
                call.enqueue(new Callback<UserTableModel>() {
                    @Override
                    public void onResponse(Call<UserTableModel> call, Response<UserTableModel> response) {
                        if (!response.isSuccessful()) {
                            Toast.makeText(Login.this, "Пользователь с указанными данными не найден. Просьба повторить попытку", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if(response.body() != null)
                        {
                            if(response.body().getToken() != null)
                            {
                                SharedPreferences prefs = getSharedPreferences( // Сохранение данных
                                        "Date", Context.MODE_PRIVATE);
                                prefs.edit().putString("Email", "" + email).apply();
                                prefs.edit().putString("Avatar", "" + response.body().getAvatar()).apply();
                                prefs.edit().putString("NickName", "" + response.body().getNickName()).apply();

                                OnBoarding.userImage = response.body().getAvatar();
                                OnBoarding.nickName = response.body().getNickName();
                                Intent intent = new Intent(Login.this, Main.class);
                                Bundle b = new Bundle();
                                intent.putExtras(b);
                                startActivity(intent);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<UserTableModel> call, Throwable t) {
                        Toast.makeText(Login.this, "Произошла ошибка: " + t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
            else
            {
                Toast.makeText(Login.this, "Поле \"Email\" обязательно должно содержать символ \'@\'", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void navigateToRegister (View view) { startActivity(new Intent(this, Register.class));}

    public void navigateToProfile (View view)
    {
        if(emailET.getText().toString().equals("") || passwordET.getText().toString().equals(""))
        {
            Toast.makeText(Login.this, "Все поля должны быть заполнены!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            if(emailET.getText().toString().contains("@"))
            {
                String email = String.valueOf(emailET.getText());
                String password = String.valueOf(passwordET.getText());
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://mskko2021.mad.hakta.pro/api/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                com.example.up_project_morozov.Retrofit retrofitAPI = retrofit.create(com.example.up_project_morozov.Retrofit.class);

                UserCheck userCheck = new UserCheck(email, password);
                Call<UserTableModel> call = retrofitAPI.createUser(userCheck);
                call.enqueue(new Callback<UserTableModel>() {
                    @Override
                    public void onResponse(Call<UserTableModel> call, Response<UserTableModel> response) {
                        if (!response.isSuccessful()) {
                            Toast.makeText(Login.this, "Пользователь с указанными данными не найден. Просьба повторить попытку", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if(response.body() != null)
                        {
                            if(response.body().getToken() != null)
                            {
                                SharedPreferences prefs = getSharedPreferences( // Сохранение данных
                                        "Date", Context.MODE_PRIVATE);
                                prefs.edit().putString("Email", "" + email).apply();
                                prefs.edit().putString("Avatar", "" + response.body().getAvatar()).apply();
                                prefs.edit().putString("NickName", "" + response.body().getNickName()).apply();

                                OnBoarding.userImage = response.body().getAvatar();
                                OnBoarding.nickName = response.body().getNickName();
                                Intent intent = new Intent(Login.this, Profile.class);
                                Bundle b = new Bundle();
                                intent.putExtras(b);
                                startActivity(intent);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<UserTableModel> call, Throwable t) {
                        Toast.makeText(Login.this, "Произошла ошибка: " + t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
            else
            {
                Toast.makeText(Login.this, "Поле \"Email\" обязательно должно содержать символ \'@\'", Toast.LENGTH_SHORT).show();
            }
        }
    }
}