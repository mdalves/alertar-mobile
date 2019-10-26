package com.senai.alertar;

import java.util.StringTokenizer;
import java.util.regex.Pattern;

/**
 * Created by De Pretto on 06/08/2017.
 */

public class Alerta {

    private String txtAlerta;
    private String titleAlerta;
    private String txtData;
    private int id;
    private int icon;


    public Alerta(int id, String title, String texto, String data, int icon){

        titleAlerta = title;
        txtAlerta = texto;
        txtData = data;
        this.id = id;
        this.icon = icon;

    }

    public int getId() { return id; }

    public String getTxtAlerta() {
        return txtAlerta;
    }

    public String getTitleAlerta() {
        return titleAlerta;
    }

    public String getTxtData() {
        return txtData;
    }

    public int getIcon() { return icon;}


    public String getPreferencesString(){

        return String.valueOf(id) + "||" + titleAlerta + "||" + txtAlerta + "||" + txtData + "||" + String.valueOf(icon);

    }

    public static Alerta interpretPreferencesString(String alerta){

        StringTokenizer tokenizer = new StringTokenizer(alerta, "||");
        String[] parts = new String[5];
        parts[0] = tokenizer.nextToken();
        parts[1] = tokenizer.nextToken();
        parts[2] = tokenizer.nextToken();
        parts[3] = tokenizer.nextToken();
        parts[4] = tokenizer.nextToken();
        return new Alerta(Integer.parseInt(parts[0]), parts[1], parts[2], parts[3], Integer.parseInt(parts[4]));

    }

}
