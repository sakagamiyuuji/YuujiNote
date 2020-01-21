package com.e.yuujinote;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

;

public class MainActivity extends AppCompatActivity{
    private FloatingActionButton fab;
    private ListView lvItems;
    private ArrayList<String> items;
    private ArrayAdapter<String> itemsAdapter;
    private EditText edtTitle, edtActivity;
    private String title, activity;

    private final String NOTE_SP = "NOTE_SP";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialComponent();

        loadData();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addtask();
            }
        });

        items = new ArrayList<String>();
        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);

        /*for (int i = 0; i < 25; i++) {
            items.add("Item ke - " + i);
        }*/

        saveData();

    }


    public void addtask () {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("NEW TASK");
        builder.setMessage("Create a new task?");
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

    public void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(items);
        editor.putString("task list", json);
        editor.apply();
    }

    public void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task list", null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        items =  gson.fromJson(json, type);

        if (items == null){
            items = new ArrayList<>();
        }
    }

/*    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences sp = getSharedPreferences(NOTE_SP, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        for (int i=0; i<= items.size(); i++){
            String key = String.valueOf(i);
            String item = items.get(i);
            editor.putString(key,item);
        }

        editor.apply();
    }

    protected void showSP(){
        SharedPreferences sp = getSharedPreferences(NOTE_SP, Context.MODE_PRIVATE);
        int createCookies = 0;

        for (int a = 0; a<= items.size(); a++){
            String key = String.valueOf(a);
            String cookies = sp.getString(key, null);
            items.add(cookies);

            if (items == null){
                items = new ArrayList<>();
            }
        }
    }*/
}

