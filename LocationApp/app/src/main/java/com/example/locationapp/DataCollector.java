package com.example.locationapp;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;

import com.example.locationapp.database.DbManager;
import com.example.locationapp.network.NetworkManager;
import com.example.locationapp.utils.Constants;


public class DataCollector {

    private Handler mHandler;
    private HandlerThread mHandlerThread;
    private static DataCollector sInstance;
    private DbManager mDbManager;
    private NetworkManager mNetworkManager;

    public static DataCollector getInstance(Context context) {
        synchronized (DataCollector.class) {
            if(sInstance == null) {
                synchronized (DataCollector.class) {
                    sInstance = new DataCollector(context);
                }
            }
        }
        return sInstance;
    }

    private DataCollector(Context context) {
        mNetworkManager = NetworkManager.getInstance(context);
        mDbManager = new DbManager(context);
        mHandlerThread = new HandlerThread("thread-bg");
        mHandlerThread.start();
        mHandler = new Handler(mHandlerThread.getLooper()) {

            @Override
            public void handleMessage(Message msg) {
                switch(msg.what) {
                    case Constants.PERSIST_LOCATION:
                        saveLocation(msg);
                        break;
                }
            }
        };
    }

    private void saveLocation(Message msg) {
        Bundle bundle = msg.getData();
        if(bundle == null) {
            return;
        }
        double lat = bundle.getDouble("lat");
        double lng = bundle.getDouble("lng");

        mDbManager.open();
        mDbManager.insertLocation(lat, lng);
        mDbManager.close();
        mNetworkManager.send(lat, lng);
    }

    public void postTask(Message msg) {
        mHandler.sendMessage(msg);
    }
}
