package com.gsb.androfrais;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gsb.androfrais.adapter.IncidentAdapter;
import com.gsb.androfrais.classesMetier.Incident;
import com.gsb.androfrais.databinding.FragmentConnexionBinding;
import com.gsb.androfrais.databinding.FragmentConsultationIncidentBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ConsultationIncidentFragment extends Fragment {

    private FragmentConsultationIncidentBinding binding;

    public ConsultationIncidentFragment() {

    }


    public static ConsultationIncidentFragment newInstance(String param1, String param2) {
        ConsultationIncidentFragment fragment = new ConsultationIncidentFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final ArrayList<Incident> collectionIncident = new ArrayList<Incident>();
        final IncidentAdapter adapter = new IncidentAdapter(getContext(), R.layout.incident, collectionIncident);
        adapter.notifyDataSetChanged();

        ListView lw = binding.ListViewConsultationIncident;
        lw.setAdapter(adapter);

        RequestQueue queue = Volley.newRequestQueue(getContext().getApplicationContext());
        StringRequest sr = new StringRequest(Request.Method.GET,
                "http://ws.portofmiami.us/api/incident",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Log.i("onCreateView",""+response);
                            collectionIncident.addAll(Incident.fromJson(jsonObject.getJSONArray("incident")));
                            adapter.addAll(collectionIncident);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String err = error.getMessage();
                Log.e("ConsulterIncidentFragment", err);
            }
        });
        queue.add(sr);

        binding.ListViewConsultationIncident.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String idIncident = ((Incident)adapterView.getItemAtPosition(i)).getIdIncident();
                supprimerIncident(idIncident);
                Log.i("Supprimer ou non", "");
                NavHostFragment.findNavController(ConsultationIncidentFragment.this)
                        .navigate(R.id.action_consultationIncidentFragment_self);

            }
        });



    }

    private void supprimerIncident(String idReservation){
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        StringRequest sr = new StringRequest( //début de l’initialisation
                Request.Method.DELETE,
                "http://ws-gsb.com/api/incident"+idReservation
                ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("fonction supprimerIncident", "reponse");
                        JSONObject deleted = null;
                        try {
                            deleted = new JSONObject(response);
                            boolean state = deleted.getBoolean("isDeleted");
                            Log.i("fonction authentification", "connecté"+deleted);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            String err = e.getMessage();
                            Log.e("fonction authentification ConnexionFragment", "JsonException" + err);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String err = error.getMessage();
                        Log.e("fonction supprimerIncident", err);
                    }
                }) ;

        queue.add(sr);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentConsultationIncidentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }



}