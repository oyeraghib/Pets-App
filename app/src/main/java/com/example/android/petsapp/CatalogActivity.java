package com.example.android.petsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.android.petsapp.data.PetsContract;
import com.example.android.petsapp.data.PetsDbHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.example.android.petsapp.data.PetsContract.PetsEntry;

import org.w3c.dom.Text;

public class CatalogActivity extends AppCompatActivity {

   private PetsDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        //Set up a FloatingActionButton to setup floating activity.
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
                startActivity(intent);

            }
        });

        mDbHelper = new PetsDbHelper(this);

    }

    @Override
    public void onStart(){
        super.onStart();
        displayDatabaseInfo();
    }

    private void displayDatabaseInfo() {
        //Create and/or open a database to read from it
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                PetsEntry._ID,
                PetsEntry.COLUMN_PET_NAME,
        PetsEntry.COLUMN_PET_BREED,
                PetsEntry.COLUMN_PET_GENDER,
        PetsEntry.COLUMN_PET_WEIGHT};

        Cursor cursor = db.query(
                PetsEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);


        TextView displayView = (TextView) findViewById(R.id.text_view_pet);
        try{
            displayView.setText("Number of rows in Pets table of Shelter database :" + cursor.getCount() + "pets.\n\n") ;

            //Create a header in the textView which looks like
            //_id - name - breed - gender - weight
            displayView.append(PetsEntry._ID + " - " +
                    PetsEntry.COLUMN_PET_NAME + " - " +
                    PetsEntry.COLUMN_PET_BREED + " - " +
                    PetsEntry.COLUMN_PET_GENDER + " - " +
                    PetsEntry.COLUMN_PET_WEIGHT +"\n");

            //Figure out the index  of each column
            int idColumnIndex = cursor.getColumnIndex(PetsEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(PetsEntry.COLUMN_PET_NAME);
            int breedColumnIndex = cursor.getColumnIndex(PetsEntry.COLUMN_PET_BREED);
            int genderColumnIndex = cursor.getColumnIndex(PetsEntry.COLUMN_PET_GENDER);
            int weightColumnIndex = cursor.getColumnIndex(PetsEntry.COLUMN_PET_WEIGHT);

            //Iterate through all the reutrned rows in the cursor
            while(cursor.moveToNext()){

                int currentId = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                String currentBreed = cursor.getString(breedColumnIndex);
                int currentGender = cursor.getInt(genderColumnIndex);
                int currentWeight = cursor.getInt(weightColumnIndex);

                displayView.append(("\n" + currentId + " - " + currentName + " - " + currentBreed + " - " + currentGender + " - " + currentWeight ));
            }


        } finally {
            //Always close the cursor when you are done reading from it. This releases all its
            //resources and makes it invalid.

            cursor.close();
        }


    }






    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    public void insertPet() {
        //Gets the data repository in write node
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        //Create a new map of values where the column name are the keys
        ContentValues values = new ContentValues();
        values.put(PetsEntry.COLUMN_PET_NAME, "Toto");
        values.put(PetsEntry.COLUMN_PET_BREED, "Terrier");
        values.put(PetsEntry.COLUMN_PET_GENDER, PetsEntry.COLUMN_PET_GENDER);
        values.put(PetsEntry.COLUMN_PET_WEIGHT, 4);

        //Insert the row returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                PetsEntry.TABLE_NAME,
                null,
                values);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                //This method insert a row in the databse
                insertPet();
                displayDatabaseInfo();
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                // Do nothing for now
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
