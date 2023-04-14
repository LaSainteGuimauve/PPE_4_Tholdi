package com.gsb.androfrais.classesMetier;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class Incident {
    private String Descriptif;
    private String DateIncident;
    private String NumeroContainer;
    private String IdIncident;

    public String getIdIncident(){return IdIncident;}

    public String getDateIncident() {return DateIncident;}

    public String getNumeroContainer() {return NumeroContainer;}


    public String getDescriptif() {return Descriptif;}

    public Incident(JSONObject jsonObject) {
        /* NOM WEB SERVICE*/
        Descriptif = jsonObject.optString("Descriptif");
        DateIncident = jsonObject.optString("Dateincident");
        NumeroContainer = jsonObject.optString("Numerocontainer");
        IdIncident = jsonObject.optString("Idincident");
    }

    public static ArrayList<Incident> fromJson(JSONArray jsonArray){
        ArrayList<Incident> collectionReservation = new ArrayList<Incident>();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                collectionReservation.add(
                        new Incident(jsonArray.getJSONObject(i))

                );
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return collectionReservation;

    }

}
