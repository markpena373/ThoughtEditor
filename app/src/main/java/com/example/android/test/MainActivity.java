package com.example.android.test;

import android.content.Intent;
import android.os.Build;
import android.support.v4.view.MarginLayoutParamsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    public Toolbar mainMenuToolbar;
    public PopupWindow saveEntryPopupWindow;
    public LinearLayout linearLayout;
    private Button submitButton, cancelButton;
    private String titleText, contentText;
    private EditText thoughtEditor, titleEditor;
    private Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainMenuToolbar = (Toolbar) findViewById(R.id.main_menu_toolbar);
        mainMenuToolbar.setTitle("");
        setSupportActionBar(mainMenuToolbar);
        intent = getIntent();
        titleText = intent.getStringExtra("title");
        contentText = intent.getStringExtra("content");
        thoughtEditor = (EditText) findViewById(R.id.thought_editor);
        titleEditor = (EditText) findViewById(R.id.titleEditor);
        titleEditor.setText(titleText);
        thoughtEditor.setText(contentText);
        thoughtEditor.requestFocus();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_toolbar_menu, menu);
        return true;
    }

    @Override
    public void onBackPressed(){
        moveTaskToBack(true);
    }

    public void toCategories(MenuItem item){
        titleText = titleEditor.getText().toString();
        contentText = thoughtEditor.getText().toString();
        Intent toCategories = new Intent(this, CategoriesGrid.class);
        if(!titleText.isEmpty() && !contentText.isEmpty()){
            toCategories.putExtra("title", titleText);
            toCategories.putExtra("content", contentText);
        }
        startActivity(toCategories);
        if(titleText == null && contentText == null){
            finish();
        }
    }

    public void submit(View view){
        linearLayout = (LinearLayout) findViewById(R.id.main_activity_layout);
        titleText = titleEditor.getText().toString();
        contentText = thoughtEditor.getText().toString();
        if(titleText.isEmpty() && contentText.isEmpty()){
            Toast.makeText(this, "Please enter a title or thought", Toast.LENGTH_SHORT).show();
            return;
        }
        LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.entry_popup_window, null);
        saveEntryPopupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        saveEntryPopupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        saveEntryPopupWindow.update();
        //needed to reference the view from which the EditText is in. Which is popupView. Was calling EditText from main view which is null
        EditText popupTitleEditor = (EditText) popupView.findViewById(R.id.popup_title_name);
        if(titleText.isEmpty()){
            String[] firstWordOfContent = contentText.split(" ");
            popupTitleEditor.setText(firstWordOfContent[0]);
            saveEntryPopupWindow.update();
        }else {
            popupTitleEditor.setText(titleText);
        }

    }

    public void popupCancel(View view){
        saveEntryPopupWindow.dismiss();
    }

    public void popupSubmit(View view){
        //choose category and save entry under category
        Intent chooseCategory = new Intent(this, CategoriesGrid.class);
        chooseCategory.putExtra("title", titleText);
        chooseCategory.putExtra("content", contentText);
        startActivity(chooseCategory);
    }
}
