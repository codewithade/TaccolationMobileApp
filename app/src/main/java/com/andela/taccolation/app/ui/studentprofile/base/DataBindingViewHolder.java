package com.andela.taccolation.app.ui.studentprofile.base;

import androidx.databinding.ViewDataBinding;
import androidx.databinding.library.baseAdapters.BR;
import androidx.recyclerview.widget.RecyclerView;

import com.andela.taccolation.app.utils.OnItemClickListener;


public class DataBindingViewHolder<T> extends RecyclerView.ViewHolder {

    private final ViewDataBinding mBinding;
    private final OnItemClickListener<T> mItemClickedListener;

    public DataBindingViewHolder(ViewDataBinding binding, OnItemClickListener<T> itemClickedListener) {
        super(binding.getRoot());
        mBinding = binding;
        mItemClickedListener = itemClickedListener;
    }

    // 'item' must be a variable in each layout where data binding is enabled
    public void bind(T item) {
        mBinding.setVariable(BR.item, item);
        mBinding.setVariable(BR.itemClickListener, mItemClickedListener);
        mBinding.executePendingBindings();
    }

}
