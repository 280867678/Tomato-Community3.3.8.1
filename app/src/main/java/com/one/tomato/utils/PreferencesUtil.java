package com.one.tomato.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import com.google.gson.Gson;
import com.one.tomato.mvp.base.BaseApplication;

/* loaded from: classes3.dex */
public class PreferencesUtil {
    private static Context context = BaseApplication.getApplication();
    private static PreferencesUtil preferencesUtil;
    private SharedPreferences.Editor editor;
    private SharedPreferences sharedPreferences;

    public static PreferencesUtil getInstance() {
        if (context == null) {
            context = BaseApplication.getApplication();
        }
        if (preferencesUtil == null) {
            preferencesUtil = new PreferencesUtil(context);
        }
        return preferencesUtil;
    }

    public static PreferencesUtil getInstance(Context context2) {
        if (context == null) {
            context = BaseApplication.getApplication();
        }
        if (context == null) {
            context = context2;
        }
        if (preferencesUtil == null) {
            preferencesUtil = new PreferencesUtil(context);
        }
        return preferencesUtil;
    }

    private PreferencesUtil(Context context2) {
        this.sharedPreferences = context2.getSharedPreferences("tomato", 0);
        this.editor = this.sharedPreferences.edit();
        new Gson();
    }

    private boolean isEmptyKey(String str) {
        return TextUtils.isEmpty(str);
    }

    public void putInt(String str, int i) {
        if (isEmptyKey(str)) {
            Log.w("PreferencesUtil", "key值为空,无法保存数据.");
            return;
        }
        this.editor.putInt(str, i);
        this.editor.commit();
    }

    public int getInt(String str) {
        return getInt(str, 0);
    }

    public int getInt(String str, int i) {
        if (isEmptyKey(str)) {
            Log.w("PreferencesUtil", "key值为空,无法获取数据.");
            return 0;
        }
        return this.sharedPreferences.getInt(str, i);
    }

    public void putLong(String str, long j) {
        if (isEmptyKey(str)) {
            Log.w("PreferencesUtil", "key值为空,无法保存数据.");
            return;
        }
        this.editor.putLong(str, j);
        this.editor.commit();
    }

    public long getLong(String str) {
        return getLong(str, 0L);
    }

    public long getLong(String str, long j) {
        if (isEmptyKey(str)) {
            Log.w("PreferencesUtil", "key值为空,无法获取数据.");
            return 0L;
        }
        return this.sharedPreferences.getLong(str, j);
    }

    public void putBoolean(String str, boolean z) {
        if (isEmptyKey(str)) {
            Log.w("PreferencesUtil", "key值为空,无法保存数据.");
            return;
        }
        this.editor.putBoolean(str, z);
        this.editor.commit();
    }

    public boolean getBoolean(String str) {
        return getBoolean(str, false);
    }

    public boolean getBoolean(String str, boolean z) {
        if (isEmptyKey(str)) {
            Log.w("PreferencesUtil", "key值为空,无法获取数据.");
            return false;
        }
        return this.sharedPreferences.getBoolean(str, z);
    }

    public void putString(String str, String str2) {
        if (isEmptyKey(str)) {
            Log.w("PreferencesUtil", "key值为空,无法保存数据.");
            return;
        }
        this.editor.putString(str, str2);
        this.editor.commit();
    }

    public String getString(String str) {
        return getString(str, "");
    }

    public String getString(String str, String str2) {
        if (isEmptyKey(str)) {
            Log.w("PreferencesUtil", "key值为空,无法获取数据.");
            return "";
        }
        return this.sharedPreferences.getString(str, str2);
    }
}
