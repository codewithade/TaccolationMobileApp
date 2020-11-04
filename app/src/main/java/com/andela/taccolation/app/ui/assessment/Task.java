package com.andela.taccolation.app.ui.assessment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.andela.taccolation.R;
import com.andela.taccolation.app.utils.DataHelper;
import com.andela.taccolation.app.utils.OnItemClickListener;
import com.andela.taccolation.databinding.FragmentTaskBinding;
import com.andela.taccolation.presentation.model.TaskItem;
import com.google.android.material.snackbar.Snackbar;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class Task extends Fragment implements OnItemClickListener<TaskItem> {

    private FragmentTaskBinding mBinding;

    public Task() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = FragmentTaskBinding.inflate(inflater);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecyclerView();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }

    @Override
    public void onItemClick(TaskItem taskItem) {
        Snackbar.make(requireView(), taskItem.getTitle(), Snackbar.LENGTH_SHORT).show();
    }

    private void initRecyclerView() {
        final RecyclerView recyclerView = mBinding.recyclerView;
        final TaskAdapter adapter = new TaskAdapter(this, R.layout.item_task);
        adapter.submitList(DataHelper.getTaskItems());
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }
}