package com.example.android.test;
import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Mark on 12/4/2017.
 */
public class Entry {
    private String name;
    private String filename;
    private String content;
    private String length;
    private File category;
    private int index;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private Calendar c;
    private String formattedDate;

    public Entry(){
        name = "";
        content = "";
        length = "";
        this.formattedDate = "";
    }

    public Entry(Context context, String name, String content, Category category) {
        this.category = category.getCategoriesFolder();
        this.name = name;
        this.content = content;
        filename = name + ".txt";
        //String separator = System.getProperty("line.separator");
        //records time and date on entry creation
        c = Calendar.getInstance();
        formattedDate = dateFormat.format(c.getTime());
        FileOutputStream fileOutputStream = null;
        FileWriter fileWriter = null;

        //write file
        try {
            File newEntry = new File(context.getFilesDir() + "/categories/" + category.getName(), filename);
            if (newEntry.exists()) {
                //this means entry with that name already already exists, ask to rename or delete
                fileWriter = null;
            } else {
                fileWriter = new FileWriter(newEntry);
                fileWriter.append(name);
                fileWriter.append("\n");
                fileWriter.append(formattedDate);
                fileWriter.append("\n");
                fileWriter.append(content);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        } finally {
            try {
                if (fileWriter != null) {
                    fileWriter.flush();
                    fileWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }
    }

    public String getName(){
        return name;
    }

    public String getContent(){
        return content;
    }

    public String getDate(){
        return formattedDate;
    }

    public String getLength(){
        int length = 0;
        String stringLength;
        if(!content.isEmpty() || content != null){
            String[] words = content.split(" ");
            length = words.length;
            return String.valueOf(length);
        }else{
            stringLength = "0";
            return stringLength;
        }
    }

    public void setName(String name){
        this.name = name;
    }

    public void setContent(String content){
        this.content = content;
    }

    public void setDate(String date){
        this.formattedDate = date;
    }

    public void setLength(String length){
        this.length = length;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        sb.append("\n");
        sb.append(formattedDate);
        sb.append("\n");
        sb.append(content);
        return sb.toString();
    }
}
