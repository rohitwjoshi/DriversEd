package com.example.khochberg.driversed;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.database.Cursor;

/**
 * Created by Rohit on 4/14/17.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "lessonManager";

    // Lessons table name
    private static final String TABLE_LESSONS = "lessons";

    // Lessons Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_DATE = "date";
    private static final String KEY_HOURS = "hours";
    private static final String KEY_TIME_OF_DAY = "time";
    private static final String KEY_LESSON_TYPE = "lesson";
    private static final String KEY_WEATHER = "weather";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LESSONS_TABLE = "CREATE TABLE " + TABLE_LESSONS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_DATE + " TEXT,"
                + KEY_HOURS + " TEXT," + KEY_TIME_OF_DAY + " TEXT," + KEY_LESSON_TYPE + " TEXT," + KEY_WEATHER + " TEXT" + ")";
        db.execSQL(CREATE_LESSONS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LESSONS);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD operations
     */
    void addLesson(Lesson lesson) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_DATE, lesson.getDate());
        values.put(KEY_HOURS, lesson.getHours());
        values.put(KEY_TIME_OF_DAY, lesson.getTimeOfDay());
        values.put(KEY_LESSON_TYPE, lesson.getLessonType());
        values.put(KEY_WEATHER, lesson.getWeather());

        // Inserting Row
        db.insert(TABLE_LESSONS, null, values);
        db.close(); // Closing database connection
    }

    public ArrayList<Lesson> getAllLessons() {
        ArrayList<Lesson> lessonList = new ArrayList<Lesson>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_LESSONS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Lesson lesson = new Lesson();
                lesson.setID(Integer.parseInt(cursor.getString(0)));
                lesson.setDate(cursor.getString(1));
                lesson.setHours(cursor.getString(2));
                lesson.setTimeOfDay(cursor.getString(3));
                lesson.setLessonType(cursor.getString(4));
                lesson.setWeather(cursor.getString(5));

                // Adding lesson to list
                lessonList.add(lesson);
            } while (cursor.moveToNext());
        }
        // return lesson list
        db.close(); // Closing database connection
        return lessonList;
    }

    // Getting single lesson
    public Lesson getLesson(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_LESSONS, new String[] { KEY_ID,
                        KEY_DATE, KEY_HOURS, KEY_TIME_OF_DAY, KEY_LESSON_TYPE, KEY_WEATHER }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Lesson lesson = new Lesson(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5));
        db.close(); // Closing database connection
        return lesson;
    }

    // Updating single lesson
    public void updateLesson(Lesson lesson) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_DATE, lesson.getDate());
        values.put(KEY_HOURS, lesson.getHours());
        values.put(KEY_TIME_OF_DAY, lesson.getTimeOfDay());
        values.put(KEY_LESSON_TYPE, lesson.getLessonType());
        values.put(KEY_WEATHER, lesson.getWeather());
        // updating row
        db.update(TABLE_LESSONS, values, KEY_ID + " = ?", new String[] { String.valueOf(lesson.getID()) });
        db.close();
        return;
    }

    // Deleting single lesson
    public void deleteLesson(Lesson lesson) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_LESSONS, KEY_ID + " = ?",
                new String[] { String.valueOf(lesson.getID()) });
        db.close();
    }
}
