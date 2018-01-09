package com.example.android.test;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Mark on 12/29/2017.
 */

public final class EntryListArrayAdapter extends ArrayAdapter{
    private ArrayList<Entry> entries;
    private Context context;

    public EntryListArrayAdapter(Context context, int resource, ArrayList<Entry> entries){
        super(context, resource, entries);
        this.context = context;
        this.entries = entries;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parentView){
        notifyDataSetChanged();
        View currentEntry = convertView;
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.entry_list_entry_view, parentView, false);
        }
        Entry entry = entries.get(position);
        if(entry != null){
            TextView title = (TextView) convertView.findViewById(R.id.entry_title);
            TextView date = (TextView) convertView.findViewById(R.id.entry_date);
            TextView length = (TextView) convertView.findViewById(R.id.entry_length);
            title.setText(entry.getName());
            date.setText(entry.getDate());
            length.setText(entry.getLength());
            LinearLayout entryView = (LinearLayout) convertView;
            entryView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    return false;
                }
            });
        }
        return convertView;
    }

}
























