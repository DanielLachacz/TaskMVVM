package com.example.daniellachacz.taskmvvm.view;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Toast;

import com.example.daniellachacz.taskmvvm.R;
import com.example.daniellachacz.taskmvvm.databinding.TaskActivityBinding;
import com.example.daniellachacz.taskmvvm.viewmodel.TaskViewModel;

import java.util.Calendar;

public class TaskActivity extends AppCompatActivity {

    private TaskActivityBinding taskActivityBinding;
    private TaskViewModel taskViewModel;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private TimePickerDialog.OnTimeSetListener timeSetListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        taskActivityBinding = DataBindingUtil.setContentView(this, R.layout.task_activity);
        taskViewModel = ViewModelProviders.of(this).get(TaskViewModel.class);
        taskActivityBinding.setTaskViewModel(taskViewModel);

        dateSetListener = (view, year, month, dayOfMonth) -> {
            month = month + 1;
            String date = dayOfMonth + "/" + month + "/" + year;
            taskActivityBinding.dateText.setText(date);
        };

        timeSetListener = (view, hourOfDay, minute) -> {
            String time = hourOfDay + ":" + minute;
            taskActivityBinding.timeText.setText(time);
        };

        taskActivityBinding.setTaskInterface(new OnTaskClickInterface() {
            @Override
            public void onDateClick() {
                showDatePicker();
            }

            @Override
            public void onTimeClick() {
                showTimePicker();
            }

            @Override
            public void onSaveClick(View view) {
                if (taskActivityBinding.descriptionText.getText().toString().trim().isEmpty()) {
                    Toast.makeText(view.getContext(), "Title is empty!", Toast.LENGTH_SHORT).show();
                    return;
                }
                taskViewModel.insert();
                Toast.makeText(view.getContext(), "Task saved", Toast.LENGTH_SHORT).show();

                taskActivityBinding.descriptionText.setText("");
                taskActivityBinding.dateText.setText("");
                taskActivityBinding.timeText.setText("");
            }

        });
    }

    public void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, dateSetListener,
                year, month, day);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    public void showTimePicker() {
        Calendar calendar = Calendar.getInstance();
        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog dialog = new TimePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, timeSetListener,
                hourOfDay, minute, DateFormat.is24HourFormat(this));
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

}
