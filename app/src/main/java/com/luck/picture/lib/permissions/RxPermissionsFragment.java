package com.luck.picture.lib.permissions;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import io.reactivex.subjects.PublishSubject;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes3.dex */
public class RxPermissionsFragment extends Fragment {
    private boolean mLogging;
    private Map<String, PublishSubject<Permission>> mSubjects = new HashMap();

    @Override // android.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setRetainInstance(true);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @TargetApi(23)
    public void requestPermissions(@NonNull String[] strArr) {
        requestPermissions(strArr, 42);
    }

    @Override // android.app.Fragment
    @TargetApi(23)
    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (i != 42) {
            return;
        }
        boolean[] zArr = new boolean[strArr.length];
        for (int i2 = 0; i2 < strArr.length; i2++) {
            zArr[i2] = shouldShowRequestPermissionRationale(strArr[i2]);
        }
        onRequestPermissionsResult(strArr, iArr, zArr);
    }

    void onRequestPermissionsResult(String[] strArr, int[] iArr, boolean[] zArr) {
        int length = strArr.length;
        for (int i = 0; i < length; i++) {
            log("onRequestPermissionsResult  " + strArr[i]);
            PublishSubject<Permission> publishSubject = this.mSubjects.get(strArr[i]);
            if (publishSubject == null) {
                Log.e("RxPermissionsLucky", "RxPermissions.onRequestPermissionsResult invoked but didn't find the corresponding permission request.");
                return;
            }
            this.mSubjects.remove(strArr[i]);
            publishSubject.onNext(new Permission(strArr[i], iArr[i] == 0, zArr[i]));
            publishSubject.onComplete();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @TargetApi(23)
    public boolean isGranted(String str) {
        return getActivity().checkSelfPermission(str) == 0;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @TargetApi(23)
    public boolean isRevoked(String str) {
        return getActivity().getPackageManager().isPermissionRevokedByPolicy(str, getActivity().getPackageName());
    }

    public PublishSubject<Permission> getSubjectByPermission(@NonNull String str) {
        return this.mSubjects.get(str);
    }

    public boolean containsByPermission(@NonNull String str) {
        return this.mSubjects.containsKey(str);
    }

    public PublishSubject<Permission> setSubjectForPermission(@NonNull String str, @NonNull PublishSubject<Permission> publishSubject) {
        return this.mSubjects.put(str, publishSubject);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void log(String str) {
        if (this.mLogging) {
            Log.d("RxPermissionsLucky", str);
        }
    }
}