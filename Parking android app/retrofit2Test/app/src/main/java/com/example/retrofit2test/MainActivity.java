package com.example.retrofit2test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.retrofit2test.models.controllerAPI;
import com.example.retrofit2test.models.spotModel;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    Button btn,btn2;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btn = findViewById(R.id.button);
        btn2 = findViewById(R.id.button2);
        textView = findViewById(R.id.text);




        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://192.168.43.88:8000/").addConverterFactory(GsonConverterFactory.create()).build();
        controllerAPI controllerAPI = retrofit.create(com.example.retrofit2test.models.controllerAPI.class);

//        btn2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                JsonObject jsonObject = new JsonObject();
//                jsonObject.addProperty("spot","3");.
//                jsonObject.addProperty("status","3");
//
//
//                Call<JsonObject> call = controllerAPI.postSPOT(jsonObject);
//                call.enqueue(new Callback<JsonObject>() {
//                    @Override
//                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
//                        if(!response.isSuccessful()){
//                            textView.setText(response.message());
//                            return;
//                        }
//                   textView.setText(response.toString());
//                    }
//
//                    @Override
//                    public void onFailure(Call<JsonObject> call, Throwable t) {
//                        textView.setText(t.toString());
//
//                    }
//                });
//            }
//        });


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Call<List<spotModel>> call = controllerAPI.getSPOTs();
                call.enqueue(new Callback<List<spotModel>>() {
                    @Override
                    public void onResponse(Call<List<spotModel>> call, Response<List<spotModel>> response) {

                        if(!response.isSuccessful()){
                            textView.setText(response.message());
                            return;
                        }
                        List<spotModel> SPOTS = response.body();
                        for (spotModel SPOT:SPOTS)
                        {
                            String content = "";
                            content+="spot "+ SPOT.getSpot()+"\n";
                            content+="intruction "+ SPOT.getInstruct()+"\n";
                            content+="status "+ SPOT.getState()+"\n\n";

                            textView.append(content);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<spotModel>> call, Throwable t) {

                        textView.setText(t.toString());
                    }
                });
            }
        });


    }
}