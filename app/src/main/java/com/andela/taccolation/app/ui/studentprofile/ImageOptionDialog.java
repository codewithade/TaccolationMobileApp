package com.andela.taccolation.app.ui.studentprofile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.andela.taccolation.databinding.ImageOptionDialogBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class ImageOptionDialog extends BottomSheetDialogFragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ImageOptionDialogBinding binding = ImageOptionDialogBinding.inflate(inflater);
        binding.fromGalleryButton.setOnClickListener(view -> {
            Toast.makeText(requireContext(), "Got clicked!", Toast.LENGTH_SHORT).show();
        });
        return binding.getRoot();
    }
}
