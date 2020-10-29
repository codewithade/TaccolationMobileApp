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
import androidx.viewpager2.widget.ViewPager2;

import com.andela.taccolation.R;
import com.andela.taccolation.app.utils.Constants;
import com.andela.taccolation.databinding.FragmentStudentProfileBinding;
import com.andela.taccolation.presentation.model.Student;
import com.andela.taccolation.presentation.viewmodel.ProfileViewModel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.List;
import java.util.Map;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class StudentProfile extends Fragment {

    private static final String TAG = Constants.LOG.getConstant();
    private FragmentStudentProfileBinding mBinding;
    private ProfileViewModel mProfileViewModel;

    public StudentProfile() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProfileViewModel = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = FragmentStudentProfileBinding.inflate(inflater);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupToolbar();
        mProfileViewModel.getStudentListPerCourse().observe(getViewLifecycleOwner(), this::setUpTabLayout);
    }

    private void setUpTabLayout(Map<String, List<Student>> studentListPerCourse) {
        TabLayout tabLayout = mBinding.tabLayout;
        ViewPager2 viewPager = mBinding.viewPager;
        viewPager.setAdapter(new ViewPagerAdapter(this, studentListPerCourse));

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            String[] studentCourseCode = new String[studentListPerCourse.keySet().size()];
            final String[] courseCode = studentListPerCourse.keySet().toArray(studentCourseCode);
            tab.setText(courseCode[position]);

        }).attach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }

    private void setupToolbar() {
        final Toolbar toolbar = mBinding.profileToolbar;
        /*
        NavController navController = Navigation.findNavController(view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration);*/
        toolbar.setNavigationOnClickListener(v -> NavHostFragment.findNavController(this).popBackStack(R.id.dashboardFragment, false));
    }
}