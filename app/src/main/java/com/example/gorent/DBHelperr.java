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

        public static final String COLUMN_VEHICLE_YEAR = "VEHICLE_YEAR";

        public static final String COLUMN_VEHICLE_TYPE = "VEHICLE_TYPE";

        public static final String COLUMN_VEHICLE_LOCATION= "VEHICLE_LOCATION";

        public static final String COLUMN_VEHICLE_DESCRIPTION= "VEHICLE_DESCRIPTION";


        public static final String COLUMN_VEHICLE_RENT= "VEHICLE_RENT";
        public static final String COLUMN_ID = "ID";

        public static final String COLUMN_VEHICLE_PHOTO = "img";

        public static final String COLUMN_USER_EMAIL = "user_email";





        // for users table
        public static final String TABLENAME = "users";
        public static final String COL1 = "email";
        public static final String COL2 = "password";
        public static final String COL3 = "name";
        public static final String COL4 = "age";
        Context context;



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
                    + COLUMN_VEHICLE_YEAR + " INTEGER,"
                    + COLUMN_VEHICLE_TYPE + " TEXT,"
                    + COLUMN_VEHICLE_LOCATION + " TEXT,"
                    + COLUMN_VEHICLE_DESCRIPTION + " TEXT,"
                    + COLUMN_VEHICLE_RENT + " INTEGER,"
                    + COLUMN_VEHICLE_PHOTO+" BLOB,"
                    + COLUMN_USER_EMAIL + " TEXT," // Add the user email column
                    + "FOREIGN KEY(" + COLUMN_USER_EMAIL + ") REFERENCES " + TABLENAME + "(" + COL1 + ")"
                    + ")";
            sqLiteDatabase.execSQL(createTableStatement);


            sqLiteDatabase.execSQL("CREATE TABLE " + TABLENAME + " (" + COL1 + " TEXT PRIMARY KEY, " + COL2 + " TEXT, " + COL3 + " TEXT, " + COL4 + " TEXT)");


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
            cv.put(COLUMN_VEHICLE_YEAR, VM.getYear());
            cv.put(COLUMN_VEHICLE_TYPE, VM.getType());
            cv.put(COLUMN_VEHICLE_LOCATION, VM.getCity());
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




        public boolean DeleteOne(VehicleModel VM){
            SQLiteDatabase db = this.getWritableDatabase();
            String queryString= "Delete From " + VEHICLE_TABLE + " WHERE " + COLUMN_ID + " = " + VM.getId() ;
            Cursor cursor = db.rawQuery(queryString, null);
            if(cursor.moveToFirst()){
                return true;
            } else{
                // nothing happens. no one is added.
                return false;
            }
            //close
        }

        // we can use this method to show the user's vehicles (he offer it for rent)
        public List<VehicleModel> getEveryone(){
            List<VehicleModel> returnList = new ArrayList<>();
            // get data from database
            String queryString = "SELECT * FROM " + VEHICLE_TABLE + " v JOIN " + TABLENAME + " u ON v." + COLUMN_USER_EMAIL + " = u." + COL1 + " WHERE u." + COL1 + " = ?";
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(queryString, null);
            if(cursor.moveToFirst()){
                // loop through cursor results
                do{
                    int SID = cursor.getInt(0); // vehicle ID
                    String VPlate = cursor.getString(1);
                    String VModel = cursor.getString(1);
                    int VYear = cursor.getInt(2);
                    String VType = cursor.getString(1);
                    String VLoc = cursor.getString(1);
                    String VDescription = cursor.getString(1);
                    int VRent = cursor.getInt(2);

                    VehicleModel newVehicle = new VehicleModel(SID, VPlate, VModel, VYear, VType, VLoc, VDescription,VRent,null);
                    returnList.add(newVehicle);
                }while (cursor.moveToNext());
            } else{
                // nothing happens. no one is added.
            }
            //close
            cursor.close();
            db.close();
            return returnList;
        }

        public Cursor getdata() {
            SQLiteDatabase DB = this.getWritableDatabase();
            Cursor cursor = DB.rawQuery("Select * from Vehicle_Table ", null);
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

    }


