package com.gmi.gtcm.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.Image;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ravi on 15/03/18.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "GTCM.db";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        // create notes table
        db.execSQL(QuizData.CREATE_TABLE);
        db.execSQL(ImageQuiz.CREATE_TABLE);
        db.execSQL(TextQuiz.CREATE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + QuizData.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ImageQuiz.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TextQuiz.TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    public long insertQuestion(String spellingbeeQuizId, String questions, String position, String answers, String time, String points ,String answeredcount,String correctansweredcount ) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
//        values.put(PendingFile.COLUMN_ID, note);
        values.put(QuizData.SPELLINGBEEQUIZID, spellingbeeQuizId);
        values.put(QuizData.QUESTIONS, questions);
        values.put(QuizData.POSITION, position);
        values.put(QuizData.ANSWERS, answers);
        values.put(QuizData.TIME, time);
        values.put(QuizData.POINTS, points);
        values.put(QuizData.ANSWEREDCOUNT, answeredcount);
        values.put(QuizData.CORRECTANSWEREDCOUNT, correctansweredcount);
        long id = db.insert(QuizData.TABLE_NAME, null, values);
        // close db connection
        db.close();
        // return newly inserted row id
        return id;
    }
   public long insertImageQuestion(String imquizid, String imquestions, String imposition , String imanswer , String imduration) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
//        values.put(PendingFile.COLUMN_ID, note);
        values.put(ImageQuiz.IMQUIZID, imquizid);
        values.put(ImageQuiz.IMQUESTIONS, imquestions);
        values.put(ImageQuiz.IMPOSITION, imposition);
        values.put(ImageQuiz.IMANSWER, imanswer);
        values.put(ImageQuiz.IMDURATION, imduration);
        long id = db.insert(ImageQuiz.TABLE_NAME, null, values);
        // close db connection
        db.close();
        // return newly inserted row id
        return id;
    }

public long insertTextQuestion(String tquizid, String tquestions, String tposition , String tanswer , String tduration) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
//        values.put(PendingFile.COLUMN_ID, note);
        values.put(TextQuiz.TQUIZID, tquizid);
        values.put(TextQuiz.TQUESTIONS, tquestions);
        values.put(TextQuiz.TPOSITION, tposition);
        values.put(TextQuiz.TANSWER, tanswer);
        values.put(TextQuiz.TDURATION, tduration);
        long id = db.insert(TextQuiz.TABLE_NAME, null, values);
        // close db connection
        db.close();
        // return newly inserted row id
        return id;
    }


    public QuizData getDatabyQUizId(String Quizid) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(QuizData.TABLE_NAME,
                new String[]{QuizData.SPELLINGBEEQUIZID, QuizData.QUESTIONS, QuizData.POSITION, QuizData.ANSWERS,QuizData.TIME,QuizData.POINTS,QuizData.ANSWEREDCOUNT,QuizData.CORRECTANSWEREDCOUNT},
                QuizData.SPELLINGBEEQUIZID + "=?",
                new String[]{Quizid}, null, null, null, null);

//        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null && cursor.getCount() > 0){
            cursor.moveToFirst();

            // prepare note object
            QuizData note = new QuizData(
                    (cursor.getString(cursor.getColumnIndex(QuizData.SPELLINGBEEQUIZID))),
                    (cursor.getString(cursor.getColumnIndex(QuizData.QUESTIONS))),
                    (cursor.getString(cursor.getColumnIndex(QuizData.POSITION))),
                    (cursor.getString(cursor.getColumnIndex(QuizData.ANSWERS))),
                    (cursor.getString(cursor.getColumnIndex(QuizData.TIME))),
                    (cursor.getString(cursor.getColumnIndex(QuizData.POINTS))),
                    (cursor.getString(cursor.getColumnIndex(QuizData.ANSWEREDCOUNT))),
                    (cursor.getString(cursor.getColumnIndex(QuizData.CORRECTANSWEREDCOUNT))));
            // close the db connection
            cursor.close();

            return note;
        }else {
            return null;
        }

    }
 public ImageQuiz getImagequizdata(String Quizid) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(ImageQuiz.TABLE_NAME,
                new String[]{ImageQuiz.IMQUIZID, ImageQuiz.IMQUESTIONS, ImageQuiz.IMPOSITION, ImageQuiz.IMANSWER,ImageQuiz.IMDURATION},
                ImageQuiz.IMQUIZID + "=?",
                new String[]{Quizid}, null, null, null, null);

//        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null && cursor.getCount() > 0){
            cursor.moveToFirst();

            // prepare note object
            ImageQuiz note = new ImageQuiz(
                    (cursor.getString(cursor.getColumnIndex(ImageQuiz.IMQUIZID))),
                    (cursor.getString(cursor.getColumnIndex(ImageQuiz.IMQUESTIONS))),
                    (cursor.getString(cursor.getColumnIndex(ImageQuiz.IMPOSITION))),
                    (cursor.getString(cursor.getColumnIndex(ImageQuiz.IMANSWER))),
                    (cursor.getString(cursor.getColumnIndex(ImageQuiz.IMDURATION))));
            // close the db connection
            cursor.close();

            return note;
        }else {
            return null;
        }

    }

 public TextQuiz getTextquizdata(String Quizid) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TextQuiz.TABLE_NAME,
                new String[]{TextQuiz.TQUIZID, TextQuiz.TQUESTIONS, TextQuiz.TPOSITION, TextQuiz.TANSWER,TextQuiz.TDURATION},
                TextQuiz.TQUIZID + "=?",
                new String[]{Quizid}, null, null, null, null);

//        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null && cursor.getCount() > 0){
            cursor.moveToFirst();

            // prepare note object
            TextQuiz note = new TextQuiz(
                    (cursor.getString(cursor.getColumnIndex(TextQuiz.TQUIZID))),
                    (cursor.getString(cursor.getColumnIndex(TextQuiz.TQUESTIONS))),
                    (cursor.getString(cursor.getColumnIndex(TextQuiz.TPOSITION))),
                    (cursor.getString(cursor.getColumnIndex(TextQuiz.TANSWER))),
                    (cursor.getString(cursor.getColumnIndex(TextQuiz.TDURATION))));
            // close the db connection
            cursor.close();

            return note;
        }else {
            return null;
        }

    }

    public boolean updateTextPosition(String position,String quizid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TextQuiz.TPOSITION,position);
        db.update(TextQuiz.TABLE_NAME, contentValues, TextQuiz.TQUIZID+"= ?",new String[] { quizid });
        return true;
    } public boolean updateImagePosition(String position,String quizid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ImageQuiz.IMPOSITION,position);
        db.update(ImageQuiz.TABLE_NAME, contentValues, ImageQuiz.IMQUIZID+"= ?",new String[] { quizid });
        return true;
    }
    public boolean updateImageanswer(String data,String quizid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ImageQuiz.IMANSWER,data);
        db.update(ImageQuiz.TABLE_NAME, contentValues, ImageQuiz.IMQUIZID+"= ?",new String[] { quizid });
        return true;
    } public boolean updateTextanswer(String data,String quizid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TextQuiz.TANSWER,data);
        db.update(TextQuiz.TABLE_NAME, contentValues, TextQuiz.TQUIZID+"= ?",new String[] { quizid });
        return true;
    }



    public boolean updatePositionData(String position,String quizid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(QuizData.POSITION,position);
        db.update(QuizData.TABLE_NAME, contentValues, QuizData.SPELLINGBEEQUIZID+"= ?",new String[] { quizid });
        return true;
    }
    public boolean updateAnswerData(String Answer,String quizid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(QuizData.ANSWERS,Answer);
        db.update(QuizData.TABLE_NAME, contentValues, QuizData.SPELLINGBEEQUIZID+"= ?",new String[] { quizid });
        return true;
    }
    public boolean updateCorrectCount(String count,String quizid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(QuizData.CORRECTANSWEREDCOUNT,count);
        db.update(QuizData.TABLE_NAME, contentValues, QuizData.SPELLINGBEEQUIZID+"= ?",new String[] { quizid });
        return true;
    }
    public boolean updateAnsweredCount(String count,String quizid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(QuizData.ANSWEREDCOUNT,count);
        db.update(QuizData.TABLE_NAME, contentValues, QuizData.SPELLINGBEEQUIZID+"= ?",new String[] { quizid });
        return true;
    }
    public boolean updatePoints(String Answer,String quizid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(QuizData.POINTS,Answer);
        db.update(QuizData.TABLE_NAME, contentValues, QuizData.SPELLINGBEEQUIZID+"= ?",new String[] { quizid });
        return true;
    }
    public boolean updateTime(String Answer,String quizid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(QuizData.TIME,Answer);
        db.update(QuizData.TABLE_NAME, contentValues, QuizData.SPELLINGBEEQUIZID+"= ?",new String[] { quizid });
        return true;
    }


    public int getNotesCount() {
        String countQuery = "SELECT  * FROM " + QuizData.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        // return count
        return count;
    }

//    public int updateNote(PendingFile note) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(PendingFile.COLUMN_NOTE, note.getNote());
//
//        // updating row
//        return db.update(PendingFile.TABLE_NAME, values, PendingFile.COLUMN_ID + " = ?",
//                new String[]{String.valueOf(note.getId())});
//    }

//    public void deleteRoadcutting(QuizData note) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(QuizData.TABLE_NAME, OfflineRoadCutting.UPLOADEDIMAGENAME + " = ?",
//                new String[]{(note.getUploadedImageName())});
//        db.close();
//    }

    public void removeAll() {
        // db.delete(String tableName, String whereClause, String[] whereArgs);
        // If whereClause is null, it will delete all rows.
        SQLiteDatabase db = this.getWritableDatabase(); // helper is object extends SQLiteOpenHelper
        db.delete(QuizData.TABLE_NAME, null, null);
        db.delete(ImageQuiz.TABLE_NAME, null, null);
        db.close();
    }
}
