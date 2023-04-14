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
import com.gsb.androfrais.adapter.ReservationAdapter;
import com.gsb.androfrais.classesMetier.Reservation;
import com.gsb.androfrais.databinding.FragmentConnexionBinding;
import com.gsb.androfrais.databinding.FragmentConsulationReservationBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ConsulationReservationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConsulationReservationFragment extends Fragment {

    private FragmentConsulationReservationBinding binding;

    public ConsulationReservationFragment() {

    }


    public static ConsulationReservationFragment newInstance(String param1, String param2) {
        ConsulationReservationFragment fragment = new ConsulationReservationFragment();
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

        final ArrayList<Reservation> collectionReservation = new ArrayList<Reservation>();
        final ReservationAdapter adapter = new ReservationAdapter(getContext(), R.layout.reservation, collectionReservation);
        adapter.notifyDataSetChanged();

        ListView lw = binding.ListViewConsultation;
        lw.setAdapter(adapter);

        RequestQueue queue = Volley.newRequestQueue(getContext().getApplicationContext());
        StringRequest sr = new StringRequest(Request.Method.GET,
                "http://ws.portofmiami.us/api/reservation",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Log.i("onCreateView",""+response);
                            collectionReservation.addAll(Reservation.fromJson(jsonObject.getJSONArray("Reservations")));
                            adapter.addAll(collectionReservation);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String err = error.getMessage();
                Log.e("ConsulterNoteDeFraisFragment", err);
            }
        });
        queue.add(sr);

        binding.ListViewConsultation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String idResaContainer = ((Reservation)adapterView.getItemAtPosition(i)).getIdReservation();
                supprimerReservation(idResaContainer);
                Log.i("Supprimer ou non", "");
                NavHostFragment.findNavController(ConsulationReservationFragment.this)
                        .navigate(R.id.action_consulationReservationFragment_self);

            }
        });



    }

    private void supprimerReservation(String idReservation){
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        StringRequest sr = new StringRequest( //début de l’initialisation
                Request.Method.DELETE,
                "http://ws-gsb.com/api/reservation"+idReservation
                ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("fonction supprimerReservation", "reponse");
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
                        Log.e("fonction supprimerReservation", err);
                    }
                }) ;

        queue.add(sr);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentConsulationReservationBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }



}