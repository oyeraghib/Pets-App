package com.example.android.petsapp.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.strictmode.SqliteObjectLeakedViolation;
import android.util.Log;

import com.example.android.petsapp.data.PetsContract.PetsEntry;


public class PetsProvider extends ContentProvider {
    //Tag for the log message
    public static final String LOG_TAG = PetsProvider.class.getSimpleName();

    //Creating an instance of PetsDbHelper class
    private PetsDbHelper mDbHelper;

    //URI matcher code for the content URI for the pets table
    private static final int PETS = 100;

    //URI matcher code for the content URI for the pets table with specific ID (row number)
    private static final int PETS_ID = 101;


    //URI matcher object to match the content URI to a corresponding code
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    //Static initialize : This is run the first time anything is called from this class.
    static {
        sUriMatcher.addURI(PetsContract.CONTENT_AUTHORITY, PetsContract.PATH_PETS, PETS);
        sUriMatcher.addURI(PetsContract.CONTENT_AUTHORITY, PetsContract.PATH_PETS + "/#", PETS_ID);

    }

/**
 * Initialise the provider and database helper object.
 */



    @Override
    public boolean onCreate() {

         mDbHelper = new PetsDbHelper(getContext());
         return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {

        //Get readable database
        SQLiteDatabase database = mDbHelper.getReadableDatabase();

        //This cursor will hold the result of the query
        Cursor cursor;

        //Figure that the URI Matcher can match the URI to a specific code
        int match = sUriMatcher.match(uri);

        switch (match) {
            case PETS:
                //Performing the database query on the pets table
                cursor = database.query(PetsEntry.TABLE_NAME, projection, selection,
                        selectionArgs, null, null, sortOrder);
                break;

            case PETS_ID:
                //Extracting out the ID number from the URI and replacing the number with the
                //mark in the selection argument
                selection = PetsEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };


                //This will perform the query on the pets table where id = 3, to return a cursor
                //returning row = 3.
                cursor = database.query(PetsEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null,null, sortOrder);

                break;

            default:
                throw new IllegalArgumentException("cannot query unknown URI " + uri);

        }
        return cursor;

    }


    @Override
    public String getType( Uri uri) {
        return null;
    }


    @Override
    public Uri insert( Uri uri,  ContentValues values) {


        final int match = sUriMatcher.match(uri);
        switch (match) {
            case PETS:
                return insertPet(uri, values);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    private Uri insertPet(Uri uri, ContentValues values) {

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        long newRowId;
        newRowId = db.insert(PetsEntry.TABLE_NAME,
                null,
                values);

        if(newRowId == -1){
            Log.e(LOG_TAG, "Error putting a new row in the table" + uri);
            return null;
        }

        return ContentUris.withAppendedId(uri, newRowId);

    }

    @Override
    public int delete( Uri uri,  String selection,  String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update( Uri uri,  ContentValues values,  String selection,  String[] selectionArgs) {
        return 0;
    }


}
