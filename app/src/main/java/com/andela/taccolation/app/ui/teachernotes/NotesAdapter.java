package com.andela.taccolation.app.ui.teachernotes;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.andela.taccolation.app.utils.OnItemClickListener;
import com.andela.taccolation.databinding.ItemTeacherFileBinding;
import com.andela.taccolation.presentation.model.TeacherFile;

public class NotesAdapter extends ListAdapter<TeacherFile, NotesAdapter.NotesViewHolder> {

    private static final DiffUtil.ItemCallback<TeacherFile> DIFF_CALLBACK = new DiffUtil.ItemCallback<TeacherFile>() {
        @Override
        public boolean areItemsTheSame(@NonNull TeacherFile oldItem, @NonNull TeacherFile newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull TeacherFile oldItem, @NonNull TeacherFile newItem) {
            return oldItem.equals(newItem);
        }
    };
    private final OnItemClickListener<TeacherFile> mOnItemClickListener;

    protected NotesAdapter(OnItemClickListener<TeacherFile> listener) {
        super(DIFF_CALLBACK);
        mOnItemClickListener = listener;
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTeacherFileBinding fileBinding = ItemTeacherFileBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new NotesViewHolder(fileBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    class NotesViewHolder extends RecyclerView.ViewHolder {

        private final ItemTeacherFileBinding mItemTeacherFileBinding;

        public NotesViewHolder(ItemTeacherFileBinding teacherFileBinding) {
            super(teacherFileBinding.getRoot());
            mItemTeacherFileBinding = teacherFileBinding;
        }

        void bind(TeacherFile item) {
            mItemTeacherFileBinding.setItem(item);
            mItemTeacherFileBinding.setItemClickListener(mOnItemClickListener);
            mItemTeacherFileBinding.executePendingBindings();
        }
    }
}
