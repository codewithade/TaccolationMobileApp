package com.andela.taccolation.app.ui.studentprofile;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.andela.taccolation.R;
import com.andela.taccolation.app.utils.Constants;
import com.andela.taccolation.app.utils.TaskStatus;
import com.andela.taccolation.databinding.FragmentAddStudentBinding;
import com.andela.taccolation.presentation.model.Student;
import com.andela.taccolation.presentation.model.StudentStatistics;
import com.andela.taccolation.presentation.model.Teacher;
import com.andela.taccolation.presentation.viewmodel.ProfileViewModel;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AddStudent extends Fragment {

    private static final String TAG = Constants.LOG.getConstant();
    private final List<String> mCourseCodeList = new ArrayList<>();
    private final ChipGroup.OnClickListener mChangeListener = view -> {
        Chip chip = (Chip) view;
        final String courseCode = chip.getText().toString();
        if (chip.isChecked()) mCourseCodeList.add(courseCode);
        else mCourseCodeList.remove(courseCode);
        Log.i(TAG, "Course code content: " + mCourseCodeList.toString());
    };
    private FragmentAddStudentBinding mBinding;
    private ProfileViewModel mProfileViewModel;
    private TextInputLayout mFirstName, mLastName;
    private final TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            mFirstName.setError("");
            mLastName.setError("");
        }
    };
    private Spinner mSpinner;
    private boolean isEmpty = true;
    // watches for empty Text Input fields
    private final TextInputLayout.OnEditTextAttachedListener mTextAttachedListener = inputLayout -> {
        if (Objects.requireNonNull(inputLayout.getEditText()).getText().toString().isEmpty()) {
            inputLayout.setError(getResources().getString(R.string.empty_input_text_error));
            isEmpty = true;
        } else {
            inputLayout.setError("");
            isEmpty = false;
        }
    };

    public AddStudent() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProfileViewModel = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = FragmentAddStudentBinding.inflate(inflater);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindViews();
        addTextWatcher();
        setupToolbar();
        mProfileViewModel.getTeacher().observe(getViewLifecycleOwner(), this::createCourseCodeChips);
        mBinding.addStudentButton.setOnClickListener(v -> {
            addTextChangeListener();
            if (!isEmpty) addStudent();
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }

    private void addStudent() {
        mBinding.addStudentButton.setEnabled(false);
        String firstName = Objects.requireNonNull(mFirstName.getEditText()).getText().toString();
        String lastName = Objects.requireNonNull(mLastName.getEditText()).getText().toString();
        String sex = (String) mSpinner.getSelectedItem();
        // creates a a stats map for each course initialized to zero
        Map<String, StudentStatistics> statisticsMap = new HashMap<>();
        for (String courseCode : mCourseCodeList)
            statisticsMap.put(courseCode, new StudentStatistics());
        // TODO: 29/10/2020 imageUrl: upload from device storage or from camera
        mBinding.addStudentButton.setEnabled(false);
        Student student = new Student(firstName, lastName, sex, mCourseCodeList, "http://www.image.com.ng", "", statisticsMap);
        mProfileViewModel.addStudent(student).observe(getViewLifecycleOwner(), this::processTaskStatus);
    }

    private void processTaskStatus(TaskStatus status) {
        switch (status) {
            case SUCCESS:
                resetViews();
                sendSnackbar(getString(R.string.student_add_task_success));
                break;
            case FAILED:
                resetViews();
                sendSnackbar(getString(R.string.student_add_task_failed));
                break;
        }
    }

    private void sendSnackbar(String message) {
        Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show();
    }

    private void createCourseCodeChips(Teacher teacher) {
        if (teacher != null) {
            final List<String> courseCodeList = teacher.getCourseCodeList();
            LayoutInflater layoutInflater = (LayoutInflater) requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            for (String courseCode : courseCodeList) {
                Chip chip = (Chip) layoutInflater.inflate(R.layout.item_chip, null);
                chip.setText(courseCode);
                chip.setOnClickListener(mChangeListener);
                mBinding.chipGroup.addView(chip);
            }
        }
    }

    private void bindViews() {
        mSpinner = mBinding.sexSpinner;
        mFirstName = mBinding.firstNameTil;
        mLastName = mBinding.lastNameTil;
    }

    private void addTextWatcher() {
        Objects.requireNonNull(mFirstName.getEditText()).addTextChangedListener(mTextWatcher);
        Objects.requireNonNull(mLastName.getEditText()).addTextChangedListener(mTextWatcher);
    }

    private void addTextChangeListener() {
        mFirstName.addOnEditTextAttachedListener(mTextAttachedListener);
        mLastName.addOnEditTextAttachedListener(mTextAttachedListener);
    }

    private void setupToolbar() {
        final Toolbar toolbar = mBinding.addToolbar;
        /*
        NavController navController = Navigation.findNavController(view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration);*/
        toolbar.setNavigationOnClickListener(v -> NavHostFragment.findNavController(this).popBackStack(R.id.dashboardFragment, false));
    }

    private void resetViews() {
        Objects.requireNonNull(mFirstName.getEditText()).setText("");
        Objects.requireNonNull(mLastName.getEditText()).setText("");
        final ChipGroup chipGroup = mBinding.chipGroup;
        for (int i = 0; i < chipGroup.getChildCount(); i++) {
            final Chip chip = (Chip) chipGroup.getChildAt(i);
            if (chip.isChecked()) chip.setChecked(false);
        }
        mBinding.addStudentButton.setEnabled(true);
    }
}