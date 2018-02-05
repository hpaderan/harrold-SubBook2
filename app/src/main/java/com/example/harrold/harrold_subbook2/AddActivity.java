package com.example.harrold.harrold_subbook2;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * AddActivity
 *
 * Version 1.0
 *
 * 2018-Feb-5
 */

public class AddActivity extends AppCompatActivity {

    private EditText nameEditText;
    private EditText dateEditText;
    private EditText chargeEditText;
    private EditText commentEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        nameEditText = (EditText) findViewById(R.id.name_edit_text);
        dateEditText = (EditText) findViewById(R.id.date_edit_text);
        chargeEditText = (EditText) findViewById(R.id.charge_edit_text);
        commentEditText = (EditText) findViewById(R.id.comment_edit_text);

        Button saveButton = (Button) findViewById(R.id.save_button);

        /**
         * On click of the save button, this will collect information in
         *  EditTexts and package it for sending back to MainActivity.
         */
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /* collect EditText information */
                String subName = nameEditText.getText().toString();
                String subDate = dateEditText.getText().toString();
                String subCharge = chargeEditText.getText().toString();
                String subComment = commentEditText.getText().toString();

                Intent returnIntent = new Intent();

                /* package strings to send back to MainActivity */
                returnIntent.putExtra("name", subName);
                returnIntent.putExtra("date", subDate);
                returnIntent.putExtra("charge", subCharge);
                returnIntent.putExtra("comment", subComment);

                AddActivity.this.setResult(Activity.RESULT_OK, returnIntent);

                finish();
            }
        });

    }
}
