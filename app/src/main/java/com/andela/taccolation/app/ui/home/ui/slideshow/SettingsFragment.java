package com.andela.taccolation.app.ui.home.ui.slideshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.andela.taccolation.databinding.FragmentSettingsBinding;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class SettingsFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentSettingsBinding binding = FragmentSettingsBinding.inflate(inflater);
        return binding.getRoot();
    }
}