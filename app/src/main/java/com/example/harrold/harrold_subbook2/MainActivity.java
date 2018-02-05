package com.example.harrold.harrold_subbook2;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
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

public class MainActivity extends AppCompatActivity {

    private ListView oldSubsList;
    private static final String FILENAME = "subs.sav";

    private String tempName;
    private String tempDate;
    private float tempCharge;
    private String tempComment;
    private int latestEditIndex;
    private float monthlyTotal;

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

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_OK);
                subsList.clear();
                subsAdapter.notifyDataSetChanged();
                saveInFile();
            }
        });

        adderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent addIntent = new Intent(MainActivity.this, AddActivity.class);
                startActivityForResult(addIntent, 1);
            }
        });

        oldSubsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String fillerName = subsList.get(i).getSubName();
                String fillerDate = subsList.get(i).getSubDate();
                float fillerCharge = subsList.get(i).getSubCharge();
                String fillerComment = subsList.get(i).getSubComment();

                latestEditIndex = i;
                Intent editIntent = new Intent(MainActivity.this, EditActivity.class);

                Bundle bundle = new Bundle();
                bundle.putString("fillerName", fillerName);
                bundle.putString("fillerDate", fillerDate);
                bundle.putFloat("fillerCharge", fillerCharge);
                bundle.putString("fillerComment", fillerComment);

                editIntent.putExtra("fillerBundle", bundle);

                startActivityForResult(editIntent, 2, bundle);

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
        } catch(IOException e) {
            throw new RuntimeException();
        }
    }

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

    /* https://stackoverflow.com/questions/10407159/how-to-manage-startactivityforresult-on-android 2018-02-05 */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            tempReset();
            tempName = (String) data.getExtras().get("name");
            tempDate = (String) data.getExtras().get("date");
            tempCharge = (Float) data.getExtras().get("charge");
            tempComment = (String) data.getExtras().get("comment");

            if(resultCode == Activity.RESULT_OK){

                Sub newSub = new Sub(tempName, tempDate, tempCharge, tempComment);
                subsList.add(newSub);
                subsAdapter.notifyDataSetChanged();
                saveInFile();

            } else if(resultCode == 2) {

                subsList.get(latestEditIndex).setName(tempName);
                subsList.get(latestEditIndex).setDate(tempDate);
                subsList.get(latestEditIndex).setCharge(tempCharge);
                subsList.get(latestEditIndex).setSubComment(tempComment);

                subsAdapter.notifyDataSetChanged();
                saveInFile();

            } else if(resultCode == 3) {
                subsList.remove(latestEditIndex);
            }
        }
    }

    private void tempReset() {
        tempName = "";
        tempDate = "";
        tempCharge = 0;
        tempComment = "";
    }

    private void updateTotal() {
        for (int i = 0; i < subsList.size(); i++) {
            monthlyTotal += subsList.get(i).getSubCharge();
        }
    }
}
