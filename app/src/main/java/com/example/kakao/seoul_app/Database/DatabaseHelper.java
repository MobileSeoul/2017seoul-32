package com.example.kakao.seoul_app.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
/**
 * Created by Kakao on 2017. 10. 2..
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String CLASSNAME = DatabaseHelper.class.getSimpleName();
    private static final String KEY_COLUMN = "_id";

    private static DatabaseHelper mInstance;
    private static SQLiteDatabase db;

    private DatabaseHelper (Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        Log.v("DB_LOG", DatabaseHelper.CLASSNAME + "Create or Open Database : " + name);
    }

    private DatabaseHelper (final Context context) {
        super(context, DatabaseCreator.DB_NAME, null, DatabaseCreator.DB_VERSION);
        Log.v("DB_LOG", DatabaseHelper.CLASSNAME + "Create or Open Database : " + DatabaseCreator.DB_NAME);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.v("DB_LOG", DatabaseHelper.CLASSNAME + " - onUpgrade() : Table Upgrade Action");
    }

    private static void initialize(Context context) {
        if (mInstance == null) {
            Log.i("DB_LOG", DatabaseHelper.CLASSNAME + "Try to create instance of database (" + DatabaseCreator.DB_NAME + ")");
            mInstance = new DatabaseHelper(context);

            DatabaseCreator mCreator = new LibDatabaseCreator();
            String[] tableCreateStmt = mCreator.getCreateTablesStmt();

            try {
                Log.i("DB_LOG", DatabaseHelper.CLASSNAME + "Creating or opening the database ( " + DatabaseCreator.DB_NAME + ")");
                db = mInstance.getWritableDatabase();
                if(tableCreateStmt != null && tableCreateStmt.length > 0) {
                    Log.v("DB_LOG", DatabaseHelper.CLASSNAME + " - onCreate() : Table Creation");
                    for(int i=0; i<tableCreateStmt.length; i++) {
                        db.execSQL(tableCreateStmt[i]);
                    }
                }
            } catch (SQLiteException se) {
                Log.e("DB_LOG", DatabaseHelper.CLASSNAME
                        + "Could not create and/or open the database ( "
                        + DatabaseCreator.DB_NAME + " ) that will be used for reading and writing.");
            }
            Log.i("DB_LOG", DatabaseHelper.CLASSNAME + "instance of database (" + DatabaseCreator.DB_NAME + ") created!");
        }
    }

    public static final DatabaseHelper getInstance(Context context) {
        initialize(context);
        return mInstance;
    }

    public void close() {
        if(mInstance != null) {
            Log.i("DB_LOG", DatabaseHelper.CLASSNAME + "Closing the database [ " + DatabaseCreator.DB_NAME + " ] ");
            db.close();
            mInstance = null;
        }
    }

    public Cursor get(String table, String[] columns) {
        return db.query(table, columns, null, null, null, null, null);
    }

    public Cursor get(String sql) {
        return db.rawQuery(sql, null);
    }

    public long insert(String table, ContentValues values) {
        return db.insert(table, null, values);
    }

    public int update (String table, ContentValues values, String whereClause) {
        return db.update(table, values, whereClause, null);
    }

    public int delete(String table, String whereClause) {
        return db.delete(table, whereClause, null);
    }

    public void exec(String sql) {
        db.execSQL(sql);
    }

    public void logCursorInfo(Cursor c) {
        Log.i("DB_LOG", "*** Cursor Begin *** " + "Results:"
                + c.getCount() + " Columns: " + c.getColumnCount());
        String rowHeaders = "|| ";
        for(int i=0; i<c.getColumnCount(); i++) {
            rowHeaders = rowHeaders.concat(c.getColumnName(i) + " || ");
        }
        Log.i("DB_LOG", "COLUMNS " + rowHeaders);

        c.moveToFirst();
        while(c.isAfterLast() == false) {
            String rowResults = "|| ";
            for(int i=0; i<c.getColumnCount(); i++) {
                rowResults = rowResults.concat(c.getString(i) + " || ");
            }
            Log.i("DB_LOG", "Row " + c.getPosition() + ": " + rowResults);

            c.moveToNext();

        }

    }



}
