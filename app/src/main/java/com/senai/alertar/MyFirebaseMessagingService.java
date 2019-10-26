package com.senai.alertar;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Created by MARI on 23/07/2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        //Log.d(TAG, "" + remoteMessage.getFrom());
        Log.d(TAG, "Notification Message Title: " + remoteMessage.getData().get("title"));
        Log.d(TAG, "Notification Message Body: " + remoteMessage.getData().get("body"));
        Log.d(TAG, "Notification Message Date Time: " + remoteMessage.getData().get("dateTime"));
        Log.d(TAG, "Notification Solved: " + remoteMessage.getData().get("solve"));
        Log.d(TAG, "Notification ID: " + remoteMessage.getData().get("id"));
        Log.d(TAG, "Notification level: " + remoteMessage.getData().get("nivel"));
        int alertaId = Integer.parseInt(remoteMessage.getData().get("id"));
        boolean solved = Boolean.valueOf(remoteMessage.getData().get("solve"));
        if (!solved)
            sendNotification(alertaId, remoteMessage.getData().get("title"), remoteMessage.getData().get("body"), remoteMessage.getData().get("dateTime"),
                    Integer.parseInt(remoteMessage.getData().get("nivel")));
        else
            solveNotification(alertaId, remoteMessage.getData().get("title"));
        //sendNotification(remoteMessage.getData().get("title"), "lol");
    }

    private void sendNotification(int alertaId, String messageTitle, String messageBody, String dateTime, int nivel) {

        Intent intent = new Intent(this, DisplayAlertas.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        int icon = 0;

        switch (nivel){
            case 1:
                icon = R.drawable.nivel1;
                break;
            case 2:
                icon = R.drawable.nivel2;
                break;
            case 3:
                icon = R.drawable.nivel3;
                break;
            default:
                icon = R.drawable.nivel2;
                break;
        }

        Alerta alerta = new Alerta(alertaId, messageTitle, messageBody, dateTime, icon);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        Set<String> alertas = new HashSet<String>();
        alertas = prefs.getStringSet("alertas", null);

        if(alertas == null) {
            alertas = new HashSet<String>();
        }

        alertas.add(alerta.getPreferencesString());

        SharedPreferences.Editor editor = prefs.edit();
        editor.remove("alertas");
        editor.commit();
        editor.putStringSet("alertas", alertas);
        editor.commit();


        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        //Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.push);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.push)
                .setColor(Color.BLACK)
                .setContentTitle(alerta.getTitleAlerta())
                .setContentText(alerta.getTxtAlerta())
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());
    }

    private void solveNotification(int alertaId, String messageTitle) {

        Intent intent = new Intent(this, DisplayAlertas.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        Log.d(TAG, "RESOLVENDO");

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        Set<String> alertas = new HashSet<String>();
        Set<String> new_alertas = new HashSet<String>();
        alertas = prefs.getStringSet("alertas", null);

        Alerta al = null;

        if(alertas == null) {
            return;
        }
        else{

            for (String st : alertas){

                al = Alerta.interpretPreferencesString(st);
                if (al.getId() == alertaId)
                    continue;

                new_alertas.add(st);

            }

        }

        SharedPreferences.Editor editor = prefs.edit();
        editor.remove("alertas");
        editor.commit();

        String notification_text = "";

        if(!new_alertas.isEmpty()) {
            editor.putStringSet("alertas", new_alertas);
            editor.commit();
            notification_text = notification_text + "Mas ainda existem alertas ativos, verifique!";
        }
        else{
            notification_text = notification_text + "Você está seguro agora!";
        }


        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        //Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.push);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.push)
                .setColor(Color.BLACK)
                .setContentTitle("RESOLVIDO: " + messageTitle)
                .setContentText(notification_text)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());
    }

}
