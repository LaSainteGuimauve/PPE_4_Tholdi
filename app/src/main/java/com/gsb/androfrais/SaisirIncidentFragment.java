package com.gsb.androfrais;

import android.os.Bundle;

        import androidx.annotation.NonNull;
        import androidx.annotation.Nullable;
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
        import com.gsb.androfrais.databinding.FragmentConnexionBinding;
        import com.gsb.androfrais.databinding.FragmentSaisirIncidentBinding;

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
public class SaisirIncidentFragment extends Fragment {

    private FragmentSaisirIncidentBinding binding;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SaisirIncidentFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static SaisirIncidentFragment newInstance(String param1, String param2) {
        SaisirIncidentFragment fragment = new SaisirIncidentFragment();
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
        binding = FragmentSaisirIncidentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonEnvoyerIncident.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saisieIncident();
            }
        });
    }

    private void saisieIncident(){
        Log.i("fonction nvIncident", "onClick");

        final String numeroContainer = binding.editTextNumeroContainer.getText().toString();
        final String dateIncident = binding.editTextDateIncident.getText().toString();
        final String descriptif = binding.editTextDescriptifIncident.getText().toString();
        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest sr = new StringRequest(Request.Method.POST, "http://ws.portofmiami.us/api/incident",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("fonction saisie Incident","reponse");
                        JSONObject connected = null;
                        try {
                            connected = new JSONObject(response);
                            boolean state = connected.getBoolean("connected");
                            if (state) {
                                Log.i("fonction Saisie Incident","connecté");
                                NavHostFragment.findNavController(SaisirIncidentFragment.this).navigate(R.id.action_AccueilFragment_to_saisirIncidentFragment);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            String err = e.getMessage();
                            Log.e("fonction Saisie Incident", "JsonException" + err);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String err = error.getMessage();
                        Log.e("fonction Saisie Incident IncidentFragment", err);
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("numeroContainer", numeroContainer);
                params.put("dateIncident", dateIncident);
                params.put("descriptif", descriptif);
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