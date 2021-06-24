package com.example.android.petsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
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

    }

    @Override
    public void onStart(){
        super.onStart();
        displayDatabaseInfo();
    }

    private void displayDatabaseInfo() {

        String[] projection = {
                PetsEntry._ID,
                PetsEntry.COLUMN_PET_NAME,
        PetsEntry.COLUMN_PET_BREED,
                PetsEntry.COLUMN_PET_GENDER,
        PetsEntry.COLUMN_PET_WEIGHT};
/**
        Cursor cursor = db.query(
                PetsEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);
*/
        //Perform the query on the provider  using ContentResolver.
        //Use the {@link PetEntry#Content_URI} to access the pet data.


         Cursor cursor = getContentResolver().query(
             PetsEntry.CONTENT_URI,                 //The content URI of the words table
             projection,                            //The columns to return for each row
                 null,                     //Row Selection Criteria
            null,                       //Row Selection Criteria
            null);                          //The sort order for the returned rows.


        //Find the list view which will be populated with the pets data
        ListView listView = (ListView) findViewById(R.id.list);

        //Set up an adapter to create a list item for each row of pet in the cursor
        PetCursorAdapter adapter = new PetCursorAdapter(this, cursor);

        listView.setAdapter(adapter);


    }






    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    public void insertPet() {

        //Create a new map of values where the column name are the keys
        ContentValues values = new ContentValues();
        values.put(PetsEntry.COLUMN_PET_NAME, "Toto");
        values.put(PetsEntry.COLUMN_PET_BREED, "Terrier");
        values.put(PetsEntry.COLUMN_PET_GENDER, PetsEntry.GENDER_MALE);
        values.put(PetsEntry.COLUMN_PET_WEIGHT, 4);



        Uri newUri = getContentResolver().insert(PetsEntry.CONTENT_URI, values);

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
