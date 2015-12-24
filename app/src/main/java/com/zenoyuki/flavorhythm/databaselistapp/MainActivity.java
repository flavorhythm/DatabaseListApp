package com.zenoyuki.flavorhythm.databaselistapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;

import data.DatabaseHandler;
import model.DataItem;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, Validator.ValidationListener {

    @NotEmpty
    private EditText dataTitle, dataContent;

    private Validator validator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataTitle = (EditText)findViewById(R.id.main_edt_title);
        dataContent = (EditText)findViewById(R.id.main_edt_content);

        Button goBtn, submitBtn;
        goBtn = (Button)findViewById(R.id.go);
        submitBtn = (Button)findViewById(R.id.submit);

        goBtn.setOnClickListener(this);
        submitBtn.setOnClickListener(this);

        validator = new Validator(MainActivity.this);
        validator.setValidationListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.go:
                startActivity(new Intent(MainActivity.this, ListActivity.class));
                break;
            case R.id.submit:
                validator.validate();
                break;
            default:
                Toast.makeText(MainActivity.this, R.string.btn_error, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void submitData() {
        DatabaseHandler db = new DatabaseHandler(getApplicationContext());
        DataItem dataItem = new DataItem();

        dataItem.setDataTitle(dataTitle.getText().toString());
        dataItem.setDataContent(dataContent.getText().toString());

        db.addItem(dataItem);
        db.close();

        dataTitle.setText("");
        dataContent.setText("");
    }

    @Override
    public void onValidationSucceeded() {
        submitData();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        dataTitle.setError("This field is blank!");
        dataContent.setError("This field is blank!");
    }
}
