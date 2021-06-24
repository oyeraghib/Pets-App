package com.example.android.petsapp;

import android.content.Context;
import android.database.Cursor;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.android.petsapp.data.PetsContract;

public class PetCursorAdapter extends CursorAdapter {

    public PetCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    //The newView method is used to inflate a new view and return it.
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    //The bindView method is used to bind all the data to a given view.
    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView nameView = (TextView) view.findViewById(R.id.name_text_view);
        TextView breedView = (TextView) view.findViewById(R.id.breed_text_view);
        //Extract the properties from the cursor

        String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
        String breed = cursor.getString(cursor.getColumnIndexOrThrow("breed"));

        //Populate the fields with the extracted values
        nameView.setText(name);
        breedView.setText(breed);

    }
}
