package bg.adastragrp.adastrafaceapp.adapters;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import bg.adastragrp.adastrafaceapp.BR;

/**
 * Base class for all recycler adapters
 *
 * @param <T> Object source for each row
 */
public abstract class BaseAdapter<T> extends RecyclerView.Adapter<BaseAdapter.BaseViewHolder> {

    private List<T> data;
    private ItemClickListener clickListener;

    class BaseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final ViewDataBinding binding;

        BaseViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        /**
         * Binds data source to view
         */
        <E> void bind(E item) {
            // Note: if child adapter extends this BaseAdapter then its layout should use data variable with name "item"
            binding.setVariable(BR.item, item);
            binding.executePendingBindings();
            binding.getRoot().setOnClickListener(BaseViewHolder.this);
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null) {
                clickListener.onItemClick(getItem(getAdapterPosition()), v);
            }
        }
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        // Inflates a binding layout and returns the newly-created binding for that layout.
        ViewDataBinding binding = DataBindingUtil.inflate(
                layoutInflater,
                getLayoutIdForType(viewType),
                parent,
                false);

        return new BaseViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseAdapter.BaseViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        if (data == null) {
            return 0;
        }

        return data.size();
    }

    public interface ItemClickListener<T> {
        void onItemClick(T item, View v);
    }

    public void updateData(List<T> newData) {
        this.data = newData;
        notifyDataSetChanged();
    }

    public T getItem(int position) {
        return data.get(position);
    }

    protected void setData(List<T> data) {
        this.data = data;
    }

    protected List<T> getData() {
        return data;
    }

    protected void setClickListener(ItemClickListener<T> clickListener) {
        this.clickListener = clickListener;
    }

    protected ItemClickListener getClickListener() {
        return this.clickListener;
    }

    // get recycler item layout
    protected abstract int getLayoutIdForType(int viewType);
}
