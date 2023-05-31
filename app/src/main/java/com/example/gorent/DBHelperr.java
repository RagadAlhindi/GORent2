package com.example.gorent;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;





    public class DBHelperr extends SQLiteOpenHelper {

        // for vehicls table
        public static final String VEHICLE_TABLE = "Vehicle_Table";
        public static final String COLUMN_VEHICLE_PLATE = "VEHICLE_PLATE";
        public static final String COLUMN_VEHICLE_MODEL = "VEHICLE_MODEL";

        public static final String COLUMN_VEHICLE_TYPE = "VEHICLE_TYPE";

        public static final String COLUMN_VEHICLE_LOCATION= "VEHICLE_LOCATION";

        public static final String COLUMN_VEHICLE_DESCRIPTION= "VEHICLE_DESCRIPTION";


        public static final String COLUMN_VEHICLE_RENT= "VEHICLE_RENT";
        public static final String COLUMN_ID = "ID";

        public static final String COLUMN_VEHICLE_PHOTO = "img";

        public static final String COLUMN_USER_EMAIL = "user_email";

        public static final String COLUMN_RENTED = "rented";
        // 0 means not rented and 1 means rented
        // if vehicle is rented by someone it won't show in the home page



        // for users table
        public static final String TABLENAME = "users";
        public static final String COL1 = "email";
        public static final String COL2 = "password";
        public static final String COL3 = "name";
        public static final String COL4 = "age";
        Context context;

        // for rent table
        public static final String RENT = "rental_application";
        public static final String RENTID = "rentid";

        public static final String VehicleID = "Vid";
        public static final String UEMAIL = "user_email";





        public DBHelperr(@Nullable Context context) {
            super(context, "NEWGORENT.db", null, 1);


        }

        // when creating the database
        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            String createTableStatement = "CREATE TABLE " + VEHICLE_TABLE + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_VEHICLE_PLATE + " TEXT,"
                    + COLUMN_VEHICLE_MODEL + " TEXT,"
                    + COLUMN_VEHICLE_TYPE + " TEXT,"
                    + COLUMN_VEHICLE_LOCATION + " TEXT,"
                    + COLUMN_VEHICLE_DESCRIPTION + " TEXT,"
                    + COLUMN_VEHICLE_RENT + " INTEGER,"
                    + COLUMN_VEHICLE_PHOTO + " BLOB,"
                    + COLUMN_USER_EMAIL + " TEXT,"
                    + COLUMN_RENTED + " INTEGER DEFAULT 0," // Add the new RENTED column with default value 0
                    + "FOREIGN KEY(" + COLUMN_USER_EMAIL + ") REFERENCES " + TABLENAME + "(" + COL1 + ")"
                    + ")";
            sqLiteDatabase.execSQL(createTableStatement);


            sqLiteDatabase.execSQL("CREATE TABLE " + TABLENAME + " (" + COL1 + " TEXT PRIMARY KEY, " + COL2 + " TEXT, " + COL3 + " TEXT, " + COL4 + " TEXT)");
            sqLiteDatabase.execSQL("CREATE TABLE " + RENT + " ("
                    + RENTID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + VehicleID + " INTEGER,"
                    + UEMAIL + " TEXT,"
                    + "FOREIGN KEY(" + VehicleID + ") REFERENCES " + VEHICLE_TABLE + "(" + COLUMN_ID + "),"
                    + "FOREIGN KEY(" + UEMAIL + ") REFERENCES " + TABLENAME + "(" + COL1 + ")"
                    +")" );

        }
        // when upgrading
        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL("drop Table if exists " + TABLENAME);

        }

        public boolean addOne(VehicleModel VM, String userEmail){
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_VEHICLE_PLATE, VM.getPlateNo());
            cv.put(COLUMN_VEHICLE_MODEL, VM.getModel());
            cv.put(COLUMN_VEHICLE_TYPE, VM.getType());
            cv.put(COLUMN_VEHICLE_LOCATION, VM.getLocation());
            cv.put(COLUMN_VEHICLE_DESCRIPTION, VM.getDescription());
            cv.put(COLUMN_VEHICLE_RENT, VM.getRent());
            cv.put(COLUMN_VEHICLE_PHOTO,VM.getImg());
            cv.put(COLUMN_USER_EMAIL, userEmail); // Insert the user email into the user_email column
            long insert = db.insert(VEHICLE_TABLE, null, cv);
            if(insert == -1){
                return false;
            }
            else {

                return true;
            }
        }





        public boolean DeleteOne(int id){
            SQLiteDatabase db = this.getWritableDatabase();
            String queryString= "Delete From " + VEHICLE_TABLE + " WHERE " + COLUMN_ID + " = " + id ;
            Cursor cursor = db.rawQuery(queryString, null);
            if(cursor.moveToFirst()){
                return true;
            } else{
                // nothing happens. no one is added.
                return false;
            }
            //close
        }





        //getdata() updated to retrieve vehicles that are not rented only
        public Cursor getdata() {
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM " + VEHICLE_TABLE + " WHERE " + COLUMN_RENTED + "=0", null);
            return cursor;
        }

        public Cursor getInfo(int VID) {
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM " + VEHICLE_TABLE + " WHERE " + COLUMN_ID + "="+VID, null);
            return cursor;
        }





        public Boolean insertData(String email, String password, String name, String age) {
            SQLiteDatabase MyDB = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COL1, email);
            contentValues.put(COL2, password);
            contentValues.put(COL3, name);
            contentValues.put(COL4, age);

            long result = MyDB.insert(TABLENAME, null, contentValues);
            if (result == -1) return false;
            else
                return true;
        }

        public Boolean checkEmail(String email) {
            SQLiteDatabase MyDB = this.getWritableDatabase();
            Cursor cursor = MyDB.rawQuery("Select * from " + TABLENAME + " where " + COL1 + " = ?", new String[]{email});
            if (cursor.getCount() > 0) return true;
            return false;
        }

        public Boolean checkEmailPassword(String email, String password) {
            SQLiteDatabase MyDB = this.getWritableDatabase();
            Cursor cursor = MyDB.rawQuery("Select * from " + TABLENAME + " where " + COL1 + " = ? and " + COL2 + " = ?", new String[]{email, password});
            if (cursor.getCount() > 0) return true;
            return false;
        }



        public boolean rentV(int VId, String email) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(VehicleID, VId);
            values.put(UEMAIL, email);

            long insertResult = db.insert(RENT, null, values);

            // Update the RENTED column to 1 for the rented vehicle
            ContentValues rentedValues = new ContentValues();
            rentedValues.put(COLUMN_RENTED, 1);
            int updateResult = db.update(VEHICLE_TABLE, rentedValues,
                    COLUMN_ID + "=?", new String[]{String.valueOf(VId)});

            db.close();

            // Check whether both queries were executed successfully
            return insertResult != -1 && updateResult > 0;
        }
// return method that takes the vehicle id
        public boolean returnV (int vehicleId) {
            SQLiteDatabase db = this.getWritableDatabase();
            int rowsDeleted = db.delete(RENT, VehicleID + "=?", new String[]{String.valueOf(vehicleId)} );

            // Update the RENTED column to 1 for the rented vehicle
            ContentValues rentedValues = new ContentValues();
            rentedValues.put(COLUMN_RENTED, 0);
            int updateResult = db.update(VEHICLE_TABLE, rentedValues,
                    COLUMN_ID + "=?", new String[]{String.valueOf(vehicleId)});

            db.close();
            return rowsDeleted > 0 && updateResult > 0;
        }


    }


