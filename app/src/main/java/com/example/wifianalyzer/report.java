package com.example.wifianalyzer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class report extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        refresh();
        final Handler handler = new Handler();
        final Runnable r = new Runnable() {
           @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
           @Override
           public void run() {
               refresh();
               handler.postDelayed(this,2000);
           }
        };
        handler.postDelayed(r,2000);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("SetTextI18n")
    public void refresh(){
        WifiManager wifiManager = (WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();

        String ssid = wifiInfo.getSSID();

        String bssid = wifiInfo.getBSSID();

        int ipadd = wifiInfo.getIpAddress();
        String ip = String.format("%d.%d.%d.%d", (ipadd & 0xff), (ipadd >> 8 & 0xff), (ipadd >> 16 & 0xff), (ipadd >> 24 & 0xff));

        int rssi = wifiInfo.getRssi();
        String strength = Integer.toString(rssi);

        int freq = wifiInfo.getFrequency();
        String frequency = Integer.toString(freq);

        int link = wifiInfo.getLinkSpeed();
        String link_speed = Integer.toString(link);

        String information = "Current connection info:\nSSID : "+ssid+"\nBSSID : "+bssid+"\nIP address : "+ip+"\nSignal Strength : "+strength+" dBm\nFrequency : "+frequency+" MHz\nLink Speed : "+link+" Mbps";
        TextView wifi_info = (TextView)findViewById(R.id.info);
        wifi_info.setText(information);

        File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "Wifi_Analyzer");
        dir.mkdirs();
        try {
            Date currentTime = Calendar.getInstance().getTime();
            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yy::hh:mm:ss-a");
            String formattedDate = df.format(currentTime);
            File myFile = new File(dir, "Signal_Strength_Log.txt");
            if (myFile.length() < 1024000) {
                FileWriter fw = new FileWriter(myFile, true);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter pw = new PrintWriter(bw);
                String printString = wifiInfo.getSSID() + "\t\t" + formattedDate + "\t\t" + wifiInfo.getRssi() + "\n";
                pw.print(printString);
                pw.close();
            }else{
                PrintWriter pw = new PrintWriter(myFile);
                pw.print("");
                pw.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
