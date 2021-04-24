package com.example.mynotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class NotesRecyclerViews extends AppCompatActivity {

    Databasehelper databaseHelper;
    NotesAdapter adapter;
    ContextCompat contextCompat;
    List<model> mdata;
    model model;
    int id = 0;
    int category = 0;
    String cName = "";
    String ctName = "";
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_recycler_views);

        Bundle extras = getIntent().getExtras();

        if (extras.getInt("category") >= 0) {
            category = extras.getInt("category");
             if(category == 0){

                 cName = "punjabi";

             }else if(category == 1){

                 cName = "arts";

             }else if(category == 2){

                 cName = "math";

             }else if(category == 3){

                 cName = "french";

             }else if(category == 4){

                 cName = "computer";

             }else if(category == 5){

                 cName = "science";

             }
        }

        //mdata = new ArrayList<>();

        databaseHelper = new Databasehelper(this);

        mdata = new ArrayList<model>();

        int i = 0;
       // mdata = databaseHelper.getNotes(cName).get(i).getTitle();


        databaseHelper = new Databasehelper(this);
        recyclerView = findViewById(R.id.recyclerView1);

        adapter = new NotesAdapter(this, databaseHelper.getNotes(cName));

        //Log.e("jhgjhghj", String.valueOf(adapter));
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));



    }

    public void fab(View view) {

        Intent intent = new Intent(NotesRecyclerViews.this, MainActivity.class);
        intent.putExtra("category", category);
        intent.putExtra("categoryName", cName);
        startActivity(intent);
        //Toast.makeText(NotesRecyclerViews.this,cName,Toast.LENGTH_SHORT).show();



    }


    public boolean onCreateOptionsMenu(Menu menu) {
        // show menu when menu button is pressed
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        Log.i("kkkkkkkkkkkkkkkkkk", String.valueOf(mdata));

        int i = 0;
        if(item.getItemId() == R.id.feeds){

            databaseHelper = new Databasehelper(this);

            adapter = new NotesAdapter(this, databaseHelper.alphaTitle(cName));

            adapter.notifyDataSetChanged();

            recyclerView.setAdapter(adapter);


            Log.i("gggggggggggggggggg", String.valueOf(adapter));
        }else if(item.getItemId() == R.id.sortDate){

            databaseHelper = new Databasehelper(this);

            adapter = new NotesAdapter(this, databaseHelper.alphaDate());

            adapter.notifyDataSetChanged();

            recyclerView.setAdapter(adapter);


        }
                return super.onOptionsItemSelected(item);
        }
    }







