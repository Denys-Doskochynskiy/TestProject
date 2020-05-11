package com.example.myapplication;


import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Calc extends AppCompatActivity implements View.OnClickListener {
    static final String LOG_TAG = "myLogs";
    EditText firstNumb, secondNumb;
    TextView sumbol, result;
    Button minus, add, mult, div;
    String oper = "";
    String[] data = {"Select color", "Blue", "Red", "Black", "Yellow"};
    DBHelper dbHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc);
        firstNumb = findViewById(R.id.firstNumber);
        secondNumb = findViewById(R.id.secondNumber);
        sumbol = findViewById(R.id.sumbol);
        result = findViewById(R.id.res);
        minus = findViewById(R.id.minus);
        add = findViewById(R.id.add);
        mult = findViewById(R.id.mult);
        div = findViewById(R.id.div);
        add.setOnClickListener(this);
        mult.setOnClickListener(this);
        minus.setOnClickListener(this);
        div.setOnClickListener(this);
        dbHelper = new Calc.DBHelper(this);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinner = findViewById(R.id.spinner);
        spinner.setAdapter(adapter);

        spinner.setPrompt("Title");

        spinner.setSelection(0);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                if (position == 1) {
                    result.setBackgroundColor(Color.BLUE);

                } else if (position == 2) {
                    result.setBackgroundColor(Color.RED);

                } else if (position == 3) {
                    result.setBackgroundColor(Color.BLACK);

                } else if (position == 4) {
                    result.setBackgroundColor(Color.YELLOW);
                } else if (position == 0) {
                    result.setBackgroundColor(Color.WHITE);

                }


            }

            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

    }


    public void onClickRecycle(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.calc_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_back_to_main:
                Intent intent = new Intent(this, Main.class);
                startActivity(intent);
                return true;
            case R.id.go_to_web:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com"));
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public double calculate(int operatorId, double numberOne, double numberTwo) {

        double res = 0;
        switch (operatorId) {
            case R.id.div:
                if (numberTwo == 0) {
                    res = 0;

                    break;
                }

                res = numberOne / numberTwo;

                break;

            case R.id.add:
                res = numberOne + numberTwo;


                break;

            case R.id.minus:
                res = numberOne - numberTwo;
                break;

            case R.id.mult:
                res = numberOne * numberTwo;

                break;

        }
        return res;
    }

    public String operator(int operatorId, String oper) {
        switch (operatorId) {
            case R.id.div:
                oper = "/";
                break;
            case R.id.add:
                oper = "+";
                break;
            case R.id.minus:
                oper = "-";
                break;
            case R.id.mult:
                oper = "*";
                break;
        }
        return oper;
    }

    @Override
    public void onClick(View v) {
        if (TextUtils.isEmpty(firstNumb.getText().toString())
                || TextUtils.isEmpty(firstNumb.getText().toString())) {
            return;
        }

        double numberOne = Double.parseDouble(firstNumb.getText().toString());
        double numberTwo = Double.parseDouble(secondNumb.getText().toString());


        sumbol.setText(String.valueOf(operator(v.getId(), oper)));

        result.setText(String.valueOf(calculate(v.getId(), numberOne, numberTwo)));
        getResultToDB();


    }

    public void getResultToDB() {
        String resultCalc = result.getText().toString();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("resultCalc", resultCalc);
        long rowID = db.insert("mytableRes", null, cv);
        Log.d(LOG_TAG, "row inserted, ID = " + rowID);
        Cursor c = db.query("mytableRes", null, null, null, null, null, null);
        if (c.moveToFirst()) {
            int idColIndex = c.getColumnIndex("id");
            int resultCalcIndex = c.getColumnIndex("resultCalc");
            do {

                Log.d(LOG_TAG,
                        "ID = " + c.getInt(idColIndex) +
                                ", name = " + c.getString(resultCalcIndex)
                );

            } while (c.moveToNext());
        } else
            Log.d(LOG_TAG, "0 rows");
        c.close();
        dbHelper.close();
    }

    static class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            super(context, "myDB1", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.d(LOG_TAG, "--- onCreate database ---");

            db.execSQL("create table mytableRes ("
                    + "id integer primary key autoincrement,"
                    + "resultCalc text" + ");");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}