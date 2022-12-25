package com.gen.p059mh.webapp_extensions.matisse.internal.p060ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import com.gen.p059mh.webapp_extensions.matisse.internal.entity.Item;
import com.gen.p059mh.webapp_extensions.matisse.internal.entity.SelectionSpec;
import java.util.ArrayList;

/* renamed from: com.gen.mh.webapp_extensions.matisse.internal.ui.SelectedPreviewActivity */
/* loaded from: classes2.dex */
public class SelectedPreviewActivity extends BasePreviewActivity {
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.gen.p059mh.webapp_extensions.matisse.internal.p060ui.BasePreviewActivity, android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.support.p002v4.app.ComponentActivity, android.app.Activity
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        if (!SelectionSpec.getInstance().hasInited) {
            setResult(0);
            finish();
            return;
        }
        ArrayList parcelableArrayList = getIntent().getBundleExtra("extra_default_bundle").getParcelableArrayList("state_selection");
        this.mAdapter.addAll(parcelableArrayList);
        this.mAdapter.notifyDataSetChanged();
        if (this.mSpec.countable) {
            this.mCheckView.setCheckedNum(1);
        } else {
            this.mCheckView.setChecked(true);
        }
        this.mPreviousPos = 0;
        updateSize((Item) parcelableArrayList.get(0));
    }
}
