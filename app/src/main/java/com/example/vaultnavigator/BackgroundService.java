package com.example.vaultnavigator;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class BackgroundService extends Service {

    private static final String TAG = "BackgroundService";
    private boolean isRunning = false;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand — startId: " + startId);

        if (intent != null && intent.hasExtra("TASK_NAME")) {
            String task = intent.getStringExtra("TASK_NAME");
            runTask(task, startId);
        } else {
            stopSelf(startId);
        }

        return START_NOT_STICKY;
    }

    private void runTask(String task, int startId) {
        if (isRunning) {
            Log.w(TAG, "Tâche déjà en cours");
            return;
        }

        isRunning = true;
        Toast.makeText(this, "Service démarré : " + task, Toast.LENGTH_SHORT).show();

        new Thread(() -> {
            Log.d(TAG, "Début : " + task);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                Log.e(TAG, "Interrompu", e);
            }
            Log.d(TAG, "Fin : " + task);
            isRunning = false;
            stopSelf(startId);
        }).start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
        Toast.makeText(this, "Service terminé", Toast.LENGTH_SHORT).show();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}