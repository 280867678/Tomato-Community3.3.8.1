package com.gen.p059mh.webapp_extensions.matisse.p061ui;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.p002v4.app.Fragment;
import android.support.p005v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.gen.p059mh.webapp_extensions.R$id;
import com.gen.p059mh.webapp_extensions.R$layout;
import com.gen.p059mh.webapp_extensions.R$string;
import com.gen.p059mh.webapp_extensions.matisse.internal.entity.Album;
import com.gen.p059mh.webapp_extensions.matisse.internal.entity.Item;
import com.gen.p059mh.webapp_extensions.matisse.internal.entity.SelectionSpec;
import com.gen.p059mh.webapp_extensions.matisse.internal.model.AlbumCollection;
import com.gen.p059mh.webapp_extensions.matisse.internal.model.SelectedItemCollection;
import com.gen.p059mh.webapp_extensions.matisse.internal.p060ui.AlbumPreviewActivity;
import com.gen.p059mh.webapp_extensions.matisse.internal.p060ui.MediaSelectionFragment;
import com.gen.p059mh.webapp_extensions.matisse.internal.p060ui.SelectedPreviewActivity;
import com.gen.p059mh.webapp_extensions.matisse.internal.p060ui.adapter.AlbumMediaAdapter;
import com.gen.p059mh.webapp_extensions.matisse.internal.p060ui.adapter.AlbumsAdapter;
import com.gen.p059mh.webapp_extensions.matisse.internal.p060ui.widget.AlbumsSpinner;
import com.gen.p059mh.webapp_extensions.matisse.internal.p060ui.widget.CheckRadioView;
import com.gen.p059mh.webapp_extensions.matisse.internal.p060ui.widget.IncapableDialog;
import com.gen.p059mh.webapp_extensions.matisse.internal.utils.MediaStoreCompat;
import com.gen.p059mh.webapp_extensions.matisse.internal.utils.PathUtils;
import com.gen.p059mh.webapp_extensions.matisse.internal.utils.PhotoMetadataUtils;
import com.gen.p059mh.webapp_extensions.matisse.listener.OnCheckedListener;
import com.gen.p059mh.webapp_extensions.matisse.listener.OnSelectedListener;
import java.util.ArrayList;
import java.util.Iterator;

/* renamed from: com.gen.mh.webapp_extensions.matisse.ui.WebSdkMatisseActivity */
/* loaded from: classes2.dex */
public class WebSdkMatisseActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener, AlbumCollection.AlbumCallbacks, MediaSelectionFragment.SelectionProvider, AlbumMediaAdapter.CheckStateListener, AlbumMediaAdapter.OnMediaClickListener, AlbumMediaAdapter.OnPhotoCapture {
    public static final String CHECK_STATE = "checkState";
    public static final String EXTRA_RESULT_ORIGINAL_ENABLE = "extra_result_original_enable";
    public static final String EXTRA_RESULT_SELECTION = "extra_result_selection";
    public static final String EXTRA_RESULT_SELECTION_PATH = "extra_result_selection_path";
    public static final String PHOTO_PATH = "photo_path";
    private static final int REQUEST_CODE_CAPTURE = 24;
    private static final int REQUEST_CODE_PREVIEW = 23;
    private AlbumsAdapter mAlbumsAdapter;
    private AlbumsSpinner mAlbumsSpinner;
    private TextView mButtonApply;
    private TextView mButtonBack;
    private TextView mButtonPreview;
    private View mContainer;
    private View mEmptyView;
    private MediaStoreCompat mMediaStoreCompat;
    private CheckRadioView mOriginal;
    private boolean mOriginalEnable;
    private LinearLayout mOriginalLayout;
    private SelectionSpec mSpec;
    private final AlbumCollection mAlbumCollection = new AlbumCollection();
    private SelectedItemCollection mSelectedCollection = new SelectedItemCollection(this);

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.support.p002v4.app.ComponentActivity, android.app.Activity
    public void onCreate(@Nullable Bundle bundle) {
        this.mSpec = SelectionSpec.getInstance();
        setTheme(this.mSpec.themeId);
        requestWindowFeature(1);
        super.onCreate(bundle);
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= 19) {
            window.addFlags(67108864);
        }
        if (!this.mSpec.hasInited) {
            setResult(0);
            finish();
            return;
        }
        setContentView(R$layout.activity_web_sdk_matisse);
        if (this.mSpec.needOrientationRestriction()) {
            setRequestedOrientation(this.mSpec.orientation);
        }
        if (this.mSpec.capture) {
            this.mMediaStoreCompat = new MediaStoreCompat(this, getIntent().getStringExtra(PHOTO_PATH));
            this.mMediaStoreCompat.setCaptureStrategy(this.mSpec.captureStrategy);
        }
        this.mButtonPreview = (TextView) findViewById(R$id.button_preview);
        this.mButtonApply = (TextView) findViewById(R$id.button_apply);
        this.mButtonBack = (TextView) findViewById(R$id.button_back);
        this.mButtonPreview.setOnClickListener(this);
        this.mButtonApply.setOnClickListener(this);
        this.mContainer = findViewById(R$id.container);
        this.mEmptyView = findViewById(R$id.empty_view);
        this.mOriginalLayout = (LinearLayout) findViewById(R$id.originalLayout);
        this.mOriginal = (CheckRadioView) findViewById(R$id.original);
        this.mOriginalLayout.setOnClickListener(this);
        this.mButtonBack.setOnClickListener(this);
        this.mSelectedCollection.onCreate(bundle);
        if (bundle != null) {
            this.mOriginalEnable = bundle.getBoolean("checkState");
        }
        updateBottomToolbar();
        this.mAlbumsAdapter = new AlbumsAdapter((Context) this, (Cursor) null, false);
        this.mAlbumsSpinner = new AlbumsSpinner(this);
        this.mAlbumsSpinner.setOnItemSelectedListener(this);
        this.mAlbumsSpinner.setSelectedTextView((TextView) findViewById(R$id.selected_album));
        this.mAlbumsSpinner.setPopupAnchorView(findViewById(R$id.bottom_toolbar));
        this.mAlbumsSpinner.setAdapter(this.mAlbumsAdapter);
        this.mAlbumCollection.onCreate(this, this);
        this.mAlbumCollection.onRestoreInstanceState(bundle);
        this.mAlbumCollection.loadAlbums();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.support.p002v4.app.ComponentActivity, android.app.Activity
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        this.mSelectedCollection.onSaveInstanceState(bundle);
        this.mAlbumCollection.onSaveInstanceState(bundle);
        bundle.putBoolean("checkState", this.mOriginalEnable);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        this.mAlbumCollection.onDestroy();
        SelectionSpec selectionSpec = this.mSpec;
        selectionSpec.onCheckedListener = null;
        selectionSpec.onSelectedListener = null;
    }

    @Override // android.app.Activity
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override // android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onBackPressed() {
        setResult(0);
        super.onBackPressed();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        Log.e("result", "requestCode" + i + " resultCode" + i2);
        if (i2 != -1) {
            return;
        }
        if (i != 23) {
            if (i != 24) {
                return;
            }
            Uri currentPhotoUri = this.mMediaStoreCompat.getCurrentPhotoUri();
            String currentPhotoPath = this.mMediaStoreCompat.getCurrentPhotoPath();
            ArrayList<? extends Parcelable> arrayList = new ArrayList<>();
            arrayList.add(currentPhotoUri);
            ArrayList<String> arrayList2 = new ArrayList<>();
            arrayList2.add(currentPhotoPath);
            Intent intent2 = new Intent();
            intent2.putParcelableArrayListExtra("extra_result_selection", arrayList);
            intent2.putStringArrayListExtra("extra_result_selection_path", arrayList2);
            setResult(-1, intent2);
            if (Build.VERSION.SDK_INT < 21) {
                revokeUriPermission(currentPhotoUri, 3);
            }
            finish();
            return;
        }
        Bundle bundleExtra = intent.getBundleExtra("extra_result_bundle");
        ArrayList<Item> parcelableArrayList = bundleExtra.getParcelableArrayList("state_selection");
        this.mOriginalEnable = intent.getBooleanExtra("extra_result_original_enable", false);
        int i3 = bundleExtra.getInt("state_collection_type", 0);
        if (intent.getBooleanExtra("extra_result_apply", false)) {
            Intent intent3 = new Intent();
            ArrayList<? extends Parcelable> arrayList3 = new ArrayList<>();
            ArrayList<String> arrayList4 = new ArrayList<>();
            if (parcelableArrayList != null) {
                Iterator<Item> it2 = parcelableArrayList.iterator();
                while (it2.hasNext()) {
                    Item next = it2.next();
                    arrayList3.add(next.getContentUri());
                    arrayList4.add(PathUtils.getPath(this, next.getContentUri()));
                }
            }
            intent3.putParcelableArrayListExtra("extra_result_selection", arrayList3);
            intent3.putStringArrayListExtra("extra_result_selection_path", arrayList4);
            intent3.putExtra("extra_result_original_enable", this.mOriginalEnable);
            setResult(-1, intent3);
            finish();
            return;
        }
        this.mSelectedCollection.overwrite(parcelableArrayList, i3);
        Fragment findFragmentByTag = getSupportFragmentManager().findFragmentByTag(MediaSelectionFragment.class.getSimpleName());
        if (findFragmentByTag instanceof MediaSelectionFragment) {
            ((MediaSelectionFragment) findFragmentByTag).refreshMediaGrid();
        }
        updateBottomToolbar();
    }

    private void updateBottomToolbar() {
        int count = this.mSelectedCollection.count();
        if (count == 0) {
            this.mButtonPreview.setEnabled(false);
            this.mButtonApply.setEnabled(false);
            this.mButtonApply.setText(getString(R$string.button_sure_default));
        } else if (count == 1 && this.mSpec.singleSelectionModeEnabled()) {
            this.mButtonPreview.setEnabled(true);
            this.mButtonApply.setText(R$string.button_sure_default);
            this.mButtonApply.setEnabled(true);
        } else {
            this.mButtonPreview.setEnabled(true);
            this.mButtonApply.setEnabled(true);
            this.mButtonApply.setText(getString(R$string.button_sure, new Object[]{Integer.valueOf(count)}));
        }
        if (this.mSpec.originalable) {
            this.mOriginalLayout.setVisibility(0);
            updateOriginalState();
            return;
        }
        this.mOriginalLayout.setVisibility(4);
    }

    private void updateOriginalState() {
        this.mOriginal.setChecked(this.mOriginalEnable);
        if (countOverMaxSize() <= 0 || !this.mOriginalEnable) {
            return;
        }
        IncapableDialog.newInstance("", getString(R$string.error_over_original_size, new Object[]{Integer.valueOf(this.mSpec.originalMaxSize)})).show(getSupportFragmentManager(), IncapableDialog.class.getName());
        this.mOriginal.setChecked(false);
        this.mOriginalEnable = false;
    }

    private int countOverMaxSize() {
        int count = this.mSelectedCollection.count();
        int i = 0;
        for (int i2 = 0; i2 < count; i2++) {
            Item item = this.mSelectedCollection.asList().get(i2);
            if (item.isImage() && PhotoMetadataUtils.getSizeInMB(item.size) > this.mSpec.originalMaxSize) {
                i++;
            }
        }
        return i;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (view.getId() == R$id.button_preview) {
            Intent intent = new Intent(this, SelectedPreviewActivity.class);
            intent.putExtra("extra_default_bundle", this.mSelectedCollection.getDataWithBundle());
            intent.putExtra("extra_result_original_enable", this.mOriginalEnable);
            startActivityForResult(intent, 23);
        } else if (view.getId() == R$id.button_apply) {
            Intent intent2 = new Intent();
            intent2.putParcelableArrayListExtra("extra_result_selection", (ArrayList) this.mSelectedCollection.asListOfUri());
            intent2.putStringArrayListExtra("extra_result_selection_path", (ArrayList) this.mSelectedCollection.asListOfString());
            intent2.putExtra("extra_result_original_enable", this.mOriginalEnable);
            setResult(-1, intent2);
            finish();
        } else if (view.getId() == R$id.originalLayout) {
            int countOverMaxSize = countOverMaxSize();
            if (countOverMaxSize > 0) {
                IncapableDialog.newInstance("", getString(R$string.error_over_original_count, new Object[]{Integer.valueOf(countOverMaxSize), Integer.valueOf(this.mSpec.originalMaxSize)})).show(getSupportFragmentManager(), IncapableDialog.class.getName());
                return;
            }
            this.mOriginalEnable = !this.mOriginalEnable;
            this.mOriginal.setChecked(this.mOriginalEnable);
            OnCheckedListener onCheckedListener = this.mSpec.onCheckedListener;
            if (onCheckedListener == null) {
                return;
            }
            onCheckedListener.onCheck(this.mOriginalEnable);
        } else if (view.getId() != R$id.button_back) {
        } else {
            onBackPressed();
        }
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
        this.mAlbumCollection.setStateCurrentSelection(i);
        this.mAlbumsAdapter.getCursor().moveToPosition(i);
        Album valueOf = Album.valueOf(this.mAlbumsAdapter.getCursor());
        if (valueOf.isAll() && SelectionSpec.getInstance().capture) {
            valueOf.addCaptureCount();
        }
        onAlbumSelected(valueOf);
    }

    @Override // com.gen.p059mh.webapp_extensions.matisse.internal.model.AlbumCollection.AlbumCallbacks
    public void onAlbumLoad(final Cursor cursor) {
        this.mAlbumsAdapter.swapCursor(cursor);
        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.gen.mh.webapp_extensions.matisse.ui.WebSdkMatisseActivity.1
            @Override // java.lang.Runnable
            public void run() {
                cursor.moveToPosition(WebSdkMatisseActivity.this.mAlbumCollection.getCurrentSelection());
                AlbumsSpinner albumsSpinner = WebSdkMatisseActivity.this.mAlbumsSpinner;
                WebSdkMatisseActivity webSdkMatisseActivity = WebSdkMatisseActivity.this;
                albumsSpinner.setSelection(webSdkMatisseActivity, webSdkMatisseActivity.mAlbumCollection.getCurrentSelection());
                Album valueOf = Album.valueOf(cursor);
                if (valueOf.isAll() && SelectionSpec.getInstance().capture) {
                    valueOf.addCaptureCount();
                }
                WebSdkMatisseActivity.this.onAlbumSelected(valueOf);
            }
        });
    }

    @Override // com.gen.p059mh.webapp_extensions.matisse.internal.model.AlbumCollection.AlbumCallbacks
    public void onAlbumReset() {
        this.mAlbumsAdapter.swapCursor(null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onAlbumSelected(Album album) {
        if (album.isAll() && album.isEmpty()) {
            this.mContainer.setVisibility(8);
            this.mEmptyView.setVisibility(0);
            return;
        }
        this.mContainer.setVisibility(0);
        this.mEmptyView.setVisibility(8);
        getSupportFragmentManager().beginTransaction().replace(R$id.container, MediaSelectionFragment.newInstance(album), MediaSelectionFragment.class.getSimpleName()).commitAllowingStateLoss();
    }

    @Override // com.gen.p059mh.webapp_extensions.matisse.internal.p060ui.adapter.AlbumMediaAdapter.CheckStateListener
    public void onUpdate() {
        updateBottomToolbar();
        OnSelectedListener onSelectedListener = this.mSpec.onSelectedListener;
        if (onSelectedListener != null) {
            onSelectedListener.onSelected(this.mSelectedCollection.asListOfUri(), this.mSelectedCollection.asListOfString());
        }
    }

    @Override // com.gen.p059mh.webapp_extensions.matisse.internal.p060ui.adapter.AlbumMediaAdapter.OnMediaClickListener
    public void onMediaClick(Album album, Item item, int i) {
        Intent intent = new Intent(this, AlbumPreviewActivity.class);
        intent.putExtra("extra_album", album);
        intent.putExtra("extra_item", item);
        intent.putExtra("extra_default_bundle", this.mSelectedCollection.getDataWithBundle());
        intent.putExtra("extra_result_original_enable", this.mOriginalEnable);
        startActivityForResult(intent, 23);
    }

    @Override // com.gen.p059mh.webapp_extensions.matisse.internal.p060ui.MediaSelectionFragment.SelectionProvider
    public SelectedItemCollection provideSelectedItemCollection() {
        return this.mSelectedCollection;
    }

    @Override // com.gen.p059mh.webapp_extensions.matisse.internal.p060ui.adapter.AlbumMediaAdapter.OnPhotoCapture
    public void capture() {
        MediaStoreCompat mediaStoreCompat = this.mMediaStoreCompat;
        if (mediaStoreCompat != null) {
            mediaStoreCompat.dispatchCaptureIntent(this, 24);
        }
    }
}
