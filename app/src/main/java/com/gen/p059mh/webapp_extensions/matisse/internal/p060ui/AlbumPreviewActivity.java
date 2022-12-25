package com.gen.p059mh.webapp_extensions.matisse.internal.p060ui;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.gen.p059mh.webapp_extensions.matisse.internal.entity.Album;
import com.gen.p059mh.webapp_extensions.matisse.internal.entity.Item;
import com.gen.p059mh.webapp_extensions.matisse.internal.entity.SelectionSpec;
import com.gen.p059mh.webapp_extensions.matisse.internal.model.AlbumMediaCollection;
import com.gen.p059mh.webapp_extensions.matisse.internal.p060ui.adapter.PreviewPagerAdapter;
import java.util.ArrayList;

/* renamed from: com.gen.mh.webapp_extensions.matisse.internal.ui.AlbumPreviewActivity */
/* loaded from: classes2.dex */
public class AlbumPreviewActivity extends BasePreviewActivity implements AlbumMediaCollection.AlbumMediaCallbacks {
    public static final String EXTRA_ALBUM = "extra_album";
    public static final String EXTRA_ITEM = "extra_item";
    private AlbumMediaCollection mCollection = new AlbumMediaCollection();
    private boolean mIsAlreadySetPosition;

    @Override // com.gen.p059mh.webapp_extensions.matisse.internal.model.AlbumMediaCollection.AlbumMediaCallbacks
    public void onAlbumMediaReset() {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.gen.p059mh.webapp_extensions.matisse.internal.p060ui.BasePreviewActivity, android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.support.p002v4.app.ComponentActivity, android.app.Activity
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        if (!SelectionSpec.getInstance().hasInited) {
            setResult(0);
            finish();
            return;
        }
        this.mCollection.onCreate(this, this);
        this.mCollection.load((Album) getIntent().getParcelableExtra("extra_album"));
        Item item = (Item) getIntent().getParcelableExtra("extra_item");
        if (this.mSpec.countable) {
            this.mCheckView.setCheckedNum(this.mSelectedCollection.checkedNumOf(item));
        } else {
            this.mCheckView.setChecked(this.mSelectedCollection.isSelected(item));
        }
        updateSize(item);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        this.mCollection.onDestroy();
    }

    @Override // com.gen.p059mh.webapp_extensions.matisse.internal.model.AlbumMediaCollection.AlbumMediaCallbacks
    public void onAlbumMediaLoad(Cursor cursor) {
        ArrayList arrayList = new ArrayList();
        while (cursor.moveToNext()) {
            arrayList.add(Item.valueOf(cursor));
        }
        if (arrayList.isEmpty()) {
            return;
        }
        PreviewPagerAdapter previewPagerAdapter = (PreviewPagerAdapter) this.mPager.getAdapter();
        previewPagerAdapter.addAll(arrayList);
        previewPagerAdapter.notifyDataSetChanged();
        if (this.mIsAlreadySetPosition) {
            return;
        }
        this.mIsAlreadySetPosition = true;
        int indexOf = arrayList.indexOf((Item) getIntent().getParcelableExtra("extra_item"));
        this.mPager.setCurrentItem(indexOf, false);
        this.mPreviousPos = indexOf;
    }
}
