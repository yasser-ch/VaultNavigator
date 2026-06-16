# 🧭 VaultNavigator

Application Android de démonstration de la navigation sécurisée entre composants — développée en Java avec Material Design 3.

---

## 📱 Aperçu

<img width="1080" height="2400" alt="image" src="https://github.com/user-attachments/assets/7657f51e-94d1-4d90-9704-4e1e1578e4ed" />


| Écran | Description |
|---|---|
| Accueil | Hub central — navigation + composants |
| DetailActivity | Reçoit et affiche un ID via Intent |
| SecureActivity | Vérifie l'identité de l'appelant via UID |
| SecurityReportActivity | Analyse les composants exportés du manifest |

---

## ✨ Fonctionnalités

- **Intent explicite** — navigation directe vers `DetailActivity` avec passage de données
- **Intent implicite** — ouverture d'une URL via `ACTION_VIEW`
- **SingleTop** — démonstration de `onNewIntent()` sans recréation d'activité
- **Notification + PendingIntent** — notification cliquable avec `FLAG_IMMUTABLE`
- **BackgroundService** — tâche en arrière-plan dans un thread séparé
- **SecureActivity** — vérification de l'appelant via `Binder.getCallingUid()`
- **Rapport de sécurité** — analyse dynamique des composants exportés avec évaluation des risques

---

## 🗂️ Structure

```
app/src/main/java/com/example/vaultnavigator/
├── MainActivity.java              # Hub principal — tous les points d'entrée
├── DetailActivity.java            # Destination Intent explicite + App Links
├── SecureActivity.java            # Vérification UID appelant
├── SecurityReportActivity.java    # Rapport dynamique du manifest
└── BackgroundService.java         # Service démarré — tâche asynchrone

res/layout/
├── activity_main.xml
├── activity_detail.xml
├── activity_secure.xml
└── activity_security_report.xml
```

---

## 🛡️ Mesures de Sécurité

| Risque | Contre-mesure | Implémentation |
|---|---|---|
| Composant accessible depuis l'extérieur | `exported="false"` | `SecureActivity`, `SecurityReportActivity`, `BackgroundService` |
| PendingIntent mutable | `FLAG_IMMUTABLE` | Notification + PendingIntent |
| Appelant non autorisé | Vérification UID | `Binder.getCallingUid()` dans `SecureActivity` |
| Intent intercepté | Intent explicite | Navigation interne via classe cible directe |
| Lien non vérifié | App Links | `autoVerify="true"` sur `DetailActivity` |

---

## 🔄 Modes de Lancement

| Mode | Comportement | Utilisé sur |
|---|---|---|
| `standard` | Nouvelle instance à chaque appel | `DetailActivity`, `SecureActivity` |
| `singleTop` | `onNewIntent()` si déjà en haut de pile | `MainActivity` |

---

## 🛠️ Stack

| Outil | Version |
|---|---|
| Java | 11 |
| Android SDK min | API 24 (Android 7.0) |
| `androidx.appcompat` | 1.6.1 |
| `material` | 1.9.0 |
| `constraintlayout` | 2.1.4 |
| `androidx.core` | 1.12.0 |

---

## 🚀 Lancer le projet

```bash
git clone https://github.com/yasser-ch/VaultNavigator.git
```

Ouvrir dans Android Studio → **Run** sur émulateur ou appareil physique (API 24+).

> ⚠️ La permission `POST_NOTIFICATIONS` est requise sur Android 13+ pour afficher les notifications. Accepte la demande au premier lancement.

---

## 📚 Contexte

TP réalisé dans le cadre du cursus **Génie Cyberdéfense & Télécommunications Embarquées** à l'ENSA Marrakech.  
Concepts abordés : Intents explicites/implicites, Task/BackStack, App Links, Services, BroadcastReceiver, PendingIntent, sécurité des composants Android.
