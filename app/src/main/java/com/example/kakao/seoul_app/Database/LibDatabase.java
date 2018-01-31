package com.example.kakao.seoul_app.Database;

/**
 * Created by Kakao on 2017. 10. 2..
 */

public final class LibDatabase {
    LibDatabase() {}

    public static final class FavDataTable {
        private FavDataTable() {}

        public static final String TABLE_NAME = "TB_FAVORITE";

        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_CATEGORY = "category";
        public static final String COLUMN_LAT = "lat";
        public static final String COLUMN_LNG = "lng";

        public String[] getColumnNames() {
            String[] columnNames = {COLUMN_ID, COLUMN_NAME, COLUMN_CATEGORY, COLUMN_LAT, COLUMN_LNG};
            return columnNames;
        }

    }

    public static final class LibDataTable {
        private LibDataTable() {}

        public static final String TABLE_NAME = "TB_LIBRARY";

        public static final String COLUMN_NO = "no";
        public static final String COLUMN_ATDRC = "atdrc";
        public static final String COLUMN_BSNS_NM = "bsns_nm";
        public static final String COLUMN_POSES_THNG = "poses_thng";
        public static final String COLUMN_ADRES = "adres";
        public static final String COLUMN_TELNO = "telno";
        public static final String COLUMN_TARGET = "target";
        public static final String COLUMN_OPENTIME = "opentime";
        public static final String COLUMN_RENT = "rent";
        public static final String COLUMN_LAT = "lat";
        public static final String COLUMN_LNG = "lng";

        public String[] getColumnNames() {
            String[] columnNames = {COLUMN_NO, COLUMN_ATDRC, COLUMN_BSNS_NM, COLUMN_POSES_THNG, COLUMN_ADRES, COLUMN_TELNO, COLUMN_TARGET, COLUMN_OPENTIME, COLUMN_RENT, COLUMN_LAT, COLUMN_LNG};
            return columnNames;
        }

    }
}
