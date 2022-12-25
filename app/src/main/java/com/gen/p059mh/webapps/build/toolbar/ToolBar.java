package com.gen.p059mh.webapps.build.toolbar;

import android.view.View;

/* renamed from: com.gen.mh.webapps.build.toolbar.ToolBar */
/* loaded from: classes2.dex */
public interface ToolBar {
    View getView();

    void hideHomeButton(boolean z);

    void release();

    void setBackClickListener(View.OnClickListener onClickListener);

    void setNavigationBarColor(int i);

    void setNavigationBarTitle(String str);

    void setTextColor(int i);
}
