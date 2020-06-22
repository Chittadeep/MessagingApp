package com.example.messagingapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;


public class notificationService extends Service {
    @Nullable
    Member user;

    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
         user = (Member) intent.getSerializableExtra("User");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users/" + user.getPhoneNum());
        myRef.child("messageBox/").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                HashMap<String, ArrayList<Message>> myMap = (HashMap<String, ArrayList<Message>>) dataSnapshot.getValue();

                Toast.makeText(getApplicationContext(), myMap.values().iterator().next().get(0).toString(), Toast.LENGTH_LONG).show();

                int count = 1;

                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                String channelID = "Personal Notification";

                Intent landingIntent = new Intent(getBaseContext(), Dashboard.class);

                PendingIntent landingPendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, landingIntent, PendingIntent.FLAG_ONE_SHOT);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                    NotificationChannel notificationChannel = new NotificationChannel(channelID, "personal notifications", NotificationManager.IMPORTANCE_DEFAULT);

                    notificationChannel.setDescription("Include all personal notifications");

                    notificationManager.createNotificationChannel(notificationChannel);
                }

                /*ArrayList<Message> list = ((ArrayList) children.next());

                Toast.makeText(getApplicationContext(), , Toast.LENGTH_LONG).show();

                String contentTitle;
                    String contentText;
                    if (count > 1) {
                        contentTitle = null;
                        contentText = count + " messages received";
                    } else {
                        contentTitle = "hello";
                        contentText = notification.getMessage();
                    }

                    NotificationCompat.Builder mBuilder =
                            new NotificationCompat.Builder(getApplicationContext(), channelID)
                                    .setSmallIcon(R.drawable.notification_icon)
                                    //.setContentTitle(contentTitle)
                                    .setContentText(contentText)
                                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                    .setAutoCancel(true)
                                    .setContentIntent(landingPendingIntent);

                    notificationManager.notify(001, mBuilder.build());

                    count++;*/

            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Database connection failed", Toast.LENGTH_LONG).show();

            }
        });

        return START_REDELIVER_INTENT;
    }

    public void onTaskRemoved(Intent rootIntent) {
        Intent restartServiceIntent = new Intent(getApplicationContext(),this.getClass());
        restartServiceIntent.setPackage(getPackageName());
        startService(restartServiceIntent);
        super.onTaskRemoved(rootIntent);
    }

    public void onDestroy() {
        Toast.makeText(getBaseContext(), "service destroyed", Toast.LENGTH_LONG).show();
        super.onDestroy();
    }
}
