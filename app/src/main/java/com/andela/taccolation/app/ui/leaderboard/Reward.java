package com.andela.taccolation.app.ui.leaderboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.andela.taccolation.app.utils.Constants;
import com.andela.taccolation.databinding.FragmentRewardBinding;
import com.andela.taccolation.presentation.viewmodel.ProfileViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class Reward extends Fragment {

    private static final String TAG = Constants.LOG.getConstant();
    private FragmentRewardBinding mBinding;
    private ProfileViewModel mProfileViewModel;
    private int mRating = 50;

    public Reward() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProfileViewModel = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = FragmentRewardBinding.inflate(inflater);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mProfileViewModel.getStudent().observe(getViewLifecycleOwner(), student -> mBinding.setStudent(student));
        if (getArguments() != null) mRating = RewardArgs.fromBundle(getArguments()).getRating();
        mBinding.rewardsButton.setOnClickListener(v -> mBinding.setRating(50));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }
}