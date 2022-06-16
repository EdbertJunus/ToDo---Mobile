package com.example.todomobile;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.todomobile.model.TaskItem;

import java.util.Calendar;

public class EditTaskActivity extends AppCompatActivity {
    private EditText etTaskName, etTaskDesc;
    private TextView tvDate, tvTime;
    private Button btnSaveChanges;

    private String date = "";
    private String time = "";
    private int myHour = 0;
    private int myMinute = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        init();
    }

    public void init() {
        etTaskName = findViewById(R.id.editTask_et_taskName);
        etTaskDesc = findViewById(R.id.editTask_et_taskDesc);
        tvDate = findViewById(R.id.editTask_tv_date);
        tvTime = findViewById(R.id.editTask_tv_time);
        btnSaveChanges = findViewById(R.id.editTask_btn_saveChanges);

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        date = getIntent().getStringExtra("taskDate");
        time = getIntent().getStringExtra("taskTime");

        etTaskName.setText(getIntent().getStringExtra("taskName"));
        etTaskDesc.setText(getIntent().getStringExtra("taskDesc"));
        tvDate.setText(date);
        tvTime.setText(time);

        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(EditTaskActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DAY_OF_MONTH, day);

                        date = java.text.DateFormat.getDateInstance(java.text.DateFormat.FULL).format(calendar.getTime());
                        tvDate.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        tvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(EditTaskActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hour, int minute) {
                        myHour = hour;
                        myMinute = minute;

                        Calendar calendar = Calendar.getInstance();
                        calendar.set(0, 0, 0, myHour, myMinute);
                        time = (String) DateFormat.format("hh:mm aa", calendar);
                        tvTime.setText(time);
                    }
                }, 12, 0, false);
                timePickerDialog.updateTime(myHour, myMinute);
                timePickerDialog.show();
            }
        });

        btnSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taskName = etTaskName.getText().toString();
                String taskDesc = etTaskDesc.getText().toString();
                String dateTime = "";

                if (taskName.equals("")) {
                    Toast.makeText(EditTaskActivity.this, "Task name cannot be empty!", Toast.LENGTH_SHORT).show();
                } else if (taskDesc.equals("")) {
                    Toast.makeText(EditTaskActivity.this, "Task description cannot be empty!", Toast.LENGTH_SHORT).show();
                } else if (date.equals("") || time.equals("")) {
                    Toast.makeText(EditTaskActivity.this, "Please pick date and time!", Toast.LENGTH_SHORT).show();
                } else {
                    dateTime = date + ", " + time;

                    // modify task using taskId and userId - from Kevin

                    Toast.makeText(EditTaskActivity.this, "Task updated!", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(EditTaskActivity.this, TaskActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}