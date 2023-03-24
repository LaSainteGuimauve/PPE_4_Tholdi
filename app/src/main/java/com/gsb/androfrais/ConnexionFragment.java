package com.gsb.androfrais;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gsb.androfrais.databinding.FragmentConnexionBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.util.HashMap;
import java.util.Map;

public class ConnexionFragment extends Fragment {

    private FragmentConnexionBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentConnexionBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    private void authentification() {

        Log.i("fonction authentification","onClick");
        CookieManager cookieManager = new CookieManager();
        CookieHandler.setDefault(cookieManager);
        final String login = binding.editTextIdentifiant.getText().toString();
        final String password = binding.editTextMdp.getText().toString();
        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest sr = new StringRequest( //début de l’initialisation
                Request.Method.POST,
                "http://ws.portofmiami.us/api/login",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("fonction authentification","reponse");
                        JSONObject connected = null;
                        try {
                            connected = new JSONObject(response);
                            boolean state = connected.getBoolean("connected");
                            if (state) {
                                Log.i("fonction authentification","connecté");
                                NavHostFragment.findNavController(ConnexionFragment.this).navigate(R.id.FragConnexion_to_Accueil);
                            }
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
                        Log.e("fonction authentification ConnexionFragment", err);
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("login", login);
                params.put("mdp", password);
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

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authentification();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}