package com.example.diabetes5;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * DataBaseHelper is a class inherited from SQLiteOpenHelper class
 * it implements methods for creation and update of database
*/
public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String LINE_TABLE = "Line_Table";
    public static final String CUSTOMER_NAME = "CUSTOMER_NAME";
    public static final String COLUMN_CUSTOMER_NAME = CUSTOMER_NAME;
    public static final String COLUMN_CUSTOMER_AGE = "COLUMN_CUSTOMER_AGE";
    public static final String COLUMN_ID = "ID";

    /**
     * A
     * @param context - reference to an application
     * @param name - Name of a database file that will be created
     * @param factory - Is used to create Cursore objects the value may be null
     * @param version - version of database schema
     */
    public DataBaseHelper(@Nullable Context context) {
        super(context, "results.db", null, 1); // passing constructor to parent class
    }

    /**
     * This method is called the first time a database is accessed.
     * There is code to create a new database.
     * @param db The database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + LINE_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_CUSTOMER_NAME + " TEXT, " + COLUMN_CUSTOMER_AGE + " INT)";
        db.execSQL(createTableStatement);
    }

    /**
     * This is called if the database version number changes.
     * It prevents previous user apps from breaking when database design is changed.
     * @param db The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addOne(CustomerModel customerModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        // cv - is kind of a map between column name and it's value
        cv.put(COLUMN_CUSTOMER_NAME, customerModel.getName());
        cv.put(COLUMN_CUSTOMER_AGE, customerModel.getAge());

        //nullColumnHack doesn't allow an application to add line completely consisting of nulls
        //At least one column should be provided
        long insert = db.insert(LINE_TABLE, null, cv);
        if(insert == -1){
            return false;
        }
        else{
            return true;
        }
    }

    // Other actions with data in database:
    // delete one item,
    boolean deleteOne(CustomerModel customerModel){
        // find customerModel in the database. if it found, delete it and return true.
        // if it is not found, return false

        SQLiteDatabase db = this.getWritableDatabase();
        String queryStatement = "DELETE FROM " + LINE_TABLE + " WHERE " + COLUMN_ID + " = " + customerModel.getId();
        Cursor cursor = db.rawQuery(queryStatement, null);
        if(cursor.moveToFirst()){
            return true;
        }
        else{
            return false;
        }
    }


    // select all items,
    // update an item,
    // search for items


    // pull all items from database
    public List<CustomerModel> getEveryone(){
        List<CustomerModel> returnList = new ArrayList<>();

        // get data from the database. It could be a PreparedStatement
        String queryStatement = "SELECT * FROM " + LINE_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();

        // Cursor in SQLite is a ResultSet
        Cursor cursor = db.rawQuery(queryStatement, null);
        if(cursor.moveToFirst()){
            // loop through the coursor (result set) and create new customer objects.
            // Put them into the return list
            do{
                int customerId = cursor.getInt(0);
                String customerName = cursor.getString(1);
                int customerAge = cursor.getInt(2);

                CustomerModel newCustomer = new CustomerModel(customerId, customerName, customerAge);
                returnList.add(newCustomer);
            } while(cursor.moveToNext());

        }
        else{
            // failure. do not add anything to the list.
        }

        //close both the cursor and the db when done.
        cursor.close();
        db.close();
        return returnList;
    }
}
