package com.example.kakao.seoul_app.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Kakao on 2017. 10. 2..
 */

public class DataDao {
    private static final String ClASSNAME = DataDao.class.getSimpleName();
    private DatabaseHelper db;

    public DataDao(Context context) {
        db = DatabaseHelper.getInstance(context);
    }

    public void close() {
        db.close();
    }

    public static class FavDataTo {
        private int id;
        private String name;
        private String category;
        private String lat;
        private String lng;

        public FavDataTo() {}

        public FavDataTo(int id, String name, String category, String lat, String lng) {
            this.id = id;
            this.name = name;
            this.category = category;
            this.lat = lat;
            this.lng = lng;
        }

        @Override
        public String toString() {
            return "DataTo [id=" + String.valueOf(id) + ", name=" + name + ", category=" + category + ", lat=" + lat + ", lng=" + lng + "]";
        }

        public int getId() {
            return id;
        }
        public String getName() {
            return name;
        }
        public String getCategory() {
            return category;
        }
        public String getLat() {
            return lat;
        }
        public String getLng() {
            return lng;
        }
        public void setId(int id) {
            this.id = id;
        }
        public void setName(String name) {
            this.name = name;
        }
        public void setCategory(String category) {
            this.category = category;
        }
        public void setLat(String lat) {
            this.lat = lat;
        }
        public void setLng(String lng) {
            this.lng = lng;
        }
    }

    public static class LibDataTo {
        private String NO;
        private String ATDRC;
        private String BSNS_NM;
        private String POSES_THNG;
        private String ADRES;
        private String TELNO;
        private String TARGET;
        private String OPENTIME;
        private String RENT;
        private String LAT;
        private String LNG;

        public LibDataTo() {}

        public LibDataTo(String NO, String ATDRC, String BSNS_NM, String POSES_THNG, String ADRES, String TELNO, String TARGET, String OPENTIME, String RENT, String LAT, String LNG) {
            this.NO = NO;
            this.ATDRC = ATDRC;
            this.BSNS_NM = BSNS_NM;
            this.POSES_THNG = POSES_THNG;
            this.ADRES = ADRES;
            this.TELNO = TELNO;
            this.TARGET = TARGET;
            this.OPENTIME = OPENTIME;
            this.RENT = RENT;
            this.LAT = LAT;
            this.LNG = LNG;
        }

        @Override
        public String toString() {
            return "DataTo [No=" + NO + ", ATDRC=" + ATDRC + ", BSNS_NM=" + BSNS_NM + ", POSES_THNG=" + POSES_THNG + ", ADRES=" + ADRES + ", TELNO=" + TELNO + ", TARGET=" + TARGET + ", OPENTIME=" + OPENTIME +", RENT=" + RENT + ", LAT=" + LAT + ", LNG=" + LNG +"]";
        }

        public String getNO() {
            return NO;
        }
        public void setNO(String NO) {
            this.NO = NO;
        }
        public String getATDRC() {
            return ATDRC;
        }
        public void setATDRC(String ATDRC) {
            this.ATDRC = ATDRC;
        }
        public String getBSNS_NM() {
            return BSNS_NM;
        }
        public void setBSNS_NM(String BSNS_NM) {
            this.BSNS_NM = BSNS_NM;
        }
        public String getPOSES_THNG() {
            return POSES_THNG;
        }
        public void setPOSES_THNG(String POSES_THNG) {
            this.POSES_THNG = POSES_THNG;
        }
        public String getADRES() {
            return ADRES;
        }
        public void setADRES(String ADRES) {
            this.ADRES = ADRES;
        }
        public String getTELNO() {
            return TELNO;
        }
        public void setTELNO(String TELNO) {
            this.TELNO = TELNO;
        }
        public String getTARGET() {
            return TARGET;
        }
        public void setTARGET(String TARGET) {
            this.TARGET = TARGET;
        }
        public String getOPENTIME() {
            return OPENTIME;
        }
        public void setOPENTIME(String OPENTIME) {
            this.OPENTIME = OPENTIME;
        }
        public String getRENT() {
            return RENT;
        }
        public void setRENT(String RENT) {
            this.RENT = RENT;
        }
        public String getLAT() {
            return LAT;
        }
        public void setLAT(String LAT) {
            this.LAT = LAT;
        }
        public String getLNG() {
            return LNG;
        }
        public void setLNG(String LNG) {
            this.LNG = LNG;
        }
    }


    public void insert(final FavDataTo to) {
        ContentValues values = new ContentValues();

        values.put(LibDatabase.FavDataTable.COLUMN_NAME, to.getName());
        values.put(LibDatabase.FavDataTable.COLUMN_CATEGORY, to.getCategory());
        values.put(LibDatabase.FavDataTable.COLUMN_LAT, to.getLat());
        values.put(LibDatabase.FavDataTable.COLUMN_LNG, to.getLng());

        long rowId = db.insert(LibDatabase.FavDataTable.TABLE_NAME, values);
        if(rowId < 0) {
            throw new SQLException("Fail At Insert");
        }
    }
    public void insert(final LibDataTo to) {
        ContentValues values = new ContentValues();

        values.put(LibDatabase.LibDataTable.COLUMN_NO, to.getNO());
        values.put(LibDatabase.LibDataTable.COLUMN_ATDRC, to.getATDRC());
        values.put(LibDatabase.LibDataTable.COLUMN_BSNS_NM, to.getBSNS_NM());
        values.put(LibDatabase.LibDataTable.COLUMN_POSES_THNG, to.getPOSES_THNG());
        values.put(LibDatabase.LibDataTable.COLUMN_ADRES, to.getADRES());
        values.put(LibDatabase.LibDataTable.COLUMN_TELNO, to.getTELNO());
        values.put(LibDatabase.LibDataTable.COLUMN_TARGET, to.getTARGET());
        values.put(LibDatabase.LibDataTable.COLUMN_OPENTIME, to.getOPENTIME());
        values.put(LibDatabase.LibDataTable.COLUMN_RENT, to.getRENT());
        values.put(LibDatabase.LibDataTable.COLUMN_LAT, to.getLAT());
        values.put(LibDatabase.LibDataTable.COLUMN_LNG, to.getLNG());

        long rowId = db.insert(LibDatabase.LibDataTable.TABLE_NAME, values);
        if(rowId < 0) {
            throw new SQLException("Fail At Insert");
        }
    }

    public void update(final FavDataTo to) {
        ContentValues values = new ContentValues();

        values.put(LibDatabase.FavDataTable.COLUMN_NAME, to.getName());
        values.put(LibDatabase.FavDataTable.COLUMN_CATEGORY, to.getCategory());
        values.put(LibDatabase.FavDataTable.COLUMN_LAT, to.getLat());
        values.put(LibDatabase.FavDataTable.COLUMN_LNG, to.getLng());

        db.update(LibDatabase.FavDataTable.TABLE_NAME, values, Integer.toString(to.getId()));

    }
    public void update(final LibDataTo to) {
        ContentValues values = new ContentValues();

        values.put(LibDatabase.LibDataTable.COLUMN_ATDRC, to.getATDRC());
        values.put(LibDatabase.LibDataTable.COLUMN_BSNS_NM, to.getBSNS_NM());
        values.put(LibDatabase.LibDataTable.COLUMN_POSES_THNG, to.getPOSES_THNG());
        values.put(LibDatabase.LibDataTable.COLUMN_ADRES, to.getADRES());
        values.put(LibDatabase.LibDataTable.COLUMN_TELNO, to.getTELNO());
        values.put(LibDatabase.LibDataTable.COLUMN_TARGET, to.getTARGET());
        values.put(LibDatabase.LibDataTable.COLUMN_OPENTIME, to.getOPENTIME());
        values.put(LibDatabase.LibDataTable.COLUMN_RENT, to.getRENT());
        values.put(LibDatabase.LibDataTable.COLUMN_LAT, to.getLAT());
        values.put(LibDatabase.LibDataTable.COLUMN_LNG, to.getLNG());


        db.update(LibDatabase.LibDataTable.TABLE_NAME, values, to.getNO());

    }

    public void delete(final int id, String tableName) {
        if(tableName.equals("FavDataTable")) {
            db.delete(LibDatabase.FavDataTable.TABLE_NAME, Integer.toString(id));
        } else {
            db.delete(LibDatabase.LibDataTable.TABLE_NAME, Integer.toString(id));
        }
    }

    public void drop(String TableName) {
        String sql = "DROP TABLE " + TableName;
        db.exec(sql);
    }

    public void deleteAll(String TableName) {
        String sql = "DELETE FROM " + TableName;
        db.exec(sql);
    }

    public List<FavDataTo> getFav() {
        Cursor c = null;
        ArrayList<FavDataTo> ret = null;

        String sql = "SELECT * FROM " + LibDatabase.FavDataTable.TABLE_NAME + " ORDER BY 1";

        try {
            c = db.get(sql);
            ret = setFavBindCursor(c);
        } catch (SQLException e) {
            Log.e("DB_LOG", DataDao.ClASSNAME + " getList ", e);
        } finally {
            if (c != null && !c.isClosed()) {
                c.close();
            }
        }
        return ret;

    }
    public List<LibDataTo> getLib() {
        Cursor c = null;
        ArrayList<LibDataTo> ret = null;

        String sql = "SELECT * FROM " + LibDatabase.LibDataTable.TABLE_NAME + " ORDER BY 1";

        try {
            c = db.get(sql);
            ret = setLibBindCursor(c);
        } catch (SQLException e) {
            Log.e("DB_LOG", DataDao.ClASSNAME + " getList ", e);
        } finally {
            if (c != null && !c.isClosed()) {
                c.close();
            }
        }
        return ret;

    }

    public LibDataTo select(String name) {
        Cursor c = null;
        LibDataTo result = null;
        String sql = "SELECT * FROM " + LibDatabase.LibDataTable.TABLE_NAME + " WHERE BSNS_NM='" + name +"'";

        try {
            c = db.get(sql);
            result = setLibBindCursor(c).get(0);
        } catch ( Exception e ) {
            Log.e("DB_LOG", DataDao.ClASSNAME + " select ", e);
        } finally {
            if (c != null && !c.isClosed()) {
                c.close();
            }
        }
        return result;
    }

    private ArrayList<FavDataTo> setFavBindCursor(final Cursor c) {
        ArrayList<FavDataTo> ret = new ArrayList<>();
        int numRows = c.getCount();
        c.moveToFirst();

        for(int i=0; i<numRows; i++) {
            FavDataTo to = new FavDataTo();
            to.setId(c.getInt(c.getColumnIndex(LibDatabase.FavDataTable.COLUMN_ID)));
            to.setName(c.getString(c.getColumnIndex(LibDatabase.FavDataTable.COLUMN_NAME)));
            to.setCategory(c.getString(c.getColumnIndex(LibDatabase.FavDataTable.COLUMN_CATEGORY)));
            to.setLat(c.getString(c.getColumnIndex(LibDatabase.FavDataTable.COLUMN_LAT)));
            to.setLng(c.getString(c.getColumnIndex(LibDatabase.FavDataTable.COLUMN_LNG)));
            ret.add(to);
            c.moveToNext();
        }
        return ret;
    }

    private ArrayList<LibDataTo> setLibBindCursor(final Cursor c) {
        ArrayList<LibDataTo> ret = new ArrayList<>();
        int numRows = c.getCount();
        c.moveToFirst();

        for(int i=0; i<numRows; i++) {
            LibDataTo to = new LibDataTo();
            to.setNO(c.getString(c.getColumnIndex(LibDatabase.LibDataTable.COLUMN_NO)));
            to.setATDRC(c.getString(c.getColumnIndex(LibDatabase.LibDataTable.COLUMN_ATDRC)));
            to.setBSNS_NM(c.getString(c.getColumnIndex(LibDatabase.LibDataTable.COLUMN_BSNS_NM)));
            to.setPOSES_THNG(c.getString(c.getColumnIndex(LibDatabase.LibDataTable.COLUMN_POSES_THNG)));
            to.setADRES(c.getString(c.getColumnIndex(LibDatabase.LibDataTable.COLUMN_ADRES)));
            to.setTELNO(c.getString(c.getColumnIndex(LibDatabase.LibDataTable.COLUMN_TELNO)));
            to.setTARGET(c.getString(c.getColumnIndex(LibDatabase.LibDataTable.COLUMN_TARGET)));
            to.setOPENTIME(c.getString(c.getColumnIndex(LibDatabase.LibDataTable.COLUMN_OPENTIME)));
            to.setRENT(c.getString(c.getColumnIndex(LibDatabase.LibDataTable.COLUMN_RENT)));
            to.setLAT(c.getString(c.getColumnIndex(LibDatabase.LibDataTable.COLUMN_LAT)));
            to.setLNG(c.getString(c.getColumnIndex(LibDatabase.LibDataTable.COLUMN_LNG)));
            ret.add(to);
            c.moveToNext();
        }
        return ret;
    }

    public void logFavListInfo(List<FavDataTo> to) {
        Log.i("DB_LOG", " *** List Begin *** " + "Result: " + to.size());
        Iterator<FavDataTo> itr = to.iterator();
        while(itr.hasNext()) {
            String msg = ((FavDataTo)itr.next()).toString();
            Log.i("DB_LOG", "Fav DATAS: " + msg);
        }
        Log.i("DB_LOG", " *** List End *** ");

    }
    public void logLibListInfo(List<LibDataTo> to) {
        Log.i("DB_LOG", " *** List Begin *** " + "Result: " + to.size());
        Iterator<LibDataTo> itr = to.iterator();
        while(itr.hasNext()) {
            String msg = ((LibDataTo)itr.next()).toString();
            Log.i("DB_LOG", "Lib DATAS: " + msg);
        }
        Log.i("DB_LOG", " *** List End *** ");

    }

}
