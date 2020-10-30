package com.andela.taccolation.app.ui.teachernotes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.andela.taccolation.R;
import com.andela.taccolation.databinding.FragmentLectureAidsBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LectureAids extends Fragment {

    private FragmentLectureAidsBinding mBinding;

    public LectureAids() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = FragmentLectureAidsBinding.inflate(inflater);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupToolbar();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }

    private void setupToolbar() {
        final Toolbar toolbar = mBinding.lectureAidsToolbar;
        /*
        NavController navController = Navigation.findNavController(view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration);*/
        toolbar.setNavigationOnClickListener(v -> NavHostFragment.findNavController(this).popBackStack(R.id.dashboardFragment, false));
    }
}