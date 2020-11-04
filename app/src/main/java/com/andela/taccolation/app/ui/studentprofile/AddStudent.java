package com.andela.taccolation.app.ui.studentprofile;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.andela.taccolation.R;
import com.andela.taccolation.app.utils.Constants;
import com.andela.taccolation.app.utils.TaskStatus;
import com.andela.taccolation.databinding.FragmentAddStudentBinding;
import com.andela.taccolation.databinding.ImageOptionDialogBinding;
import com.andela.taccolation.presentation.model.Student;
import com.andela.taccolation.presentation.model.StudentStatistics;
import com.andela.taccolation.presentation.model.Teacher;
import com.andela.taccolation.presentation.viewmodel.ProfileViewModel;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AddStudent extends Fragment {

    private static final String TAG = Constants.LOG.getConstant();
    private static final int GET_FROM_GALLERY = 10;
    private static final int GET_FROM_CAMERA = 20;
    private final List<String> mCourseCodeList = new ArrayList<>();
    private FragmentAddStudentBinding mBinding;
    private ProfileViewModel mProfileViewModel;
    private TextInputLayout mFirstName, mLastName;
    private Spinner mSpinner;
    private boolean isEmpty = true;
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
    private final ChipGroup.OnClickListener mChangeListener = view -> {
        Chip chip = (Chip) view;
        final String courseCode = chip.getText().toString();
        if (chip.isChecked()) mCourseCodeList.add(courseCode);
        else mCourseCodeList.remove(courseCode);
        Log.i(TAG, "Course code content: " + mCourseCodeList.toString());
    };
    private final TextInputLayout.OnEditTextAttachedListener mTextAttachedListener = inputLayout -> {
        if (Objects.requireNonNull(inputLayout.getEditText()).getText().toString().isEmpty()) {
            inputLayout.setError(getResources().getString(R.string.empty_input_text_error));
            isEmpty = true;
        } else {
            inputLayout.setError("");
            isEmpty = false;
        }
    };
    // watches for empty Text Input fields


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

    private AlertDialog mAlertDialog;
    private String mCurrentPhotoPath;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }

    private Bitmap mPhotoBitmap;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindViews();
        addTextWatcher();
        mProfileViewModel.getTeacher().observe(getViewLifecycleOwner(), this::createCourseCodeChips);
        mBinding.addStudentButton.setOnClickListener(v -> {
            addTextChangeListener();
            if (!isEmpty) addStudent();
        });
        mBinding.profileImage.setOnClickListener(v -> {
            mAlertDialog = new MaterialAlertDialogBuilder(requireContext()).create();
            ImageOptionDialogBinding binding = DataBindingUtil.inflate(LayoutInflater.from(requireContext()), R.layout.image_option_dialog, null, false);
            binding.fromGalleryButton.setOnClickListener(vi -> getImageFromGallery());
            binding.fromCameraButton.setOnClickListener(vi -> getImageFromCamera());
            mAlertDialog.setView(binding.getRoot());
            mAlertDialog.show();
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri selectedImageUri;
        Bitmap bitmap;
        //Detects request codes
        if (requestCode == GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            selectedImageUri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), selectedImageUri);
                mPhotoBitmap = bitmap;
                mBinding.profileImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                sendSnackbar(getString(R.string.error_accessing_file));
                e.printStackTrace();
            }
        } else if (requestCode == GET_FROM_CAMERA && resultCode == Activity.RESULT_OK) {
            bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath);
            mBinding.profileImage.setImageBitmap(bitmap);
        }
    }

    private void getImageFromGallery() {
        startActivityForResult(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
        mAlertDialog.dismiss();
    }

    @SuppressLint("QueryPermissionsNeeded")
    private void getImageFromCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(requireActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                sendSnackbar(getString(R.string.error_creating_file));
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Log.i(TAG, "getImageFromCamera: photoFile: " + photoFile.getAbsolutePath());
                Uri photoURI = FileProvider.getUriForFile(requireContext(),
                        Constants.FILE_PROVIDER.getConstant(),
                        photoFile);
                Log.i(TAG, "getImageFromCamera: photoURI: " + photoURI);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, GET_FROM_CAMERA);
            }
        } else sendSnackbar(getString(R.string.no_camera_error));
        mAlertDialog.dismiss();
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        Log.i(TAG, "createImageFile: currentPhotoPath: " + mCurrentPhotoPath);
        return image;
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

    private void addStudent() {
        mBinding.addStudentButton.setEnabled(false);
        String firstName = Objects.requireNonNull(mFirstName.getEditText()).getText().toString();
        String lastName = Objects.requireNonNull(mLastName.getEditText()).getText().toString();
        String sex = (String) mSpinner.getSelectedItem();
        // creates a a stats map for each course initialized to zero
        Map<String, StudentStatistics> statisticsMap = new HashMap<>();
        for (String courseCode : mCourseCodeList)
            statisticsMap.put(courseCode, new StudentStatistics());
        mBinding.addStudentButton.setEnabled(false);
        Student student = new Student(firstName, lastName, sex, mCourseCodeList, "http://www.image.com.ng", "", 0, 0, statisticsMap);
        LiveData<TaskStatus> addStudent;
        if (mPhotoBitmap == null)
            addStudent = mProfileViewModel.addStudent(student, mCurrentPhotoPath, null);
        else addStudent = mProfileViewModel.addStudent(student, null, mPhotoBitmap);
        addStudent.observe(getViewLifecycleOwner(), this::processTaskStatus);
    }

    private void resetViews() {
        Objects.requireNonNull(mFirstName.getEditText()).setText("");
        Objects.requireNonNull(mLastName.getEditText()).setText("");
        final ChipGroup chipGroup = mBinding.chipGroup;
        for (int i = 0; i < chipGroup.getChildCount(); i++) {
            final Chip chip = (Chip) chipGroup.getChildAt(i);
            if (chip.isChecked()) chip.setChecked(false);
        }
        mBinding.profileImage.setImageResource(R.drawable.pro1);
        mBinding.addStudentButton.setEnabled(true);
    }

}