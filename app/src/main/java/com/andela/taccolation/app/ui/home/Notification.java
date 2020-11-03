package com.andela.taccolation.app.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.andela.taccolation.databinding.FragmentNotificationBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class Notification extends Fragment {

    private FragmentNotificationBinding mBinding;

    public Notification() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = FragmentNotificationBinding.inflate(inflater);
        return mBinding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }
}