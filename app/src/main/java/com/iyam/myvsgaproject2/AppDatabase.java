/*
 *    Hi, Code Enthusiast!
 *    https://github.com/yudiatmoko
 */

package com.iyam.myvsgaproject2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class AppDatabase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "student_database";
    private static final String TABLE_STUDENTS = "students";
    private static final String KEY_ID = "id";
    private static final String KEY_FIRSTNAME = "name";

    public AppDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

//    final String CREATE_TABLE_STUDENT = "CREATE TABLE " + TABLE_STUDENTS + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTO INCREMENT," + KEY_FIRSTNAME + " TEXT);";

    final String CREATE_TABLE_STUDENT = "CREATE TABLE " + TABLE_STUDENTS + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_FIRSTNAME + " TEXT)";

    final String DROP_TABLE_STUDENT = "DROP TABLE IF EXISTS '" + TABLE_STUDENTS + "'";

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_STUDENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DROP_TABLE_STUDENT);
        onCreate(sqLiteDatabase);
    }

    long addStudent(String student){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_FIRSTNAME, student);

        return db.insert(TABLE_STUDENTS, null, values);
    }

    ArrayList<String> getAllStudentsList(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> studentsList = new ArrayList<>();

        String name;
        String selectQuery = "SELECT * FROM " + TABLE_STUDENTS;
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()){
            do {
                name = c.getString(c.getColumnIndexOrThrow(KEY_FIRSTNAME));
                studentsList.add(name);
            } while (c.moveToNext());
        }
        return studentsList;
    }
}
