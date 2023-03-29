package com.gsb.androfrais.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.gsb.androfrais.R;
import com.gsb.androfrais.classesMetier.Reservation;

import java.util.ArrayList;
import java.util.Date;

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

        TextView dateStockage = (TextView) convertView.findViewById(R.id.textViewDateStockageLinearLayoutReservationXML);
        TextView nbrJoursStocker = (TextView) convertView.findViewById(R.id.textViewNbrJrStockageLinearLayoutReservationXML);
         TextView nbrContainer = (TextView) convertView.findViewById(R.id.textViewQuantiteContLinearLayoutReservationXML);

cd 

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
