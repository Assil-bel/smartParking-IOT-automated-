package com.example.retrofit2test;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.retrofit2test.models.controllerAPI;
import com.example.retrofit2test.models.spotModel;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class parkingControl extends AppCompatActivity {

    Button s1, s2,s3,s4,s5,s6,s7,s8,s9,s10;
    String address = null;
    private ProgressDialog progress;
    BluetoothAdapter myBluetooth = null;
    BluetoothSocket btSocket = null;
    private boolean isBtConnected = false;
//    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    UUID myUUID;
    BluetoothDevice device;
    public List<spotModel> SPOTS;
    Button getData;

//    /////MAKE HTTP GET
//    Retrofit retrofit = new Retrofit.Builder().baseUrl("http://192.168.43.88:8000").addConverterFactory(GsonConverterFactory.create()).build();
//    controllerAPI controllerAPI = retrofit.create(com.example.retrofit2test.models.controllerAPI.class);
//
//    Call<List<spotModel>> call = controllerAPI.getSPOTs();


//    ////BLUETOOTH CONNECTION
//    private class ConnectBT extends AsyncTask<Void, Void, Void>  // UI thread
//    {
//        private boolean ConnectSuccess = true; //if it's here, it's almost connected
//
//        @Override
//        protected void onPreExecute()
//        {
//            progress = ProgressDialog.show(parkingControl.this, "Connecting...", "Please wait!!!");  //show a progress dialog
//        }
//
//        @Override
//        protected Void doInBackground(Void... devices) //while the progress dialog is shown, the connection is done in background
//        {
//            try
//            {
//                if (btSocket == null || !isBtConnected)
//                {
//                    myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
//                    BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(address);//connects to the device's address and checks if it's available
//                    btSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID);//create a RFCOMM (SPP) connection
//                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
//                    btSocket.connect();//start connection
//                }
//            }
//            catch (IOException e)
//            {
//                ConnectSuccess = false;//if the try failed, you can check the exception here
//            }
//            return null;
//        }
//        @Override
//        protected void onPostExecute(Void result) //after the doInBackground, it checks if everything went fine
//        {
//            super.onPostExecute(result);
//
//            if (!ConnectSuccess)
//            {
//                msg("Connection Failed. Is it a SPP Bluetooth? Try again.");
//                finish();
//            }
//            else
//            {
//                msg("Connected.");
//                isBtConnected = true;
//            }
//            progress.dismiss();
//        }
//    }



    //----------------------HANDLER-------------------------




    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_control);



        //// GET THE DEVISE IP
        Intent newint = getIntent();
        Bundle b = newint.getExtras();
//        try {
            String address =(String) b.get("address");
//        }catch (Exception i){
//
//        }


//        if (address != null) {
            boolean ConnectSuccess = true; //if it's here, it's almost connected
            progress = ProgressDialog.show(parkingControl.this, "Connecting...", "Please wait!!!");  //show a progress dialog
            label: if (btSocket == null || !isBtConnected) {
                try {


                    if (address == "skipped"){
                        ConnectSuccess = false;//if the try failed, you can check the exception here
                        break label;}
                    else{
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                    Log.v("tessst:  ", "" + address);
                    BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(address);//connects to the device's address and checks if it's available
                    ParcelUuid list[] = dispositivo.getUuids();
                    String id = list[0].toString();
                    Log.v("uuid :  ", id);
                    myUUID = UUID.fromString(list[0].toString());
                    btSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID);//create a RFCOMM (SPP) connection
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    btSocket.connect();//start connection
                    }

                } catch (IOException i) {
                    Log.v("mohamed:  ", "hiiiiiii" + i.getMessage());

                    ConnectSuccess = false;//if the try failed, you can check the exception here
                }

            if (!ConnectSuccess) {
                msg("Connection Failed. Is it a SPP Bluetooth? Try again.");
                finish();
                Log.v("failed:  ", "hiiiiiii");

            } else {
                msg("Connected.");
                isBtConnected = true;
                Log.v("connected:  ", "hiiiiiii");
                progress.cancel();

            }
        }



        s1 = findViewById(R.id.spot1);
        s2 = findViewById(R.id.spot2);
        s3 = findViewById(R.id.spot3);
        s4 = findViewById(R.id.spot4);
        s5 = findViewById(R.id.spot5);
        s6 = findViewById(R.id.spot6);
        s7 = findViewById(R.id.spot7);
        s8 = findViewById(R.id.spot8);


        getData = findViewById(R.id.getData);

        /////MAKE HTTP GET
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://192.168.43.88:8000").addConverterFactory(GsonConverterFactory.create()).build();
        controllerAPI controllerAPI = retrofit.create(com.example.retrofit2test.models.controllerAPI.class);


        Call<List<spotModel>> call = controllerAPI.getSPOTs();

        Log.v("reponse1 :"," error");

        getData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                call.clone().enqueue(new Callback<List<spotModel>>() {
                    @Override
                    public void onResponse(Call<List<spotModel>> call, Response<List<spotModel>> response) {

                        if(!response.isSuccessful()){
                            Log.v("reponse1 :"," error "+response.message());
                            msg("...Connect to your database...");
                            return;
                        }
                        SPOTS = response.body();
                        Log.v("spots:  ",SPOTS.toString());
                        for (spotModel SPOT:SPOTS)
                        {
                            String content = "";
                            content+="spot "+ SPOT.getSpot()+"\n";
                            content+="instruction "+ SPOT.getInstruct()+"\n";
                            content+="status "+ SPOT.getState()+"\n\n";
                            Log.v("sppot:  ",content);
                        }

                        if(SPOTS.get(0).getState().equals("1")){
                            s1.setBackgroundColor(Color.RED);
                            s1.setEnabled(true);
                        }
                        else {s1.setBackgroundColor(Color.GREEN);
                            s1.setEnabled(true);}
                        if(SPOTS.get(1).getState().equals("1")){
                            s2.setBackgroundColor(Color.RED);
                            s2.setEnabled(true);
                        }  else {s2.setBackgroundColor(Color.GREEN);
                            s2.setEnabled(true);}
                        if(SPOTS.get(2).getState().equals("1")){
                            s3.setBackgroundColor(Color.RED);
                            s3.setEnabled(true);
                        }else {s3.setBackgroundColor(Color.GREEN);
                            s3.setEnabled(true);}
                        if(SPOTS.get(3).getState().equals("1")){
                            s4.setBackgroundColor(Color.RED);
                            s4.setEnabled(true);
                        }else {s4.setBackgroundColor(Color.GREEN);
                            s4.setEnabled(true);}
                        if(SPOTS.get(4).getState().equals("1")){
                            s5.setBackgroundColor(Color.RED);
                            s5.setEnabled(true);
                        }else {s5.setBackgroundColor(Color.GREEN);
                            s5.setEnabled(true);
                        }
                        s5.setEnabled(true);
                        if(SPOTS.get(5).getState().equals("1")){
                            s6.setBackgroundColor(Color.RED);
                            s6.setEnabled(true);
                        }else {s6.setBackgroundColor(Color.GREEN);
                            s6.setEnabled(true);}
                        if(SPOTS.get(6).getState().equals("1")){
                            s7.setBackgroundColor(Color.RED);
                            s7.setEnabled(true);
                        }else {s7.setBackgroundColor(Color.GREEN);
                            s7.setEnabled(true);}
                        if(SPOTS.get(7).getState().equals("1")){
                            s8.setBackgroundColor(Color.RED);
                            s8.setClickable(true);
                        }else {s8.setBackgroundColor(Color.GREEN);
                            s8.setEnabled(true);}

                    }
                    @Override
                    public void onFailure(Call<List<spotModel>> call, Throwable t) {
                        Log.v("reponse :"," error  "+t.getMessage());
                        msg("...Connect to your database...");

                    }
                });


            }
        });

//        Timer _timer = new Timer();
//
//        _timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                // use runOnUiThread(Runnable action)
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        call.clone().enqueue(new Callback<List<spotModel>>() {
//                            @Override
//                            public void onResponse(Call<List<spotModel>> call, Response<List<spotModel>> response) {
//
//                                if(!response.isSuccessful()){
//                                    Log.v("reponse1 :"," error "+response.message());
////                                    msg("...Connect to your database...");
//                                    return;
//                                }
//                                SPOTS = response.body();
//                                Log.v("mohamed:  ",SPOTS.toString());
//                                for (spotModel SPOT:SPOTS)
//                                {
//                                    String content = "";
//                                    content+="spot "+ SPOT.getSpot()+"\n";
//                                    content+="instruction "+ SPOT.getInstruct()+"\n";
//                                    content+="status "+ SPOT.getState()+"\n\n";
//                                    Log.v("mohamed:  ",content);
//                                }
//
//                                if(SPOTS.get(0).getState().equals("1")){
//                                    s1.setBackgroundColor(Color.RED);
//                                    s1.setEnabled(true);
//                                }
//                                else {s1.setBackgroundColor(Color.GREEN);
//                                    s1.setEnabled(true);}
//                                if(SPOTS.get(1).getState().equals("1")){
//                                    s2.setBackgroundColor(Color.RED);
//                                    s2.setEnabled(true);
//                                }  else {s2.setBackgroundColor(Color.GREEN);
//                                    s2.setEnabled(true);}
//                                if(SPOTS.get(2).getState().equals("1")){
//                                    s3.setBackgroundColor(Color.RED);
//                                    s3.setEnabled(true);
//                                }else {s3.setBackgroundColor(Color.GREEN);
//                                    s3.setEnabled(true);}
//                                if(SPOTS.get(3).getState().equals("1")){
//                                    s4.setBackgroundColor(Color.RED);
//                                    s4.setEnabled(true);
//                                }else {s4.setBackgroundColor(Color.GREEN);
//                                    s4.setEnabled(true);}
//                                if(SPOTS.get(4).getState().equals("1")){
//                                    s5.setBackgroundColor(Color.RED);
//                                    s5.setEnabled(true);
//                                }else {s5.setBackgroundColor(Color.GREEN);}
//                                s5.setEnabled(true);
//                                if(SPOTS.get(5).getState().equals("1")){
//                                    s6.setBackgroundColor(Color.RED);
//                                    s6.setEnabled(true);
//                                }else {s6.setBackgroundColor(Color.GREEN);
//                                    s6.setEnabled(true);}
//                                if(SPOTS.get(6).getState().equals("1")){
//                                    s7.setBackgroundColor(Color.RED);
//                                    s7.setEnabled(true);
//                                }else {s7.setBackgroundColor(Color.GREEN);
//                                    s7.setEnabled(true);}
//                                if(SPOTS.get(7).getState().equals("1")){
//                                    s8.setBackgroundColor(Color.RED);
//                                    s8.setEnabled(true);
//                                }else {s8.setBackgroundColor(Color.GREEN);
//                                    s8.setEnabled(true);}
//                            }
//                            @Override
//                            public void onFailure(Call<List<spotModel>> call, Throwable t) {
//                                Log.v("reponse :"," error  "+t.getMessage());
////                                msg("...Connect to your database...");
//
//                            }
//                        });
//                    }
//                });
//            }
//        }, 1);





//        // / MAKE THE TAKEN SPOTS RED
//        for (spotModel SPOT:SPOTS)
//        {
//            if (SPOT.getSpot() == Integer.parseInt("1")){
//                s1.setBackgroundColor(Color.RED);
//            }
//        }









        ////TO SET THEM BLUE AS WAITING LIST
        s1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(SPOTS.get(0).getState().equals("0")){
                takeSpot("Z1");
                s1.setClickable(false);
                s1.setBackgroundColor(Color.BLUE);
                // inflate the layout of the popup window
                LayoutInflater inflater = (LayoutInflater)
                        getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.popup, null);
                //change Text
                TextView textView = popupView.findViewById(R.id.popUpText);
                textView.setText("PARKING IN PROGRESS PLEASE WAIT ....");
                // create the popup window
                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                boolean focusable = true; // lets taps outside the popup also dismiss it
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
                // show the popup window
                // which view you pass in doesn't matter, it is only used for the window tolken
                popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);}

                else{
                    // inflate the layout of the popup window
                    LayoutInflater inflater = (LayoutInflater)
                            getSystemService(LAYOUT_INFLATER_SERVICE);
                    View popupView = inflater.inflate(R.layout.popup, null);
                    //change Text
                    TextView textView = popupView.findViewById(R.id.popUpText);
                    textView.setText("SPOT TAKEN PLEASE WAIT OR CHOOSE ANOTHER SPOT");
                    // create the popup window
                    int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                    int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                    boolean focusable = true; // lets taps outside the popup also dismiss it
                    final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
                    // show the popup window
                    // which view you pass in doesn't matter, it is only used for the window tolken
                    popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
                    popupView.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            popupWindow.dismiss();
                            return true;
                        }
                    });}

            // dismiss the popup window when touched

                // dismiss the popup window when touched
//                popupView.setOnTouchListener(new View.OnTouchListener() {
//                    @Override
//                    public boolean onTouch(View v, MotionEvent event) {
//                        popupWindow.dismiss();
//                        return true;
//                    }
//                });
            }
        });



        s2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SPOTS.get(1).getState().equals("0")){
                    takeSpot("Z2");
                    s2.setClickable(false);
                    s2.setBackgroundColor(Color.BLUE);
                    // inflate the layout of the popup window
                    LayoutInflater inflater = (LayoutInflater)
                            getSystemService(LAYOUT_INFLATER_SERVICE);
                    View popupView = inflater.inflate(R.layout.popup, null);
                    //change Text
                    TextView textView = popupView.findViewById(R.id.popUpText);
                    textView.setText("PARKING IN PROGRESS PLEASE WAIT ....");
                    // create the popup window
                    int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                    int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                    boolean focusable = true; // lets taps outside the popup also dismiss it
                    final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
                    // show the popup window
                    // which view you pass in doesn't matter, it is only used for the window tolken
                    popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);}

                else{
                    // inflate the layout of the popup window
                    LayoutInflater inflater = (LayoutInflater)
                            getSystemService(LAYOUT_INFLATER_SERVICE);
                    View popupView = inflater.inflate(R.layout.popup, null);
                    //change Text
                    TextView textView = popupView.findViewById(R.id.popUpText);
                    textView.setText("SPOT TAKEN PLEASE WAIT OR CHOOSE ANOTHER SPOT");
                    // create the popup window
                    int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                    int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                    boolean focusable = true; // lets taps outside the popup also dismiss it
                    final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
                    // show the popup window
                    // which view you pass in doesn't matter, it is only used for the window tolken
                    popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
                    popupView.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            popupWindow.dismiss();
                            return true;
                        }
                    });}
            }
        });
        s3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SPOTS.get(2).getState().equals("0")){
                    takeSpot("Z3");
                    s3.setClickable(false);
                    s3.setBackgroundColor(Color.BLUE);
                    // inflate the layout of the popup window
                    LayoutInflater inflater = (LayoutInflater)
                            getSystemService(LAYOUT_INFLATER_SERVICE);
                    View popupView = inflater.inflate(R.layout.popup, null);
                    //change Text
                    TextView textView = popupView.findViewById(R.id.popUpText);
                    textView.setText("PARKING IN PROGRESS PLEASE WAIT ....");
                    // create the popup window
                    int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                    int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                    boolean focusable = true; // lets taps outside the popup also dismiss it
                    final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
                    // show the popup window
                    // which view you pass in doesn't matter, it is only used for the window tolken
                    popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);}

                else{
                    // inflate the layout of the popup window
                    LayoutInflater inflater = (LayoutInflater)
                            getSystemService(LAYOUT_INFLATER_SERVICE);
                    View popupView = inflater.inflate(R.layout.popup, null);
                    //change Text
                    TextView textView = popupView.findViewById(R.id.popUpText);
                    textView.setText("SPOT TAKEN PLEASE WAIT OR CHOOSE ANOTHER SPOT");
                    // create the popup window
                    int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                    int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                    boolean focusable = true; // lets taps outside the popup also dismiss it
                    final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
                    // show the popup window
                    // which view you pass in doesn't matter, it is only used for the window tolken
                    popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
                    popupView.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            popupWindow.dismiss();
                            return true;
                        }
                    });}

            }
        });
        s4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SPOTS.get(3).getState().equals("0")){
                    takeSpot("Z4");
                    s4.setClickable(false);
                    s4.setBackgroundColor(Color.BLUE);
                    // inflate the layout of the popup window
                    LayoutInflater inflater = (LayoutInflater)
                            getSystemService(LAYOUT_INFLATER_SERVICE);
                    View popupView = inflater.inflate(R.layout.popup, null);
                    //change Text
                    TextView textView = popupView.findViewById(R.id.popUpText);
                    textView.setText("PARKING IN PROGRESS PLEASE WAIT ....");
                    // create the popup window
                    int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                    int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                    boolean focusable = true; // lets taps outside the popup also dismiss it
                    final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
                    // show the popup window
                    // which view you pass in doesn't matter, it is only used for the window tolken
                    popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);}

                else{
                    // inflate the layout of the popup window
                    LayoutInflater inflater = (LayoutInflater)
                            getSystemService(LAYOUT_INFLATER_SERVICE);
                    View popupView = inflater.inflate(R.layout.popup, null);
                    //change Text
                    TextView textView = popupView.findViewById(R.id.popUpText);
                    textView.setText("SPOT TAKEN PLEASE WAIT OR CHOOSE ANOTHER SPOT");
                    // create the popup window
                    int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                    int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                    boolean focusable = true; // lets taps outside the popup also dismiss it
                    final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
                    // show the popup window
                    // which view you pass in doesn't matter, it is only used for the window tolken
                    popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
                    popupView.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            popupWindow.dismiss();
                            return true;
                        }
                    });}
            }
        });
        s5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SPOTS.get(4).getState().equals("0")){
                    takeSpot("Z5");
                    s5.setClickable(false);
                    s5.setBackgroundColor(Color.BLUE);
                    // inflate the layout of the popup window
                    LayoutInflater inflater = (LayoutInflater)
                            getSystemService(LAYOUT_INFLATER_SERVICE);
                    View popupView = inflater.inflate(R.layout.popup, null);
                    //change Text
                    TextView textView = popupView.findViewById(R.id.popUpText);
                    textView.setText("PARKING IN PROGRESS PLEASE WAIT ....");
                    // create the popup window
                    int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                    int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                    boolean focusable = true; // lets taps outside the popup also dismiss it
                    final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
                    // show the popup window
                    // which view you pass in doesn't matter, it is only used for the window tolken
                    popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);}

                else{
                    // inflate the layout of the popup window
                    LayoutInflater inflater = (LayoutInflater)
                            getSystemService(LAYOUT_INFLATER_SERVICE);
                    View popupView = inflater.inflate(R.layout.popup, null);
                    //change Text
                    TextView textView = popupView.findViewById(R.id.popUpText);
                    textView.setText("SPOT TAKEN PLEASE WAIT OR CHOOSE ANOTHER SPOT");
                    // create the popup window
                    int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                    int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                    boolean focusable = true; // lets taps outside the popup also dismiss it
                    final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
                    // show the popup window
                    // which view you pass in doesn't matter, it is only used for the window tolken
                    popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
                    popupView.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            popupWindow.dismiss();
                            return true;
                        }
                    });}
            }
        });
        s6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {if(SPOTS.get(5).getState().equals("0")){
                takeSpot("Z6");
                s6.setClickable(false);
                s6.setBackgroundColor(Color.BLUE);
                // inflate the layout of the popup window
                LayoutInflater inflater = (LayoutInflater)
                        getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.popup, null);
                //change Text
                TextView textView = popupView.findViewById(R.id.popUpText);
                textView.setText("PARKING IN PROGRESS PLEASE WAIT ....");
                // create the popup window
                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                boolean focusable = true; // lets taps outside the popup also dismiss it
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
                // show the popup window
                // which view you pass in doesn't matter, it is only used for the window tolken
                popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);}

            else{
                // inflate the layout of the popup window
                LayoutInflater inflater = (LayoutInflater)
                        getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.popup, null);
                //change Text
                TextView textView = popupView.findViewById(R.id.popUpText);
                textView.setText("SPOT TAKEN PLEASE WAIT OR CHOOSE ANOTHER SPOT");
                // create the popup window
                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                boolean focusable = true; // lets taps outside the popup also dismiss it
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
                // show the popup window
                // which view you pass in doesn't matter, it is only used for the window tolken
                popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
                popupView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        popupWindow.dismiss();
                        return true;
                    }
                });}
            }
        });
        s7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SPOTS.get(6).getState().equals("0")){
                    takeSpot("Z7");
                    s7.setClickable(false);
                    s7.setBackgroundColor(Color.BLUE);
                    // inflate the layout of the popup window
                    LayoutInflater inflater = (LayoutInflater)
                            getSystemService(LAYOUT_INFLATER_SERVICE);
                    View popupView = inflater.inflate(R.layout.popup, null);
                    //change Text
                    TextView textView = popupView.findViewById(R.id.popUpText);
                    textView.setText("PARKING IN PROGRESS PLEASE WAIT ....");
                    // create the popup window
                    int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                    int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                    boolean focusable = true; // lets taps outside the popup also dismiss it
                    final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
                    // show the popup window
                    // which view you pass in doesn't matter, it is only used for the window tolken
                    popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);}

                else{
                    // inflate the layout of the popup window
                    LayoutInflater inflater = (LayoutInflater)
                            getSystemService(LAYOUT_INFLATER_SERVICE);
                    View popupView = inflater.inflate(R.layout.popup, null);
                    //change Text
                    TextView textView = popupView.findViewById(R.id.popUpText);
                    textView.setText("SPOT TAKEN PLEASE WAIT OR CHOOSE ANOTHER SPOT");
                    // create the popup window
                    int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                    int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                    boolean focusable = true; // lets taps outside the popup also dismiss it
                    final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
                    // show the popup window
                    // which view you pass in doesn't matter, it is only used for the window tolken
                    popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
                    popupView.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            popupWindow.dismiss();
                            return true;
                        }
                    });}
            }
        });
        s8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SPOTS.get(7).getState().equals("0")){
                    takeSpot("Z8");
                    s8.setClickable(false);
                    s8.setBackgroundColor(Color.BLUE);
                    // inflate the layout of the popup window
                    LayoutInflater inflater = (LayoutInflater)
                            getSystemService(LAYOUT_INFLATER_SERVICE);
                    View popupView = inflater.inflate(R.layout.popup, null);
                    //change Text
                    TextView textView = popupView.findViewById(R.id.popUpText);
                    textView.setText("PARKING IN PROGRESS PLEASE WAIT ....");
                    // create the popup window
                    int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                    int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                    boolean focusable = true; // lets taps outside the popup also dismiss it
                    final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
                    // show the popup window
                    // which view you pass in doesn't matter, it is only used for the window tolken
                    popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);

                }

                else{
                    // inflate the layout of the popup window
                    LayoutInflater inflater = (LayoutInflater)
                            getSystemService(LAYOUT_INFLATER_SERVICE);
                    View popupView = inflater.inflate(R.layout.popup, null);
                    //change Text
                    TextView textView = popupView.findViewById(R.id.popUpText);
                    textView.setText("SPOT TAKEN PLEASE WAIT OR CHOOSE ANOTHER SPOT");
                    // create the popup window
                    int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                    int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                    boolean focusable = true; // lets taps outside the popup also dismiss it
                    final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
                    // show the popup window
                    // which view you pass in doesn't matter, it is only used for the window tolken
                    popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
                    popupView.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            popupWindow.dismiss();
                            return true;
                        }
                    });}
            }
        });


    }


    /// TO SEND DATA VIA BLUETOOTH
    private void takeSpot(String Spot)
    {
        if (btSocket!=null)
        {
            try
            {

                btSocket.getOutputStream().write(Spot.toString().getBytes());
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }

    ///// TO REFRESH SPOTS
//    private class checkSpots extends AsyncTask<Void, Void, Void>  // UI thread
//    {
//
//        @Override
//        protected void onPreExecute()
//        {
//        }
//
//        @Override
//        protected Void doInBackground(Void... devices) //while the progress dialog is shown, the connection is done in background
//        {
//
//            call.enqueue(new Callback<List<spotModel>>() {
//                @Override
//                public void onResponse(Call<List<spotModel>> call, Response<List<spotModel>> response) {
//
//                    if(!response.isSuccessful()){
//                        return;
//                    }
//                    SPOTS = response.body();
//                    for (spotModel SPOT:SPOTS)
//                    {
//                        String content = "";
//                        content+="spot "+ SPOT.getSpot()+"\n";
//                        content+="instruction "+ SPOT.getInstruct()+"\n";
//                        content+="status "+ SPOT.getState()+"\n\n";
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<List<spotModel>> call, Throwable t) {
//                }
//            });
//            return null;
//        }
//        @Override
//        protected void onPostExecute(Void result) //after the doInBackground, it checks if everything went fine
//        {
//        }
//    }
//
//

    ////// MSG
    private void msg(String s)
    {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
    }
}