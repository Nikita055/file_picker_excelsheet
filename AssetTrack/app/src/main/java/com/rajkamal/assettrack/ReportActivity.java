package com.rajkamal.assettrack;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.apache.poi.ss.usermodel.CellStyle.ALIGN_CENTER;


public class ReportActivity extends Activity implements View.OnClickListener {
    ArrayList<String[]> ItemMasterData ,scannedData,missingData;
    DatabaseHelper helper=new DatabaseHelper(this);
    String[] reportData,scanned,missing;
    TableLayout tabLay;
    Button export;
    RadioButton pre,scan,miss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);



        tabLay=(TableLayout)findViewById(R.id.table);
        ItemMasterData = new ArrayList();
        scannedData = new ArrayList();
        missingData= new ArrayList();
        export=(Button)findViewById(R.id.exp);
        pre=(RadioButton)findViewById(R.id.preCheck);
        scan=(RadioButton)findViewById(R.id.currCheck);
        miss=(RadioButton)findViewById(R.id.MissCheck);

        addHeaders();
        ItemMasterData=helper.getItemMasterData();
        addAllData();

    }

    public void resetField(View view) {
    }

    @Override
    public void onBackPressed() {


        androidx.appcompat.app.AlertDialog dialog = new androidx.appcompat.app.AlertDialog.Builder(ReportActivity.this,R.style.CustomDialogTheme)
                // after calling setter methods
                .setTitle("Asset Tracking ")
                .setMessage("Return to Menu ")
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
    @NonNull
    private TableRow.LayoutParams getLayoutParams() {
        TableRow.LayoutParams params = new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT);
        params.setMargins(2, 0, 0, 2);
        return params;
    }

    @NonNull
    private TableLayout.LayoutParams getTblLayoutParams() {
        return new TableLayout.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT);
    }
    public void addHeaders() {
        TableLayout tl = (TableLayout) findViewById(R.id.table);
        TableRow tr = new TableRow(this);
        tr.setLayoutParams(getLayoutParams());
        tr.addView(getTextView(0, "SrNo", Color.WHITE, Typeface.BOLD,  ContextCompat.getColor(this, R.color.colorPrimary)));
        tr.addView(getTextView(0, "ItemCode", Color.WHITE, Typeface.BOLD, ContextCompat.getColor(this, R.color.colorPrimary)));
        tr.addView(getTextView(0, "Description", Color.WHITE, Typeface.BOLD, ContextCompat.getColor(this, R.color.colorPrimary)));
        tr.addView(getTextView(0, "DATE & TIME ", Color.WHITE, Typeface.BOLD,ContextCompat.getColor(this, R.color.colorPrimary)));
        tl.addView(tr, getTblLayoutParams());
    }
    private TextView getTextView(int id, String title, int color, int typeface, int bgColor) {
        TextView tv = new TextView(this);
        tv.setId(id);
        tv.setText(title);
        tv.setTextColor(color);
        tv.setPadding(40, 40, 40, 40);
        tv.setTypeface(Typeface.DEFAULT, typeface);
        tv.setBackgroundColor(bgColor);
        tv.setLayoutParams(getLayoutParams());
        tv.setOnClickListener(ReportActivity.this);
        return tv;
    }

    @Override
    public void onClick(View v) {

    }



    public void addAllData() {
        int numCompanies = ItemMasterData.size();
        if (ItemMasterData.size()<=0){
            export.setVisibility(View.GONE);

        }
        TableLayout tl = (TableLayout) findViewById(R.id.table);

        for (int i = 0; i < numCompanies; i++) {
            reportData=ItemMasterData.get(i);

            TableRow tr = new TableRow(this);
            tr.setLayoutParams(getLayoutParams());
            tr.addView(getTextView(i + 1, String.valueOf(i+1), Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.skyblue)));
            tr.addView(getTextView(i + 2,reportData[0], Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.skyblue)));
            tr.addView(getTextView(i + 3,reportData[1], Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.skyblue)));
            tr.addView(getTextView(i + numCompanies,reportData[2], Color.BLACK, Typeface.NORMAL,ContextCompat.getColor(this, R.color.skyblue)));
            tl.addView(tr, getTblLayoutParams());

        }
    }

    public void addScannedData() {
        int numCompanies = scannedData.size();
        if (scannedData.size()<=0){
            export.setVisibility(View.GONE);

        }
        TableLayout tl = (TableLayout) findViewById(R.id.table);

        for (int i = 0; i < numCompanies; i++) {
            scanned=scannedData.get(i);
            TableRow tr = new TableRow(this);
            tr.setLayoutParams(getLayoutParams());
            tr.addView(getTextView(i + 1, String.valueOf(i+1), Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.skyblue)));
            tr.addView(getTextView(i + 2,scanned[0], Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.skyblue)));
            tr.addView(getTextView(i + 3,scanned[1], Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.skyblue)));
            tr.addView(getTextView(i + numCompanies,scanned[2], Color.BLACK, Typeface.NORMAL,ContextCompat.getColor(this, R.color.skyblue)));
            tl.addView(tr, getTblLayoutParams());

        }
    }
    public void addMissingData() {
        int numCompanies = missingData.size();
        if (missingData.size()<=0){
            export.setVisibility(View.GONE);

        }
        TableLayout tl = (TableLayout) findViewById(R.id.table);

        for (int i = 0; i < numCompanies; i++) {
            missing=missingData.get(i);
            TableRow tr = new TableRow(this);
            tr.setLayoutParams(getLayoutParams());
            tr.addView(getTextView(i + 1, String.valueOf(i+1), Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.skyblue)));
            tr.addView(getTextView(i + 2,missing[0], Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.skyblue)));
            tr.addView(getTextView(i + 3,missing[1], Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.skyblue)));
            tr.addView(getTextView(i + numCompanies,missing[2], Color.BLACK, Typeface.NORMAL,ContextCompat.getColor(this, R.color.skyblue)));
            tl.addView(tr, getTblLayoutParams());

        }
    }

    public void checkBoxClick(View view) {
        switch (view.getId()){
            case R.id.preCheck:{
                export.setVisibility(View.VISIBLE);
                tabLay .removeViews(1,tabLay.getChildCount()-1 );
                ItemMasterData=helper.getItemMasterData();
                addAllData();
                break;
            }
            case R.id.currCheck:{
                export.setVisibility(View.VISIBLE);
                tabLay .removeViews(1,tabLay.getChildCount()-1 );
                scannedData=helper.getScannedData();
                addScannedData();
                break;
            }
            case R.id.MissCheck:{
                export.setVisibility(View.VISIBLE);
                tabLay .removeViews(1,tabLay.getChildCount()-1 );
                missingData=helper.getMissedData();
                addMissingData();
                break;
            }
        }

    }



    public static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            return true;
        }
        return false;
    }


    public static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    private boolean saveExcelFile(String fileName, ArrayList<String[]> list) {
        String path;
        File dir;
        String[] exp;
        List<String> exportData=new ArrayList<>();

        String FolderName="Asset Tracking Reports";
        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
            Log.e("Failed", "Storage not available or read only");
            return false;
        }
        boolean success = false;

        //New Workbook
        Workbook wb = new HSSFWorkbook();

        Cell c = null;
        Cell i = null;
        Cell j = null;

        Cell d=null;
        Cell g=null;
        Cell h=null;
        //Cell style for header row
        CellStyle cs = wb.createCellStyle();
        cs.setFillForegroundColor(HSSFColor.LIME.index);
        cs.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cs.setAlignment(ALIGN_CENTER);

        CellStyle cellStyle = wb.createCellStyle();
        CellStyle cellStyle1 = wb.createCellStyle();
        cellStyle.setAlignment(ALIGN_CENTER);


        //New Sheet
        Sheet sheet1 = null;
        sheet1 = wb.createSheet("Test Report");

        // Generate column headings

        Row row = null;

        row = sheet1.createRow(0);

        Row header=sheet1.createRow(0);
        c=header.createCell(0);
        c.setCellValue("SR.NO");
        c.setCellStyle(cellStyle);
        h=header.createCell(1);
        h.setCellValue("ITEM CODE ");
        h.setCellStyle(cellStyle);
        h=header.createCell(2);
        h.setCellValue("DESCRIPTION ");
        h.setCellStyle(cellStyle);
        h=header.createCell(3);
        h.setCellValue("DATE AND TIME ");
        h.setCellStyle(cellStyle);


        int k = 1;
        for(int m=1;m<=list.size();m++){
            exp=list.get(m-1);

            System.out.println("EX "+exp[1]);
            row = sheet1.createRow(k);
            row = sheet1.createRow(k);
            d = row.createCell(0);
            d.setCellValue(m);
            d.setCellStyle(cellStyle);
            g = row.createCell(1);
            g.setCellValue(exp[0]);
            g.setCellStyle(cellStyle);
            i = row.createCell(2);
            i.setCellValue(exp[1]);
            i.setCellStyle(cellStyle);
            j = row.createCell(3);
            j.setCellValue(exp[2]);
            j.setCellStyle(cellStyle);

            sheet1.setColumnWidth(m, (15 * 500));
            k++;
        }

        File sdCardDir = Environment.getExternalStorageDirectory();
        dir = new File(sdCardDir + "/" + FolderName + "/");

        if (!dir.exists()) {
            dir.mkdir();
            System.out.println("Directory created");
        }
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(dir, fileName);
        FileOutputStream os = null;

        try {
            os = new FileOutputStream(file);
            wb.write(os);
            Log.w("FileUtils", "Writing file" + file);
            success = true;
            Toast.makeText(ReportActivity.this, " File Saved Successfully", Toast.LENGTH_LONG).show();
            MediaScannerConnection.scanFile(ReportActivity.this, new String[] {file.toString()}, null, null); // auto refresh folder in  device

        } catch (IOException e) {
            Log.w("FileUtils", "Error writing " + file, e);
            Toast.makeText(ReportActivity.this, "Error ", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Log.w("FileUtils", "Failed to save file", e);
            Toast.makeText(ReportActivity.this, " Error ", Toast.LENGTH_LONG).show();

        } finally {
            try {
                if (null != os)
                    os.close();
            } catch (Exception ex) {
            }

        }
        return success;
    }

    public void exportClick(View view) {

        if (pre.isChecked()) {

            saveExcelFile("ItemDetails.csv", ItemMasterData);
        }
        if (scan.isChecked()) {

            saveExcelFile("ScannedItemDetails.csv", scannedData);
        }
        if (miss.isChecked()) {

            saveExcelFile("MissedItem.csv", missingData);
        }
    }
}
