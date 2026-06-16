package com.example.vaultnavigator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "VaultNavigator";
    private TextInputEditText etItemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etItemId = findViewById(R.id.etItemId);

        setupListeners();
    }

    private void setupListeners() {

        // Intent explicite → DetailActivity
        findViewById(R.id.btnExplicitIntent).setOnClickListener(v -> {
            String id = getItemId();
            if (id == null) return;

            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra("ITEM_ID", id);
            startActivity(intent);
            Log.d(TAG, "Intent explicite → DetailActivity avec ID: " + id);
        });

        // Intent implicite → navigateur
        findViewById(R.id.btnImplicitIntent).setOnClickListener(v -> {
            String id = getItemId();
            if (id == null) return;

            String url = "https://example.com/items/" + id;
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
                Log.d(TAG, "Intent implicite → " + url);
            } else {
                Toast.makeText(this, "Aucune app ne peut gérer cette action", Toast.LENGTH_SHORT).show();
            }
        });

        // SingleTop → relance MainActivity sans recréer
        findViewById(R.id.btnSingleTop).setOnClickListener(v -> {
            String id = getItemId();
            if (id == null) return;

            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("ITEM_ID", id);
            startActivity(intent);
            Log.d(TAG, "Intent singleTop avec ID: " + id);
        });

        // Notification + PendingIntent
        findViewById(R.id.btnNotification).setOnClickListener(v -> {
            showNotification();
        });

        // Démarrer BackgroundService
        findViewById(R.id.btnService).setOnClickListener(v -> {
            String id = getItemId();
            if (id == null) return;

            Intent intent = new Intent(this, BackgroundService.class);
            intent.putExtra("TASK_NAME", "Traitement ressource #" + id);
            startService(intent);
        });

        // Activité sécurisée
        findViewById(R.id.btnSecureActivity).setOnClickListener(v -> {
            String id = getItemId();
            if (id == null) return;

            Intent intent = new Intent(this, SecureActivity.class);
            intent.putExtra("SECURE_DATA", "Données confidentielles #" + id);
            startActivity(intent);
        });

        // Rapport de sécurité
        findViewById(R.id.btnSecurityReport).setOnClickListener(v ->
                startActivity(new Intent(this, SecurityReportActivity.class)));
    }

    private void showNotification() {
        String channelId = "vault_channel";
        NotificationChannel channel = new NotificationChannel(
                channelId, "VaultNavigator", NotificationManager.IMPORTANCE_DEFAULT);
        NotificationManager nm = getSystemService(NotificationManager.class);
        nm.createNotificationChannel(channel);

        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("ITEM_ID", "notif_001");

        PendingIntent pi = PendingIntent.getActivity(
                this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("VaultNavigator")
                .setContentText("Touchez pour ouvrir la ressource notif_001")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pi)
                .setAutoCancel(true);

        nm.notify(1, builder.build());
        Toast.makeText(this, "Notification envoyée", Toast.LENGTH_SHORT).show();
    }

    /** Récupère l'ID du champ, affiche un toast si vide */
    private String getItemId() {
        String id = etItemId.getText() != null
                ? etItemId.getText().toString().trim() : "";
        if (id.isEmpty()) {
            Toast.makeText(this, "Entrez un ID d'abord", Toast.LENGTH_SHORT).show();
            return null;
        }
        return id;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        if (intent.hasExtra("ITEM_ID")) {
            String id = intent.getStringExtra("ITEM_ID");
            Toast.makeText(this, "onNewIntent — ID: " + id, Toast.LENGTH_LONG).show();
            Log.d(TAG, "onNewIntent avec ID: " + id);
        }
    }

    // Cycle de vie loggué
    @Override protected void onStart()   { super.onStart();   Log.d(TAG, "onStart"); }
    @Override protected void onResume()  { super.onResume();  Log.d(TAG, "onResume"); }
    @Override protected void onPause()   { super.onPause();   Log.d(TAG, "onPause"); }
    @Override protected void onStop()    { super.onStop();    Log.d(TAG, "onStop"); }
    @Override protected void onDestroy() { super.onDestroy(); Log.d(TAG, "onDestroy"); }
}