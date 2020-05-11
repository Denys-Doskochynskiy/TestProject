package com.example.myapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Main extends AppCompatActivity {
    TextView hello;
    SharedPreferences sharePref;
    final int DIALOG_EXIT = 1;
    final String SAVED_TEXT = "saved text";
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        hello = findViewById(R.id.hello);
        getStringName();

    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start:

                saveAndGoToNextActivity();
                break;
            case R.id.refresh:
                loadText();
                break;
        }


    }

    void saveText() {
        sharePref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor edit = sharePref.edit();
        edit.putString(SAVED_TEXT, hello.getText().toString());
        edit.commit();

    }

    public void loadText() {
        sharePref = getPreferences(MODE_PRIVATE);
        String savedText = sharePref.getString(SAVED_TEXT, "");
        hello.setText(savedText);


    }

    public void saveAndGoToNextActivity() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Загрузка");
        progressDialog.setMessage("Почекайте трохи");

        progressDialog.setButton(Dialog.BUTTON_POSITIVE, "OK", new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        progressDialog.show();
        Intent intent = new Intent(this, Calc.class);
        saveText();
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.go_to_web:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com"));
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public void getStringName() {
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");


        if (name == null) {
            hello.setText("Hello ");
        } else
            hello.setText(("Hello " + name));

    }

    public void onclick(View v) {

        showDialog(DIALOG_EXIT);
    }

    protected Dialog onCreateDialog(int id) {
        if (id == DIALOG_EXIT) {
            AlertDialog.Builder adb = new AlertDialog.Builder(this);

            adb.setTitle(R.string.exit);

            adb.setMessage(R.string.something);

            adb.setIcon(android.R.drawable.ic_dialog_info);

            adb.setPositiveButton(R.string.yes, myClickListener);

            adb.setNegativeButton(R.string.no, myClickListener);

            adb.setNeutralButton(R.string.next, myClickListener);

            return adb.create();
        }
        return super.onCreateDialog(id);
    }

    DialogInterface.OnClickListener myClickListener = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {

                case Dialog.BUTTON_POSITIVE:
                    saveData();
                    goToNext();
                    break;

                case Dialog.BUTTON_NEGATIVE:
                    dontSavaData();
                    goToHello();
                    break;

                case Dialog.BUTTON_NEUTRAL:
                    goToNext();
                    break;
            }
        }
    };
    void goToHello(){
        Intent intent = new Intent(this, Hello.class);
        startActivity(intent);
    }
    void goToNext(){
        Intent intent = new Intent(this, Calc.class);
        startActivity(intent);
    }
    void dontSavaData() {
        Toast.makeText(this, "Move to Hello", Toast.LENGTH_SHORT).show();
    }
    void saveData() {
        Toast.makeText(this, R.string.saved, Toast.LENGTH_SHORT).show();
    }
}


