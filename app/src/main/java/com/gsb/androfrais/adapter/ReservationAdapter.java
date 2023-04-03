package com.gsb.androfrais.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.fragment.NavHostFragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gsb.androfrais.ConnexionFragment;
import com.gsb.androfrais.ConsulationReservationFragment;
import com.gsb.androfrais.MainActivityFragment;
import com.gsb.androfrais.R;
import com.gsb.androfrais.SaisirReservationContainerFragment;
import com.gsb.androfrais.classesMetier.Reservation;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ReservationAdapter extends ArrayAdapter<Reservation> {

    public  ReservationAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Reservation> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Reservation reservation = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.reservation, parent, false);
        }
        if (position % 2 == 0) {
            convertView.setBackgroundColor(Color.GRAY);
        } else {
            convertView.setBackgroundColor(Color.WHITE);
        }

        ImageButton imgBtn = convertView.findViewById(R.id.imageButton);
        imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.i("idReservation",""+reservation.getIdReservation());

                Log.i("fonction authentification","onClick");
                CookieManager cookieManager = new CookieManager();
                CookieHandler.setDefault(cookieManager);
                //final String login = binding.editTextIdentifiant.getText().toString();
                //final String password = binding.editTextMdp.getText().toString();
                RequestQueue queue = Volley.newRequestQueue(getContext());
                StringRequest sr = new StringRequest( //début de l’initialisation
                        Request.Method.DELETE,
                        "http://ws.portofmiami.us/api/reservation/"+reservation.getIdReservation(),
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.i("fonction suppression Reservation","reponse");
                                JSONObject connected = null;
                                try {
                                    connected = new JSONObject(response);
                                    boolean state = connected.getBoolean("connected");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    String err = e.getMessage();
                                    Log.e("fonction suppression Réservation", "JsonException" + err);
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                String err = error.getMessage();
                                Log.e("fonction suppression Réservation", err);
                            }
                        });
                queue.add(sr);


                /*Fragment frg = null;
                MainActivityFragment mainActivityFragment = (MainActivityFragment)getContext();
                frg = mainActivityFragment.getSupportFragmentManager().findFragmentByTag("consultationReservation_TAG");
                final FragmentTransaction ft = mainActivityFragment.getSupportFragmentManager().beginTransaction();
                ft.detach(frg);
                ft.attach(frg);
                ft.commit();*/

                /*Fragment currentFragment = ((MainActivityFragment)getContext()).getSupportFragmentManager().findFragmentById(R.id.consulationReservationFragment);*/

                /*if (currentFragment instanceof ConsulationReservationFragment) {

                    try {
                        FragmentTransaction fragTransaction =   ((MainActivityFragment)getContext()).getSupportFragmentManager().beginTransaction();
                        fragTransaction.remove(currentFragment);
                        Fragment.SavedState savedState = ((MainActivityFragment)getContext()).getSupportFragmentManager().saveFragmentInstanceState(currentFragment);
                        Fragment newInstance = currentFragment.getClass().newInstance();
                        newInstance.setInitialSavedState(savedState);
                        fragTransaction.add(R.id.consulationReservationFragment,newInstance);
                        fragTransaction.commit();
                    }
                    catch (Exception e) // InstantiationException, IllegalAccessException
                    {
                        throw new RuntimeException("Cannot reinstantiate fragment " , e);
                    }

                }*/

            }
        });

        TextView dateStockage = (TextView) convertView.findViewById(R.id.textViewDateStockageLinearLayoutReservationXML);
        TextView nbrJoursStocker = (TextView) convertView.findViewById(R.id.textViewNbrJrStockageLinearLayoutReservationXML);
         TextView nbrContainer = (TextView) convertView.findViewById(R.id.textViewQuantiteContLinearLayoutReservationXML);



        nbrJoursStocker.setText(reservation.getNbrJourStock());
        dateStockage.setText(reservation.getDateStockage());
        nbrContainer.setText(reservation.getNbrContainer());



        /*String date = Integer.toString(ficheFrais.getNbJustificatifs());
        if (nbJustificatif == "NaN") {
            nbJustificatif = "0";
        }
        tvNbJustificatifs.setText(nbJustificatif);

        String montantValide = Double.toString(ficheFrais.getMontantValide());
        if (montantValide == "NaN") {
            montantValide = "0";
        }
        tvMontantValide.setText(montantValide);

        String dateModification;
        long timeStamp = ficheFrais.getDateModif();
        if (timeStamp == 0) {
            dateModification = "Pas de modifications";
        }else {
            Date date = new Date(timeStamp);
            dateModification = date.toString();
        }
        tvDateModif.setText(dateModification);*/

        return convertView;
    }


}
