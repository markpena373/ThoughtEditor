package com.example.android.test;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class EntryList extends AppCompatActivity {
    private Toolbar entryListToolbar;
    private Context context = this;
    private boolean nameUp, nameDown, dateUp, dateDown, lengthUp, lengthDown;
    private Button name, date, length;
    private String category;
    private Intent intent;
    private ArrayList<Entry> entriesList;
    private FileReader fileReader;
    private BufferedReader bufferedReader;
    private Scanner scanner;
    private ListView list;
    private EntryListArrayAdapter entryListArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_list);
        entryListToolbar = (Toolbar) findViewById(R.id.entry_list_toolbar);
        entryListToolbar.setTitle("");
        setSupportActionBar(entryListToolbar);
        name = (Button)findViewById(R.id.entry_list_name_button);
        date = (Button)findViewById(R.id.entry_list_date_button);
        length = (Button)findViewById(R.id.entry_list_length_button);
        intent = getIntent();
        category = intent.getStringExtra("category");
        entriesList = new ArrayList<Entry>();
        parseEntries();
        fillEntryList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.entry_list_menu, menu);
        return true;
    }

    public void toThoughtEditor(MenuItem item){
        Intent toThoughtEditor = new Intent(context, MainActivity.class);
        startActivity(toThoughtEditor);
        finish();
    }

    public void toCategories(MenuItem item){
        Intent toCategories = new Intent(context, CategoriesGrid.class);
        startActivity(toCategories);
        finish();
    }

    public void sortName(View view){
        name.setCompoundDrawablePadding(0);
        Drawable upArrow = context.getResources().getDrawable(R.drawable.ic_arrow_upward_black_18dp);
        Drawable downArrow = context.getResources().getDrawable(R.drawable.ic_arrow_downward_black_18dp);
        if(nameDown){
            name.setCompoundDrawablesWithIntrinsicBounds(upArrow, null, null, null);
            nameDown = false;
            nameUp = true;
        }else if(nameUp){
            name.setCompoundDrawablesWithIntrinsicBounds(downArrow, null, null, null);
            nameUp = false;
            nameDown = true;
        }else{
            name.setCompoundDrawablesWithIntrinsicBounds(downArrow, null, null, null);
            nameDown = true;
            nameUp = false;
        }
    }

    public void parseEntries(){
        File categoryDir = new File(this.getFilesDir() + "/categories/", category);
        boolean dirExists = categoryDir.exists();
        boolean dirCreated;
        if(!dirExists){
            dirCreated = categoryDir.mkdirs();
            System.out.println(dirCreated);
        }
        String title = "";
        String date = "";
        String content = "";
        String length;
        try{
            File[] entries = categoryDir.listFiles();
            for(File entryFile : entries){
                ArrayList<String> lines = new ArrayList<String>();
                String line;
                fileReader = new FileReader(entryFile);
                bufferedReader = new BufferedReader(fileReader);
                while((line = bufferedReader.readLine()) != null){
                    lines.add(line);
                }
                if(lines.size() == 2){
                    title = lines.get(0);
                    date = lines.get(1);
                    content = "";
                }else{
                    title = lines.get(0);
                    date = lines.get(1);
                    content = lines.get(2);
                }
                Entry newEntry = new Entry();
                newEntry.setName(title);
                newEntry.setDate(date);
                newEntry.setContent(content);
                length = newEntry.getLength();
                newEntry.setLength(String.valueOf(length));
                entriesList.add(newEntry);
                //ENTRIES NOT UPDATING, NEED TO FIGURE OUT WHY. NEED TO ADD THEM TO ADAPTER
                //entryListArrayAdapter.add(newEntry.getName());
            }
        }catch(FileNotFoundException e){
            e.printStackTrace();
            System.out.println("Class: EntryList. Error: Could not find entry file in category folder");
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void fillEntryList(){
        list = (ListView) findViewById(R.id.entry_listview);
        entryListArrayAdapter = new EntryListArrayAdapter(this, R.layout.entry_list_entry_view, entriesList);
        list.setAdapter(entryListArrayAdapter);
    }
}
