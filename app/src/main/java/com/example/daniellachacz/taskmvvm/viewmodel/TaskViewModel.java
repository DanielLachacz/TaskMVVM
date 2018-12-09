package com.example.daniellachacz.taskmvvm.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.example.daniellachacz.taskmvvm.model.Task;
import com.example.daniellachacz.taskmvvm.model.data.TaskRepository;

public class TaskViewModel extends AndroidViewModel {

    private TaskRepository taskRepository;
    public final ObservableField<String> description = new ObservableField<>();
    public final ObservableField<String> date = new ObservableField<>();
    public final ObservableField<String> time = new ObservableField<>();

    public TaskViewModel(@NonNull Application application) {
        super(application);
        taskRepository = new TaskRepository(application);
    }

    public void insert() {
        addTask(description.get(), date.get(), time.get());
    }

    private void addTask(String description, String date, String time) {
        Task task = new Task(description, date, time);
        taskRepository.insert(task);
    }
}
