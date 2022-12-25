package com.sensorsdata.analytics.android.sdk.data;

import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import com.j256.ormlite.field.FieldType;
import com.sensorsdata.analytics.android.sdk.AopConstants;
import com.sensorsdata.analytics.android.sdk.DataEntity;
import com.sensorsdata.analytics.android.sdk.SALog;
import com.sensorsdata.analytics.android.sdk.SensorsDataAPI;
import com.sensorsdata.analytics.android.sdk.data.PersistentLoader;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public class DbAdapter {
    private static final String TAG = "SA.DbAdapter";
    private static DbAdapter instance;
    private ContentResolver contentResolver;
    private final Context mContext;
    private final File mDatabaseFile;
    private final DbParams mDbParams;
    private int mSessionTime = 30000;
    private long mAppEndTime = 0;

    private DbAdapter(Context context, String str) {
        this.mContext = context.getApplicationContext();
        this.contentResolver = this.mContext.getContentResolver();
        this.mDatabaseFile = context.getDatabasePath("sensorsdata");
        this.mDbParams = DbParams.getInstance(str);
    }

    public static DbAdapter getInstance(Context context, String str) {
        if (instance == null) {
            instance = new DbAdapter(context, str);
        }
        return instance;
    }

    public static DbAdapter getInstance() {
        DbAdapter dbAdapter = instance;
        if (dbAdapter != null) {
            return dbAdapter;
        }
        throw new IllegalStateException("The static method getInstance(Context context, String packageName) should be called before calling getInstance()");
    }

    private long getMaxCacheSize(Context context) {
        try {
            return SensorsDataAPI.sharedInstance(context).getMaxCacheSize();
        } catch (Exception e) {
            SALog.printStackTrace(e);
            return 33554432L;
        }
    }

    private boolean belowMemThreshold() {
        if (this.mDatabaseFile.exists()) {
            SALog.m3675i("本地数据库的当前大小：" + (this.mDatabaseFile.length() / 1024) + "kb");
            return this.mDatabaseFile.length() >= getMaxCacheSize(this.mContext);
        }
        return false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x00d5, code lost:
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x00d2, code lost:
        if (r1 == null) goto L18;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public int addJSON(JSONObject jSONObject) {
        SALog.m3675i("添加到数据库的事件：" + jSONObject.toString());
        int i = -1;
        Cursor cursor = null;
        try {
            try {
                if (belowMemThreshold()) {
                    SALog.m3674i(TAG, "There is not enough space left on the device to store events, so will delete 100 oldest events");
                    String[] generateDataString = generateDataString("events", 100);
                    SALog.m3675i("数据库满了，删除旧数据100条：" + generateDataString);
                    if (generateDataString == null || (i = cleanupEvents(generateDataString[0])) <= 0) {
                        return -2;
                    }
                }
                ContentValues contentValues = new ContentValues();
                contentValues.put(AopConstants.APP_PROPERTIES_KEY, jSONObject.toString() + "\t" + jSONObject.toString().hashCode());
                contentValues.put("created_at", Long.valueOf(System.currentTimeMillis()));
                this.contentResolver.insert(this.mDbParams.getEventUri(), contentValues);
                cursor = this.contentResolver.query(this.mDbParams.getEventUri(), null, null, null, null);
                if (cursor != null) {
                    i = cursor.getCount();
                    SALog.m3675i("本地数据条数：" + i);
                }
            } catch (Exception e) {
                SALog.printStackTrace(e);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:23:0x00ae, code lost:
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x00ab, code lost:
        if (r1 == null) goto L23;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public int addJSON(List<JSONObject> list) {
        int i = -1;
        Cursor cursor = null;
        try {
            try {
                int i2 = 0;
                if (belowMemThreshold()) {
                    SALog.m3674i(TAG, "There is not enough space left on the device to store events, so will delete 100 oldest events");
                    String[] generateDataString = generateDataString("events", 100);
                    if (generateDataString == null || (i = cleanupEvents(generateDataString[0])) <= 0) {
                        return -2;
                    }
                }
                ContentValues[] contentValuesArr = new ContentValues[list.size()];
                for (JSONObject jSONObject : list) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(AopConstants.APP_PROPERTIES_KEY, jSONObject.toString() + "\t" + jSONObject.toString().hashCode());
                    contentValues.put("created_at", Long.valueOf(System.currentTimeMillis()));
                    contentValuesArr[i2] = contentValues;
                    i2++;
                }
                this.contentResolver.bulkInsert(this.mDbParams.getEventUri(), contentValuesArr);
                cursor = this.contentResolver.query(this.mDbParams.getEventUri(), null, null, null, null);
                if (cursor != null) {
                    i = cursor.getCount();
                }
            } catch (Exception e) {
                SALog.printStackTrace(e);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public void deleteAllEvents() {
        try {
            this.contentResolver.delete(this.mDbParams.getEventUri(), null, null);
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x002e, code lost:
        r0.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0038, code lost:
        if (r0 == null) goto L8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:7:0x002c, code lost:
        if (r0 != null) goto L10;
     */
    /* JADX WARN: Code restructure failed: missing block: B:8:0x003b, code lost:
        return r1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public int cleanupEvents(String str) {
        Cursor cursor = null;
        int i = -1;
        try {
            try {
                this.contentResolver.delete(this.mDbParams.getEventUri(), "_id <= ?", new String[]{str});
                cursor = this.contentResolver.query(this.mDbParams.getEventUri(), null, null, null, null);
                if (cursor != null) {
                    i = cursor.getCount();
                }
            } catch (Throwable th) {
                if (cursor != null) {
                    try {
                        cursor.close();
                    } catch (Exception unused) {
                    }
                }
                throw th;
            }
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x0053, code lost:
        if (r11 != null) goto L16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x0066, code lost:
        if (r11 == null) goto L13;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public int cleanupEvents(List<String> list) {
        ArrayList<ContentProviderOperation> arrayList = new ArrayList<>();
        Iterator<String> it2 = list.iterator();
        while (it2.hasNext()) {
            arrayList.add(ContentProviderOperation.newDelete(this.mDbParams.getEventUri()).withSelection("_id = ?", new String[]{it2.next()}).build());
        }
        Cursor cursor = null;
        int i = -1;
        try {
            try {
                try {
                    this.contentResolver.applyBatch(SensorsDataContentProvider.authority, arrayList);
                    cursor = this.contentResolver.query(this.mDbParams.getEventUri(), null, null, null, null);
                    if (cursor != null) {
                        i = cursor.getCount();
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                    if (cursor != null) {
                        try {
                            cursor.close();
                        } catch (Exception unused) {
                            Log.i("meme", "数据库剩余：" + i);
                            return i;
                        }
                    }
                    Log.i("meme", "数据库剩余：" + i);
                    return i;
                }
            } catch (OperationApplicationException e2) {
                e2.printStackTrace();
            }
        } catch (Throwable th) {
            if (cursor != null) {
                try {
                    cursor.close();
                } catch (Exception unused2) {
                }
            }
            throw th;
        }
    }

    public void commitActivityCount(int i) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("activity_started_count", Integer.valueOf(i));
        this.contentResolver.insert(this.mDbParams.getActivityStartCountUri(), contentValues);
    }

    public int getActivityCount() {
        Cursor query = this.contentResolver.query(this.mDbParams.getActivityStartCountUri(), null, null, null, null);
        int i = 0;
        if (query != null && query.getCount() > 0) {
            int i2 = 0;
            while (query.moveToNext()) {
                i2 = query.getInt(0);
            }
            i = i2;
        }
        if (query != null) {
            query.close();
        }
        return i;
    }

    public void commitAppStartTime(long j) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(PersistentLoader.PersistentName.APP_START_TIME, Long.valueOf(j));
        this.contentResolver.insert(this.mDbParams.getAppStartTimeUri(), contentValues);
    }

    public long getAppStartTime() {
        Cursor query = this.contentResolver.query(this.mDbParams.getAppStartTimeUri(), null, null, null, null);
        long j = 0;
        if (query != null && query.getCount() > 0) {
            while (query.moveToNext()) {
                j = query.getLong(0);
            }
        }
        if (query != null) {
            query.close();
        }
        SALog.m3677d(TAG, "getAppStartTime:" + j);
        return j;
    }

    public void commitAppEndTime(long j) {
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(PersistentLoader.PersistentName.APP_PAUSED_TIME, Long.valueOf(j));
            this.contentResolver.insert(this.mDbParams.getAppPausedUri(), contentValues);
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
        this.mAppEndTime = j;
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x0035, code lost:
        if (r0 != null) goto L15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x0040, code lost:
        r0.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x003e, code lost:
        if (r0 == null) goto L25;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public long getAppEndTime() {
        if (System.currentTimeMillis() - this.mAppEndTime > this.mSessionTime) {
            Cursor cursor = null;
            try {
                try {
                    cursor = this.contentResolver.query(this.mDbParams.getAppPausedUri(), null, null, null, null);
                    if (cursor != null && cursor.getCount() > 0) {
                        while (cursor.moveToNext()) {
                            this.mAppEndTime = cursor.getLong(0);
                        }
                    }
                } catch (Exception e) {
                    SALog.printStackTrace(e);
                }
            } catch (Throwable th) {
                if (cursor != null) {
                    cursor.close();
                }
                throw th;
            }
        }
        return this.mAppEndTime;
    }

    public void commitAppEndData(String str) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(PersistentLoader.PersistentName.APP_END_DATA, str);
        this.contentResolver.insert(this.mDbParams.getAppEndDataUri(), contentValues);
    }

    public String getAppEndData() {
        Cursor query = this.contentResolver.query(this.mDbParams.getAppEndDataUri(), null, null, null, null);
        String str = "";
        if (query != null && query.getCount() > 0) {
            while (query.moveToNext()) {
                str = query.getString(0);
            }
        }
        if (query != null) {
            query.close();
        }
        return str;
    }

    public void commitLoginId(String str) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(PersistentLoader.PersistentName.LOGIN_ID, str);
        this.contentResolver.insert(this.mDbParams.getLoginIdUri(), contentValues);
    }

    public String getLoginId() {
        Cursor query = this.contentResolver.query(this.mDbParams.getLoginIdUri(), null, null, null, null);
        String str = "";
        if (query != null && query.getCount() > 0) {
            while (query.moveToNext()) {
                str = query.getString(0);
            }
        }
        if (query != null) {
            query.close();
        }
        SALog.m3677d(TAG, "getLoginId:" + str);
        return str;
    }

    public void commitSessionIntervalTime(int i) {
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(PersistentLoader.PersistentName.APP_SESSION_TIME, Integer.valueOf(i));
            this.contentResolver.insert(this.mDbParams.getSessionTimeUri(), contentValues);
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x0027, code lost:
        if (r0 != null) goto L16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x0035, code lost:
        com.sensorsdata.analytics.android.sdk.SALog.m3677d(com.sensorsdata.analytics.android.sdk.data.DbAdapter.TAG, "getSessionIntervalTime:" + r7.mSessionTime);
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0051, code lost:
        return r7.mSessionTime;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0032, code lost:
        r0.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0030, code lost:
        if (r0 == null) goto L13;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public int getSessionIntervalTime() {
        Cursor cursor = null;
        try {
            try {
                cursor = this.contentResolver.query(this.mDbParams.getSessionTimeUri(), null, null, null, null);
                if (cursor != null && cursor.getCount() > 0) {
                    while (cursor.moveToNext()) {
                        this.mSessionTime = cursor.getInt(0);
                    }
                }
            } catch (Exception e) {
                SALog.printStackTrace(e);
            }
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:12:0x00f2 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:62:0x00f6  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x00ea  */
    /* JADX WARN: Type inference failed for: r14v0, types: [int] */
    /* JADX WARN: Type inference failed for: r14v1 */
    /* JADX WARN: Type inference failed for: r14v3, types: [android.database.Cursor] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public String[] generateDataString(String str, int i) {
        Cursor cursor;
        String str2;
        String str3;
        try {
            try {
                cursor = this.contentResolver.query(this.mDbParams.getEventUri(), null, null, null, "created_at ASC LIMIT " + ((int) i));
                if (cursor != null) {
                    try {
                        StringBuilder sb = new StringBuilder();
                        sb.append("[");
                        String str4 = ",";
                        str3 = null;
                        while (cursor.moveToNext()) {
                            if (cursor.isLast()) {
                                str4 = "]";
                                str3 = cursor.getString(cursor.getColumnIndex(FieldType.FOREIGN_ID_FIELD_SUFFIX));
                            }
                            try {
                                String string = cursor.getString(cursor.getColumnIndex(AopConstants.APP_PROPERTIES_KEY));
                                if (!TextUtils.isEmpty(string)) {
                                    int lastIndexOf = string.lastIndexOf("\t");
                                    if (lastIndexOf > -1) {
                                        String replaceFirst = string.substring(lastIndexOf).replaceFirst("\t", "");
                                        string = string.substring(0, lastIndexOf);
                                        if (!TextUtils.isEmpty(string) && !TextUtils.isEmpty(replaceFirst) && replaceFirst.equals(String.valueOf(string.hashCode()))) {
                                        }
                                    }
                                    sb.append((CharSequence) string, 0, string.length() - 1);
                                    sb.append("}");
                                    sb.append(str4);
                                }
                            } catch (Exception e) {
                                SALog.printStackTrace(e);
                            }
                        }
                        str2 = sb.toString();
                    } catch (SQLiteException e2) {
                        e = e2;
                        SALog.m3673i(TAG, "Could not pull records for SensorsData out of database " + str + ". Waiting to send.", e);
                        if (cursor != null) {
                            cursor.close();
                        }
                        str2 = null;
                        str3 = null;
                        if (str3 == null) {
                        }
                    }
                } else {
                    str2 = null;
                    str3 = null;
                }
                if (cursor != null) {
                    cursor.close();
                }
            } catch (Throwable th) {
                th = th;
                if (i != 0) {
                    i.close();
                }
                throw th;
            }
        } catch (SQLiteException e3) {
            e = e3;
            cursor = null;
        } catch (Throwable th2) {
            th = th2;
            i = 0;
            if (i != 0) {
            }
            throw th;
        }
        if (str3 == null) {
            return new String[]{str3, str2};
        }
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:47:0x00c4  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00ca A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public DataEntity generateDataEntity(int i) {
        Cursor cursor;
        String str;
        ArrayList arrayList = new ArrayList();
        try {
            cursor = this.contentResolver.query(this.mDbParams.getEventUri(), null, null, null, "created_at ASC LIMIT " + i);
            try {
                try {
                    SALog.m3675i("取出的数据个数：" + cursor.getCount());
                    str = null;
                    if (cursor != null) {
                        while (cursor.moveToNext()) {
                            if (cursor.isLast()) {
                                str = cursor.getString(cursor.getColumnIndex(FieldType.FOREIGN_ID_FIELD_SUFFIX));
                            }
                            try {
                                String string = cursor.getString(cursor.getColumnIndex(AopConstants.APP_PROPERTIES_KEY));
                                if (!TextUtils.isEmpty(string)) {
                                    int lastIndexOf = string.lastIndexOf("\t");
                                    if (lastIndexOf > -1) {
                                        String replaceFirst = string.substring(lastIndexOf).replaceFirst("\t", "");
                                        string = string.substring(0, lastIndexOf);
                                        if (!TextUtils.isEmpty(string) && !TextUtils.isEmpty(replaceFirst) && replaceFirst.equals(String.valueOf(string.hashCode()))) {
                                        }
                                    }
                                    arrayList.add(string);
                                }
                            } catch (Exception e) {
                                SALog.printStackTrace(e);
                            }
                        }
                    }
                    if (cursor != null) {
                        cursor.close();
                    }
                } catch (SQLiteException unused) {
                    if (cursor != null) {
                        cursor.close();
                    }
                    str = null;
                    if (str == null) {
                    }
                }
            } catch (Throwable th) {
                th = th;
                if (cursor != null) {
                    cursor.close();
                }
                throw th;
            }
        } catch (SQLiteException unused2) {
            cursor = null;
        } catch (Throwable th2) {
            th = th2;
            cursor = null;
        }
        if (str == null) {
            return new DataEntity(str, arrayList);
        }
        return null;
    }

    public List<JSONObject> generateDataEntity() {
        Cursor cursor;
        JSONObject jSONObject;
        ArrayList arrayList = new ArrayList();
        Cursor cursor2 = null;
        try {
            cursor = this.contentResolver.query(this.mDbParams.getEventUri(), null, null, null, null);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    try {
                        try {
                            try {
                                String string = cursor.getString(cursor.getColumnIndex(AopConstants.APP_PROPERTIES_KEY));
                                String string2 = cursor.getString(cursor.getColumnIndex(FieldType.FOREIGN_ID_FIELD_SUFFIX));
                                if (!TextUtils.isEmpty(string)) {
                                    int lastIndexOf = string.lastIndexOf("\t");
                                    if (lastIndexOf > -1) {
                                        String replaceFirst = string.substring(lastIndexOf).replaceFirst("\t", "");
                                        String substring = string.substring(0, lastIndexOf);
                                        if (!TextUtils.isEmpty(substring) && !TextUtils.isEmpty(replaceFirst) && replaceFirst.equals(String.valueOf(substring.hashCode()))) {
                                            jSONObject = new JSONObject(substring);
                                            jSONObject.put(AopConstants.DB_ID_KEY, string2);
                                        }
                                    } else {
                                        jSONObject = null;
                                    }
                                    arrayList.add(jSONObject);
                                }
                            } catch (Exception unused) {
                            }
                        } catch (Throwable th) {
                            th = th;
                            if (cursor != null) {
                                cursor.close();
                            }
                            throw th;
                        }
                    } catch (SQLiteException unused2) {
                        cursor2 = cursor;
                        if (cursor2 != null) {
                            cursor2.close();
                        }
                        return arrayList;
                    }
                }
            }
            if (cursor != null) {
                cursor.close();
            }
        } catch (SQLiteException unused3) {
        } catch (Throwable th2) {
            th = th2;
            cursor = null;
        }
        return arrayList;
    }
}
