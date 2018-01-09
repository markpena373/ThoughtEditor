package com.example.android.test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class CategoriesGrid extends AppCompatActivity {
    public Toolbar categoriesToolBar;
    private final String CATEGORIES_DIR_NAME = "categories";
    private ArrayList<String> categories;
    private String[] categoryNames;
    private Intent intent;
    private GridArrayAdapter categoriesAdapter;
    private GridView grid;
    private PopupWindow newCategoryPopupWindow;
    private String titleText, contentText;
    View popupView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories_grid);
        //setting up toolbar
        categoriesToolBar = (Toolbar) findViewById(R.id.categories_toolbar);
        categoriesToolBar.setTitle("");
        //categories = new ArrayList<String>();
        setSupportActionBar(categoriesToolBar);
        //Get intent that started activitity
        intent = getIntent();
        titleText = intent.getStringExtra("title");
        contentText = intent.getStringExtra("content");
        parseCategories();
        fillCategoryGrid();
        Bundle extras = intent.getExtras();
        if(extras != null){
            chooseCategory();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.categories_menu, menu);
        return true;
    }

    @Override
    public void onBackPressed(){
        finish();
    }

    public void toThoughtEditor(MenuItem item){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("title", titleText);
        intent.putExtra("content", contentText);
        startActivity(intent);
        finish();
    }

    public void parseCategories(){
        File categoriesDir = new File(this.getFilesDir(), CATEGORIES_DIR_NAME);
        boolean dirExists = categoriesDir.exists();
        boolean dirCreated;
        if(!dirExists){
            dirCreated = categoriesDir.mkdirs();
            System.out.println(dirCreated);
        }
        //parse /categories directory for category names
        categoryNames = categoriesDir.list();
        categories = new ArrayList<String>(Arrays.asList(categoryNames));
    }

    public void fillCategoryGrid(){
        //fills in the grid with buttons based on how many categories there are
        grid = (GridView) findViewById(R.id.category_grid);
        categoriesAdapter = new GridArrayAdapter(this, R.layout.categories_grid_button, categories);
        grid.setAdapter(categoriesAdapter);
    }

    public void chooseCategory(){
        //implement method to exit back to thought editor when youve chosen a category to save new entry in
        String title = intent.getStringExtra("title");
        String content = intent.getStringExtra("content");
        TextView titleView = findViewById(R.id.category_grid_title);
        titleView.setText(R.string.choose_category_title);
        categoriesAdapter.passInfo(title, content);
    }

    public void addCategory(View view){
        LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        popupView = inflater.inflate(R.layout.new_category_popup_window, null);
        newCategoryPopupWindow = new PopupWindow(popupView, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT, true);
        newCategoryPopupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
    }

    public void popupCancel(View view){
        newCategoryPopupWindow.dismiss();
    }

    public void popupSubmit(View view){
        RelativeLayout parent = (RelativeLayout) findViewById(R.id.category_grid_parent);
        EditText editText = (EditText) popupView.findViewById(R.id.new_category_name);
        if(editText.getText().toString().isEmpty()){
            Toast.makeText(this, "Please enter a category name", Toast.LENGTH_SHORT).show();
            return;
        }else{
            new Category(this, editText.getText().toString());
            categoriesAdapter.add(editText.getText().toString());
        }
        newCategoryPopupWindow.dismiss();
    }
}
