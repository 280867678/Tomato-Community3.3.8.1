package com.sensorsdata.analytics.android.sdk.data.persistent;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import com.sensorsdata.analytics.android.sdk.SALog;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@SuppressLint({"CommitPrefEdits"})
/* loaded from: classes3.dex */
public abstract class PersistentIdentity<T> {
    private static final String TAG = "SA.PersistentIdentity";
    private T item;
    private final Future<SharedPreferences> loadStoredPreferences;
    private final String persistentKey;
    private final PersistentSerializer serializer;

    /* loaded from: classes3.dex */
    interface PersistentSerializer<T> {
        /* renamed from: create */
        T mo6511create();

        /* renamed from: load */
        T mo6512load(String str);

        String save(T t);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public PersistentIdentity(Future<SharedPreferences> future, String str, PersistentSerializer<T> persistentSerializer) {
        this.loadStoredPreferences = future;
        this.serializer = persistentSerializer;
        this.persistentKey = str;
    }

    public T get() {
        if (this.item == null) {
            synchronized (this.loadStoredPreferences) {
                String str = null;
                try {
                    SharedPreferences sharedPreferences = this.loadStoredPreferences.get();
                    if (sharedPreferences != null) {
                        str = sharedPreferences.getString(this.persistentKey, null);
                    }
                } catch (InterruptedException e) {
                    SALog.m3676d(TAG, "Cannot read distinct ids from sharedPreferences.", e);
                } catch (ExecutionException e2) {
                    SALog.m3676d(TAG, "Cannot read distinct ids from sharedPreferences.", e2.getCause());
                }
                if (str == null) {
                    this.item = (T) this.serializer.mo6511create();
                } else {
                    this.item = (T) this.serializer.mo6512load(str);
                }
            }
        }
        return this.item;
    }

    public void commit(T t) {
        this.item = t;
        synchronized (this.loadStoredPreferences) {
            SharedPreferences sharedPreferences = null;
            try {
                try {
                    sharedPreferences = this.loadStoredPreferences.get();
                } catch (InterruptedException e) {
                    SALog.m3676d(TAG, "Cannot read distinct ids from sharedPreferences.", e);
                }
            } catch (ExecutionException e2) {
                SALog.m3676d(TAG, "Cannot read distinct ids from sharedPreferences.", e2.getCause());
            }
            if (sharedPreferences == null) {
                return;
            }
            SharedPreferences.Editor edit = sharedPreferences.edit();
            if (this.item == null) {
                this.item = (T) this.serializer.mo6511create();
            }
            edit.putString(this.persistentKey, this.serializer.save(this.item));
            edit.apply();
        }
    }
}
