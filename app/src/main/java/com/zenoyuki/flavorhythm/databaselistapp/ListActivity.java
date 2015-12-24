package com.zenoyuki.flavorhythm.databaselistapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import data.DataAdapter;
import data.DatabaseHandler;
import model.DataItem;

public class ListActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView listView;
    private ArrayList<DataItem> dataItemArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        listView = (ListView)findViewById(R.id.list_list);

        Button backBtn = (Button)findViewById(R.id.list_btn_back);
        backBtn.setOnClickListener(this);
    }

    public void refreshData() {
        dataItemArrayList.clear();
        DatabaseHandler db = new DatabaseHandler(getApplicationContext());

        ArrayList<DataItem> dataItemsInDB = db.getAllItems();

        for(int i = 0; i < dataItemsInDB.size(); i++) {
            DataItem dataItem = new DataItem();

            String id = dataItemsInDB.get(i).getId();
            String title = dataItemsInDB.get(i).getDataTitle();
            String content = dataItemsInDB.get(i).getDataContent();
            Long timestampLong = dataItemsInDB.get(i).getTimestamp();

            dataItem.setId(id);
            dataItem.setDataTitle(title);
            dataItem.setDataContent(content);
            dataItem.setTimestamp(timestampLong);

            dataItemArrayList.add(dataItem);
        }
        db.close();

        DataAdapter dataAdapter = new DataAdapter(ListActivity.this, R.layout.list_row, dataItemArrayList);
        listView.setAdapter(dataAdapter);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.list_btn_back:
                startActivity(new Intent(ListActivity.this, MainActivity.class));
                finish();
                break;
            default:
                Toast.makeText(ListActivity.this, R.string.btn_error, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        refreshData();
    }
}
