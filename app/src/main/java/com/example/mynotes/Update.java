package com.example.mynotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Update extends AppCompatActivity {

    String receiveTitle;
    String receiveDesc,receivedCname,uri;
    int receivedId;
    int receivedCpos = 0;
    EditText title;
    EditText desc;
    Databasehelper databasehelper;
    String titl;
    String description;
    MediaPlayer mediaPlayer;
    Uri myUri ;
    SimpleDateFormat dateFormat;
    String currentTimeStamp;
    ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        databasehelper = new Databasehelper(this);
        imageView = findViewById(R.id.img);

        Intent intent = getIntent();
        title = findViewById(R.id.editTextTextPersonName2);
         desc = findViewById(R.id.editTextTextPersonName3);

        if(intent != null){

            receiveTitle = intent.getStringExtra("title");
            receiveDesc = intent.getStringExtra("desc");
            receivedId = intent.getIntExtra("id",0);
            receivedCname = intent.getStringExtra("category");
            receivedCpos = intent.getIntExtra("cpos",0);
            uri = intent.getStringExtra("uri");

            Toast.makeText(Update.this,uri,Toast.LENGTH_SHORT).show();


            title.setText(receiveTitle);
            desc.setText(receiveDesc);
            

        }
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                myUri = Uri.parse(uri);

                Toast.makeText(getApplicationContext(), uri, Toast.LENGTH_LONG).show();

                mediaPlayer = new MediaPlayer();
                try {
                    mediaPlayer.setDataSource(String.valueOf(myUri));
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                    Toast.makeText(getApplicationContext(), "Recording Started Playing", Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    Log.e("heyyyyyy", "prepare() failed");
                }
            }
        });

    }


    public boolean onCreateOptionsMenu(Menu menu) {
        // show menu when menu button is pressed
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.update_menu, menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection

        int i = 0;
        if(item.getItemId() == R.id.update){



            titl = title.getText().toString();
            description = desc.getText().toString();

            boolean data = databasehelper.update(titl,description,receivedId,receivedCname,getCurrentTimeStamp());

            if(data == true){

                Toast.makeText(Update.this,"Note updated!",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Update.this,NotesRecyclerViews.class);
                intent.putExtra("category",receivedCpos);
                startActivity(intent);
            }else{
                Toast.makeText(Update.this,"Note not updated!",Toast.LENGTH_SHORT).show();

            }
        }else if(item.getItemId() == R.id.back){

            Intent intent = new Intent(Update.this, NotesRecyclerViews.class);
            intent.putExtra("category",receivedCname);
            startActivity(intent);

        }



        return super.onOptionsItemSelected(item);
    }

    public String getCurrentTimeStamp() {
        try {
            dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            currentTimeStamp = dateFormat.format(new Date()); // Find todays date
            return currentTimeStamp;

        } catch (Exception e) {
            e.printStackTrace();


        }
        return null;
    }
}