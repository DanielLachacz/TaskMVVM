package com.example.daniellachacz.taskmvvm.view;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.widget.Toast;

import com.example.daniellachacz.taskmvvm.R;
import com.example.daniellachacz.taskmvvm.databinding.EditTaskActivityBinding;
import com.example.daniellachacz.taskmvvm.viewmodel.MainViewModel;

import java.util.Calendar;

public class EditTaskActivity extends AppCompatActivity {

    public EditTaskActivityBinding editTaskActivityBinding;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private TimePickerDialog.OnTimeSetListener timeSetListener;
    public static final String EXTRA_DESCRIPTION = "com.example.daniellachacz.taskmvvm.EXTRA_DESCRIPTION";
    public static final String EXTRA_DATE = "com.example.daniellachacz.taskmvvm.EXTRA_DATE";
    public static final String EXTRA_TIME = "com.example.daniellachacz.taskmvvm.EXTRA_TIME";
    public static final String EXTRA_ID = "com.example.daniellachacz.taskmvvm.EXTRA_ID";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        editTaskActivityBinding = DataBindingUtil.setContentView(this, R.layout.edit_task_activity);
        MainViewModel mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        editTaskActivityBinding.setEditTaskViewModel(mainViewModel);

        Intent intent = getIntent();
        mainViewModel.description.set(intent.getStringExtra(EXTRA_DESCRIPTION));
        mainViewModel.date.set(intent.getStringExtra(EXTRA_DATE));
        mainViewModel.time.set(intent.getStringExtra(EXTRA_TIME));
        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            intent.putExtra(EXTRA_ID, id);
        }

        dateSetListener = (view, year, month, dayOfMonth) -> {
            month = month + 1;
            String date = dayOfMonth + "/" + month + "/" + year;
            editTaskActivityBinding.editDateText.setText(date);
        };

        timeSetListener = (view, hourOfDay, minute) -> {
            String time = hourOfDay + ":" + minute;
            editTaskActivityBinding.editTimeText.setText(time);
        };

        editTaskActivityBinding.setEditTaskInterface(new OnEditTaskClickInterface() {
            @Override
            public void onEditClick() {
                if (id != -1) {
                    Toast.makeText(getApplicationContext(), "Task updated", Toast.LENGTH_SHORT).show();
                    mainViewModel.getDataToEdit(id);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Task can't be updated", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onEditDateClick() {
                showDatePicker();
            }

            @Override
            public void onEditTimeClick() {
                showTimePicker();
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
