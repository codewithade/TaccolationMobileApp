package com.andela.taccolation.app.ui.leaderboard;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.andela.taccolation.R;
import com.andela.taccolation.app.ui.studentprofile.StudentAdapter;
import com.andela.taccolation.app.utils.OnItemClickListener;
import com.andela.taccolation.databinding.FragmentLeaderBoardBinding;
import com.andela.taccolation.presentation.model.Student;
import com.andela.taccolation.presentation.viewmodel.ProfileViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LeaderBoard extends Fragment implements OnItemClickListener<Student> {

    private final List<Student> mStudentList = new ArrayList<>();
    private FragmentLeaderBoardBinding mBinding;
    private final TextWatcher mSearchTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (!charSequence.toString().isEmpty()) {
                List<Student> filteredList = new ArrayList<>();
                for (Student student : mStudentList)
                    if (student.getFirstName().toLowerCase().contains(charSequence.toString().toLowerCase()) || student.getLastName().toLowerCase().contains(charSequence.toString().toLowerCase()))
                        filteredList.add(student);
                if (filteredList.isEmpty()) { // No match found for search query
                    mBinding.group.setVisibility(View.INVISIBLE);
                    mBinding.noMatchTextView.setText(getString(R.string.search_no_match, charSequence));
                    mBinding.noMatchTextView.setVisibility(View.VISIBLE);
                } else { // match found
                    mBinding.group.setVisibility(View.VISIBLE);
                    mBinding.noMatchTextView.setVisibility(View.GONE);
                    initRecyclerView(filteredList);
                }
            } else {
                mBinding.group.setVisibility(View.VISIBLE);
                mBinding.noMatchTextView.setVisibility(View.GONE);
                initRecyclerView(mStudentList);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
    private ProfileViewModel mProfileViewModel;

    public LeaderBoard() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProfileViewModel = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = FragmentLeaderBoardBinding.inflate(inflater);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Objects.requireNonNull(mBinding.searchBar.getEditText()).addTextChangedListener(mSearchTextWatcher);
        mProfileViewModel.getStudentListPerCourse().observe(getViewLifecycleOwner(), this::setUpStudentList);
        initRecyclerView(mStudentList);
    }

    private void setUpStudentList(Map<String, List<Student>> studentListPerCourse) {
        if (studentListPerCourse != null && !studentListPerCourse.isEmpty()) {
            String[] studentCourseCode = new String[studentListPerCourse.keySet().size()];
            final String[] courseCode = studentListPerCourse.keySet().toArray(studentCourseCode);
            for (int i = 0; i < studentListPerCourse.size(); i++)
                for (Student student : Objects.requireNonNull(studentListPerCourse.get(courseCode[i])))
                    if (!mStudentList.contains(student)) mStudentList.add(student);
            Collections.sort(mStudentList, (s1, s2) -> Integer.compare(s1.getPoints(), s2.getPoints()));
            Collections.reverse(mStudentList);
            for (int i = 0; i < mStudentList.size(); i++) mStudentList.get(i).setRank(i + 1);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }

    @Override
    public void onItemClick(Student student) {

    }

    private void initRecyclerView(List<Student> students) {
        RecyclerView recyclerView = mBinding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        StudentAdapter studentAdapter = new StudentAdapter(this, R.layout.item_leaderboard);
        studentAdapter.submitList(students);
        recyclerView.setAdapter(studentAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
    }
}