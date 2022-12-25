package com.tomatolive.library.p136ui.view.widget.matisse.p138ui;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.p002v4.app.Fragment;
import android.support.p005v7.app.ActionBar;
import android.support.p005v7.app.AppCompatActivity;
import android.support.p005v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.tomatolive.library.R$attr;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.p136ui.view.widget.matisse.internal.entity.Album;
import com.tomatolive.library.p136ui.view.widget.matisse.internal.entity.CaptureStrategy;
import com.tomatolive.library.p136ui.view.widget.matisse.internal.entity.Item;
import com.tomatolive.library.p136ui.view.widget.matisse.internal.entity.SelectionSpec;
import com.tomatolive.library.p136ui.view.widget.matisse.internal.model.AlbumCollection;
import com.tomatolive.library.p136ui.view.widget.matisse.internal.model.SelectedItemCollection;
import com.tomatolive.library.p136ui.view.widget.matisse.internal.p137ui.MediaSelectionFragment;
import com.tomatolive.library.p136ui.view.widget.matisse.internal.p137ui.SelectedPreviewActivity;
import com.tomatolive.library.p136ui.view.widget.matisse.internal.p137ui.adapter.AlbumMediaAdapter;
import com.tomatolive.library.p136ui.view.widget.matisse.internal.p137ui.adapter.AlbumsAdapter;
import com.tomatolive.library.p136ui.view.widget.matisse.internal.p137ui.widget.AlbumsSpinner;
import com.tomatolive.library.p136ui.view.widget.matisse.internal.p137ui.widget.CheckRadioView;
import com.tomatolive.library.p136ui.view.widget.matisse.internal.p137ui.widget.IncapableDialog;
import com.tomatolive.library.p136ui.view.widget.matisse.internal.utils.MediaStoreCompat;
import com.tomatolive.library.p136ui.view.widget.matisse.internal.utils.PathUtils;
import com.tomatolive.library.p136ui.view.widget.matisse.internal.utils.PhotoMetadataUtils;
import com.tomatolive.library.p136ui.view.widget.matisse.listener.OnCheckedListener;
import com.tomatolive.library.p136ui.view.widget.matisse.listener.OnSelectedListener;
import java.util.ArrayList;
import java.util.Iterator;

/* renamed from: com.tomatolive.library.ui.view.widget.matisse.ui.MatisseActivity */
/* loaded from: classes4.dex */
public class MatisseActivity extends AppCompatActivity implements AlbumCollection.AlbumCallbacks, AdapterView.OnItemSelectedListener, MediaSelectionFragment.SelectionProvider, View.OnClickListener, AlbumMediaAdapter.CheckStateListener, AlbumMediaAdapter.OnMediaClickListener, AlbumMediaAdapter.OnPhotoCapture {
    public static final String CHECK_STATE = "checkState";
    public static final String EXTRA_RESULT_ORIGINAL_ENABLE = "extra_result_original_enable";
    public static final String EXTRA_RESULT_SELECTION = "extra_result_selection";
    public static final String EXTRA_RESULT_SELECTION_PATH = "extra_result_selection_path";
    private static final int REQUEST_CODE_CAPTURE = 24;
    private static final int REQUEST_CODE_PREVIEW = 23;
    private AlbumsAdapter mAlbumsAdapter;
    private AlbumsSpinner mAlbumsSpinner;
    private TextView mButtonApply;
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
        super.onCreate(bundle);
        if (!this.mSpec.hasInited) {
            setResult(0);
            finish();
            return;
        }
        setContentView(R$layout.fq_matisse_activity_matisse);
        if (this.mSpec.needOrientationRestriction()) {
            setRequestedOrientation(this.mSpec.orientation);
        }
        if (this.mSpec.capture) {
            this.mMediaStoreCompat = new MediaStoreCompat(this);
            CaptureStrategy captureStrategy = this.mSpec.captureStrategy;
            if (captureStrategy == null) {
                throw new RuntimeException("Don't forget to set CaptureStrategy.");
            }
            this.mMediaStoreCompat.setCaptureStrategy(captureStrategy);
        }
        Toolbar toolbar = (Toolbar) findViewById(R$id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setDisplayShowTitleEnabled(false);
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        Drawable navigationIcon = toolbar.getNavigationIcon();
        TypedArray obtainStyledAttributes = getTheme().obtainStyledAttributes(new int[]{R$attr.album_element_color});
        int color = obtainStyledAttributes.getColor(0, 0);
        obtainStyledAttributes.recycle();
        navigationIcon.setColorFilter(color, PorterDuff.Mode.SRC_IN);
        this.mButtonPreview = (TextView) findViewById(R$id.button_preview);
        this.mButtonApply = (TextView) findViewById(R$id.button_apply);
        this.mButtonPreview.setOnClickListener(this);
        this.mButtonApply.setOnClickListener(this);
        this.mContainer = findViewById(R$id.container);
        this.mEmptyView = findViewById(R$id.empty_view);
        this.mOriginalLayout = (LinearLayout) findViewById(R$id.originalLayout);
        this.mOriginal = (CheckRadioView) findViewById(R$id.original);
        this.mOriginalLayout.setOnClickListener(this);
        this.mSelectedCollection.onCreate(bundle);
        if (bundle != null) {
            this.mOriginalEnable = bundle.getBoolean("checkState");
        }
        updateBottomToolbar();
        this.mAlbumsAdapter = new AlbumsAdapter((Context) this, (Cursor) null, false);
        this.mAlbumsSpinner = new AlbumsSpinner(this);
        this.mAlbumsSpinner.setOnItemSelectedListener(this);
        this.mAlbumsSpinner.setSelectedTextView((TextView) findViewById(R$id.selected_album));
        this.mAlbumsSpinner.setPopupAnchorView(findViewById(R$id.toolbar));
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
            this.mButtonApply.setText(getString(R$string.fq_matisse_button_sure_default));
        } else if (count == 1 && this.mSpec.singleSelectionModeEnabled()) {
            this.mButtonPreview.setEnabled(true);
            this.mButtonApply.setText(R$string.fq_matisse_button_sure_default);
            this.mButtonApply.setEnabled(true);
        } else {
            this.mButtonPreview.setEnabled(true);
            this.mButtonApply.setEnabled(true);
            this.mButtonApply.setText(getString(R$string.fq_matisse_button_sure, new Object[]{Integer.valueOf(count)}));
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
        IncapableDialog.newInstance("", getString(R$string.fq_matisse_error_over_original_size, new Object[]{Integer.valueOf(this.mSpec.originalMaxSize)})).show(getSupportFragmentManager(), IncapableDialog.class.getName());
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
        } else if (view.getId() != R$id.originalLayout) {
        } else {
            int countOverMaxSize = countOverMaxSize();
            if (countOverMaxSize > 0) {
                IncapableDialog.newInstance("", getString(R$string.fq_matisse_error_over_original_count, new Object[]{Integer.valueOf(countOverMaxSize), Integer.valueOf(this.mSpec.originalMaxSize)})).show(getSupportFragmentManager(), IncapableDialog.class.getName());
                return;
            }
            this.mOriginalEnable = !this.mOriginalEnable;
            this.mOriginal.setChecked(this.mOriginalEnable);
            OnCheckedListener onCheckedListener = this.mSpec.onCheckedListener;
            if (onCheckedListener == null) {
                return;
            }
            onCheckedListener.onCheck(this.mOriginalEnable);
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

    @Override // com.tomatolive.library.p136ui.view.widget.matisse.internal.model.AlbumCollection.AlbumCallbacks
    public void onAlbumLoad(final Cursor cursor) {
        this.mAlbumsAdapter.swapCursor(cursor);
        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.tomatolive.library.ui.view.widget.matisse.ui.MatisseActivity.1
            @Override // java.lang.Runnable
            public void run() {
                cursor.moveToPosition(MatisseActivity.this.mAlbumCollection.getCurrentSelection());
                AlbumsSpinner albumsSpinner = MatisseActivity.this.mAlbumsSpinner;
                MatisseActivity matisseActivity = MatisseActivity.this;
                albumsSpinner.setSelection(matisseActivity, matisseActivity.mAlbumCollection.getCurrentSelection());
                Album valueOf = Album.valueOf(cursor);
                if (valueOf.isAll() && SelectionSpec.getInstance().capture) {
                    valueOf.addCaptureCount();
                }
                MatisseActivity.this.onAlbumSelected(valueOf);
            }
        });
    }

    @Override // com.tomatolive.library.p136ui.view.widget.matisse.internal.model.AlbumCollection.AlbumCallbacks
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

    @Override // com.tomatolive.library.p136ui.view.widget.matisse.internal.p137ui.adapter.AlbumMediaAdapter.CheckStateListener
    public void onUpdate() {
        updateBottomToolbar();
        OnSelectedListener onSelectedListener = this.mSpec.onSelectedListener;
        if (onSelectedListener != null) {
            onSelectedListener.onSelected(this.mSelectedCollection.asListOfUri(), this.mSelectedCollection.asListOfString());
        }
    }

    @Override // com.tomatolive.library.p136ui.view.widget.matisse.internal.p137ui.adapter.AlbumMediaAdapter.OnMediaClickListener
    public void onMediaClick(Album album, Item item, int i) {
        Uri contentUri = item.getContentUri();
        ArrayList<? extends Parcelable> arrayList = new ArrayList<>();
        arrayList.add(contentUri);
        ArrayList<String> arrayList2 = new ArrayList<>();
        arrayList2.add(PathUtils.getPath(this, contentUri));
        Intent intent = new Intent();
        intent.putParcelableArrayListExtra("extra_result_selection", arrayList);
        intent.putStringArrayListExtra("extra_result_selection_path", arrayList2);
        setResult(-1, intent);
        if (Build.VERSION.SDK_INT < 21) {
            revokeUriPermission(contentUri, 3);
        }
        finish();
    }

    @Override // com.tomatolive.library.p136ui.view.widget.matisse.internal.p137ui.MediaSelectionFragment.SelectionProvider
    public SelectedItemCollection provideSelectedItemCollection() {
        return this.mSelectedCollection;
    }

    @Override // com.tomatolive.library.p136ui.view.widget.matisse.internal.p137ui.adapter.AlbumMediaAdapter.OnPhotoCapture
    public void capture() {
        MediaStoreCompat mediaStoreCompat = this.mMediaStoreCompat;
        if (mediaStoreCompat != null) {
            mediaStoreCompat.dispatchCaptureIntent(this, 24);
        }
    }
}
