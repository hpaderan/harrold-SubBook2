package com.example.harrold.harrold_subbook2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * MainActivity
 *
 * Version 1.0
 *
 * Created by Harrold on 2018-02-05.
 *
 * This activity keeps information of main list and displays it on ListView
 *  - ArrayList<Sub> subsList - this is the main list where all Sub
 *                              objects will be kept
 *  - ArrayAdapter<Sub> subsAdapter - used by ListView to display
 *                              information from subsList
 */

public class MainActivity extends AppCompatActivity {

    private ListView oldSubsList;
    private static final String FILENAME = "subs.sav";

    private String tempName;
    private String tempDate;
    private String tempCharge;
    private String tempComment;

    private ArrayList<Sub> subsList;
    private ArrayAdapter<Sub> subsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        oldSubsList = (ListView) findViewById(R.id.subs_list);
        Button adderButton = (Button) findViewById(R.id.add_button);
        Button clearButton = (Button) findViewById(R.id.temp_clear);

        tempReset();

        /**
         *  On click of the clearButton, subList will be cleared
         *  *NOT required for assignment.
         */
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_OK);
                subsList.clear();
                subsAdapter.notifyDataSetChanged();
                saveInFile();
            }
        });

        /**
         * On click of the adder button, AddActivity will open,
         *  this will wait for result_code and call onActivityResult
         */
        adderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent addIntent = new Intent(MainActivity.this, AddActivity.class);
                startActivityForResult(addIntent, 1);
            }
        });

        /**
         * On click of individual item in the ListView, this will bundle information
         *  about the specific Sub object and open EditActivity will open,
         *  this will wait for result_code and call onActivityResult.
         */
        oldSubsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String fillerName = subsList.get(i).getSubName();
                String fillerDate = subsList.get(i).getSubDate();
                String fillerCharge = subsList.get(i).getSubCharge();
                String fillerComment = subsList.get(i).getSubComment();

                Intent editIntent = new Intent(MainActivity.this, EditActivity.class);

                Bundle bundle = new Bundle();
                bundle.putInt("pos", i);
                bundle.putString("fillerName", fillerName);
                bundle.putString("fillerDate", fillerDate);
                bundle.putString("fillerCharge", fillerCharge);
                bundle.putString("fillerComment", fillerComment);

                editIntent.putExtra("fillerBundle", bundle);

                startActivityForResult(editIntent, 2);

            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();

        loadFromFile();

        subsAdapter = new ArrayAdapter<Sub>(this, R.layout.list_item, subsList);
        oldSubsList.setAdapter(subsAdapter);
    }

    /**
     * loads saved information from FILENAME
     */
    private void loadFromFile() {

        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();

            // Taken https://stackoverflow.com/questions/12384064/gson-convert-from-json-to-a-typed-arraylistt
            // 2018-01-23
            Type listType = new TypeToken<ArrayList<Sub>>(){}.getType();
            subsList = gson.fromJson(in, listType);

        } catch(FileNotFoundException e) {
            subsList = new ArrayList<Sub>();
        }
    }

    /**
     * recognizes changes in the list and saves
     */
    private void saveInFile() {
        try {

            FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));

            Gson gson = new Gson();
            gson.toJson(subsList, out);
            out.flush();

        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    /**
     * Waits for result_code from other Activity and acts accordingly.
     *
     * @param requestCode
     * @param resultCode - specified int sent by other Activity
     * @param data - bundle of data
     */
    /* https://stackoverflow.com/questions/10407159/how-to-manage-startactivityforresult-on-android 2018-02-05 */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            tempReset();
           // tempName = (String) data.getExtras().get("name");
            tempName = getStringFromBundle("name", data);
            tempDate = getStringFromBundle("date", data);
            tempCharge = getStringFromBundle("charge", data);
            tempComment = getStringFromBundle("comment", data);
            int pos = data.getExtras().getInt("posBack");

            if(resultCode == Activity.RESULT_OK){

                Sub newSub = new Sub(tempName, tempDate, tempCharge, tempComment);
                subsList.add(newSub);
                subsAdapter.notifyDataSetChanged();
                saveInFile();

            } else if(resultCode == 2) {

                subsList.get(pos).setName(tempName);
                subsList.get(pos).setDate(tempDate);
                subsList.get(pos).setCharge(tempCharge);
                subsList.get(pos).setSubComment(tempComment);

                subsAdapter.notifyDataSetChanged();
                saveInFile();

            } else if(resultCode == 3) {
                subsList.remove(pos);
            }
        }
    }

    /**
     * Empties temp string identifiers
     */
    private void tempReset() {
        String empty = "";
        tempName = empty;
        tempDate = empty;
        tempCharge = empty;
        tempComment = empty;
    }

    /**
     * Unbundles data and returns the String that goes with specified dataTag.
     *
     * @param dataTag - String parameter that is paired with desired information
     * @param data - bundle of data
     *
     * @return String from data that is paired with dataTags
     */
    private String getStringFromBundle(String dataTag, Intent data) {
        String newString;

        try {
            newString = data.getExtras().getString(dataTag);
        } catch(NullPointerException e) {
            newString = "";
        }

        return newString;
    }
}
