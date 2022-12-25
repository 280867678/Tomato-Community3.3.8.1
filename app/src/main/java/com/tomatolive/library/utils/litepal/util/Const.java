package com.tomatolive.library.utils.litepal.util;

/* loaded from: classes4.dex */
public interface Const {

    /* loaded from: classes4.dex */
    public interface Config {
        public static final String CASES_KEEP = "keep";
        public static final String CASES_LOWER = "lower";
        public static final String CASES_UPPER = "upper";
        public static final String CONFIGURATION_FILE_NAME = "localLitepal.xml";
        public static final String DB_NAME_SUFFIX = ".db";
    }

    /* loaded from: classes4.dex */
    public interface Model {
        public static final int MANY_TO_MANY = 3;
        public static final int MANY_TO_ONE = 2;
        public static final int ONE_TO_ONE = 1;
    }

    /* loaded from: classes4.dex */
    public interface TableSchema {
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_TYPE = "type";
        public static final int GENERIC_TABLE = 2;
        public static final int INTERMEDIATE_JOIN_TABLE = 1;
        public static final int NORMAL_TABLE = 0;
        public static final String TABLE_NAME = "table_schema";
    }

    /* loaded from: classes4.dex */
    public interface Utils {
        public static final String TABLE_SCHEMA_CLASS_NAME = "com.tomatolive.library.utils.litepal.model.Table_Schema";
    }
}
