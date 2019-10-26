package com.senai.alertar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by De Pretto on 06/08/2017.
 */

public class AdapterAlerta extends ArrayAdapter<String> {
    private final Context context;
    private final String[] values;

    public AdapterAlerta(Context context, String[] values) {
        super(context, R.layout.row_layout, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        String currdata = values[position];

        if(currdata.equals(DisplayAlertas.EMPTY_NOTIFICATIONS)){

            View rowView = inflater.inflate(R.layout.row_layout, parent, false);
            TextView txtItem = (TextView) rowView.findViewById(R.id.txtItem);
            TextView titleItem = (TextView) rowView.findViewById(R.id.tituloItem);
            ImageView imageView = (ImageView) rowView.findViewById(R.id.imgItem);
            txtItem.setText("Você está seguro!");
            titleItem.setText("Sem notificações!");
            imageView.setImageResource(R.drawable.push3);

            return rowView;


        }
        Alerta alerta = Alerta.interpretPreferencesString(currdata);

        String displaytxt = alerta.getTxtAlerta() + "\nRecebido em: " + alerta.getTxtData();

        View rowView = inflater.inflate(R.layout.row_layout, parent, false);
        TextView txtItem = (TextView) rowView.findViewById(R.id.txtItem);
        TextView titleItem = (TextView) rowView.findViewById(R.id.tituloItem);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.imgItem);
        txtItem.setText(displaytxt);
        titleItem.setText(alerta.getTitleAlerta());
        imageView.setImageResource(alerta.getIcon());

        return rowView;
    }
}
