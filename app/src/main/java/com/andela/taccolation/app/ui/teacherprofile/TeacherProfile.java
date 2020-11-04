package com.andela.taccolation.app.ui.teacherprofile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.andela.taccolation.R;
import com.andela.taccolation.databinding.FragmentTeacherProfileBinding;
import com.andela.taccolation.presentation.model.Student;
import com.andela.taccolation.presentation.viewmodel.ProfileViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class TeacherProfile extends Fragment {

    private ProfileViewModel mProfileViewModel;
    private FragmentTeacherProfileBinding mBinding;

    public TeacherProfile() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProfileViewModel = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = FragmentTeacherProfileBinding.inflate(inflater);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBinding.editProfileFab.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_teacherProfile_to_editProfile));
        mProfileViewModel.getStudentListPerCourse().observe(getViewLifecycleOwner(), this::getStudentCount);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }

    private void getStudentCount(Map<String, List<Student>> studentListPerCourse) {
        if (!studentListPerCourse.isEmpty()) {
            List<Student> studentList = new ArrayList<>();
            String[] studentCourseCode = new String[studentListPerCourse.keySet().size()];
            final String[] courseCode = studentListPerCourse.keySet().toArray(studentCourseCode);
            for (String s : courseCode)
                for (Student student : Objects.requireNonNull(studentListPerCourse.get(s)))
                    if (!studentList.contains(student)) studentList.add(student);
            mBinding.setStudentCount(studentList.size());
        }
    }

}