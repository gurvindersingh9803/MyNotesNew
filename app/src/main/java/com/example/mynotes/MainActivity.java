package com.example.mynotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity {

    EditText titl;
    EditText desc;
    Databasehelper databaseHelper;
    String title;
    String description;
    String cName = "";
    int cpos = 0;
    Uri uri;
    SimpleDateFormat dateFormat;
    String currentTimeStamp;
    ContentValues contentValues;
    Uri a;
    Drawable unwrappedDrawable;
    Drawable wrappedDrawable;



    ImageView imageView, imageView1;
    private MediaRecorder mRecorder;
    private MediaPlayer mPlayer;
    private static final String LOG_TAG = "AudioRecording";
    private static String mFileName = null;
    public static final int REQUEST_AUDIO_PERMISSION_CODE = 1;
    byte[] byteAudio = null;
    Intent intent;
    String date = getTimeStamp();






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            cName = extras.getString("categoryName");
            cpos = extras.getInt("category");

        }
        databaseHelper = new Databasehelper(this);


        titl = findViewById(R.id.editTextTextPersonName);
        desc = findViewById(R.id.editTextTextMultiLine);


        imageView = findViewById(R.id.images);
        imageView1 = findViewById(R.id.image1);

        imageView1.setEnabled(false);
        mFileName = Environment.getExternalStorageDirectory().
                getAbsolutePath() + "/rec" + getCurrentTimeStamp() + "records.mp3";


        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        mRecorder.setOutputFile(mFileName);

        imageView.setOnClickListener(new View.OnClickListener() {
            // boolean mStartRecording = true;
            @Override
            public void onClick(View v) {

                startRecording();

            }
        });


        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopRecording();

            }
        });
    }

    protected void stopRecording() {
        try {
            mRecorder.stop();
            mRecorder.reset();
            mRecorder.release();

            mRecorder = null;

        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            // no valid audio/video data has been received
            e.printStackTrace();
        }
        imageView1.setBackgroundColor(Color.RED);
        String c = "#D0F0C0";
        imageView.setBackgroundColor(Color.parseColor(c));
        imageView.setEnabled(true);
        //plays.setEnabled(true);
        Toast.makeText(getApplicationContext(), "Recording Stopped", Toast.LENGTH_SHORT).show();
        //refresh();
    }

    public void refresh() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    protected void startRecording() {
        try {
            mRecorder.prepare();
            mRecorder.start();

            a =Uri.parse(mFileName);


        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //plays.setEnabled(false);
        imageView1.setEnabled(true);
        imageView.setBackgroundColor(Color.RED);

        Toast.makeText(getApplicationContext(), "Recording...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_AUDIO_PERMISSION_CODE:
                if (grantResults.length> 0) {
                    boolean permissionToRecord = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean permissionToStore = grantResults[1] ==  PackageManager.PERMISSION_GRANTED;
                    if (permissionToRecord && permissionToStore) {
                        Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(),"Permission Denied",Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }
    public boolean CheckPermissions() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), RECORD_AUDIO);
        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
    }
    private void RequestPermissions() {
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{RECORD_AUDIO, WRITE_EXTERNAL_STORAGE}, REQUEST_AUDIO_PERMISSION_CODE);
    }





    public void go(View view){

        Intent intent = new Intent(MainActivity.this, NotesRecyclerViews.class);
        startActivity(intent);
    }

    public String getCurrentTimeStamp() {
        try {
            dateFormat = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss");
            currentTimeStamp = dateFormat.format(new Date()); // Find todays date
            return currentTimeStamp;

        } catch (Exception e) {
            e.printStackTrace();


        }
        return null;
    }

    public String getTimeStamp() {
        try {
            dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            currentTimeStamp = dateFormat.format(new Date()); // Find todays date
            return currentTimeStamp;

        } catch (Exception e) {
            e.printStackTrace();


        }
        return null;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // show menu when menu button is pressed
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_menu, menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection

        int i = 0;
        if(item.getItemId() == R.id.save){



                title = titl.getText().toString();
                description = desc.getText().toString();

                Boolean data = databaseHelper.addNotes(title, description,cName,cpos,String.valueOf(a),date);


                if (data == true) {

                    Toast.makeText(MainActivity.this, "Note Inserted in " +  cName , Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(MainActivity.this, NotesRecyclerViews.class);
                    intent.putExtra("category",cpos);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "no insertion", Toast.LENGTH_LONG).show();

                }
            }else if(item.getItemId() == R.id.back){

            Intent intent = new Intent(MainActivity.this, NotesRecyclerViews.class);
            intent.putExtra("category",cpos);
            startActivity(intent);

        }



        return super.onOptionsItemSelected(item);
    }
    }










