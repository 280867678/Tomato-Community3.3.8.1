package com.sensorsdata.analytics.android.sdk.internal;

import android.content.Context;
import android.text.TextUtils;
import com.sensorsdata.analytics.android.sdk.SALog;
import com.sensorsdata.analytics.android.sdk.SensorsDataIgnoreTrackAppViewScreen;
import com.sensorsdata.analytics.android.sdk.SensorsDataIgnoreTrackAppViewScreenAndAppClick;
import com.sensorsdata.analytics.android.sdk.util.SensorsDataUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/* loaded from: classes3.dex */
public class FragmentAPI implements IFragmentAPI {
    private Set<Integer> mAutoTrackFragments;
    private Set<Integer> mAutoTrackIgnoredFragments;
    private boolean mTrackFragmentAppViewScreen;

    public FragmentAPI(Context context) {
        ArrayList<String> autoTrackFragments = SensorsDataUtils.getAutoTrackFragments(context);
        if (autoTrackFragments.size() > 0) {
            this.mAutoTrackFragments = new CopyOnWriteArraySet();
            Iterator<String> it2 = autoTrackFragments.iterator();
            while (it2.hasNext()) {
                this.mAutoTrackFragments.add(Integer.valueOf(it2.next().hashCode()));
            }
        }
    }

    @Override // com.sensorsdata.analytics.android.sdk.internal.IFragmentAPI
    public void trackFragmentAppViewScreen() {
        this.mTrackFragmentAppViewScreen = true;
    }

    @Override // com.sensorsdata.analytics.android.sdk.internal.IFragmentAPI
    public boolean isTrackFragmentAppViewScreenEnabled() {
        return this.mTrackFragmentAppViewScreen;
    }

    @Override // com.sensorsdata.analytics.android.sdk.internal.IFragmentAPI
    public void enableAutoTrackFragment(Class<?> cls) {
        if (cls == null) {
            return;
        }
        try {
            if (this.mAutoTrackFragments == null) {
                this.mAutoTrackFragments = new CopyOnWriteArraySet();
            }
            String canonicalName = cls.getCanonicalName();
            if (TextUtils.isEmpty(canonicalName)) {
                return;
            }
            this.mAutoTrackFragments.add(Integer.valueOf(canonicalName.hashCode()));
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    @Override // com.sensorsdata.analytics.android.sdk.internal.IFragmentAPI
    public void enableAutoTrackFragments(List<Class<?>> list) {
        if (list == null || list.size() == 0) {
            return;
        }
        if (this.mAutoTrackFragments == null) {
            this.mAutoTrackFragments = new CopyOnWriteArraySet();
        }
        try {
            for (Class<?> cls : list) {
                String canonicalName = cls.getCanonicalName();
                if (!TextUtils.isEmpty(canonicalName)) {
                    this.mAutoTrackFragments.add(Integer.valueOf(canonicalName.hashCode()));
                }
            }
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    @Override // com.sensorsdata.analytics.android.sdk.internal.IFragmentAPI
    public boolean isFragmentAutoTrackAppViewScreen(Class<?> cls) {
        if (cls == null) {
            return false;
        }
        try {
            if (this.mAutoTrackFragments != null && this.mAutoTrackFragments.size() > 0) {
                String canonicalName = cls.getCanonicalName();
                if (!TextUtils.isEmpty(canonicalName)) {
                    return this.mAutoTrackFragments.contains(Integer.valueOf(canonicalName.hashCode()));
                }
            }
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
        if (cls.getAnnotation(SensorsDataIgnoreTrackAppViewScreen.class) != null || cls.getAnnotation(SensorsDataIgnoreTrackAppViewScreenAndAppClick.class) != null) {
            return false;
        }
        if (this.mAutoTrackIgnoredFragments != null && this.mAutoTrackIgnoredFragments.size() > 0) {
            String canonicalName2 = cls.getCanonicalName();
            if (!TextUtils.isEmpty(canonicalName2)) {
                return !this.mAutoTrackIgnoredFragments.contains(Integer.valueOf(canonicalName2.hashCode()));
            }
        }
        return true;
    }

    @Override // com.sensorsdata.analytics.android.sdk.internal.IFragmentAPI
    public void ignoreAutoTrackFragments(List<Class<?>> list) {
        if (list != null) {
            try {
                if (list.size() == 0) {
                    return;
                }
                if (this.mAutoTrackIgnoredFragments == null) {
                    this.mAutoTrackIgnoredFragments = new CopyOnWriteArraySet();
                }
                for (Class<?> cls : list) {
                    if (cls != null) {
                        String canonicalName = cls.getCanonicalName();
                        if (!TextUtils.isEmpty(canonicalName)) {
                            this.mAutoTrackIgnoredFragments.add(Integer.valueOf(canonicalName.hashCode()));
                        }
                    }
                }
            } catch (Exception e) {
                SALog.printStackTrace(e);
            }
        }
    }

    @Override // com.sensorsdata.analytics.android.sdk.internal.IFragmentAPI
    public void ignoreAutoTrackFragment(Class<?> cls) {
        if (cls == null) {
            return;
        }
        try {
            if (this.mAutoTrackIgnoredFragments == null) {
                this.mAutoTrackIgnoredFragments = new CopyOnWriteArraySet();
            }
            String canonicalName = cls.getCanonicalName();
            if (TextUtils.isEmpty(canonicalName)) {
                return;
            }
            this.mAutoTrackIgnoredFragments.add(Integer.valueOf(canonicalName.hashCode()));
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    @Override // com.sensorsdata.analytics.android.sdk.internal.IFragmentAPI
    public void resumeIgnoredAutoTrackFragments(List<Class<?>> list) {
        if (list != null) {
            try {
                if (list.size() != 0 && this.mAutoTrackIgnoredFragments != null) {
                    for (Class<?> cls : list) {
                        if (cls != null) {
                            String canonicalName = cls.getCanonicalName();
                            if (!TextUtils.isEmpty(canonicalName)) {
                                this.mAutoTrackIgnoredFragments.remove(Integer.valueOf(canonicalName.hashCode()));
                            }
                        }
                    }
                }
            } catch (Exception e) {
                SALog.printStackTrace(e);
            }
        }
    }

    @Override // com.sensorsdata.analytics.android.sdk.internal.IFragmentAPI
    public void resumeIgnoredAutoTrackFragment(Class<?> cls) {
        if (cls != null) {
            try {
                if (this.mAutoTrackIgnoredFragments == null) {
                    return;
                }
                String canonicalName = cls.getCanonicalName();
                if (TextUtils.isEmpty(canonicalName)) {
                    return;
                }
                this.mAutoTrackIgnoredFragments.remove(Integer.valueOf(canonicalName.hashCode()));
            } catch (Exception e) {
                SALog.printStackTrace(e);
            }
        }
    }
}
