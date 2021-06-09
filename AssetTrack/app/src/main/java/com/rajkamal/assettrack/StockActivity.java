package com.rajkamal.assettrack;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;

import com.google.android.gms.vision.barcode.Barcode;

import java.util.ArrayList;
import java.util.List;

public class StockActivity extends Activity {

    EditText scanEdit;

    DatabaseHelper helper=new DatabaseHelper(this);
 int REQUEST_CODE=100;
    CardView cardView;
    TextView itemCode,desc,dt;
    List<String> ItemData;
    ArrayList<String[]> itemDetails;

    String serialNo="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);


        scanEdit=(EditText)findViewById(R.id.stockEdit);
        cardView=(CardView)findViewById(R.id.cardDetails);
        itemCode=(TextView)findViewById(R.id.itemcode);
        desc=(TextView)findViewById(R.id.desc);
        dt=(TextView)findViewById(R.id.date);



        ItemData=new ArrayList<>();

        scanEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                cardView.setVisibility(View.GONE);
            if (!scanEdit.getText().toString().trim().equals("")) {
                serialNo = scanEdit.getText().toString().trim();
                if (serialNo.length()==10){
                if (helper.checkSerialNoExists(serialNo)) {
                    if (!helper.checkItemScanned(serialNo)) {
                        helper.updateDetails(serialNo);
                        ItemData=helper.getSingleItemData(serialNo);
                        cardView.setVisibility(View.VISIBLE);
                        itemCode.setText(ItemData.get(0));
                        desc.setText(ItemData.get(1));
                        dt.setText(ItemData.get(2));


                    }else {
                       /* new AlertDialog.Builder(StockActivity.this)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setTitle("Confirm")
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener()
                                {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        scanEdit.setText("");
                                    }

                                })
                                .setMessage("Already Scanned !")
                                .show();*/

                        androidx.appcompat.app.AlertDialog dialog = new androidx.appcompat.app.AlertDialog.Builder(StockActivity.this,R.style.CustomDialogTheme)
                                // after calling setter methods
                                .setTitle("Asset Tracking ")
                                .setMessage("Already Scanned ! ")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        scanEdit.setText("");

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
                }else {
                 /*   new AlertDialog.Builder(StockActivity.this)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("Confirm")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                   scanEdit.setText("");
                                }

                            })
                            .setMessage("No Records Found !")
                            .show();*/

                    androidx.appcompat.app.AlertDialog dialog = new androidx.appcompat.app.AlertDialog.Builder(StockActivity.this,R.style.CustomDialogTheme)
                            // after calling setter methods
                            .setTitle("Asset Tracking ")
                            .setMessage("No Records Found ! ")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    scanEdit.setText("");

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


                }}else
                if (serialNo.length()>10){
                    androidx.appcompat.app.AlertDialog dialog = new androidx.appcompat.app.AlertDialog.Builder(StockActivity.this,R.style.CustomDialogTheme)
                            // after calling setter methods
                            .setTitle("Asset Tracking ")
                            .setMessage("Invalid Barcode ! ")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    scanEdit.setText("");

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
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }





    @Override
    public void onBackPressed() {
       /* new AlertDialog.Builder(StockActivity.this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Confirm")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                })
                .setNegativeButton("No",new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }

                })
                .setMessage("Return to Menu?")
                .show();*/

        androidx.appcompat.app.AlertDialog dialog = new androidx.appcompat.app.AlertDialog.Builder(StockActivity.this,R.style.CustomDialogTheme)
                // after calling setter methods
                .setTitle("Asset Tracking ")
                .setMessage("Return to Menu? ")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
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

    public void scan(View view) {

        scanEdit.setText("");
        cardView.setVisibility(View.GONE);
        Intent intent = new Intent(StockActivity.this, BarcodeScanner.class);
        startActivityForResult(intent, REQUEST_CODE);

    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK){
            if(data != null){
                final Barcode barcode = data.getParcelableExtra("barcode");

                String barcodeValue=barcode.displayValue;
                System.out.println("value"+barcodeValue);
                scanEdit.setText(barcodeValue);




            }
        }
    }
}
