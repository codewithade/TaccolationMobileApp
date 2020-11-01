package com.andela.taccolation.app.ui.studentprofile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.andela.taccolation.databinding.FragmentStudentDetailsBinding;
import com.andela.taccolation.presentation.viewmodel.ProfileViewModel;

import java.util.Objects;


public class StudentDetails extends Fragment {

    private FragmentStudentDetailsBinding mBinding;
    private ProfileViewModel mProfileViewModel;
    private String mCourseCode;
    private int mStudentRating = 0;

    public StudentDetails() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProfileViewModel = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);
        if (getArguments() != null)
            mCourseCode = StudentDetailsArgs.fromBundle(getArguments()).getCourseCode();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = FragmentStudentDetailsBinding.inflate(inflater);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupToolbar();
        mProfileViewModel.getStudent().observe(getViewLifecycleOwner(), student -> {
            mBinding.setStudent(student);
            if (student.getStudentDetailsMap() != null) {
                mBinding.setStatistics(student.getStudentDetailsMap().get(mCourseCode));
                mStudentRating = Objects.requireNonNull(student.getStudentDetailsMap().get(mCourseCode)).getRating();
            }
        });

        mBinding.rewardsButton.setOnClickListener(v -> NavHostFragment.findNavController(this).navigate(StudentDetailsDirections.actionStudentDetailsToReward().setRating(mStudentRating)));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }

    private void setupToolbar() {
        final Toolbar toolbar = mBinding.detailsToolbar;
        // calling popBackStack() navigates to the previous destination
        toolbar.setNavigationOnClickListener(v -> NavHostFragment.findNavController(this).popBackStack());
    }
}