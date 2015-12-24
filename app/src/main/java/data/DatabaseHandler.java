package data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.zenoyuki.flavorhythm.databaselistapp.R;

import java.util.ArrayList;

import Util.Constants;
import model.DataItem;

/**
 * Created by zyuki on 12/21/2015.
 */
public class DatabaseHandler extends SQLiteOpenHelper {
    private final ArrayList<DataItem> dataItemArrayList = new ArrayList<>();

    private Context context;

    public DatabaseHandler(Context context) {
        super(context, Constants.dbStrings.DB_NAME, null, Constants.dbStrings.DB_VER);

        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String DB_TABLE_CREATE_CMD = "CREATE TABLE " + Constants.dbStrings.DB_TABLE + "(" +
                Constants.dbStrings.ID + " INTEGER PRIMARY KEY, " +
                Constants.dbStrings.TITLE + " TEXT, " +
                Constants.dbStrings.CONTENT + " TEXT, " +
                Constants.dbStrings.TIMESTAMP + " INTEGER);"; //Java will put LONG data types here

        db.execSQL(DB_TABLE_CREATE_CMD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Constants.dbStrings.DB_TABLE);
        onCreate(db);
    }

    public ArrayList<DataItem> getAllItems() {
        dataItemArrayList.clear();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                Constants.dbStrings.DB_TABLE,
                new String[] {
                        Constants.dbStrings.ID,
                        Constants.dbStrings.TITLE,
                        Constants.dbStrings.CONTENT,
                        Constants.dbStrings.TIMESTAMP
                },
                null, null, null, null,
                Constants.dbStrings.TIMESTAMP + " DESC"
        );

        if(cursor.moveToFirst()) {
            do {
                DataItem dataItem = new DataItem();

                dataItem.setId(cursor.getString(cursor.getColumnIndex(Constants.dbStrings.ID)));
                dataItem.setDataTitle(cursor.getString(cursor.getColumnIndex(Constants.dbStrings.TITLE)));
                dataItem.setDataContent(cursor.getString(cursor.getColumnIndex(Constants.dbStrings.CONTENT)));
                dataItem.setTimestamp(cursor.getLong(cursor.getColumnIndex(Constants.dbStrings.TIMESTAMP))); //Pulling LONG data types from an SQL INTEGER column

                dataItemArrayList.add(dataItem);
            } while(cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return dataItemArrayList;
    }

    public void addItem(DataItem dataItem) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constants.dbStrings.TITLE, dataItem.getDataTitle());
        values.put(Constants.dbStrings.CONTENT, dataItem.getDataContent());
        values.put(Constants.dbStrings.TIMESTAMP, System.currentTimeMillis());
        db.insert(Constants.dbStrings.DB_TABLE, null, values);
        db.close();

        Toast.makeText(context, R.string.success_toast, Toast.LENGTH_SHORT).show();
    }

    public void delItem(String id) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(Constants.dbStrings.DB_TABLE,
                Constants.dbStrings.ID + " = ?",
                new String[] {
                        id
                });

        db.close();

        Toast.makeText(context, R.string.success_toast, Toast.LENGTH_SHORT).show();
    }
}
