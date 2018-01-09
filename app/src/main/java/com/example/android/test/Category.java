package com.example.android.test;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Mark on 12/4/2017.
 */

public class Category {
    private String name;
    private Context context;
    private ArrayList<String> entries;
    private String[] entryNames;
    private FileInputStream fileInputStream = null;
    private File categoriesFolder;

    //folder based

    public Category(Context context, String name){
        this.context = context;
        this.name = name;
        //this.filename = name + ".txt";
        //categoriesFolder = context.getDir(this.name, Context.MODE_PRIVATE);
        categoriesFolder = new File(context.getFilesDir() + "/categories/", name);
        if(!categoriesFolder.exists()){
            categoriesFolder.mkdir();
        }
        entryNames = categoriesFolder.list();
        entries = new ArrayList<String>(Arrays.asList(entryNames));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getEntries() {
        return entries;
    }

    public File getCategoriesFolder(){
        return categoriesFolder;
    }
}
