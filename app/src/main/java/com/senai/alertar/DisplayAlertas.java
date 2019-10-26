package com.senai.alertar;

import android.app.ListActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;

import java.util.HashSet;
import java.util.Set;

public class DisplayAlertas extends ListActivity {

    public static final String EMPTY_NOTIFICATIONS = "empty_notifications";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_alertas);

        String[] values = null;

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        Set<String> alertas = new HashSet<String>();
        alertas = prefs.getStringSet("alertas", null);

        if (alertas != null) {
            values = new String[alertas.size()];
            int i = 0;
            for (String st : alertas) {
                values[i] = st;
                i++;
            }
        }
        else{
            values = new String[1];
            values[0] = EMPTY_NOTIFICATIONS;
        }

        AdapterAlerta adapter = new AdapterAlerta(this, values);
        setListAdapter(adapter);

    }

}
