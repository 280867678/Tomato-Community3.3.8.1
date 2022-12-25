package com.sensorsdata.analytics.android.sdk.data;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import com.j256.ormlite.field.FieldType;
import com.sensorsdata.analytics.android.sdk.AopConstants;
import com.sensorsdata.analytics.android.sdk.SALog;
import com.sensorsdata.analytics.android.sdk.data.PersistentLoader;
import com.sensorsdata.analytics.android.sdk.data.persistent.PersistentAppEndData;
import com.sensorsdata.analytics.android.sdk.data.persistent.PersistentAppPaused;
import com.sensorsdata.analytics.android.sdk.data.persistent.PersistentAppStartTime;
import com.sensorsdata.analytics.android.sdk.data.persistent.PersistentLoginId;
import com.sensorsdata.analytics.android.sdk.data.persistent.PersistentSessionIntervalTime;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public class SensorsDataContentProvider extends ContentProvider {
    private static final int ACTIVITY_START_COUNT = 2;
    private static final int APP_END_DATA = 4;
    private static final int APP_PAUSED_TIME = 5;
    private static final int APP_START_TIME = 3;
    private static final int EVENTS = 1;
    private static final int LOGIN_ID = 7;
    private static final int SESSION_INTERVAL_TIME = 6;
    public static String authority;
    private static UriMatcher uriMatcher = new UriMatcher(-1);
    private ContentResolver contentResolver;
    private SensorsDataDBHelper dbHelper;
    private PersistentAppEndData persistentAppEndData;
    private PersistentAppPaused persistentAppPaused;
    private PersistentAppStartTime persistentAppStartTime;
    private PersistentLoginId persistentLoginId;
    private PersistentSessionIntervalTime persistentSessionIntervalTime;
    private boolean isDbWritable = true;
    private int startActivityCount = 0;

    @Override // android.content.ContentProvider
    public String getType(Uri uri) {
        return null;
    }

    @Override // android.content.ContentProvider
    public int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        return 0;
    }

    @Override // android.content.ContentProvider
    public boolean onCreate() {
        String str;
        Context context = getContext();
        if (context != null) {
            try {
                str = context.getApplicationContext().getPackageName();
            } catch (UnsupportedOperationException unused) {
                str = "com.sensorsdata.analytics.android.sdk.test";
            }
            String str2 = str;
            authority = str2 + ".SensorsDataContentProvider";
            this.contentResolver = context.getContentResolver();
            uriMatcher.addURI(authority, "events", 1);
            uriMatcher.addURI(authority, "activity_started_count", 2);
            uriMatcher.addURI(authority, PersistentLoader.PersistentName.APP_START_TIME, 3);
            uriMatcher.addURI(authority, PersistentLoader.PersistentName.APP_END_DATA, 4);
            uriMatcher.addURI(authority, PersistentLoader.PersistentName.APP_PAUSED_TIME, 5);
            uriMatcher.addURI(authority, PersistentLoader.PersistentName.APP_SESSION_TIME, 6);
            uriMatcher.addURI(authority, PersistentLoader.PersistentName.LOGIN_ID, 7);
            this.dbHelper = new SensorsDataDBHelper(context);
            try {
                if (context.getDatabasePath(str2).exists()) {
                    JSONArray allEvents = new OldBDatabaseHelper(context, str2).getAllEvents();
                    for (int i = 0; i < allEvents.length(); i++) {
                        JSONObject jSONObject = allEvents.getJSONObject(i);
                        ContentValues contentValues = new ContentValues();
                        contentValues.put(AopConstants.APP_PROPERTIES_KEY, jSONObject.getString(AopConstants.APP_PROPERTIES_KEY));
                        contentValues.put("created_at", jSONObject.getString("created_at"));
                        try {
                            this.dbHelper.getWritableDatabase().insert("events", FieldType.FOREIGN_ID_FIELD_SUFFIX, contentValues);
                        } catch (SQLiteException e) {
                            this.isDbWritable = false;
                            SALog.printStackTrace(e);
                        }
                    }
                }
                if (this.isDbWritable) {
                    context.deleteDatabase(str2);
                }
            } catch (Exception e2) {
                SALog.printStackTrace(e2);
            }
            PersistentLoader.initLoader(context);
            this.persistentAppEndData = (PersistentAppEndData) PersistentLoader.loadPersistent(PersistentLoader.PersistentName.APP_END_DATA);
            this.persistentAppStartTime = (PersistentAppStartTime) PersistentLoader.loadPersistent(PersistentLoader.PersistentName.APP_START_TIME);
            this.persistentAppPaused = (PersistentAppPaused) PersistentLoader.loadPersistent(PersistentLoader.PersistentName.APP_PAUSED_TIME);
            this.persistentSessionIntervalTime = (PersistentSessionIntervalTime) PersistentLoader.loadPersistent(PersistentLoader.PersistentName.APP_SESSION_TIME);
            this.persistentLoginId = (PersistentLoginId) PersistentLoader.loadPersistent(PersistentLoader.PersistentName.LOGIN_ID);
            return true;
        }
        return true;
    }

    @Override // android.content.ContentProvider
    public int delete(Uri uri, String str, String[] strArr) {
        int i = 0;
        if (!this.isDbWritable) {
            return 0;
        }
        try {
            if (1 == uriMatcher.match(uri)) {
                try {
                    i = this.dbHelper.getWritableDatabase().delete("events", str, strArr);
                } catch (SQLiteException e) {
                    this.isDbWritable = false;
                    SALog.printStackTrace(e);
                }
            }
        } catch (Exception e2) {
            SALog.printStackTrace(e2);
        }
        return i;
    }

    @Override // android.content.ContentProvider
    public Uri insert(Uri uri, ContentValues contentValues) {
        if (this.isDbWritable && contentValues != null && contentValues.size() != 0) {
            try {
                int match = uriMatcher.match(uri);
                if (match == 1) {
                    try {
                        SQLiteDatabase writableDatabase = this.dbHelper.getWritableDatabase();
                        if (contentValues.containsKey(AopConstants.APP_PROPERTIES_KEY) && contentValues.containsKey("created_at")) {
                            return ContentUris.withAppendedId(uri, writableDatabase.insert("events", FieldType.FOREIGN_ID_FIELD_SUFFIX, contentValues));
                        }
                        return uri;
                    } catch (SQLiteException e) {
                        this.isDbWritable = false;
                        SALog.printStackTrace(e);
                        return uri;
                    }
                }
                insert(match, uri, contentValues);
                return uri;
            } catch (Exception e2) {
                SALog.printStackTrace(e2);
            }
        }
        return uri;
    }

    @Override // android.content.ContentProvider
    public int bulkInsert(Uri uri, ContentValues[] contentValuesArr) {
        if (!this.isDbWritable) {
            return 0;
        }
        SQLiteDatabase sQLiteDatabase = null;
        try {
            try {
                sQLiteDatabase = this.dbHelper.getWritableDatabase();
                sQLiteDatabase.beginTransaction();
                int length = contentValuesArr.length;
                for (ContentValues contentValues : contentValuesArr) {
                    insert(uri, contentValues);
                }
                sQLiteDatabase.setTransactionSuccessful();
                return length;
            } catch (SQLiteException e) {
                this.isDbWritable = false;
                SALog.printStackTrace(e);
                return 0;
            }
        } finally {
            if (sQLiteDatabase != null) {
                sQLiteDatabase.endTransaction();
            }
        }
    }

    @Override // android.content.ContentProvider
    public Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        Cursor query;
        if (!this.isDbWritable) {
            return null;
        }
        try {
            int match = uriMatcher.match(uri);
            if (match == 1) {
                try {
                    query = this.dbHelper.getWritableDatabase().query("events", strArr, str, strArr2, null, null, str2);
                } catch (SQLiteException e) {
                    this.isDbWritable = false;
                    SALog.printStackTrace(e);
                    return null;
                }
            } else {
                query = query(match);
            }
            return query;
        } catch (Exception e2) {
            SALog.printStackTrace(e2);
            return null;
        }
    }

    private void insert(int i, Uri uri, ContentValues contentValues) {
        switch (i) {
            case 2:
                this.startActivityCount = contentValues.getAsInteger("activity_started_count").intValue();
                return;
            case 3:
                this.persistentAppStartTime.commit(contentValues.getAsLong(PersistentLoader.PersistentName.APP_START_TIME));
                return;
            case 4:
                this.persistentAppEndData.commit(contentValues.getAsString(PersistentLoader.PersistentName.APP_END_DATA));
                return;
            case 5:
                this.persistentAppPaused.commit(contentValues.getAsLong(PersistentLoader.PersistentName.APP_PAUSED_TIME));
                return;
            case 6:
                this.persistentSessionIntervalTime.commit(contentValues.getAsInteger(PersistentLoader.PersistentName.APP_SESSION_TIME));
                this.contentResolver.notifyChange(uri, null);
                return;
            case 7:
                this.persistentLoginId.commit(contentValues.getAsString(PersistentLoader.PersistentName.LOGIN_ID));
                return;
            default:
                return;
        }
    }

    private Cursor query(int i) {
        String str;
        Object obj = null;
        switch (i) {
            case 2:
                obj = Integer.valueOf(this.startActivityCount);
                str = "activity_started_count";
                break;
            case 3:
                obj = this.persistentAppStartTime.get();
                str = PersistentLoader.PersistentName.APP_START_TIME;
                break;
            case 4:
                obj = this.persistentAppEndData.get();
                str = PersistentLoader.PersistentName.APP_END_DATA;
                break;
            case 5:
                obj = this.persistentAppPaused.get();
                str = PersistentLoader.PersistentName.APP_PAUSED_TIME;
                break;
            case 6:
                obj = this.persistentSessionIntervalTime.get();
                str = PersistentLoader.PersistentName.APP_SESSION_TIME;
                break;
            case 7:
                obj = this.persistentLoginId.get();
                str = PersistentLoader.PersistentName.LOGIN_ID;
                break;
            default:
                str = null;
                break;
        }
        MatrixCursor matrixCursor = new MatrixCursor(new String[]{str});
        matrixCursor.addRow(new Object[]{obj});
        return matrixCursor;
    }

    @Override // android.content.ContentProvider
    public ContentProviderResult[] applyBatch(String str, ArrayList<ContentProviderOperation> arrayList) throws OperationApplicationException {
        SQLiteDatabase writableDatabase = this.dbHelper.getWritableDatabase();
        writableDatabase.beginTransaction();
        try {
            ContentProviderResult[] applyBatch = super.applyBatch(str, arrayList);
            writableDatabase.setTransactionSuccessful();
            return applyBatch;
        } finally {
            if (writableDatabase != null) {
                writableDatabase.endTransaction();
            }
        }
    }
}
