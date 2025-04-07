package com.example.myapplication.utility;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.google.firebase.messaging.FirebaseMessaging;

public class AssignmentReminderWorker extends Worker{
    public AssignmentReminderWorker(@NonNull Context context, @NonNull WorkerParameters workerParameters) {
        super(context, workerParameters);
    }

    @NonNull
    @Override
    public Result doWork() {
        String title = getInputData().getString("title");
        String message = getInputData().getString("message");

        Log.d("ReminderWorker", "Triggering local FCM-like reminder: " + message);

        //TODO: Show notification here (can use NotificationManager)
        return Result.success();
    }
}
