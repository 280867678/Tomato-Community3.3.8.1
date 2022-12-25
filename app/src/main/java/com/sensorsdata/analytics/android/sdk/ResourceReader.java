package com.sensorsdata.analytics.android.sdk;

import android.R;
import android.content.Context;
import android.util.SparseArray;
import com.eclipsesource.p056v8.Platform;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes3.dex */
public abstract class ResourceReader implements ResourceIds {
    private static final String TAG = "SA.ResourceReader";
    private final Context mContext;
    private final Map<String, Integer> mIdNameToId = new HashMap();
    private final SparseArray<String> mIdToIdName = new SparseArray<>();

    protected abstract String getLocalClassName(Context context);

    protected abstract Class<?> getSystemClass();

    protected ResourceReader(Context context) {
        this.mContext = context;
    }

    private static void readClassIds(Class<?> cls, String str, Map<String, Integer> map) {
        Field[] fields;
        try {
            for (Field field : cls.getFields()) {
                if (Modifier.isStatic(field.getModifiers()) && field.getType() == Integer.TYPE) {
                    String name = field.getName();
                    int i = field.getInt(null);
                    if (str != null) {
                        name = str + ":" + name;
                    }
                    map.put(name, Integer.valueOf(i));
                }
            }
        } catch (IllegalAccessException e) {
            SALog.m3673i(TAG, "Can't read built-in id names from " + cls.getName(), e);
        }
    }

    @Override // com.sensorsdata.analytics.android.sdk.ResourceIds
    public boolean knownIdName(String str) {
        return this.mIdNameToId.containsKey(str);
    }

    @Override // com.sensorsdata.analytics.android.sdk.ResourceIds
    public int idFromName(String str) {
        return this.mIdNameToId.get(str).intValue();
    }

    @Override // com.sensorsdata.analytics.android.sdk.ResourceIds
    public String nameForId(int i) {
        return this.mIdToIdName.get(i);
    }

    protected void initialize() {
        this.mIdNameToId.clear();
        this.mIdToIdName.clear();
        readClassIds(getSystemClass(), Platform.ANDROID, this.mIdNameToId);
        String localClassName = getLocalClassName(this.mContext);
        try {
            readClassIds(Class.forName(localClassName), null, this.mIdNameToId);
        } catch (ClassNotFoundException unused) {
            SALog.m3674i(TAG, "Can't load names for Android view ids from '" + localClassName + "', ids by name will not be available in the events editor.");
            SALog.m3674i(TAG, "You may be missing a Resources class for your package due to your proguard configuration, or you may be using an applicationId in your build that isn't the same as the package declared in your AndroidManifest.xml file.\nIf you're using proguard, you can fix this issue by adding the following to your proguard configuration:\n\n-keep class **.R$* {\n    <fields>;\n}\n\nIf you're not using proguard, or if your proguard configuration already contains the directive above, you can add the following to your AndroidManifest.xml file to explicitly point the SensorsData library to the appropriate library for your resources class:\n\n<meta-data android:name=\"com.sensorsdata.analytics.android.ResourcePackageName\" android:value=\"YOUR_PACKAGE_NAME\" />\n\nwhere YOUR_PACKAGE_NAME is the same string you use for the \"package\" attribute in your <manifest> tag.");
        }
        for (Map.Entry<String, Integer> entry : this.mIdNameToId.entrySet()) {
            this.mIdToIdName.put(entry.getValue().intValue(), entry.getKey());
        }
    }

    /* loaded from: classes3.dex */
    public static class Ids extends ResourceReader {
        private final String mResourcePackageName;

        public Ids(String str, Context context) {
            super(context);
            this.mResourcePackageName = str;
            initialize();
        }

        @Override // com.sensorsdata.analytics.android.sdk.ResourceReader
        protected Class<?> getSystemClass() {
            return R.id.class;
        }

        @Override // com.sensorsdata.analytics.android.sdk.ResourceReader
        protected String getLocalClassName(Context context) {
            return this.mResourcePackageName + ".R$id";
        }
    }

    /* loaded from: classes3.dex */
    public static class Drawables extends ResourceReader {
        private final String mResourcePackageName;

        protected Drawables(String str, Context context) {
            super(context);
            this.mResourcePackageName = str;
            initialize();
        }

        @Override // com.sensorsdata.analytics.android.sdk.ResourceReader
        protected Class<?> getSystemClass() {
            return R.drawable.class;
        }

        @Override // com.sensorsdata.analytics.android.sdk.ResourceReader
        protected String getLocalClassName(Context context) {
            return this.mResourcePackageName + ".R$drawable";
        }
    }
}
