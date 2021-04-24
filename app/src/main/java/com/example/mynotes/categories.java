package com.example.mynotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.List;

public class categories extends AppCompatActivity {



    List<String> list;
    ImageAdapter adapter;
    int[] imageId = {
            R.drawable.ic_baseline_4k_24,
            R.drawable.ic_baseline_account_balance_24,
            R.drawable.ic_baseline_add_24,
            R.drawable.ic_baseline_add_location_24,
            R.drawable.ic_baseline_add_comment_24,
            R.drawable.ic_baseline_airline_seat_recline_extra_24,
    };
    String[] web = {
            "Google",
            "Github",
            "Instagram",
            "Facebook",
            "Flickr",
            "Pinterest",
            "Quora",
            "Twitter",
            "Vimeo",
            "WordPress",
            "Youtube",
            "Stumbleupon",
            "SoundCloud",
            "Reddit",
            "Blogger"

    } ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        adapter = new ImageAdapter(this,web,imageId);
        GridView grid=findViewById(R.id.grid_view);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                String selectedItem = parent.getItemAtPosition(position).toString();
                Toast.makeText(categories.this,selectedItem,Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(categories.this,NotesRecyclerViews.class);
                intent.putExtra("category",position);
                startActivity(intent);


            }
        });

    }
}