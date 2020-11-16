package com.andela.taccolation.app.ui.teachernotes;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.andela.taccolation.R;
import com.andela.taccolation.app.utils.Constants;
import com.andela.taccolation.app.utils.OnViewClickListener;
import com.andela.taccolation.app.utils.TaskStatus;
import com.andela.taccolation.databinding.DialogTeacherCourseListBinding;
import com.andela.taccolation.databinding.EnterTextBinding;
import com.andela.taccolation.databinding.FragmentTeacherNotesBinding;
import com.andela.taccolation.local.entities.Notes;
import com.andela.taccolation.presentation.model.Teacher;
import com.andela.taccolation.presentation.viewmodel.ProfileViewModel;
import com.andela.taccolation.presentation.viewmodel.TeacherNotesViewModel;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class TeacherNotes extends Fragment implements OnViewClickListener<Notes, View> {

    private static final int FIND_FILE_REQUEST_CODE = 70;
    private static final int CREATE_FILE_REQUEST_CODE = 80;
    private static final String TAG = Constants.LOG.getConstant();
    private FragmentTeacherNotesBinding mBinding;
    private AlertDialog mAlertDialog;
    private ProfileViewModel mProfileViewModel;
    private TeacherNotesViewModel mTeacherNotesViewModel;
    private Teacher mTeacher;
    private String mSelectedCourseCode = null;
    private NotesAdapter mNotesAdapter;

    public TeacherNotes() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProfileViewModel = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);
        mTeacherNotesViewModel = new ViewModelProvider(requireActivity()).get(TeacherNotesViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = FragmentTeacherNotesBinding.inflate(inflater);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mProfileViewModel.getTeacher().observe(getViewLifecycleOwner(), teacher -> mTeacher = teacher);
        initRecyclerView();
        mBinding.uploadButton.setOnClickListener(v -> displaySelectCourseDialog());
        mBinding.newDocButton.setOnClickListener(v -> createNewDocumentDialog(false));
        mBinding.linkButton.setOnClickListener(v -> createNewDocumentDialog(true));
        mTeacherNotesViewModel.getAllNotes().observe(getViewLifecycleOwner(), notes -> {
            mNotesAdapter.submitList(notes);
            if (notes.isEmpty()) mBinding.recyclerView.setVisibility(View.GONE);
            else mBinding.recyclerView.setVisibility(View.VISIBLE);
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri fileUri;
        Bitmap bitmap;
        List<String> pathList = new ArrayList<>();
        List<String> fileNames = new ArrayList<>();
        Notes[] notes;
        // Detects request codes
        if (requestCode == FIND_FILE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                ClipData clipData = data.getClipData();
                if (clipData == null) { // occurs when user selects only a file
                    notes = new Notes[1];
                    // notes[0] = new Notes(getQueryName(data.getData()), mSelectedCourseCode, Objects.requireNonNull(data.getData()).toString());
                    notes[0] = new Notes();
                    notes[0].setCourseCode(mSelectedCourseCode);
                    notes[0].setFilePath(Objects.requireNonNull(data.getData()).toString());
                    notes[0].setTitle(getQueryName(data.getData()));
                    pathList.add(Objects.requireNonNull(data.getData()).toString());
                    fileNames.add(getQueryName(data.getData()));
                } else { // occurs when user selects multiple files
                    notes = new Notes[clipData.getItemCount()];
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        ClipData.Item item = clipData.getItemAt(i);
                        Uri uri = item.getUri();
                        processUri(uri);
                        pathList.add(uri.toString());
                        fileNames.add(getQueryName(uri));
                        // notes[i] = new Notes(getQueryName(uri), mSelectedCourseCode, uri.toString());
                        notes[i] = new Notes();
                        notes[i].setCourseCode(mSelectedCourseCode);
                        notes[i].setFilePath(uri.toString());
                        notes[i].setTitle(getQueryName(uri));
                    }
                }
                if (!pathList.isEmpty() && !fileNames.isEmpty() && pathList.size() == fileNames.size()) { // mapping of fileNames to their respective Uri
                    mTeacherNotesViewModel.uploadTeacherFiles(mTeacher, pathList, fileNames, mSelectedCourseCode).observe(getViewLifecycleOwner(), this::processTaskStatus);
                    mTeacherNotesViewModel.insertAllNotes(notes);
                    mBinding.recyclerView.setVisibility(View.VISIBLE);
                }
            }
        } else if (requestCode == CREATE_FILE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                fileUri = data.getData();
                Log.i(TAG, "onActivityResult: URI: " + fileUri);
            }
        }
    }

    /*@Override
    public void onItemClick(TeacherFile teacherFile) {
        final List<TeacherFile> currentList = mAdapter.getCurrentList();
        for (int i = 0; i < currentList.size(); i++) {
            if (currentList.get(i).equals(teacherFile)) {
                mTeacherFiles.remove(i);
                mAdapter.notifyItemRemoved(i);
                break;
            }
        }
    }*/

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }

    private void initRecyclerView() {
        final int SPAN_COUNT = 1;
        RecyclerView recyclerView = mBinding.recyclerView;
        mNotesAdapter = new NotesAdapter(this);
        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), SPAN_COUNT));
        recyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(mNotesAdapter);
    }

    private void createNewDocumentDialog(boolean isLink) {
        mAlertDialog = new MaterialAlertDialogBuilder(requireContext(), R.style.Theme_MaterialComponents_DayNight_Dialog_Alert).create();
        EnterTextBinding binding = DataBindingUtil.inflate(LayoutInflater.from(requireContext()), R.layout.enter_text, null, false);
        if (!isLink) {
            binding.textTil.setHint("Document title");
            binding.dialogTitle.setText(R.string.create_new_document);
        } else {
            binding.textTil.setHint("Link");
            binding.dialogTitle.setText(R.string.create_link);
        }
        binding.proceedButton.setOnClickListener(v -> {
            if (!Objects.requireNonNull(binding.textTil.getEditText()).getText().toString().isEmpty() && !isLink)
                dispatchNewDocumentIntent(binding.textTil.getEditText().getText().toString());
            else if (!binding.textTil.getEditText().getText().toString().isEmpty() && isLink)
                uploadLink();
        });
        mAlertDialog.setView(binding.getRoot());
        mAlertDialog.show();
    }

    private void displaySelectCourseDialog() {
        mAlertDialog = new MaterialAlertDialogBuilder(requireContext(), R.style.Theme_MaterialComponents_DayNight_Dialog_Alert).create();
        DialogTeacherCourseListBinding binding = DataBindingUtil.inflate(LayoutInflater.from(requireContext()), R.layout.dialog_teacher_course_list, null, false);
        final RadioGroup radioGroup = binding.radioGroup;
        if (mTeacher != null) {
            final List<String> courseCodeList = mTeacher.getCourseCodeList();
            LayoutInflater layoutInflater = (LayoutInflater) requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            for (String courseCode : courseCodeList) {
                AppCompatRadioButton radioButton = (AppCompatRadioButton) layoutInflater.inflate(R.layout.item_radio_button, null);
                radioButton.setText(courseCode);
                radioGroup.addView(radioButton);
            }
        }
        binding.proceedButton.setOnClickListener(view -> {
            boolean isAnyButtonChecked = false;
            for (int i = 0; i < radioGroup.getChildCount(); i++) {
                if (((AppCompatRadioButton) radioGroup.getChildAt(i)).isChecked()) {
                    isAnyButtonChecked = true;
                    mSelectedCourseCode = ((AppCompatRadioButton) radioGroup.getChildAt(i)).getText().toString();
                    break;
                }

            }
            if (isAnyButtonChecked) {
                if (mAlertDialog != null)
                    mAlertDialog.dismiss();
                uploadFileFromStorage();
            } else sendSnackBar("You have to select a course to proceed");
        });
        mAlertDialog.setView(binding.getRoot());
        mAlertDialog.show();
    }

    private void dispatchNewDocumentIntent(String documentTitle) {
        Intent fileIntent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        fileIntent.addCategory(Intent.CATEGORY_OPENABLE);
        String[] mimeTypes = {"application/pdf", "application/doc", "image/*", "text/csv", "text/comma-separated-values"};
        fileIntent.setType("*/*");
        fileIntent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        fileIntent.putExtra(Intent.EXTRA_TITLE, documentTitle);
        startActivityForResult(fileIntent, CREATE_FILE_REQUEST_CODE);
    }

    private void uploadLink() {
        if (mAlertDialog != null)
            mAlertDialog.dismiss();
        sendSnackBar("Upload in progress...");
    }

    private void uploadFileFromStorage() {
        Intent fileIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        fileIntent.addCategory(Intent.CATEGORY_OPENABLE);
        String[] mimeTypes = {"application/pdf", "application/doc", "image/*", "application/vnd.openxmlformats-officedocument.wordprocessingml.document", "text/csv", "text/comma-separated-values"};
        fileIntent.setType("*/*");
        fileIntent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        fileIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(fileIntent, FIND_FILE_REQUEST_CODE);
    }

    private void processTaskStatus(TaskStatus status) {
        switch (status) {
            case SUCCESS:
                sendSnackBar("File uploaded successfully");
                break;
            case FAILED:
                sendSnackBar("File upload failed");
                break;
        }
    }

    private void sendSnackBar(String message) {
        Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show();
    }

    private void processUri(Uri returnUri) {
        Cursor returnCursor = requireActivity().getContentResolver().query(returnUri, null, null, null, null);
        /*
         * Get the column indexes of the data in the Cursor,
         * move to the first row in the Cursor, get the data,
         * and display it.
         */
        if (returnCursor != null) {
            int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
            int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
            returnCursor.moveToFirst();
            Log.i(TAG, "processUri: Uri: " + returnUri.toString());
            Log.i(TAG, "processUri: " + returnCursor.getString(nameIndex));
            Log.i(TAG, "processUri: " + returnCursor.getLong(sizeIndex));
            returnCursor.close();
        }
    }

    private String getQueryName(Uri uri) {
        Cursor returnCursor = requireActivity().getContentResolver().query(uri, null, null, null, null);
        String name = null;
        if (returnCursor != null) {
            int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
            returnCursor.moveToFirst();
            name = returnCursor.getString(nameIndex);
            returnCursor.close();
        }
        return name;
    }


    @Override
    public void onViewClicked(Notes note, View view) {
        int id = view.getId();
        if (id == R.id.share_button)
            shareNote(note);
        else if (id == R.id.delete_button)
            deleteNote(note);
        else if (id == R.id.open_button)
            openNote(note);
    }

    private void deleteNote(Notes note) {
        new MaterialAlertDialogBuilder(requireContext(), R.style.Theme_MaterialComponents_DayNight_Dialog_Alert)
                .setMessage("Are you sure you want to delete\n" + "'" + note.getTitle() + "' ?")
                .setTitle("Confirm Delete")
                .setIcon(android.R.drawable.ic_delete)
                .setPositiveButton("YES", (dialogInterface, i) -> mTeacherNotesViewModel.deleteNote(note))
                .setNegativeButton("NO", (dialogInterface, i) -> dialogInterface.dismiss()).show();
    }

    @SuppressLint("QueryPermissionsNeeded")
    private void shareNote(Notes note) {
        // Get URI and MIME type of file
        Uri uri = Uri.parse(note.getFilePath());
        String mime = requireActivity().getContentResolver().getType(uri);

        Intent intent = new Intent();
        intent.setType(mime);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.setAction(Intent.ACTION_SEND);
        Log.i(TAG, "shareNote: File Path: " + note.getFilePath());
        // Verify that the intent will resolve to an activity
        if (intent.resolveActivity(requireActivity().getPackageManager()) != null)
            startActivity(intent);
        else sendSnackBar(getString(R.string.no_app_found_intent));
    }

    @SuppressLint("QueryPermissionsNeeded")
    private void openNote(Notes note) {
        // Get URI and MIME type of file
        Uri uri = Uri.parse(note.getFilePath());
        String mime = requireActivity().getContentResolver().getType(uri);

        // Open file with user selected app
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, mime);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        if (intent.resolveActivity(requireActivity().getPackageManager()) != null)
            startActivity(intent);
        else sendSnackBar(getString(R.string.no_app_found_intent));
    }
}