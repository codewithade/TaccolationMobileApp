package com.andela.taccolation.app.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.andela.taccolation.R;
import com.andela.taccolation.app.utils.AuthenticationState;
import com.andela.taccolation.app.utils.Constants;
import com.andela.taccolation.presentation.viewmodel.AuthViewModel;
import com.andela.taccolation.presentation.viewmodel.ProfileViewModel;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class WorkerFragment extends Fragment {

    private static final String TAG = Constants.LOG.getConstant();
    private AuthViewModel mAuthViewModel;
    private ProfileViewModel mProfileViewModel;

    public WorkerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requireActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        // requireActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Log.d(TAG, "onCreate: WORKER FRAGMENT CALLED");
        processSharedPreferences();
        initViewModel();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: WORKER FRAGMENT");
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_worker, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        populateData();
    }

    private void processSharedPreferences() {
        // checks if its first time app was ran, navigates to onBoarding page
        boolean isFirstRun = requireActivity().getPreferences(Context.MODE_PRIVATE).getBoolean(Constants.FIRST_RUN.getConstant(), true);
        // TODO: 27/10/2020 Reset this when user or Teacher decides to log out
        boolean isUserAuthenticated = requireActivity().getPreferences(Context.MODE_PRIVATE).getBoolean(Constants.USER_AUTHENTICATED.getConstant(), false);

        if (isFirstRun)
            NavHostFragment.findNavController(this).navigate(R.id.action_workerFragment_to_OnBoardingFragment);
        else if (!isUserAuthenticated)
            NavHostFragment.findNavController(this).navigate(WorkerFragmentDirections.actionWorkerFragmentToLoginFragment(AuthenticationState.UNAUTHENTICATED));
    }

    private void initViewModel() {
        mAuthViewModel = new ViewModelProvider(requireActivity()).get(AuthViewModel.class);
        mProfileViewModel = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);
    }

    private void populateData() {
        mAuthViewModel.getTeacherDetails().observe(getViewLifecycleOwner(), teacher -> {
            if (teacher.getFirstName() != null) {
                Log.i(TAG, "processAuthState: Teacher: " + teacher.toString());
                final List<String> courseCodeList = teacher.getCourseCodeList();
                mProfileViewModel.getStudentList(courseCodeList).observe(getViewLifecycleOwner(), studentListPerCourse -> {
                    if (!studentListPerCourse.isEmpty()) {
                        Log.i(TAG, "processAuthState: studentListPerCourse: " + studentListPerCourse.toString());
                        mProfileViewModel.setStudentListPerCourse(studentListPerCourse);
                        mProfileViewModel.setTeacher(teacher);
                        NavHostFragment.findNavController(this).navigate(R.id.action_workerFragment_to_dashboardFragment);
                    }
                });
            }
        });
    }

    @Override
    public void onStop() {
        Log.i(TAG, "onStop: ");
        super.onStop();
        requireActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    /*private void processAuthState(AuthenticationState authenticationState) {
        switch (authenticationState) {
            case UNAUTHENTICATED:
            case EMAIL_UNCONFIRMED:
                // navigate user to login screen
                mNavController.navigate(WorkerFragmentDirections.actionWorkerFragmentToLoginFragment(authenticationState));
                break;
            case AUTHENTICATED:
                // welcome the teacher to the dashboard
                mAuthViewModel.getTeacherDetails().observe(getViewLifecycleOwner(), teacher -> {
                    if (teacher.getFirstName() != null) {
                        Log.i(TAG, "processAuthState: Teacher: " + teacher.toString());
                        final List<String> courseCodeList = teacher.getCourseCodeList();
                        mProfileViewModel.getStudentList(courseCodeList).observe(getViewLifecycleOwner(), studentListPerCourse -> {
                            if (!studentListPerCourse.isEmpty()) {
                                Log.i(TAG, "processAuthState: studentListPerCourse: " + studentListPerCourse.toString());
                                mProfileViewModel.setStudentListPerCourse(studentListPerCourse);
                                mProfileViewModel.setTeacher(teacher);
                                mNavController.navigate(R.id.action_workerFragment_to_dashboardFragment);
                            }
                        });
                    }
                });
                break;
        }
    }*/
}