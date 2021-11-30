package com.example.cp470project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class WatchListDBHelper extends SQLiteOpenHelper {
    protected static final String ACTIVITY_NAME = "WatchListDBHelper";
    public static final String DATABASE_NAME = "watchlist.db";
    public static final String TABLE_NAME = "watchlist_data";
    public static final String COL1 = "ID";
    public static final String COL2 = "Movie";
    private static final String DB_CREATE = "create table "
            + TABLE_NAME + "(" + COL1
            + " integer primary key autoincrement, "
            + COL2 + " text not null);";
    public WatchListDBHelper(Context context){super(context,DATABASE_NAME,null,1);}
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.i("WatchListDBHelper", "Calling onCreate");
        sqLiteDatabase.execSQL(DB_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.i("ChatDatabaseHelper", "Calling onUpgrade, oldVersion=" + i + " newVersion=" + i1);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean addData(String item1){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL2, item1);

        long result =  db.insert(TABLE_NAME, null, cv);

        if (result == -1){
            return false;
        }
        else{
            return true;
        }
    }
    public Cursor getListContents(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM "+ TABLE_NAME, null);
        return data;
    }

    public void deleteMovie(int id, String movieName){
        SQLiteDatabase db = this.getWritableDatabase();
        String dQuery = "DELETE FROM " + TABLE_NAME + " WHERE "
                + COL1 + " = '" + id + "'" + " AND "
                + COL2 + " = '" + movieName + "'";
        Log.d(ACTIVITY_NAME,"DeleteName: query: "+dQuery);
        Log.d(ACTIVITY_NAME,"DeleteName: Deleting  "+ movieName + " from database");
        db.execSQL(dQuery);
    }

    public Cursor getID(String movieName){
        SQLiteDatabase db = this.getWritableDatabase();
        String dQuery = "SELECT " + COL1 + " FROM "
                + TABLE_NAME + " WHERE " + COL2 + " = '" +
                 movieName + "'";
        Cursor data = db.rawQuery(dQuery, null);
        return data;
    }
}
