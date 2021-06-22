package com.example.android.petsapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.android.petsapp.data.PetsContract.PetsEntry;

public class PetsDbHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = PetsDbHelper.class.getSimpleName();

    //Name of the database
    public static final String DATABASE_NAME = "shelter.db";
    //Version of database. If you increase database schema(update the database) you have to increase
    //its value as well.

    public static final int DATABASE_NUMBER = 1;

    public PetsDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_NUMBER);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
//Create Table pets(_id INTEGER PRIMARY KEY AUTOINCREMENT, breed TEXT, name TEXT NOT NULL,
        //gender INTEGER, weight INTEGER DEFAULT 0

        /*
        Create a String that contains the SQL statement to create Pets table.
         */

        String SQL_CREATES_PETS_TABLE = "CREATE TABLE " + PetsEntry.TABLE_NAME + "("
                + PetsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + PetsEntry.COLUMN_PET_NAME + " TEXT NOT NULL, "
                + PetsEntry.COLUMN_PET_BREED + " TEXT, "
                + PetsEntry.COLUMN_PET_GENDER + " INTEGER NOT NULL, "
                + PetsEntry.COLUMN_PET_WEIGHT + " INTEGER NOT NULL DEFAULT 0 );" ;

        db.execSQL(SQL_CREATES_PETS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
