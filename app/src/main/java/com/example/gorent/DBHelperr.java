package com.example.gorent;



    import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import androidx.annotation.Nullable;

    public class DBHelperr extends SQLiteOpenHelper {
        public static final String DBNAME = "Login.db";
        public static final String TABLENAME = "users";
        public static final String COL1 = "email";
        public static final String COL2 = "password";
        public static final String COL3 = "name";
        public static final String COL4 = "age";

        public DBHelperr(@Nullable Context context) {
            super(context, "Login.db", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL("CREATE TABLE " + TABLENAME + " (" + COL1 + " TEXT PRIMARY KEY, " + COL2 + " TEXT, " + COL3 + " TEXT, " + COL4 + " TEXT)");
        }


        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL("drop Table if exists " + TABLENAME);
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



