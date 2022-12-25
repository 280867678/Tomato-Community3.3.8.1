package com.gen.p059mh.webapp_extensions.matisse;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.p002v4.app.Fragment;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Set;

/* renamed from: com.gen.mh.webapp_extensions.matisse.Matisse */
/* loaded from: classes2.dex */
public final class Matisse {
    private final WeakReference<Activity> mContext;
    private final WeakReference<Fragment> mFragment;

    private Matisse(Activity activity) {
        this(activity, null);
    }

    private Matisse(Fragment fragment) {
        this(fragment.getActivity(), fragment);
    }

    private Matisse(Activity activity, Fragment fragment) {
        this.mContext = new WeakReference<>(activity);
        this.mFragment = new WeakReference<>(fragment);
    }

    public static Matisse from(Activity activity) {
        return new Matisse(activity);
    }

    public static Matisse from(Fragment fragment) {
        return new Matisse(fragment);
    }

    public static List<Uri> obtainResult(Intent intent) {
        return intent.getParcelableArrayListExtra("extra_result_selection");
    }

    public static List<String> obtainPathResult(Intent intent) {
        return intent.getStringArrayListExtra("extra_result_selection_path");
    }

    public static boolean obtainOriginalState(Intent intent) {
        return intent.getBooleanExtra("extra_result_original_enable", false);
    }

    public SelectionCreator choose(Set<MimeType> set) {
        return choose(set, true);
    }

    public SelectionCreator choose(Set<MimeType> set, boolean z) {
        return new SelectionCreator(this, set, z);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Nullable
    public Activity getActivity() {
        return this.mContext.get();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Nullable
    public Fragment getFragment() {
        WeakReference<Fragment> weakReference = this.mFragment;
        if (weakReference != null) {
            return weakReference.get();
        }
        return null;
    }
}