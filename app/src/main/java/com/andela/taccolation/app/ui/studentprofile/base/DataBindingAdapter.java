package com.andela.taccolation.app.ui.studentprofile.base;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.andela.taccolation.app.utils.OnItemClickListener;

public abstract class DataBindingAdapter<T> extends ListAdapter<T, DataBindingViewHolder<T>> {

    private final OnItemClickListener<T> mOnItemClickListener;
    private final int layoutResId;

    protected DataBindingAdapter(@NonNull DiffUtil.ItemCallback<T> diffCallback, OnItemClickListener<T> listener, int layoutResId) {
        super(diffCallback);
        mOnItemClickListener = listener;
        this.layoutResId = layoutResId;
    }

    @NonNull
    @Override
    public DataBindingViewHolder<T> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), viewType, parent, false);
        return new DataBindingViewHolder<>(binding, mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull DataBindingViewHolder<T> holder, int position) {
        holder.bind(getItem(position));
    }

    @Override
    public int getItemViewType(int position) {
        return layoutResId;
    }
}
