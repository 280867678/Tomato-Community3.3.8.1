package com.amazonaws.mobileconnectors.p053s3.transferutility;

import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import com.amazonaws.logging.LogFactory;

/* renamed from: com.amazonaws.mobileconnectors.s3.transferutility.TransferDBBase */
/* loaded from: classes2.dex */
class TransferDBBase {
    private static final Object LOCK = new Object();
    private final Uri contentUri;
    private final Context context;
    private SQLiteDatabase database;
    private final TransferDatabaseHelper databaseHelper;
    private final UriMatcher uriMatcher = new UriMatcher(-1);

    static {
        LogFactory.getLog(TransferDBBase.class);
    }

    public TransferDBBase(Context context) {
        this.context = context;
        String packageName = context.getApplicationContext().getPackageName();
        this.databaseHelper = new TransferDatabaseHelper(this.context);
        this.database = this.databaseHelper.getWritableDatabase();
        this.contentUri = Uri.parse("content://" + packageName + "/transfers");
        this.uriMatcher.addURI(packageName, "transfers", 10);
        this.uriMatcher.addURI(packageName, "transfers/#", 20);
        this.uriMatcher.addURI(packageName, "transfers/part/#", 30);
        this.uriMatcher.addURI(packageName, "transfers/state/*", 40);
    }

    public Uri getContentUri() {
        return this.contentUri;
    }

    public Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        SQLiteQueryBuilder sQLiteQueryBuilder = new SQLiteQueryBuilder();
        sQLiteQueryBuilder.setTables("awstransfer");
        int match = this.uriMatcher.match(uri);
        if (match == 10) {
            sQLiteQueryBuilder.appendWhere("part_num=0");
        } else if (match == 20) {
            sQLiteQueryBuilder.appendWhere("_id=" + uri.getLastPathSegment());
        } else if (match == 30) {
            sQLiteQueryBuilder.appendWhere("main_upload_id=" + uri.getLastPathSegment());
        } else if (match == 40) {
            sQLiteQueryBuilder.appendWhere("state=");
            sQLiteQueryBuilder.appendWhereEscapeString(uri.getLastPathSegment());
        } else {
            throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        ensureDatabaseOpen();
        return sQLiteQueryBuilder.query(this.database, strArr, str, strArr2, null, null, str2);
    }

    public synchronized int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        int update;
        int match = this.uriMatcher.match(uri);
        ensureDatabaseOpen();
        if (match == 10) {
            update = this.database.update("awstransfer", contentValues, str, strArr);
        } else if (match == 20) {
            String lastPathSegment = uri.getLastPathSegment();
            if (TextUtils.isEmpty(str)) {
                SQLiteDatabase sQLiteDatabase = this.database;
                update = sQLiteDatabase.update("awstransfer", contentValues, "_id=" + lastPathSegment, null);
            } else {
                SQLiteDatabase sQLiteDatabase2 = this.database;
                update = sQLiteDatabase2.update("awstransfer", contentValues, "_id=" + lastPathSegment + " and " + str, strArr);
            }
        } else {
            throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        return update;
    }

    public int delete(Uri uri, String str, String[] strArr) {
        int match = this.uriMatcher.match(uri);
        ensureDatabaseOpen();
        if (match != 10) {
            if (match == 20) {
                String lastPathSegment = uri.getLastPathSegment();
                if (TextUtils.isEmpty(str)) {
                    SQLiteDatabase sQLiteDatabase = this.database;
                    return sQLiteDatabase.delete("awstransfer", "_id=" + lastPathSegment, null);
                }
                SQLiteDatabase sQLiteDatabase2 = this.database;
                return sQLiteDatabase2.delete("awstransfer", "_id=" + lastPathSegment + " and " + str, strArr);
            }
            throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        return this.database.delete("awstransfer", str, strArr);
    }

    private void ensureDatabaseOpen() {
        synchronized (LOCK) {
            if (!this.database.isOpen()) {
                this.database = this.databaseHelper.getWritableDatabase();
            }
        }
    }
}
