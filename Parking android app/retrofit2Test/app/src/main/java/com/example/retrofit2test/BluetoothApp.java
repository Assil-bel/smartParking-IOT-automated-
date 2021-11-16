package com.example.retrofit2test;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.NavigableMap;
import java.util.Set;
import java.util.jar.Attributes;

public class BluetoothApp extends AppCompatActivity {

    private BluetoothAdapter adapter;
    private Set<BluetoothDevice> pairedD;
    CheckBox enableB , visibleB;
    ImageButton imageButton;
    public ListView devisesL;
    TextView NAME;
    FloatingActionButton FAB;
    Boolean isChecked = false;
    Button skipBtn;




    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_app);

        FAB = findViewById(R.id.FAB);

//        enableB = findViewById(R.id.enable);
        devisesL = findViewById(R.id.List);
        adapter = BluetoothAdapter.getDefaultAdapter();
        NAME = findViewById(R.id.textView);
//        imageButton = findViewById(R.id.imageButton);

        skipBtn =findViewById(R.id.SKIP);
        NAME.setText("Bluetooth name : "+ getLocalName());






        skipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), parkingControl.class);
                //Change the activity.
                startActivity(i);
                i.putExtra("address", "skiped");//this will be received at ledControl (class) Activity


            }
        });

        ///////////////////////
//        if(adapter==null){
//            Toast.makeText(this, "Bluetooth not supported ", Toast.LENGTH_SHORT).show();
//            finish();
//        }
//        if(adapter.isEnabled()){
//            Toast.makeText(this, "Bluetooth enabled ", Toast.LENGTH_SHORT).show();
//            enableB.setChecked(true);
//
//        }

        ///////////////////////
//        FAB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if(!isChecked){
//                    adapter.disable();
//                    Toast.makeText(getApplication(), "Bluetooth turned off ", Toast.LENGTH_SHORT).show();
//                    finish();
//                }else{
//                    Intent intentOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//                    startActivityForResult(intentOn,0);
//                    Toast.makeText(getApplication(), "Bluetooth turned on ", Toast.LENGTH_SHORT).show();
//                    list();
//
//                }
//            }
//        });



        ////////////////////////
//        visibleB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if(isChecked){
//                    Intent intentVisible = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
//                    startActivityForResult(intentVisible,0);
//                    Toast.makeText(getApplication(), " visible for 2 mn ", Toast.LENGTH_SHORT).show();
//                    }
//
//            }
//        });


        FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(adapter==null){
                    isChecked = false;
                }
                if(adapter.isEnabled()){
                    isChecked = true;
                }
                if(isChecked){
                    list();
                }else{
                    Intent intentOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(intentOn,0);
                    Toast.makeText(getApplication(), "Bluetooth turned on ", Toast.LENGTH_SHORT).show();
                    list();
//
                } }
        });




    }




    //////////////////////////
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void list() {

        pairedD = adapter.getBondedDevices();
        ArrayList list = new ArrayList();
        ArrayList list2 = new ArrayList();
        for (BluetoothDevice bt :pairedD){
            list.add(bt.getName());
        }
        ArrayAdapter arrayAdapter= new ArrayAdapter(this, android.R.layout.simple_list_item_1,list);


        devisesL.setAdapter(arrayAdapter);
        devisesL.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the device MAC address, the last 17 chars in the View
                String Name = ((TextView) view).getText().toString();
                String address = null;
                for (BluetoothDevice bt :pairedD){
                    list2.add(bt.getName());
                    if (Name.matches(bt.getName()) )
                        address = bt.getAddress();
                }
                // Make an intent to start next activity.
                if (BluetoothAdapter.checkBluetoothAddress(address)) {
                    //It is a valid MAC address.
                    BluetoothDevice device = adapter.getRemoteDevice(address);

                } else {

                    Log.v("checkkk :  ",""+BluetoothAdapter.checkBluetoothAddress(address));

                    Toast.makeText(getApplicationContext(), "Invalid MAC: Address", Toast.LENGTH_LONG).show();
                }
                Intent i = new Intent(getApplicationContext(), parkingControl.class);
                //Change the activity.
                i.putExtra("address", address);//this will be received at ledControl (class) Activity
                startActivity(i);
                Log.v("address:  ",""+address);

            }
        });

    }
    ////////////////////////
    public String getLocalName() {
        if (adapter == null) {
            adapter = BluetoothAdapter.getDefaultAdapter();
        }
        String name = adapter.getName();
        if (name == null) {
            name = adapter.getAddress();
        }
        return name;
    }
}