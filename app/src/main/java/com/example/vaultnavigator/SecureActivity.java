package com.example.vaultnavigator;

import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class SecureActivity extends AppCompatActivity {

    private static final String TAG = "SecureActivity";
    private TextView tvSecurityInfo;
    private TextView tvCallerInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secure);

        tvSecurityInfo = findViewById(R.id.tvSecurityInfo);
        tvCallerInfo   = findViewById(R.id.tvCallerInfo);
        MaterialButton btnBack = findViewById(R.id.btnBack);

        boolean authorized = verifyCallingPackage();

        if (!authorized) {
            tvSecurityInfo.setText("⛔ Accès refusé — appelant non autorisé");
            tvSecurityInfo.setTextColor(getColor(R.color.danger));
            Toast.makeText(this, "Accès non autorisé détecté", Toast.LENGTH_LONG).show();
        } else {
            Intent intent = getIntent();
            if (intent != null && intent.hasExtra("SECURE_DATA")) {
                tvSecurityInfo.setText(intent.getStringExtra("SECURE_DATA"));
                tvSecurityInfo.setTextColor(getColor(R.color.teal_accent));
            } else {
                tvSecurityInfo.setText("Aucune donnée sécurisée reçue");
            }
        }

        btnBack.setOnClickListener(v -> finish());
    }

    private boolean verifyCallingPackage() {
        int callingUid = Binder.getCallingUid();
        int selfUid    = android.os.Process.myUid();

        if (callingUid == selfUid) {
            tvCallerInfo.setText("✅ Appelant : même application");
            tvCallerInfo.setTextColor(getColor(R.color.teal_accent));
            Log.d(TAG, "Appelant vérifié — même UID");
            return true;
        }

        String callingPackage = getPackageManager().getNameForUid(callingUid);
        tvCallerInfo.setText("⚠️ Appelant externe : " + callingPackage);
        tvCallerInfo.setTextColor(getColor(R.color.warning));
        Log.w(TAG, "Appelant externe — UID: " + callingUid + ", pkg: " + callingPackage);
        return false;
    }
}