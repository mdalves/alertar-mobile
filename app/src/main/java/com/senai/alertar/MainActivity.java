package com.senai.alertar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Controle para criar o topic apenas uma vez
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (!prefs.getBoolean("firstTime", false)) {
            FirebaseMessaging.getInstance().subscribeToTopic("alertar");
            Log.d("FCM_TOPIC", "Subscribed to alertar topic");
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("firstTime", true);
            editor.commit();
        }
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onResume(){
        super.onResume();

        setContentView(R.layout.activity_main);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        if (prefs.getStringSet("alertas", null) != null) {
            TextView txtView = (TextView) findViewById(R.id.txtInicial);
            txtView.setText("Há notificações ativas!");
        }

        final Button button = (Button) findViewById(R.id.btnNotif);
        final Intent intent = new Intent(this, DisplayAlertas.class);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(intent);

            }
        });

    }

    @Override
    public void onStart(){
        super.onStart();

        setContentView(R.layout.activity_main);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        if (prefs.getStringSet("alertas", null) != null) {
            TextView txtView = (TextView) findViewById(R.id.txtInicial);
            txtView.setText("Há notificações ativas!");
        }

        final Button button = (Button) findViewById(R.id.btnNotif);
        final Intent intent = new Intent(this, DisplayAlertas.class);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(intent);

            }
        });

    }


}
