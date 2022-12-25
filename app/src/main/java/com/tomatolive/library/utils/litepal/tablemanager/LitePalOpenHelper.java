package com.tomatolive.library.utils.litepal.tablemanager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.tomatolive.library.utils.litepal.LitePalApplication;
import com.tomatolive.library.utils.litepal.Operator;
import com.tomatolive.library.utils.litepal.parser.LitePalAttr;
import com.tomatolive.library.utils.litepal.tablemanager.callback.DatabaseListener;
import com.tomatolive.library.utils.litepal.util.SharedUtil;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public class LitePalOpenHelper extends SQLiteOpenHelper {
    public static final String TAG = "LitePalHelper";

    LitePalOpenHelper(Context context, String str, SQLiteDatabase.CursorFactory cursorFactory, int i) {
        super(context, str, cursorFactory, i);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public LitePalOpenHelper(String str, int i) {
        this(LitePalApplication.getContext(), str, null, i);
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        Generator.create(sQLiteDatabase);
        DatabaseListener dBListener = Operator.getDBListener();
        if (dBListener != null) {
            dBListener.onCreate();
        }
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        Generator.upgrade(sQLiteDatabase);
        SharedUtil.updateVersion(LitePalAttr.getInstance().getExtraKeyName(), i2);
        DatabaseListener dBListener = Operator.getDBListener();
        if (dBListener != null) {
            dBListener.onUpgrade(i, i2);
        }
    }
}
