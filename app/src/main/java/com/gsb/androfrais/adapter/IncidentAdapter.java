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
import com.gsb.androfrais.ConsultationIncidentFragment;
import com.gsb.androfrais.MainActivityFragment;
import com.gsb.androfrais.R;
import com.gsb.androfrais.SaisirIncidentFragment;
import com.gsb.androfrais.classesMetier.Incident;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class IncidentAdapter extends ArrayAdapter<Incident> {

    public IncidentAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Incident> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Incident incident = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.incident, parent, false);
        }
        if (position % 2 == 0) {
            convertView.setBackgroundColor(Color.GRAY);
        } else {
            convertView.setBackgroundColor(Color.WHITE);
        }



        TextView numeroContainer = (TextView) convertView.findViewById(R.id.textViewNumeroContainerIncidentXML);
        TextView dateIncident = (TextView) convertView.findViewById(R.id.textViewDateIncidentXML);
        TextView descriptif = (TextView) convertView.findViewById(R.id.textViewDescriptifIncidentXML);



        dateIncident.setText(incident.getDateIncident());
        numeroContainer.setText(incident.getNumeroContainer());
        descriptif.setText(incident.getDescriptif());

        return convertView;
    }


}