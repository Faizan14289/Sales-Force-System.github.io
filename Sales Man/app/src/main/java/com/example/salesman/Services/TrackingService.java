package com.example.salesman.Services;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.os.IBinder;
import android.widget.Toast;

import androidx.core.content.ContextCompat;


import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TrackingService extends Service {

    private static final String TAG = TrackingService.class.getSimpleName();
    FusedLocationProviderClient client;
    LocationRequest request;
    String sss;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        buildNotification();

        requestLocationUpdates();
       // loginToFirebase();
    }
    LocationCallback mLocationCallback =new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {




            final DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference("salesMan-list").child(sss);
            Location location = locationResult.getLastLocation();
            if (location != null) {


                mDatabaseRef.child("longitude").setValue(String.valueOf(location.getLongitude()));
                mDatabaseRef.child("latitude").setValue(String.valueOf(location.getLatitude()));

            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
      //  Toast.makeText(this, "Destoryed", Toast.LENGTH_SHORT).show();
        if (client != null) {
            client.removeLocationUpdates(mLocationCallback);
        }
        stopSelf();
    }
//Create the persistent notification//

    private void buildNotification() {
                if (Build.VERSION.SDK_INT >= 26) {
                    NotificationManager notificationManager;
                    notificationManager =
                            (NotificationManager)
                                    getSystemService(Context.NOTIFICATION_SERVICE);

                    int importance = NotificationManager.IMPORTANCE_LOW;
                    NotificationChannel channel =
                            new NotificationChannel("com.ebookfrenzy.notifydemo.news", "NotifyDemo News", importance);

                    channel.setDescription("Example News Channel");
                    channel.enableLights(true);
                    channel.setLightColor(Color.RED);
                    channel.enableVibration(true);
                    channel.setVibrationPattern(
                            new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                    notificationManager.createNotificationChannel(channel);

                    int notificationID = 101;

                    String channelID = "com.ebookfrenzy.notifydemo.news";
                    String stop = "stop";
                    registerReceiver(stopReceiver, new IntentFilter(stop));
                    PendingIntent broadcastIntent = PendingIntent.getBroadcast(
                            this, 0, new Intent(stop), PendingIntent.FLAG_UPDATE_CURRENT);
                    Notification.Builder notification =
                            new Notification.Builder(TrackingService.this, channelID)
                                    .setContentTitle("Location is Shared")
                                    .setContentText("Tap to cancel")
                                    .setSmallIcon(android.R.drawable.ic_dialog_info)
                                    .setChannelId(channelID).setOngoing(true)
                                    .setContentIntent(broadcastIntent);
                    startForeground(1, notification.build());
                }

    }

    protected BroadcastReceiver stopReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

//Unregister the BroadcastReceiver when the notification is tapped//

            unregisterReceiver(stopReceiver);

//Stop the Service//
            if (client != null) {
                client.removeLocationUpdates(mLocationCallback);
            }
            stopSelf();

        }
    };


    private void requestLocationUpdates() {
        request = new LocationRequest();



        request.setInterval(10000);

        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
         client = LocationServices.getFusedLocationProviderClient(this);
      //  final String path = getString(R.string.firebase_path);
        int permission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);


        if (permission == PackageManager.PERMISSION_GRANTED) {
            SharedPreferences sharedpreferences;
            sharedpreferences = getSharedPreferences("cnic", Context.MODE_PRIVATE);
             sss = sharedpreferences.getString("cnic",null);


            client.requestLocationUpdates(request,mLocationCallback , null);
        }
    }
}
