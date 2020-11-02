package com.andela.taccolation.app.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import com.andela.taccolation.app.utils.DataHelper;
import com.andela.taccolation.app.utils.OnItemClickListener;
import com.andela.taccolation.databinding.FragmentDashboardBinding;
import com.andela.taccolation.presentation.model.DashboardItem;
import com.andela.taccolation.presentation.viewmodel.ProfileViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class DashboardFragment extends Fragment implements OnItemClickListener<DashboardItem> {

    private static final String TAG = Constants.LOG.getConstant();
    public static final int GET_FROM_GALLERY = 3;
    private FragmentDashboardBinding mBinding;
    // FIXME: 25/10/2020 This is temporary. Transfer to ProfileFragment
    private ProfileViewModel mProfileViewModel;

    public DashboardFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate: DASHBOARD FRAGMENT");
        mProfileViewModel = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: DASHBOARD FRAGMENT");
        mBinding = FragmentDashboardBinding.inflate(inflater);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mProfileViewModel.getIsDataDownloaded().observe(getViewLifecycleOwner(), isDataDownloaded -> {
            if (!isDataDownloaded)
                NavHostFragment.findNavController(this).navigate(R.id.action_dashboardFragment_to_workerFragment);
        });
        initRecyclerView();
        mProfileViewModel.getTeacher().observe(getViewLifecycleOwner(), teacher -> {
            if (teacher.getFirstName() != null) {
                Log.i(TAG, "Teacher: " + teacher.toString());
                mBinding.setTeacher(teacher);
            }
        });

        mBinding.teacherImageFab.setOnClickListener(v -> startActivityForResult(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Detects request codes
        if (requestCode == GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), selectedImage);
                mProfileViewModel.saveProfileImage(bitmap, mBinding.getTeacher());
                mBinding.profileImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }


    private void initRecyclerView() {
        final int SPAN_COUNT = requireActivity().getResources().getInteger(R.integer.layout_span_count);
        final RecyclerView recyclerView = mBinding.recyclerView;
        final DashboardAdapter adapter = new DashboardAdapter(this);
        final int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.item_dashboard_margin);
        recyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        adapter.submitList(DataHelper.getDashboardItems());
        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), SPAN_COUNT));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onItemClick(DashboardItem item) {
        switch (item.getDashboardMenu()) {
            case STUDENT_PROFILE:
                NavHostFragment.findNavController(this).navigate(DashboardFragmentDirections.actionDashboardFragmentToStudentProfile());
                break;
            case TEACHER_PROFILE:
                NavHostFragment.findNavController(this).navigate(R.id.action_dashboardFragment_to_teacherProfile);
                break;
            case TEACHER_NOTES:
                NavHostFragment.findNavController(this).navigate(R.id.action_dashboardFragment_to_teacherNotes);
                break;
            case REPORT_SHEET:
                NavHostFragment.findNavController(this).navigate(R.id.action_dashboardFragment_to_reportSheet);
                break;
            case ATTENDANCE:
                NavHostFragment.findNavController(this).navigate(R.id.action_dashboardFragment_to_attendance);
                break;
            case ADD_STUDENT:
                NavHostFragment.findNavController(this).navigate(R.id.action_dashboardFragment_to_addStudent);
                break;
            case TASKS:
                NavHostFragment.findNavController(this).navigate(R.id.action_dashboardFragment_to_task);
                break;
            case LECTURE_AIDS:
                NavHostFragment.findNavController(this).navigate(R.id.action_dashboardFragment_to_lectureAids);
                break;
            default:
                Snackbar.make(requireView(), getString(item.getItemTitle()) + " under construction", Snackbar.LENGTH_LONG).show();
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }
}