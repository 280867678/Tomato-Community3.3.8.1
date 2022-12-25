package com.tomatolive.library.p136ui.view.widget.matisse.internal.p137ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import com.tomatolive.library.p136ui.view.widget.matisse.internal.entity.Item;
import com.tomatolive.library.p136ui.view.widget.matisse.internal.entity.SelectionSpec;
import java.util.ArrayList;

/* renamed from: com.tomatolive.library.ui.view.widget.matisse.internal.ui.SelectedPreviewActivity */
/* loaded from: classes4.dex */
public class SelectedPreviewActivity extends BasePreviewActivity {
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tomatolive.library.p136ui.view.widget.matisse.internal.p137ui.BasePreviewActivity, android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.support.p002v4.app.ComponentActivity, android.app.Activity
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
