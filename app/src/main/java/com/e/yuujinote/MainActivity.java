package com.e.yuujinote;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

;

public class MainActivity extends AppCompatActivity{
    private FloatingActionButton fab, save;
    private ListView lvItems;
    private ArrayList<String> items;
    private ArrayAdapter<String> itemsAdapter;
    private final String NOTE_SP = "NOTE_SP";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialComponent();

        //loadData();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addtask();
            }
        });

        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final int which_item = position;

                new AlertDialog.Builder(MainActivity.this)
                .setIcon(R.drawable.ic_delete_forever_black_24dp)
                .setTitle("DELETE FOREVER")
                .setMessage("Are You Sure?")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        items.remove(which_item);
                        sortSP();

                        itemsAdapter.notifyDataSetChanged();

                    }
                })

                .setNegativeButton("NO", null)
                .show();

                return true;
            }
        });

        items = new ArrayList<String>();
        showSP();
        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);


        /*for (int i = 0; i < 25; i++) {
            items.add("Item ke - " + i);
        }*/
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
                int new_key = items.size();
                String item = inputField.getText().toString();

                items.add(new_key,item);
                addToSh(new_key, item);

                itemsAdapter.notifyDataSetChanged();

                String text = String.valueOf(items.size());
                Toast.makeText(getApplicationContext(),"DATA TELAH DI TAMBAHKAN", Toast.LENGTH_SHORT).show();

            }
        });

        builder.setNegativeButton("Cancel", null);

        builder.create().show();
    }

    private void delSP(int position){
        String key = String.valueOf(position);
        SharedPreferences sh = getSharedPreferences("todo", MODE_PRIVATE);
        sh.edit().remove(key);
        //hapus 1 item. celah kosong
    }

    private void sortSP(){
        SharedPreferences sp = getSharedPreferences("todo",MODE_PRIVATE);
        SharedPreferences.Editor editor= sp.edit();
        editor.clear();
        editor.apply();
        for(int i = 0; i < items.size();i++){
            editor.putString(String.valueOf(i),items.get(i));
        }
        editor.apply();

    }

    private void showSP(){
        SharedPreferences sh = getSharedPreferences("todo", MODE_PRIVATE);
        if (sh.getAll().size() > 0){
            for (int i=0 ; i< sh.getAll().size(); i++){
                String key = String.valueOf(i);
                items.add(sh.getString(key, null));
            }
        }
    }

    private void addToSh(int key, String item){
        SharedPreferences sh = getSharedPreferences("todo", MODE_PRIVATE);
        SharedPreferences.Editor editor = sh.edit();
        String k = String.valueOf(key);
        editor.putString(k, item);
        editor.apply();
    }

    public void initialComponent() {
        fab = findViewById(R.id.fab_btn);
        save = findViewById(R.id.save_btn);
        lvItems = findViewById(R.id.lv_toDo);

    }

/*
    public void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        //SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(items);
        editor.putString("task list", json);
        editor.apply();
    }

    public void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        //SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task list", null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        items = gson.fromJson(json, type);

        //Type type = new TypeToken<ArrayList<String>>() {}.getType();
        //items =  gson.fromJson(json, type);

        if (items == null){
            items = new ArrayList<>();
        }
    }
*/


}

