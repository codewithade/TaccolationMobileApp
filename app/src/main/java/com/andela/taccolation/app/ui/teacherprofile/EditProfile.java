package com.andela.taccolation.app.ui.teacherprofile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.andela.taccolation.R;
import com.andela.taccolation.app.utils.Constants;
import com.andela.taccolation.app.utils.TaskStatus;
import com.andela.taccolation.databinding.FragmentEditProfileBinding;
import com.andela.taccolation.presentation.model.Course;
import com.andela.taccolation.presentation.model.Teacher;
import com.andela.taccolation.presentation.viewmodel.ProfileViewModel;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class EditProfile extends Fragment {

    private static final int GET_FROM_GALLERY = 50;
    private static final String TAG = Constants.LOG.getConstant();
    private final Set<String> mCourseCodeList = new HashSet<>();
    private final ChipGroup.OnClickListener mChangeListener = view -> {
        Chip chip = (Chip) view;
        final String courseCode = chip.getText().toString();
        if (chip.isChecked()) mCourseCodeList.add(courseCode);
        else mCourseCodeList.remove(courseCode);
        Log.i(TAG, "Course code content: " + mCourseCodeList.toString());
    };
    private FragmentEditProfileBinding mBinding;
    private ProfileViewModel mProfileViewModel;
    private Teacher mTeacher;
    private Bitmap mProfileImageBitmap;
    private Teacher mNewTeacherDetails;

    public EditProfile() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProfileViewModel = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentEditProfileBinding.inflate(inflater);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mProfileViewModel.getTeacher().observe(getViewLifecycleOwner(), teacher -> {
            if (teacher.getFirstName() != null) {
                mBinding.setTeacher(teacher);
                mTeacher = teacher;
                mCourseCodeList.addAll(teacher.getCourseCodeList());
                setDefaultData(mTeacher);
            }
        });
        mProfileViewModel.getCourses().observe(getViewLifecycleOwner(), this::extractCourses);
        mBinding.updateProfileButton.setOnClickListener(v -> updateTeacherRecord());
        mBinding.profileImage.setOnClickListener(v -> startActivityForResult(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Detects request codes
        if (requestCode == GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            try {
                mProfileImageBitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), selectedImage);
                // mProfileViewModel.saveProfileImage(mProfileImageBitmap, mBinding.getTeacher());
                mBinding.profileImage.setImageBitmap(mProfileImageBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }

    private void extractCourses(List<Course> courses) {
        List<String> courseCodeList = new ArrayList<>();
        if (!courses.isEmpty())
            for (Course course : courses)
                courseCodeList.add(course.getCode());
        createCourseCodeChips(courseCodeList);
    }

    private void updateTeacherRecord() {
        if (changesOccurred()) {
            if (mCourseCodeList.isEmpty()) sendSnackBar(getString(R.string.select_course_hint));
            else {
                disableViews();
                Log.i(TAG, "updateTeacherRecord: mCourseCodeList: " + mCourseCodeList.toString());
                String firstName = getData(mBinding.firstNameTil, mTeacher.getFirstName());
                String lastName = getData(mBinding.lastNameTil, mTeacher.getLastName());
                String designation = mBinding.designationSpinner.getSelectedItem().toString();
                String sex = mBinding.sexSpinner.getSelectedItem().toString();
                String id = mTeacher.getId();
                String email = mTeacher.getEmail();
                String imageUrl = mTeacher.getImageUrl();
                String phone = getData(mBinding.phoneTil, "");
                String address = mBinding.addressTv.getText().toString();
                String password = mTeacher.getPassword();

                mNewTeacherDetails = new Teacher(firstName, lastName, designation, new ArrayList<>(mCourseCodeList), id, email, imageUrl, phone, sex, address, password);
                mProfileViewModel.saveProfileImage(mProfileImageBitmap, mNewTeacherDetails).observe(getViewLifecycleOwner(), this::processTaskStatus);
            }

        } else sendSnackBar(getString(R.string.no_changes_made));
    }

    private String getData(TextInputLayout textInputLayout, String defaultData) {
        if (TextUtils.isEmpty(Objects.requireNonNull(textInputLayout.getEditText()).getText().toString()))
            return defaultData;
        else return textInputLayout.getEditText().getText().toString();
    }

    private boolean changesOccurred() {
        return !mTeacher.getFirstName().equalsIgnoreCase(Objects.requireNonNull(mBinding.firstNameTil.getEditText()).getText().toString())
                || !mTeacher.getLastName().equalsIgnoreCase(Objects.requireNonNull(mBinding.lastNameTil.getEditText()).getText().toString())
                || !mTeacher.getPhone().equalsIgnoreCase(Objects.requireNonNull(mBinding.phoneTil.getEditText()).getText().toString())
                || !mTeacher.getSex().equalsIgnoreCase(Objects.requireNonNull(mBinding.sexSpinner.getSelectedItem()).toString())
                || !mTeacher.getAddress().equalsIgnoreCase(Objects.requireNonNull(mBinding.addressTv.getText()).toString())
                || !mCourseCodeList.containsAll(mTeacher.getCourseCodeList()) || !(mTeacher.getCourseCodeList().size() == mCourseCodeList.size())
                || !mTeacher.getDesignation().equalsIgnoreCase(Objects.requireNonNull(mBinding.designationSpinner.getSelectedItem()).toString());
    }

    private void setDefaultData(Teacher teacher) {
        final String[] designation = getResources().getStringArray(R.array.designation);
        final String[] sex = getResources().getStringArray(R.array.sex);
        for (int i = 0; i < designation.length; i++)
            if (designation[i].equalsIgnoreCase(teacher.getDesignation())) {
                mBinding.designationSpinner.setSelection(i);
                break;
            }
        for (int i = 0; i < sex.length; i++)
            if (sex[i].equalsIgnoreCase(teacher.getSex())) {
                mBinding.sexSpinner.setSelection(i);
                break;
            }
    }

    // retrieves and creates chips for all courses taught by the institution
    private void createCourseCodeChips(List<String> courseCodeList) {
        if (!courseCodeList.isEmpty()) {
            // sets the current courses taught by the teacher to checked state
            final List<String> originalTeacherCourses = mTeacher.getCourseCodeList();
            LayoutInflater layoutInflater = (LayoutInflater) requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            for (String courseCode : courseCodeList) {
                Chip chip = (Chip) layoutInflater.inflate(R.layout.item_chip, null);
                chip.setText(courseCode);
                if (originalTeacherCourses.contains(courseCode)) chip.setChecked(true);
                chip.setOnClickListener(mChangeListener);
                mBinding.chipGroup.addView(chip);
            }
        }
    }

    private void processTaskStatus(TaskStatus status) {
        switch (status) {
            case SUCCESS:
                // update the student list per course if changes (courses where added or removed) were made.
                if (!mTeacher.getCourseCodeList().containsAll(mNewTeacherDetails.getCourseCodeList()) || !(mTeacher.getCourseCodeList().size() == mNewTeacherDetails.getCourseCodeList().size())) {
                    mProfileViewModel.getStudentList(mNewTeacherDetails.getCourseCodeList()).observe(getViewLifecycleOwner(), studentListPerCourse -> {
                        if (!studentListPerCourse.isEmpty()) {
                            enableViews();
                            mProfileViewModel.setStudentListPerCourse(studentListPerCourse);
                            sendSnackBar(getString(R.string.teacher_update_task_success));
                        }
                    });
                } else {
                    enableViews();
                    sendSnackBar(getString(R.string.teacher_update_task_success));
                }
                // update the Teacher details
                mProfileViewModel.setTeacher(mNewTeacherDetails);
                break;
            case FAILED:
                enableViews();
                sendSnackBar(getString(R.string.teacher_update_task_failed));
                break;
        }
    }

    private void sendSnackBar(String message) {
        Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show();
    }

    private void disableViews() {
        mBinding.updateProfileButton.setEnabled(false);
        mBinding.group.setVisibility(View.VISIBLE);
        mBinding.profileImage.setEnabled(false);
    }

    private void enableViews() {
        mBinding.updateProfileButton.setEnabled(true);
        mBinding.group.setVisibility(View.INVISIBLE);
        mBinding.profileImage.setEnabled(true);
    }
}