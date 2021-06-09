package com.rajkamal.assettrack;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.thanosfisherman.mayi.Mayi;

public class LoginActivity extends Activity {
    EditText userEdt,passEdt;
    String username="",password="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userEdt=(EditText)findViewById(R.id.username);
        passEdt=(EditText)findViewById(R.id.password);


        Mayi.withActivity(LoginActivity.this)
                .withPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE
                        ,Manifest.permission.CAMERA,Manifest.permission.INTERNET)
                .check();


    }

    public void loginClick(View view) {


        if (userEdt.getText().toString().trim().equals("")||
                passEdt.getText().toString().trim().equals("")){
            Toast.makeText(LoginActivity.this,"All Fields are Required",Toast.LENGTH_LONG).show();
        }
        else if (userEdt.getText().toString().trim().equals("admin")||
                passEdt.getText().toString().trim().equals("123")){
           startActivity(new Intent(LoginActivity.this, MenuActivity.class));
           finish();
        }else{
            Toast.makeText(LoginActivity.this,"Wrong Credentials",Toast.LENGTH_LONG).show();
            userEdt.setText("");
            passEdt.setText("");
            userEdt.requestFocus();


        }


    }
    }

