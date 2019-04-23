package com.example.wifianalyzer;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button current = findViewById(R.id.current_info);
        current.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                if(!wifiManager.isWifiEnabled()){

                    Context context = getApplicationContext();
                    CharSequence text = "Wifi is disabled... Enabling Wifi";
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();

                    wifiManager.setWifiEnabled(true);
                }
                Intent current = new Intent(MainActivity.this, report.class);
                startActivity(current);
            }
        });

        Button all_net = findViewById(R.id.all_networks);
        all_net.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                if(!wifiManager.isWifiEnabled()){

                    Context context = getApplicationContext();
                    CharSequence text = "Wifi is disabled... Enabling Wifi";
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();

                    wifiManager.setWifiEnabled(true);
                }
                Intent all_net = new Intent(MainActivity.this, all_networks.class);
                startActivity(all_net);
            }
        });

    }

}

