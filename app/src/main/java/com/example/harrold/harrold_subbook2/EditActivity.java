package com.example.harrold.harrold_subbook2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Harrold on 2018-02-05.
 */

public class EditActivity extends AppCompatActivity {

    private EditText nameEditText2;
    private EditText dateEditText2;
    private EditText chargeEditText2;
    private EditText commentEditText2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        nameEditText2 = (EditText) findViewById(R.id.name_edit_text2);
        dateEditText2 = (EditText) findViewById(R.id.date_edit_text2);
        chargeEditText2 = (EditText) findViewById(R.id.charge_edit_text2);
        commentEditText2 = (EditText) findViewById(R.id.comment_edit_text2);

        Bundle bundleGet = getIntent().getExtras();
        if (bundleGet != null) {
            nameEditText2.setText((String) bundleGet.get("fillerName"));
            dateEditText2.setText((String) bundleGet.get("fillerDate"));
            chargeEditText2.setText((String) bundleGet.get("fillerCharge"));
            commentEditText2.setText((String) bundleGet.get("fillerComment"));
        }

        Button saveEditButton = (Button) findViewById(R.id.save_button2);
        Button deleteButton = (Button) findViewById(R.id.delete_button);

        saveEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String subName = nameEditText2.getText().toString();
                String subDate = dateEditText2.getText().toString();
                String subCharge = chargeEditText2.getText().toString();
                String subComment = commentEditText2.getText().toString();

                Intent returnEditIntent = new Intent();

                returnEditIntent.putExtra("name", subName);
                returnEditIntent.putExtra("date", subDate);
                returnEditIntent.putExtra("charge", subCharge);
                returnEditIntent.putExtra("comment", subComment);

                EditActivity.this.setResult(2, returnEditIntent);

                finish();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnEditIntent = new Intent();

                EditActivity.this.setResult(3, returnEditIntent);

                finish();
            }
        });
    }
}
