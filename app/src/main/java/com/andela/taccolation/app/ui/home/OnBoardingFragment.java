package com.andela.taccolation.app.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.andela.taccolation.R;
import com.andela.taccolation.app.utils.Constants;

import dagger.hilt.android.AndroidEntryPoint;

// FIXME: 25/10/2020 change to onBoarding flow
@AndroidEntryPoint
public class OnBoardingFragment extends Fragment {

    private static final String TAG = Constants.LOG.getConstant();
    NavController mNavController;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: OnBoarding FRAGMENT CALLED");
        mNavController = NavHostFragment.findNavController(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: OnBoarding FRAGMENT");
        // checks if its first time app was ran, navigates to onBoarding page
        // its weird. onCreateView gets called but not onCreate when popBackStack is invoked by LoginFragment
        boolean isFirstRun = requireActivity().getPreferences(Context.MODE_PRIVATE).getBoolean(Constants.FIRST_RUN.getConstant(), true);
        if (!isFirstRun) mNavController.navigate(R.id.action_OnBoardingFragment_to_workerFragment);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_onboarding, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.launch_auth_screen).setOnClickListener(view1 -> {
            SharedPreferences.Editor editor = requireActivity().getPreferences(Context.MODE_PRIVATE).edit();
            editor.putBoolean(Constants.FIRST_RUN.getConstant(), false);
            editor.apply();
            //NavHostFragment.findNavController(OnBoardingFragment.this).popBackStack(R.id.action_OnBoardingFragment_to_workerFragment, false);
            mNavController.navigate(R.id.action_OnBoardingFragment_to_workerFragment);
        });
    }

}