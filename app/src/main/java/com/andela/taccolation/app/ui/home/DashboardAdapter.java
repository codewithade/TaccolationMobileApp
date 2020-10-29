package com.andela.taccolation.app.ui.home;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.andela.taccolation.app.utils.OnItemClickListener;
import com.andela.taccolation.databinding.ItemDashboardBinding;
import com.andela.taccolation.presentation.model.DashboardItem;

public class DashboardAdapter extends ListAdapter<DashboardItem, DashboardAdapter.DashboardViewHolder> {

    private static final DiffUtil.ItemCallback<DashboardItem> DIFF_UTIL = new DiffUtil.ItemCallback<DashboardItem>() {
        @Override
        public boolean areItemsTheSame(@NonNull DashboardItem oldItem, @NonNull DashboardItem newItem) {
            return oldItem.getItemTitle() == (newItem.getItemTitle());
        }

        @Override
        public boolean areContentsTheSame(@NonNull DashboardItem oldItem, @NonNull DashboardItem newItem) {
            return oldItem.equals(newItem);
        }
    };
    private final OnItemClickListener<DashboardItem> mOnItemClickListener;

    public DashboardAdapter(OnItemClickListener<DashboardItem> onItemClickListener) {
        super(DIFF_UTIL);
        mOnItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public DashboardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemDashboardBinding dashboardBinding = ItemDashboardBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new DashboardViewHolder(dashboardBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull DashboardViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    class DashboardViewHolder extends RecyclerView.ViewHolder {

        private final ItemDashboardBinding mItemDashboardBinding;

        public DashboardViewHolder(ItemDashboardBinding dashboardBinding) {
            super(dashboardBinding.getRoot());
            mItemDashboardBinding = dashboardBinding;
        }

        void bind(DashboardItem item) {
            mItemDashboardBinding.setDashboardItem(item);
            mItemDashboardBinding.setItemClick(mOnItemClickListener);
            mItemDashboardBinding.executePendingBindings();
        }
    }
}
