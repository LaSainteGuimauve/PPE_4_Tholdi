package com.gsb.androfrais.classesMetier;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class Reservation {
    private String DateStockage;
    private String NbrJourStock;
    private String NbrContainer;
    private String IdReservation;

    public String getIdReservation(){return IdReservation;}

    public String getDateStockage() {return DateStockage;}

    public String getNbrJourStock() {return NbrJourStock;}


    public String getNbrContainer() {return NbrContainer;}

    public Reservation(JSONObject jsonObject) {
        /* NOM WEB SERVICE*/
        DateStockage = jsonObject.optString("Dateprevuestockage");
        NbrJourStock = jsonObject.optString("Nbjoursdestockageprevu");
        NbrContainer = jsonObject.optString("Quantite");
        IdReservation = jsonObject.optString("Id");
    }

    public static ArrayList<Reservation> fromJson(JSONArray jsonArray){
        ArrayList<Reservation> collectionReservation = new ArrayList<Reservation>();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                collectionReservation.add(
                        new Reservation(jsonArray.getJSONObject(i))

                );
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return collectionReservation;

    }

}
