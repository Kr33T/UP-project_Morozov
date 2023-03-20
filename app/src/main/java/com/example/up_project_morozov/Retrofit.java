package com.example.up_project_morozov;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface Retrofit {
    @POST("user/login")
    Call<UserTableModel> createUser(@Body UserCheck userCheck);
}
