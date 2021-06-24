package com.example.android.petsapp.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public class PetsContract  {


    private PetsContract() {}

    //Creating constants for the URI

    public static final String CONTENT_AUTHORITY = "com.example.android.petsapp";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_PETS = "pets";

    public static final class PetsEntry implements BaseColumns {

        //
        public static final String CONTENT_LIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" +
                CONTENT_AUTHORITY + "/" + PATH_PETS;

        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" +
                CONTENT_AUTHORITY + "/" + PATH_PETS;


        //Complete final URI statement

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_PETS);



        public final static String TABLE_NAME ="pets";

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_PET_NAME = "name";
        public final static String COLUMN_PET_BREED = "breed";
        public final static String COLUMN_PET_GENDER = "gender";
        public final static String COLUMN_PET_WEIGHT = "weight";

        public final static int GENDER_UNKNOWN = 0;
        public static final int GENDER_MALE = 1;
        public static final int GENDER_FEMALE = 2;

        //This method returns whether the given gender is {@link GENDER_UNKNOWN}, {@link GENDER_MALE},
        //{@link GENDER_FEMALE}

        public static boolean isValid(int gender){
            if(gender == GENDER_FEMALE || gender == GENDER_MALE || gender == GENDER_UNKNOWN){
                return true;
            }
            return false;
        }




    }
}
