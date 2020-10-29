package com.andela.taccolation.app.ui.assessment;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.andela.taccolation.R;
import com.andela.taccolation.app.ui.studentprofile.base.DataBindingAdapter;
import com.andela.taccolation.app.utils.OnItemClickListener;
import com.andela.taccolation.presentation.model.TaskItem;

public class TaskAdapter extends DataBindingAdapter<TaskItem> {
    private static final DiffUtil.ItemCallback<TaskItem> DIFF_CALLBACK = new DiffUtil.ItemCallback<TaskItem>() {
        @Override
        public boolean areItemsTheSame(@NonNull TaskItem oldItem, @NonNull TaskItem newItem) {
            return oldItem.getPosition() == newItem.getPosition();
        }

        @Override
        public boolean areContentsTheSame(@NonNull TaskItem oldItem, @NonNull TaskItem newItem) {
            return oldItem.equals(newItem);
        }
    };

    protected TaskAdapter(OnItemClickListener<TaskItem> listener) {
        super(DIFF_CALLBACK, listener);
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.item_task;
    }
}
