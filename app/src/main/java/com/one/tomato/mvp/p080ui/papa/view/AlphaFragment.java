package com.one.tomato.mvp.p080ui.papa.view;

import android.os.Bundle;
import android.support.p002v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.broccoli.p150bh.R;
import java.util.HashMap;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: AlphaFragment.kt */
/* renamed from: com.one.tomato.mvp.ui.papa.view.AlphaFragment */
/* loaded from: classes3.dex */
public final class AlphaFragment extends Fragment {
    private HashMap _$_findViewCache;

    public void _$_clearFindViewByIdCache() {
        HashMap hashMap = this._$_findViewCache;
        if (hashMap != null) {
            hashMap.clear();
        }
    }

    @Override // android.support.p002v4.app.Fragment
    public /* synthetic */ void onDestroyView() {
        super.onDestroyView();
        _$_clearFindViewByIdCache();
    }

    @Override // android.support.p002v4.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle bundle) {
        Intrinsics.checkParameterIsNotNull(inflater, "inflater");
        return inflater.inflate(R.layout.alph_fragment, viewGroup, false);
    }
}
