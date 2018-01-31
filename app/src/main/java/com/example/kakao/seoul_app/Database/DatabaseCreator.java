package com.example.kakao.seoul_app.Database;

/**
 * Created by Kakao on 2017. 10. 2..
 */

public interface DatabaseCreator {

    public static final String DB_NAME = "APP_DB.db";
    public static final int DB_VERSION = 1;

    public String[] getCreateTablesStmt();
    public String[] getCreateIndexStmt();
    public String[] getCreateViewStmt();
    public String[] getCreateTriggerStmt();
    public String[] getInitDataInsertStmt();
}
