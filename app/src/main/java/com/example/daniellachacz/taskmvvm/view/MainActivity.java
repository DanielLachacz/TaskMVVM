package com.example.daniellachacz.taskmvvm.view;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.example.daniellachacz.taskmvvm.R;
import com.example.daniellachacz.taskmvvm.adapter.RecyclerViewAdapter;
import com.example.daniellachacz.taskmvvm.model.Task;
import com.example.daniellachacz.taskmvvm.viewmodel.MainViewModel;
import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton floatingActionButton = findViewById(R.id.floating_action_button);

        List<Task> tasks = new ArrayList<>();

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        final RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(getApplicationContext(), tasks);
        recyclerView.setAdapter(recyclerViewAdapter);

        recyclerViewAdapter.setOnItemClickListener((view, item) -> {
            Intent intent = new Intent(getApplicationContext(), EditTaskActivity.class);
            intent.putExtra(EditTaskActivity.EXTRA_DESCRIPTION, item.getDescription());
            intent.putExtra(EditTaskActivity.EXTRA_DATE, item.getDate());
            intent.putExtra(EditTaskActivity.EXTRA_TIME, item.getTime());
            intent.putExtra(EditTaskActivity.EXTRA_ID, item.getId());
            startActivity(intent);
            mainViewModel.onItemClick(view, item);
        });

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.getAllTasks().observe(this, recyclerViewAdapter::setTasks);

        floatingActionButton.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, TaskActivity.class)));

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                mainViewModel.deleteTask(recyclerViewAdapter.getTaskPosition(viewHolder.getAdapterPosition()));

            }
        }).attachToRecyclerView(recyclerView);

    }
}