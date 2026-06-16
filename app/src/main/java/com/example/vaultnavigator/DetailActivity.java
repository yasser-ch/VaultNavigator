package com.example.vaultnavigator;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class DetailActivity extends AppCompatActivity {

    private TextView tvItemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvItemId = findViewById(R.id.tvItemId);
        MaterialButton btnBack = findViewById(R.id.btnBack);

        loadData(getIntent());
        btnBack.setOnClickListener(v -> finish());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        loadData(intent);
    }

    private void loadData(Intent intent) {
        if (intent != null && intent.hasExtra("ITEM_ID")) {
            String id = intent.getStringExtra("ITEM_ID");
            tvItemId.setText("Ressource : #" + id);
        } else {
            tvItemId.setText("Aucun identifiant reçu");
        }
    }
}