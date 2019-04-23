package com.example.wifianalyzer;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class all_networks extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_networks);
        TextView info_field = (TextView)findViewById(R.id.all_networks);
        info_field.setMovementMethod(new ScrollingMovementMethod());

        refresh();
        final Handler handler = new Handler();
        final Runnable r = new Runnable() {
            @Override
            public void run() {
                refresh();
                handler.postDelayed(this,2000);
            }
        };
        handler.postDelayed(r,2000);

    }
    public void refresh(){

        ScanResult scanResult = null;
        String ssid,bssid,rss,frequency,info;
        int level,freq;
        TextView info_field = (TextView)findViewById(R.id.all_networks);
        info_field.setText("");
        WifiManager wifiManager = (WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        List<ScanResult> network_list = wifiManager.getScanResults();
        Log.e("all_networks", network_list.size()+"");
        if(network_list != null){
            for(int i=0;i<((List) network_list).size();i++){
                scanResult = network_list.get(i);
                ssid = scanResult.SSID;
                bssid = scanResult.BSSID;
                level = scanResult.level;
                rss = Integer.toString(level);
                freq = scanResult.frequency;
                frequency = Integer.toString(freq);

                info = "\n\nSSID : "+ssid+"\nBSSID : "+bssid+"\nSignal Strength : "+rss+" dBm\nFrequency : "+frequency;
                info_field.append(info);

            }
        }
    }
}
