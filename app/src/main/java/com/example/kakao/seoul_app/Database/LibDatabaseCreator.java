package com.example.kakao.seoul_app.Database;

import com.example.kakao.seoul_app.Database.LibDatabase.FavDataTable;
import com.example.kakao.seoul_app.Database.LibDatabase.LibDataTable;
/**
 * Created by Kakao on 2017. 10. 2..
 */

public class LibDatabaseCreator implements DatabaseCreator {

    private final String TABLE_CREATE_DATATABLE1 = "CREATE TABLE IF NOT EXISTS "
            + FavDataTable.TABLE_NAME + " ( "
            + FavDataTable.COLUMN_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
            + FavDataTable.COLUMN_NAME + " TEXT, "
            + FavDataTable.COLUMN_CATEGORY + " TEXT, "
            + FavDataTable.COLUMN_LAT + " TEXT, "
            + FavDataTable.COLUMN_LNG + " TEXT);";

    private final String TABLE_CREATE_DATATABLE2 = "CREATE TABLE IF NOT EXISTS "
            + LibDataTable.TABLE_NAME + " ( "
            + LibDataTable.COLUMN_NO + " TEXT NOT NULL PRIMARY KEY, "
            + LibDataTable.COLUMN_ATDRC + " TEXT, "
            + LibDataTable.COLUMN_BSNS_NM + " TEXT, "
            + LibDataTable.COLUMN_POSES_THNG + " TEXT, "
            + LibDataTable.COLUMN_ADRES + " TEXT, "
            + LibDataTable.COLUMN_TELNO + " TEXT, "
            + LibDataTable.COLUMN_TARGET + " TEXT, "
            + LibDataTable.COLUMN_OPENTIME + " TEXT, "
            + LibDataTable.COLUMN_RENT + " TEXT, "
            + LibDataTable.COLUMN_LAT + " TEXT, "
            + LibDataTable.COLUMN_LNG + " TEXT);";



    @Override
    public String[] getCreateTablesStmt() {
        String[] tableStmt = {TABLE_CREATE_DATATABLE1, TABLE_CREATE_DATATABLE2};
        return tableStmt;
    }

    @Override
    public String[] getCreateIndexStmt() {
        return null;
    }

    @Override
    public String[] getCreateViewStmt() {
        return null;
    }

    @Override
    public String[] getCreateTriggerStmt() {
        return null;
    }

    @Override
    public String[] getInitDataInsertStmt() {
        return null;
    }
}
