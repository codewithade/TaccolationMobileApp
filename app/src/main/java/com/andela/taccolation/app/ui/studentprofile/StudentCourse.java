package com.andela.taccolation.app.ui.studentprofile;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.andela.taccolation.R;
import com.andela.taccolation.app.utils.Constants;
import com.andela.taccolation.app.utils.OnItemClickListener;
import com.andela.taccolation.databinding.FragmentStudentCourseBinding;
import com.andela.taccolation.presentation.model.Student;
import com.andela.taccolation.presentation.viewmodel.ProfileViewModel;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StudentCourse#newInstance} factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
public class StudentCourse extends Fragment implements OnItemClickListener<Student> {

    private static final String TAG = Constants.LOG.getConstant();
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TAB_POSITION = "com.andela.taccolation.app.ui.studentprofile.TAB_POSITION";
    private static List<Student> mStudentList = new ArrayList<>();
    private static List<String> mCourseCodeList = new ArrayList<>();
    private ProfileViewModel mProfileViewModel;
    private int position;
    private FragmentStudentCourseBinding mBinding;

    public StudentCourse() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param position Parameter 1.
     * @return A new instance of fragment StudentCourse.
     */
    public static StudentCourse newInstance(int position, List<String> courseCodeList, List<Student> students) {
        StudentCourse studentCourse = new StudentCourse();
        mStudentList = students;
        mCourseCodeList = courseCodeList;
        Bundle args = new Bundle();
        args.putInt(TAB_POSITION, position);
        studentCourse.setArguments(args);
        return studentCourse;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProfileViewModel = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);
        if (getArguments() != null) position = getArguments().getInt(TAB_POSITION);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentStudentCourseBinding.inflate(inflater);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecyclerView();
    }

    private void initRecyclerView() {
        final RecyclerView recyclerView = mBinding.recyclerView;
        final StudentAdapter adapter = new StudentAdapter(this, R.layout.item_student_profile);
        adapter.submitList(mStudentList);
        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 1));
        recyclerView.setAdapter(adapter);
        if (adapter.getCurrentList().isEmpty()) mBinding.noStudentTv.setVisibility(View.VISIBLE);
        else mBinding.noStudentTv.setVisibility(View.GONE);
    }

    @Override
    public void onItemClick(Student student) {
        Log.i(TAG, "onItemClick: TAB POSITION: " + position);
        Log.i(TAG, "onItemClick: SELECTED COURSE CODE: " + mCourseCodeList.get(position));
        mProfileViewModel.setStudent(student);
        NavHostFragment.findNavController(getParentFragment()).navigate(StudentProfileDirections.actionStudentProfileToStudentDetails(mCourseCodeList.get(position)));
        //NavHostFragment.findNavController(getParentFragment()).navigate(StudentCourseDirections.actionStudentCourseToStudentDetails(mCourseCodeList.get(position)));
        //Snackbar.make(requireView(), student.getFirstName(), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }
}