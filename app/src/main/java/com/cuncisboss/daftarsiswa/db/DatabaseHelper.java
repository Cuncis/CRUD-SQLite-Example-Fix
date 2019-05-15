package com.cuncisboss.daftarsiswa.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.cuncisboss.daftarsiswa.model.Student;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "biodata.db";
    public static final int DB_VERSION = 1;

    public static final String TABLE_NAME = "student";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String CITY = "city";
    public static final String CURRENT_DATE = "current_date";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                NAME + " TEXT, " +
                CITY + " TEXT, " +
                CURRENT_DATE + "DATE DEFAULT CURRENT_DATE);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addStudent(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME, student.getName());
        values.put(CITY, student.getCity());

        db.insert(TABLE_NAME, null, values);
    }

    public List<Student> getAllStudent() {
        List<Student> studentList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + ID + " DESC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);

            Student student = new Student();
            student.setId(cursor.getString(cursor.getColumnIndex(ID)));
            student.setName(cursor.getString(cursor.getColumnIndex(NAME)));
            student.setCity(cursor.getString(cursor.getColumnIndex(CITY)));
            student.setDate(cursor.getString(3));
            studentList.add(student);
        }

        return studentList;
    }

    public void updateStudent(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME, student.getName());
        values.put(CITY, student.getCity());

        db.update(TABLE_NAME, values, ID + "=?", new String[]{String.valueOf(student.getId())});
        db.close();
    }

    public void deleteStudent(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
//        String deleteQuery = "DELETE FROM " + TABLE_NAME + " WHERE " + ID + "='" + id + "'";
//        db.execSQL(deleteQuery);
        db.delete(TABLE_NAME, "id=?", new String[]{id});
        db.close();
    }

    public void deleteAllStudent() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
        db.close();
    }

}


















