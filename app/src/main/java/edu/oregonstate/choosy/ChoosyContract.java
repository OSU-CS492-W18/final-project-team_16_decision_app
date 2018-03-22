package edu.oregonstate.choosy;

import android.provider.BaseColumns;

/**
 * Created by Samuel on 3/19/2018.
 */

public class ChoosyContract {
    private ChoosyContract() {}

    public static class Comparisons implements BaseColumns {
        public static final String TABLE_NAME = "comparisons";
        public static final String COLUMN_FIRST = "firstItem";
        public static final String COLUMN_SECOND = "secondItem";
        public static final String COLUMN_TIMESTAMP = "timestamp";
    }

    public static class Factors implements BaseColumns {
        public static final String TABLE_NAME = "factors";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_COMP = "comp";
        public static final String COLUMN_PRO = "pro";
        public static final String COLUMN_WEIGHT = "weight";
        public static final String COLUMN_TIMESTAMP = "timestamp";
    }
}
