package com.zzz.ipfssdk.crypt;

import android.util.Pair;
import com.zzz.ipfssdk.callback.exception.CodeState;

/* loaded from: classes4.dex */
public interface ResultCallBack {
    void onResult(Pair<String, CodeState> pair);
}
