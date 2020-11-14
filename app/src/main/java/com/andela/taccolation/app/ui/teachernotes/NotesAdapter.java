package com.andela.taccolation.app.ui.teachernotes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.andela.taccolation.app.utils.OnViewClickListener;
import com.andela.taccolation.databinding.ItemTeacherFileBinding;
import com.andela.taccolation.local.entities.Notes;

public class NotesAdapter extends ListAdapter<Notes, NotesAdapter.NotesViewHolder> {

    private static final DiffUtil.ItemCallback<Notes> DIFF_CALLBACK = new DiffUtil.ItemCallback<Notes>() {
        @Override
        public boolean areItemsTheSame(@NonNull Notes oldItem, @NonNull Notes newItem) {
            return oldItem.getFilePath().equals(newItem.getFilePath());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Notes oldItem, @NonNull Notes newItem) {
            return oldItem.equals(newItem);
        }
    };

    private final OnViewClickListener<Notes, View> mOnViewClickListener;

    protected NotesAdapter(OnViewClickListener<Notes, View> onViewClickListener) {
        super(DIFF_CALLBACK);
        mOnViewClickListener = onViewClickListener;
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

        void bind(Notes item) {
            mItemTeacherFileBinding.setItem(item);
            mItemTeacherFileBinding.setViewClickListener(mOnViewClickListener);
            mItemTeacherFileBinding.executePendingBindings();
        }
    }
}
