package com.gsb.androfrais;

import android.os.Bundle;

import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gsb.androfrais.databinding.FragmentSaisirReservationContainerBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.CookieManager;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SaisirReservationContainerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SaisirReservationContainerFragment extends Fragment {

    private FragmentSaisirReservationContainerBinding binding;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SaisirReservationContainerFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static SaisirReservationContainerFragment newInstance(String param1, String param2) {
        SaisirReservationContainerFragment fragment = new SaisirReservationContainerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_saisir_reservation_container, container, false);
    }

    private void saisieContainer(){
        Log.i("fonction nvContainer", "onClick");
        CookieManager cookieManager = new CookieManager();
        CookieManager.setDefault(cookieManager);
        final String nbrJourStockage = binding.editTextNbJoursStockage.getText().toString();
        final String nbrContainer = binding.editTextQuantiteContainer.getText().toString();
        final String typeContainer = binding.editTextTypedeContainer.getText().toString();
        final String dateStockage = binding.editTextDateStockagePrVue.getText().toString();
        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest sr = new StringRequest(Request.Method.POST, "http://ws.portofmiami.us/api/login",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("fonction saisie Reservation","reponse");
                        JSONObject connected = null;
                        try {
                            connected = new JSONObject(response);
                            boolean state = connected.getBoolean("connected");
                            if (state) {
                                Log.i("fonction Saisie Reservation","connecté");
                                NavHostFragment.findNavController(SaisirReservationContainerFragment.this).navigate(R.id.action_AccueilFragment_to_saisirReservationContainerFragment);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            String err = e.getMessage();
                            Log.e("fonction Saisie Réservation", "JsonException" + err);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String err = error.getMessage();
                        Log.e("fonction Saisie Réservation ResaFragment", err);
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Jour Réserver", nbrJourStockage);
                params.put("Nombre Container", nbrContainer);
                params.put("Date stockage", dateStockage);
                params.put("Type Container", typeContainer);
                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
        queue.add(sr);
    }

}