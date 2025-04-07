package com.example.myapplication;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {


    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Log.d("FCM", "Message from:" + remoteMessage.getFrom());

        if(remoteMessage.getNotification() != null) {
            Log.d("FCM", "Notification body: " + remoteMessage.getNotification().getBody());
        }

        if(!remoteMessage.getData().isEmpty()) {
            Log.d("FCM", "Data payload " + remoteMessage.getData());
        }
    }

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        Log.d("FCM", "New token: " + token);
    }
}
