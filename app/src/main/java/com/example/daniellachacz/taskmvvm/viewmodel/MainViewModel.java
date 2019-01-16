package com.example.daniellachacz.taskmvvm.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.view.View;

import com.example.daniellachacz.taskmvvm.adapter.RecyclerViewAdapter;
import com.example.daniellachacz.taskmvvm.model.Task;
import com.example.daniellachacz.taskmvvm.model.data.TaskRepository;

import java.util.List;

public class MainViewModel extends AndroidViewModel implements RecyclerViewAdapter.OnItemClickListener {

    private TaskRepository taskRepository;
    private LiveData<List<Task>> allTasks;
    public final ObservableField<String> description = new ObservableField<>();
    public final ObservableField<String> date = new ObservableField<>();
    public final ObservableField<String> time = new ObservableField<>();


    public MainViewModel(@NonNull Application application) {
        super(application);
        taskRepository = new TaskRepository(application);
        allTasks = taskRepository.getAllTasks();
    }

    public LiveData<List<Task>> getAllTasks() {
        return allTasks;
    }

    public void deleteTask(Task task) {
        taskRepository.delete(task);
    }

    public void getDataToEdit(int id) {
        editTask(description.get(), date.get(), time.get(), id);
    }

    public void editTask(String description, String date, String time, int id) {
        Task task = new Task(description, date, time);
        task.setId(id);
        taskRepository.update(task);
    }

    @Override
    public void onItemClick(View view, Task item) {
        description.set(item.getDescription());
        date.set(item.getDate());
        time.set(item.getTime());
    }
}