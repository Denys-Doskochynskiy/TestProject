package com.example.myapplication;


import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.sql.Date;
import java.text.SimpleDateFormat;


public class Hello extends Activity implements OnClickListener {
    Button saveName;
    EditText name;
    TextView timeNow;
    int DIALOG_TIME = 1;
    int myHour = 14;
    TextView tvTime;
    int myMinute = 35;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello);
        saveName = findViewById(R.id.save);
        name = findViewById(R.id.name);
        timeNow = findViewById(R.id.timeNow);
        tvTime = findViewById(R.id.textView2);
        saveName.setOnClickListener(this);
        showTime();

    }

    public void onclickTime(View view) {
        showDialog(DIALOG_TIME);


    }

    protected Dialog onCreateDialog(int id) {
        if (id == DIALOG_TIME) {
            TimePickerDialog tpd = new TimePickerDialog(this, myCallBack, myHour, myMinute, true);
            return tpd;
        }
        return super.onCreateDialog(id);
    }

    TimePickerDialog.OnTimeSetListener myCallBack = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            myHour = hourOfDay;
            myMinute = minute;
            tvTime.setText("Time is " + myHour + " hours " + myMinute + " minutes");
        }
    };

    public void onClick(View v) {

        Intent intent = new Intent(this, Main.class);
        intent.putExtra("name", name.getText().toString());
        startActivity(intent);
    }

    public void showTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String time = sdf.format(new Date(System.currentTimeMillis()));
        timeNow.setText(time);
    }
}
