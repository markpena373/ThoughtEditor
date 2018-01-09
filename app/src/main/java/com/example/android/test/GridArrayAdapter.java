package com.example.android.test;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Mark on 12/27/2017.
 */

public final class GridArrayAdapter extends ArrayAdapter{
    private Entry entry;
    private Context context;
    private ArrayList<String> categories;
    private String title, content, category;



    public GridArrayAdapter(Context context, int resource, ArrayList<String> categories){
        super(context, resource, categories);
        this.context = context;
        this.categories = categories;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parentView){
        notifyDataSetChanged();
        category = categories.get(position);
        Button btn;
        if(convertView == null){
            // if view is null, inflate the view with the categories_grid_button layout
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.categories_grid_button, parentView, false);
            // convertView is inherently a button, cast to button
            btn = (Button) convertView;
            // set text of button to whatever its supposed to be given its position in the categories list
            btn.setText(category);
        }else{
            btn = (Button) convertView;
            btn.setText(category);
            final String categoryName = btn.getText().toString();
            btn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    Button categoryButton = (Button) view;
                    if(title != null && content != null){
                        Entry entry = new Entry(context, title, content, new Category(context, categoryButton.getText().toString()));
                        Toast.makeText(context, "Saved under " + categoryButton.getText().toString().toUpperCase(), Toast.LENGTH_SHORT).show();
                        Intent toThoughtEditor = new Intent(context, MainActivity.class);
                        context.startActivity(toThoughtEditor);
                        ((Activity)context).finish();
                    }else {
                        Intent toEntryList = new Intent(context, EntryList.class);
                        toEntryList.putExtra("category", categoryName);
                        context.startActivity(toEntryList);
                    }
                }
            });

        }
        return btn;
    }

    public void passInfo(String title, String content){
        this.title = title;
        this.content = content;
    }
}
