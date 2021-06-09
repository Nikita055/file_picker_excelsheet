package com.rajkamal.assettrack;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;
import com.opencsv.CSVReader;

import java.io.File;
import java.io.FileReader;
import java.util.regex.Pattern;

public class MenuActivity extends Activity {
    String[] nextLine;

    DatabaseHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        helper=new DatabaseHelper(this);

     ;
    }

    public void stockClick(View view) {
        startActivity(new Intent(MenuActivity.this,StockActivity.class));

    }

    public void uploadClick(View view) {


        new MaterialFilePicker()
                .withActivity(this)
                .withRequestCode(1)
                .withFilter(Pattern.compile(".*\\.csv$",Pattern.CASE_INSENSITIVE)) // Filtering files and directories by file name using regexp
                .withFilterDirectories(false) // Set directories filterable (false by default)
                .withHiddenFiles(false) // Show hidden files and folders
                .start();

 /*
        Intent chooseFile;
        Intent intent;
        chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
        chooseFile.setType("text/csv");
        intent = Intent.createChooser(chooseFile, "Choose a CSV");
        startActivityForResult(intent, 1);*/



    }

    public void reportClick(View view) {
        startActivity(new Intent(MenuActivity.this,ReportActivity.class));

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println("code"+requestCode+"  "+resultCode);

        if (requestCode == 1 && resultCode == RESULT_OK) {
           String filePath = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
            onImport(new File(filePath));
        }



    }

    public void onImport(File files) {
        try {
            CSVReader reader = new CSVReader(new FileReader(files));
             nextLine = null;
            while (( nextLine = reader.readNext())!= null) {
                // nextLine[] is an array of values from the line
                String itemCode=nextLine[0];
                String desc=nextLine[1];
                if (!helper.checkSerialNoExists(itemCode)) {
                    helper.AddItemDetails(itemCode, desc);
                }else {
                    androidx.appcompat.app.AlertDialog dialog = new androidx.appcompat.app.AlertDialog.Builder(MenuActivity.this,R.style.CustomDialogTheme)
                            // after calling setter methods
                            .setTitle("Asset Tracking ")
                            .setMessage("Duplicate Data ")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {


                                }
                            })
                            .setNegativeButton("No",new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }

                            })
                            .create();
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.show();

                }

                System.out.println("DATA"+nextLine[1] + "etc...");
                Toast.makeText(this, "File Uploaded Successfully", Toast.LENGTH_SHORT).show();


            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Menu",e.getMessage());

            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onBackPressed() {

        androidx.appcompat.app.AlertDialog dialog = new androidx.appcompat.app.AlertDialog.Builder(MenuActivity.this,R.style.CustomDialogTheme)
                // after calling setter methods
                .setTitle("Asset Tracking ")
                .setMessage("Return to Login Page?")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(MenuActivity.this,LoginActivity.class));
                        finish();

                    }
                })
                .setNegativeButton("No",new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }

                })
                .create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();


    }

}
