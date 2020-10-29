package com.andela.taccolation.app.ui.studentprofile;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.andela.taccolation.R;
import com.andela.taccolation.app.ui.studentprofile.base.DataBindingAdapter;
import com.andela.taccolation.app.utils.OnItemClickListener;
import com.andela.taccolation.presentation.model.Student;

public class StudentAdapter extends DataBindingAdapter<Student> {

    private static final DiffUtil.ItemCallback<Student> DIFF_UTIL = new DiffUtil.ItemCallback<Student>() {
        @Override
        public boolean areItemsTheSame(@NonNull Student oldItem, @NonNull Student newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Student oldItem, @NonNull Student newItem) {
            return oldItem.equals(newItem);
        }
    };

    public StudentAdapter(OnItemClickListener<Student> onItemClickListener) {
        super(DIFF_UTIL, onItemClickListener);
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.item_student_profile;
    }
}
