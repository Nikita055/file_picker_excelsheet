package com.rbs.filepicker;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.jaiselrahman.filepicker.activity.FilePickerActivity;
import com.jaiselrahman.filepicker.config.Configurations;
import com.jaiselrahman.filepicker.model.MediaFile;

import java.util.ArrayList;

//https://www.youtube.com/watch?v=wKYjNmgSPRU
public class MainActivity extends AppCompatActivity {
    //initialize variable
    Button btImage,btFile,btAudio,btVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Assign variable
        btImage = findViewById(R.id.btn_image);
        btFile = findViewById(R.id.btn_file);
        btAudio = findViewById(R.id.btn_audio);
        btVideo = findViewById(R.id.btn_video);

        btImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check condition
                if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED){
                //When permission not granted
                //Request permission
                ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.CAMERA},1);
            }else{
                    //when permission
                    //create image picker method
                    imagePicker();
                }
            }
        });

        btFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //initialize Intent
                Intent intent = new Intent(MainActivity.this,FilePickerActivity.class);
                //put Extra
                intent.putExtra(FilePickerActivity.CONFIGS, new Configurations.Builder()
                .setCheckPermission(true)
                .setShowFiles(true)
                .setShowImages(false)
                .setShowVideos(false)
                .setMaxSelection(1)
                .setSuffixes("txt","pdf","docx","xlsx")
                .setSkipZeroSizeFiles(true)
                .build());
                //start activity result
              startActivityForResult(intent,102);
            }
        });

        btAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //initialize intent
                Intent intent = new Intent(MainActivity.this,FilePickerActivity.class);
                //put extra
                    intent.putExtra(FilePickerActivity.CONFIGS,new Configurations.Builder()
                    .setCheckPermission(true)
                    .setShowAudios(true)
                    .setShowImages(false)
                    .setShowVideos(false)
                    .setMaxSelection(1)
                    .setSkipZeroSizeFiles(true)
                    .build());
                    //Start activity result
                startActivityForResult(intent,103);
            }
        });

        btVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Check condition
                if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED){
                    //When permission not granted
                    //Request permission
                    ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.CAMERA},1);
                }else {
                    //when permission
                    //create image picker method
                   VideoPicker();
                }
                }
        });

    }

    private void VideoPicker() {
        //initialize intent
        Intent intent = new Intent(MainActivity.this,FilePickerActivity.class);
        //put Extra
        intent.putExtra(FilePickerActivity.CONFIGS, new Configurations.Builder()
        .setCheckPermission(true)
        .setShowVideos(true)
        .setShowImages(false)
        .enableVideoCapture(true)
        .setMaxSelection(1)
        .setSkipZeroSizeFiles(true)
        .build());

        //start activity result
        startActivityForResult(intent,101);
    }

    private void imagePicker() {
        //initialize intent
        Intent intent = new Intent(MainActivity.this, FilePickerActivity.class);
        //put Extra
        intent.putExtra(FilePickerActivity.CONFIGS, new Configurations.Builder()
                .setCheckPermission(true)
                .setShowImages(true)
                .setShowVideos(false)
                .enableImageCapture(true)
                .setMaxSelection(1)
                .setSkipZeroSizeFiles(true)
                .build());

        //start activity result
        startActivityForResult(intent,101);

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        //check condition
        if((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED))
        {
            //when permission granted
            //check condition
            if(requestCode == 1)
            {
                //when request code is 1
                // call image picker method
                imagePicker();
            }else
            {
                //when request code is 2
                // call video call method
                VideoPicker();
            }
        }else{
            //when permission denied
            //Display Toast
            Toast.makeText(getApplicationContext(),"Permission Denied",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //check condition
        if(requestCode == RESULT_OK && data != null){
            //when result code is ok and data is not equal to null
            //Initialize array list
            ArrayList<MediaFile> mediaFiles = data.getParcelableArrayListExtra(FilePickerActivity.MEDIA_FILES);
            //Get String path
            String path = mediaFiles.get(0).getPath();
            //check condition
            switch (requestCode){
                case 101:
                    //when request code is equal to image code
                    //Initialize toast message
                    String s = "Image path : " + path;
                    //create toast
                    displayToast(s);
                    break;
                case 102:
                    //when request code is equal to file code
                    displayToast("File path: " + path);
                    break;
                case 103:
                    //when request code is equal to audio code
                    displayToast("Audio path: " + path);
                    break;
                case 104:
                    //when request code is equal to video code
                    displayToast("Video path: " + path);
                    break;
            }
        }
    }

    private void displayToast(String s){
        //initialize and show toast

    }

}