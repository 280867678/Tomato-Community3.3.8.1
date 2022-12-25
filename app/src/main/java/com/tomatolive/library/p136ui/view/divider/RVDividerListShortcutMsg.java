package com.tomatolive.library.p136ui.view.divider;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.p002v4.content.ContextCompat;
import com.tomatolive.library.p136ui.view.divider.decoration.Y_Divider;
import com.tomatolive.library.p136ui.view.divider.decoration.Y_DividerBuilder;
import com.tomatolive.library.p136ui.view.divider.decoration.Y_DividerItemDecoration;

/* renamed from: com.tomatolive.library.ui.view.divider.RVDividerListShortcutMsg */
/* loaded from: classes3.dex */
public class RVDividerListShortcutMsg extends Y_DividerItemDecoration {
    private int colorRes;
    private Context context;
    private float heightDp;

    public RVDividerListShortcutMsg(Context context, @ColorRes int i) {
        super(context);
        this.heightDp = 1.0f;
        this.context = context;
        this.colorRes = i;
    }

    public RVDividerListShortcutMsg(Context context, @ColorRes int i, float f) {
        super(context);
        this.heightDp = 1.0f;
        this.context = context;
        this.colorRes = i;
        this.heightDp = f;
    }

    @Override // com.tomatolive.library.p136ui.view.divider.decoration.Y_DividerItemDecoration
    public Y_Divider getDivider(int i) {
        if (i == 0) {
            return new Y_DividerBuilder().setBottomSideLine(false, ContextCompat.getColor(this.context, this.colorRes), 0.0f, 0.0f, 0.0f).create();
        }
        return new Y_DividerBuilder().setTopSideLine(true, ContextCompat.getColor(this.context, this.colorRes), this.heightDp, 0.0f, 0.0f).create();
    }
}
