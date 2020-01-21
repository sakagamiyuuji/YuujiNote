package com.e.yuujinote;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{
    private FloatingActionButton fab;
    private ListView lvItems;
    private ArrayList<String> items;
    private ArrayAdapter<String> itemsAdapter;
    private EditText edtTitle, edtActivity;
    private String title, activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialComponent();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addtask2();
            }
        });

        items = new ArrayList<String>();
        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);

        /*for (int i = 0; i < 25; i++) {
            items.add("Item ke - " + i);
        }*/
    }



    public void formKosong(){
        edtTitle.setText(null);
        edtActivity.setText(null);
    }

    public void getForm(){
        title = edtTitle.getText().toString();
        activity = edtActivity.getText().toString();
    }

    public void addTask () {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("New Task");
        alert.setMessage("Create new task?");

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.form_alert, null);
        alert.setView(dialogView);
        alert.setCancelable(true);
        alert.setIcon(R.mipmap.ic_launcher);

        edtTitle = findViewById(R.id.edt_title);
        edtActivity = findViewById(R.id.edt_activity);
        formKosong();

        alert.setPositiveButton("SUBMIT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                title = edtTitle.getText().toString();
                items.add(title);

            }
        });

        alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alert.create().show();

        //alert.setView(inputField);


    }

    public void addtask2 () {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add a task");
        builder.setMessage("What do you want to do?");
        final EditText inputField = new EditText(this);
        builder.setView(inputField);
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                items.add(inputField.getText().toString());

            }
        });

        builder.setNegativeButton("Cancel", null);

        builder.create().show();
    }

    public void initialComponent() {
        fab = findViewById(R.id.fab_btn);
        lvItems = findViewById(R.id.lv_toDo);

    }
}

