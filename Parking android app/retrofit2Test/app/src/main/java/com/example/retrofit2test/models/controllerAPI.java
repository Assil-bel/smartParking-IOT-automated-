package com.example.retrofit2test.models;

import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface controllerAPI {

    @GET("/")
    Call<List<spotModel>> getSPOTs() ;

    @POST("/json-example")
    Call<JsonObject> postSPOT(@Body JsonObject jsonObject);

}
