package com.example.locationapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.example.locationapp.utils.Constants;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

public class LocationUpdateService extends Service {

    private static final String TAG = "LocationApp";

    private static final int NOTIFICATION_ID = 1008;

    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 1000;
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS =
            UPDATE_INTERVAL_IN_MILLISECONDS / 2;
    private static final String EXTRA_STARTED_FROM_NOTIFICATION = LocationUpdateService.class
            .getPackage().getName() + ".started_from_notification";

    private LocationCallback mLocationCallback;
    private LocationRequest mLocationRequest;
    private Location mLocation;
    NotificationManager mNotiManager;
    private FusedLocationProviderClient mFusedLocationProviderClient;

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate Service");
        super.onCreate();
        mNotiManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                Log.d(TAG, " onLocationResult = " + locationResult);
                mLocation = locationResult.getLastLocation();
                Message msg = Message.obtain();
                Bundle b = new Bundle();
                b.putDouble("lat", mLocation.getLatitude());
                b.putDouble("lng", mLocation.getLongitude());
                msg.what = Constants.PERSIST_LOCATION;
                msg.setData(b);
                DataCollector.getInstance(LocationUpdateService.this.getApplicationContext()).postTask(msg);
            }
        };

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startMyForegroundService();
        } else {
            startForeground(1, new Notification());
        }

        createLocationRequest();
        getLastLocation();


    }

    private void startMyForegroundService(){
        String NOTIFICATION_CHANNEL_ID = "com.example.locationapp";
        String channelName = "Service";
        NotificationChannel chan = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName,
                NotificationManager.IMPORTANCE_NONE);
        chan.setLightColor(Color.BLUE);
        chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.createNotificationChannel(chan);


        Intent intent = new Intent(this, LocationUpdateService.class);
        intent.putExtra(EXTRA_STARTED_FROM_NOTIFICATION, true);
        PendingIntent servicePendingIntent = PendingIntent.getService(this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        Notification notification = notificationBuilder.setOngoing(true)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Location App is running")
                .addAction(R.drawable.ic_close, getString(R.string.remove_location_updates),
                        servicePendingIntent)
                .setPriority(NotificationManager.IMPORTANCE_MIN)
                .setCategory(Notification.CATEGORY_SERVICE)
                .build();
        startForeground(213, notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "Service started");
        if(intent != null) {
            boolean clickedNotification = intent.getBooleanExtra(EXTRA_STARTED_FROM_NOTIFICATION,
                    false);

            if (clickedNotification) {
                Log.d(TAG, "stopping location updates");
                removeLocationUpdates();
                stopSelf();
            }
        }
        requestLocationUpdates();
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    private void getLastLocation() {
        try {
            mFusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if(location == null) {
                                return;
                            }
                            mLocation = location;
                            Log.e(TAG, "location = " + location.getLatitude());
                        }

                    })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e(TAG, "Failed to get location exception = " + e);
                }
            });
        } catch (SecurityException e) {
            Log.e(TAG,  e.toString());
        }
    }

    public void requestLocationUpdates() {
        Log.d(TAG, "Requesting location updates");
        try {
            mFusedLocationProviderClient.requestLocationUpdates(mLocationRequest,
                    mLocationCallback, Looper.myLooper());
        } catch (SecurityException e) {
            Log.e(TAG, e.toString());
        }
    }

    public void removeLocationUpdates() {
        Log.i(TAG, "Removing location updates");
        try {
            mFusedLocationProviderClient.removeLocationUpdates(mLocationCallback);
            stopSelf();
        } catch (SecurityException exception) {
            Log.e(TAG, "Lost location permission. Could not remove updates. " + exception);
        }
    }
}