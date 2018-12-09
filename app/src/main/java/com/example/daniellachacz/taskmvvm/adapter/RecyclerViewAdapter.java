package com.example.daniellachacz.taskmvvm.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.daniellachacz.taskmvvm.R;
import com.example.daniellachacz.taskmvvm.databinding.RecyclerViewItemBinding;
import com.example.daniellachacz.taskmvvm.model.Task;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.TaskViewHolder> {

    private LayoutInflater mLayoutInflater;
    private List<Task> mTasks;
    private OnItemClickListener mOnItemClickListener;


    public RecyclerViewAdapter(@NonNull Context context, @NonNull List<Task> tasks) {
        this.mLayoutInflater = LayoutInflater.from(context);
        this.mTasks = tasks;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final RecyclerViewItemBinding binding = DataBindingUtil.inflate(mLayoutInflater, R.layout.recycler_view_item, parent, false);
        return new TaskViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerViewAdapter.TaskViewHolder holder, final int position) {
        Task currentTask = mTasks.get(position);
        holder.bind(currentTask, mOnItemClickListener);
    }

    @Override
    public int getItemCount() {
        return mTasks == null ? 0 : mTasks.size();
    }

    public void setTasks(List<Task> tasks) {
        this.mTasks = tasks;
        notifyDataSetChanged();
    }

    public Task getTaskPosition(int position) {
        return mTasks.get(position);
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder {
        private final RecyclerViewItemBinding mBinding;

        public TaskViewHolder(RecyclerViewItemBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }
            public void bind (Task item, OnItemClickListener onItemClickListener){
                mBinding.setItem(item);
                mBinding.executePendingBindings();
                itemView.setOnClickListener(view -> {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(view, item);
                    }
                });
            }
    }
        public interface OnItemClickListener {
            void onItemClick(View view, Task item);
        }
    }