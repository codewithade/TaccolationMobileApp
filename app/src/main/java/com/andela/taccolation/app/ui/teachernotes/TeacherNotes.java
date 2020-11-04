package com.andela.taccolation.app.ui.teachernotes;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.andela.taccolation.R;
import com.andela.taccolation.app.utils.Constants;
import com.andela.taccolation.databinding.EnterTextBinding;
import com.andela.taccolation.databinding.FragmentTeacherNotesBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class TeacherNotes extends Fragment {

    private static final int FIND_FILE_REQUEST_CODE = 70;
    private static final int CREATE_FILE_REQUEST_CODE = 80;
    private static final String TAG = Constants.LOG.getConstant();
    private FragmentTeacherNotesBinding mBinding;
    private AlertDialog mAlertDialog;

    public TeacherNotes() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = FragmentTeacherNotesBinding.inflate(inflater);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBinding.uploadButton.setOnClickListener(v -> uploadFileFromStorage());
        mBinding.newDocButton.setOnClickListener(v -> createNewDocumentDialog(false));
        mBinding.linkButton.setOnClickListener(v -> createNewDocumentDialog(true));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri fileUri;
        Bitmap bitmap;
        StringBuilder path = new StringBuilder();
        //Detects request codes
        if (requestCode == FIND_FILE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            ClipData clipData = data.getClipData();
            if (clipData == null)
                path.append(data.getData().toString());
            else {
                for (int i = 0; i < clipData.getItemCount(); i++) {
                    ClipData.Item item = clipData.getItemAt(i);
                    Uri uri = item.getUri();
                    path.append(uri.toString()).append("\n");
                }
            }
            Log.i(TAG, "onActivityResult: URI: " + path.toString());
        } else if (requestCode == CREATE_FILE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            fileUri = data.getData();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }

    private void createNewDocumentDialog(boolean isLink) {
        mAlertDialog = new MaterialAlertDialogBuilder(requireContext()).create();
        EnterTextBinding binding = DataBindingUtil.inflate(LayoutInflater.from(requireContext()), R.layout.enter_text, null, false);
        if (!isLink) {
            binding.textTil.setHint("Document title");
            binding.dialogTitle.setText(R.string.create_new_document);
        } else {
            binding.textTil.setHint("Link");
            binding.dialogTitle.setText(R.string.create_link);
        }
        binding.proceedButton.setOnClickListener(v -> {
            if (!binding.textTil.getEditText().getText().toString().isEmpty() && !isLink)
                dispatchNewDocumentIntent(binding.textTil.getEditText().getText().toString());
            else if (!binding.textTil.getEditText().getText().toString().isEmpty() && isLink)
                uploadLink();
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
        sendSnackbar("Upload in progress...");
    }

    private void uploadFileFromStorage() {
        Intent fileIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        fileIntent.addCategory(Intent.CATEGORY_OPENABLE);
        String[] mimeTypes = {"application/pdf", "application/doc", "image/*", "text/csv", "text/comma-separated-values"};
        fileIntent.setType("*/*");
        fileIntent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        fileIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(fileIntent, FIND_FILE_REQUEST_CODE);
    }

    private void sendSnackbar(String message) {
        Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show();
    }
}