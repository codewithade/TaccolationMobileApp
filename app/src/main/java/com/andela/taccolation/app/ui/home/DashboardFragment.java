package com.andela.taccolation.app.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
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

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class DashboardFragment extends Fragment implements OnItemClickListener<DashboardItem> {

    private static final String TAG = Constants.LOG.getConstant();
    private FragmentDashboardBinding mBinding;
    // FIXME: 25/10/2020 This is temporary. Transfer to ProfileFragment
    private ProfileViewModel mProfileViewModel;
    private NavController mNavController;

    public DashboardFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNavController = NavHostFragment.findNavController(this);
        mProfileViewModel = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = FragmentDashboardBinding.inflate(inflater);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        closeAppOnBackPressed();
        initRecyclerView();
        mProfileViewModel.getTeacher().observe(getViewLifecycleOwner(), teacher -> {
            if (teacher.getFirstName() != null) {
                Log.i(TAG, "Teacher: " + teacher.toString());
                mBinding.setTeacher(teacher);
            }
        });
    }


    private void initRecyclerView() {
        final int SPAN_COUNT = requireActivity().getResources().getInteger(R.integer.layout_span_count);
        final RecyclerView recyclerView = mBinding.recyclerView;
        final DashboardAdapter adapter = new DashboardAdapter(this);
        adapter.submitList(DataHelper.getDashboardItems());
        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), SPAN_COUNT));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(DashboardItem item) {
        switch (item.getDashboardMenu()) {
            case STUDENT_PROFILE:
                mNavController.navigate(DashboardFragmentDirections.actionDashboardFragmentToStudentProfile());
                break;
            case TEACHER_PROFILE:
                mNavController.navigate(R.id.action_dashboardFragment_to_teacherProfile);
                break;
            case STUDENT_STATISTICS:
                break;
            case ADD_STUDENT:
                mNavController.navigate(R.id.action_dashboardFragment_to_addStudent);
                break;
            case TASKS:
                mNavController.navigate(R.id.action_dashboardFragment_to_task);
                break;
            default:
                Snackbar.make(requireView(), getString(item.getItemTitle()), Snackbar.LENGTH_LONG).show();
        }

    }

    private void closeAppOnBackPressed() {
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                requireActivity().finish();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }
}