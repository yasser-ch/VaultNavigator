package com.example.vaultnavigator;

import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class SecurityReportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_report);

        TextView tvReport = findViewById(R.id.tvReport);
        MaterialButton btnBack = findViewById(R.id.btnBack);

        tvReport.setText(generateReport());
        btnBack.setOnClickListener(v -> finish());
    }

    private String generateReport() {
        StringBuilder sb = new StringBuilder();
        sb.append("━━━━━━━━━━━━━━━━━━━━━━━━\n");
        sb.append("  RAPPORT DE SÉCURITÉ\n");
        sb.append("━━━━━━━━━━━━━━━━━━━━━━━━\n\n");

        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    getPackageName(),
                    PackageManager.GET_ACTIVITIES |
                            PackageManager.GET_SERVICES |
                            PackageManager.GET_RECEIVERS
            );

            // Activités
            sb.append("📱 ACTIVITÉS\n\n");
            if (info.activities != null) {
                for (ActivityInfo a : info.activities) {
                    String name = a.name.replace(getPackageName() + ".", "");
                    sb.append("• ").append(name).append("\n");
                    sb.append("  Exportée : ").append(a.exported).append("\n");
                    if (a.exported && name.toLowerCase().contains("secure")) {
                        sb.append("  🔴 RISQUE ÉLEVÉ\n");
                    } else if (a.exported) {
                        sb.append("  🟡 RISQUE MOYEN\n");
                    } else {
                        sb.append("  🟢 RISQUE FAIBLE\n");
                    }
                    sb.append("\n");
                }
            }

            // Services
            sb.append("⚙️ SERVICES\n\n");
            if (info.services != null) {
                for (ServiceInfo s : info.services) {
                    String name = s.name.replace(getPackageName() + ".", "");
                    sb.append("• ").append(name).append("\n");
                    sb.append("  Exporté : ").append(s.exported).append("\n");
                    sb.append(s.exported ? "  🔴 RISQUE ÉLEVÉ\n" : "  🟢 RISQUE FAIBLE\n");
                    sb.append("\n");
                }
            }

            // Receivers
            sb.append("📡 RÉCEPTEURS\n\n");
            if (info.receivers != null) {
                for (ActivityInfo r : info.receivers) {
                    String name = r.name.replace(getPackageName() + ".", "");
                    sb.append("• ").append(name).append("\n");
                    sb.append("  Exporté : ").append(r.exported).append("\n");
                    sb.append(r.exported ? "  🟡 RISQUE MOYEN\n" : "  🟢 RISQUE FAIBLE\n");
                    sb.append("\n");
                }
            }

            // Recommandations
            sb.append("━━━━━━━━━━━━━━━━━━━━━━━━\n");
            sb.append("  RECOMMANDATIONS\n");
            sb.append("━━━━━━━━━━━━━━━━━━━━━━━━\n\n");
            sb.append("1. exported=\"false\" sur tous les\n   composants internes\n\n");
            sb.append("2. FLAG_IMMUTABLE sur tous les\n   PendingIntent\n\n");
            sb.append("3. Vérifier Binder.getCallingUid()\n   sur les composants sensibles\n\n");
            sb.append("4. Intents explicites plutôt\n   qu'implicites\n");

        } catch (PackageManager.NameNotFoundException e) {
            sb.append("Erreur : ").append(e.getMessage());
        }

        return sb.toString();
    }
}