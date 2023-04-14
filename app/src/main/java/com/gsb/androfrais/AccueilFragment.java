package com.gsb.androfrais;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.gsb.androfrais.databinding.FragmentAccueilBinding;

public class AccueilFragment extends Fragment {

    private FragmentAccueilBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentAccueilBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonAllerSaisie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(AccueilFragment.this)
                        .navigate(R.id.action_AccueilFragment_to_saisirReservationContainerFragment);
            }
        });

        binding.buttonAllerConsulterLesIncidents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(AccueilFragment.this)
                        .navigate(R.id.action_AccueilFragment_to_consultationIncidentFragment);
            }
        });

        binding.buttonAllerConsulterLesResa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(AccueilFragment.this)
                        .navigate((R.id.action_AccueilFragment_to_consulationReservationFragment));
            }
        });

        binding.buttonAllerSaisieIncident.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(AccueilFragment.this)
                        .navigate((R.id.action_AccueilFragment_to_saisirIncidentFragment));
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}