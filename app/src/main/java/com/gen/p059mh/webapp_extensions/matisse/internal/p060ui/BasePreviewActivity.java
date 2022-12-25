package com.gen.p059mh.webapp_extensions.matisse.internal.p060ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.p002v4.view.ViewPager;
import android.support.p002v4.view.animation.FastOutSlowInInterpolator;
import android.support.p005v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.gen.p059mh.webapp_extensions.R$id;
import com.gen.p059mh.webapp_extensions.R$layout;
import com.gen.p059mh.webapp_extensions.R$string;
import com.gen.p059mh.webapp_extensions.matisse.internal.entity.IncapableCause;
import com.gen.p059mh.webapp_extensions.matisse.internal.entity.Item;
import com.gen.p059mh.webapp_extensions.matisse.internal.entity.SelectionSpec;
import com.gen.p059mh.webapp_extensions.matisse.internal.model.SelectedItemCollection;
import com.gen.p059mh.webapp_extensions.matisse.internal.p060ui.adapter.PreviewPagerAdapter;
import com.gen.p059mh.webapp_extensions.matisse.internal.p060ui.widget.CheckRadioView;
import com.gen.p059mh.webapp_extensions.matisse.internal.p060ui.widget.CheckView;
import com.gen.p059mh.webapp_extensions.matisse.internal.p060ui.widget.IncapableDialog;
import com.gen.p059mh.webapp_extensions.matisse.internal.utils.PhotoMetadataUtils;
import com.gen.p059mh.webapp_extensions.matisse.internal.utils.Platform;
import com.gen.p059mh.webapp_extensions.matisse.listener.OnCheckedListener;
import com.gen.p059mh.webapp_extensions.matisse.listener.OnFragmentInteractionListener;
import com.gen.p059mh.webapp_extensions.matisse.listener.OnSelectedListener;

/* renamed from: com.gen.mh.webapp_extensions.matisse.internal.ui.BasePreviewActivity */
/* loaded from: classes2.dex */
public abstract class BasePreviewActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, View.OnClickListener, OnFragmentInteractionListener {
    public static final String CHECK_STATE = "checkState";
    public static final String EXTRA_DEFAULT_BUNDLE = "extra_default_bundle";
    public static final String EXTRA_RESULT_APPLY = "extra_result_apply";
    public static final String EXTRA_RESULT_BUNDLE = "extra_result_bundle";
    public static final String EXTRA_RESULT_ORIGINAL_ENABLE = "extra_result_original_enable";
    protected PreviewPagerAdapter mAdapter;
    private FrameLayout mBottomToolbar;
    protected TextView mButtonApply;
    protected TextView mButtonBack;
    protected CheckView mCheckView;
    private CheckRadioView mOriginal;
    protected boolean mOriginalEnable;
    private LinearLayout mOriginalLayout;
    protected ViewPager mPager;
    protected TextView mSize;
    protected SelectionSpec mSpec;
    private FrameLayout mTopToolbar;
    protected final SelectedItemCollection mSelectedCollection = new SelectedItemCollection(this);
    protected int mPreviousPos = -1;
    private boolean mIsToolbarHide = false;

    @Override // android.support.p002v4.view.ViewPager.OnPageChangeListener
    public void onPageScrollStateChanged(int i) {
    }

    @Override // android.support.p002v4.view.ViewPager.OnPageChangeListener
    public void onPageScrolled(int i, float f, int i2) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.support.p002v4.app.ComponentActivity, android.app.Activity
    public void onCreate(@Nullable Bundle bundle) {
        setTheme(SelectionSpec.getInstance().themeId);
        requestWindowFeature(1);
        super.onCreate(bundle);
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= 19) {
            window.addFlags(67108864);
            window.addFlags(1024);
        }
        if (!SelectionSpec.getInstance().hasInited) {
            setResult(0);
            finish();
            return;
        }
        setContentView(R$layout.activity_web_sdk_media_preview);
        if (Platform.hasKitKat()) {
            getWindow().addFlags(67108864);
        }
        this.mSpec = SelectionSpec.getInstance();
        if (this.mSpec.needOrientationRestriction()) {
            setRequestedOrientation(this.mSpec.orientation);
        }
        if (bundle == null) {
            this.mSelectedCollection.onCreate(getIntent().getBundleExtra("extra_default_bundle"));
            this.mOriginalEnable = getIntent().getBooleanExtra("extra_result_original_enable", false);
        } else {
            this.mSelectedCollection.onCreate(bundle);
            this.mOriginalEnable = bundle.getBoolean("checkState");
        }
        this.mButtonBack = (TextView) findViewById(R$id.button_back);
        this.mButtonApply = (TextView) findViewById(R$id.button_apply);
        this.mSize = (TextView) findViewById(R$id.size);
        this.mButtonBack.setOnClickListener(this);
        this.mButtonApply.setOnClickListener(this);
        this.mPager = (ViewPager) findViewById(R$id.pager);
        this.mPager.addOnPageChangeListener(this);
        this.mAdapter = new PreviewPagerAdapter(getSupportFragmentManager(), null);
        this.mPager.setAdapter(this.mAdapter);
        this.mCheckView = (CheckView) findViewById(R$id.check_view);
        this.mCheckView.setCountable(this.mSpec.countable);
        this.mBottomToolbar = (FrameLayout) findViewById(R$id.bottom_toolbar);
        this.mTopToolbar = (FrameLayout) findViewById(R$id.top_toolbar);
        this.mCheckView.setOnClickListener(new View.OnClickListener() { // from class: com.gen.mh.webapp_extensions.matisse.internal.ui.BasePreviewActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                BasePreviewActivity basePreviewActivity = BasePreviewActivity.this;
                Item mediaItem = basePreviewActivity.mAdapter.getMediaItem(basePreviewActivity.mPager.getCurrentItem());
                if (!BasePreviewActivity.this.mSelectedCollection.isSelected(mediaItem)) {
                    if (BasePreviewActivity.this.assertAddSelection(mediaItem)) {
                        BasePreviewActivity.this.mSelectedCollection.add(mediaItem);
                        BasePreviewActivity basePreviewActivity2 = BasePreviewActivity.this;
                        if (basePreviewActivity2.mSpec.countable) {
                            basePreviewActivity2.mCheckView.setCheckedNum(basePreviewActivity2.mSelectedCollection.checkedNumOf(mediaItem));
                        } else {
                            basePreviewActivity2.mCheckView.setChecked(true);
                        }
                    }
                } else {
                    BasePreviewActivity.this.mSelectedCollection.remove(mediaItem);
                    BasePreviewActivity basePreviewActivity3 = BasePreviewActivity.this;
                    if (basePreviewActivity3.mSpec.countable) {
                        basePreviewActivity3.mCheckView.setCheckedNum(Integer.MIN_VALUE);
                    } else {
                        basePreviewActivity3.mCheckView.setChecked(false);
                    }
                }
                BasePreviewActivity.this.updateApplyButton();
                BasePreviewActivity basePreviewActivity4 = BasePreviewActivity.this;
                OnSelectedListener onSelectedListener = basePreviewActivity4.mSpec.onSelectedListener;
                if (onSelectedListener != null) {
                    onSelectedListener.onSelected(basePreviewActivity4.mSelectedCollection.asListOfUri(), BasePreviewActivity.this.mSelectedCollection.asListOfString());
                }
            }
        });
        this.mOriginalLayout = (LinearLayout) findViewById(R$id.originalLayout);
        this.mOriginal = (CheckRadioView) findViewById(R$id.original);
        this.mOriginalLayout.setOnClickListener(new View.OnClickListener() { // from class: com.gen.mh.webapp_extensions.matisse.internal.ui.BasePreviewActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                int countOverMaxSize = BasePreviewActivity.this.countOverMaxSize();
                if (countOverMaxSize > 0) {
                    IncapableDialog.newInstance("", BasePreviewActivity.this.getString(R$string.error_over_original_count, new Object[]{Integer.valueOf(countOverMaxSize), Integer.valueOf(BasePreviewActivity.this.mSpec.originalMaxSize)})).show(BasePreviewActivity.this.getSupportFragmentManager(), IncapableDialog.class.getName());
                    return;
                }
                BasePreviewActivity basePreviewActivity = BasePreviewActivity.this;
                basePreviewActivity.mOriginalEnable = true ^ basePreviewActivity.mOriginalEnable;
                basePreviewActivity.mOriginal.setChecked(BasePreviewActivity.this.mOriginalEnable);
                BasePreviewActivity basePreviewActivity2 = BasePreviewActivity.this;
                if (!basePreviewActivity2.mOriginalEnable) {
                    basePreviewActivity2.mOriginal.setColor(-1);
                }
                BasePreviewActivity basePreviewActivity3 = BasePreviewActivity.this;
                OnCheckedListener onCheckedListener = basePreviewActivity3.mSpec.onCheckedListener;
                if (onCheckedListener == null) {
                    return;
                }
                onCheckedListener.onCheck(basePreviewActivity3.mOriginalEnable);
            }
        });
        updateApplyButton();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.support.p002v4.app.ComponentActivity, android.app.Activity
    public void onSaveInstanceState(Bundle bundle) {
        this.mSelectedCollection.onSaveInstanceState(bundle);
        bundle.putBoolean("checkState", this.mOriginalEnable);
        super.onSaveInstanceState(bundle);
    }

    @Override // android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onBackPressed() {
        sendBackResult(false);
        super.onBackPressed();
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (view.getId() == R$id.button_back) {
            onBackPressed();
        } else if (view.getId() != R$id.button_apply) {
        } else {
            sendBackResult(true);
            finish();
        }
    }

    @Override // com.gen.p059mh.webapp_extensions.matisse.listener.OnFragmentInteractionListener
    public void onClick() {
        if (!this.mSpec.autoHideToobar) {
            return;
        }
        if (this.mIsToolbarHide) {
            this.mTopToolbar.animate().setInterpolator(new FastOutSlowInInterpolator()).translationYBy(this.mTopToolbar.getMeasuredHeight()).start();
            this.mBottomToolbar.animate().translationYBy(this.mBottomToolbar.getMeasuredHeight()).setInterpolator(new FastOutSlowInInterpolator()).start();
        } else {
            this.mTopToolbar.animate().setInterpolator(new FastOutSlowInInterpolator()).translationYBy(-this.mTopToolbar.getMeasuredHeight()).start();
            this.mBottomToolbar.animate().setInterpolator(new FastOutSlowInInterpolator()).translationYBy(-this.mBottomToolbar.getMeasuredHeight()).start();
        }
        this.mIsToolbarHide = !this.mIsToolbarHide;
    }

    @Override // android.support.p002v4.view.ViewPager.OnPageChangeListener
    public void onPageSelected(int i) {
        PreviewPagerAdapter previewPagerAdapter = (PreviewPagerAdapter) this.mPager.getAdapter();
        int i2 = this.mPreviousPos;
        if (i2 != -1 && i2 != i) {
            ((PreviewItemFragment) previewPagerAdapter.mo6346instantiateItem((ViewGroup) this.mPager, i2)).resetView();
            Item mediaItem = previewPagerAdapter.getMediaItem(i);
            if (this.mSpec.countable) {
                int checkedNumOf = this.mSelectedCollection.checkedNumOf(mediaItem);
                this.mCheckView.setCheckedNum(checkedNumOf);
                if (checkedNumOf > 0) {
                    this.mCheckView.setEnabled(true);
                } else {
                    this.mCheckView.setEnabled(true ^ this.mSelectedCollection.maxSelectableReached());
                }
            } else {
                boolean isSelected = this.mSelectedCollection.isSelected(mediaItem);
                this.mCheckView.setChecked(isSelected);
                if (isSelected) {
                    this.mCheckView.setEnabled(true);
                } else {
                    this.mCheckView.setEnabled(true ^ this.mSelectedCollection.maxSelectableReached());
                }
            }
            updateSize(mediaItem);
        }
        this.mPreviousPos = i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateApplyButton() {
        int count = this.mSelectedCollection.count();
        if (count == 0) {
            this.mButtonApply.setText(R$string.button_sure_default);
            this.mButtonApply.setEnabled(false);
        } else if (count == 1 && this.mSpec.singleSelectionModeEnabled()) {
            this.mButtonApply.setText(R$string.button_sure_default);
            this.mButtonApply.setEnabled(true);
        } else {
            this.mButtonApply.setEnabled(true);
            this.mButtonApply.setText(getString(R$string.button_sure, new Object[]{Integer.valueOf(count)}));
        }
        if (this.mSpec.originalable) {
            this.mOriginalLayout.setVisibility(0);
            updateOriginalState();
            return;
        }
        this.mOriginalLayout.setVisibility(8);
    }

    private void updateOriginalState() {
        this.mOriginal.setChecked(this.mOriginalEnable);
        if (!this.mOriginalEnable) {
            this.mOriginal.setColor(-1);
        }
        if (countOverMaxSize() <= 0 || !this.mOriginalEnable) {
            return;
        }
        IncapableDialog.newInstance("", getString(R$string.error_over_original_size, new Object[]{Integer.valueOf(this.mSpec.originalMaxSize)})).show(getSupportFragmentManager(), IncapableDialog.class.getName());
        this.mOriginal.setChecked(false);
        this.mOriginal.setColor(-1);
        this.mOriginalEnable = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int countOverMaxSize() {
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

    /* JADX INFO: Access modifiers changed from: protected */
    public void updateSize(Item item) {
        if (item.isGif()) {
            this.mSize.setVisibility(0);
            TextView textView = this.mSize;
            textView.setText(PhotoMetadataUtils.getSizeInMB(item.size) + "M");
        } else {
            this.mSize.setVisibility(8);
        }
        if (item.isVideo()) {
            this.mOriginalLayout.setVisibility(8);
        } else if (!this.mSpec.originalable) {
        } else {
            this.mOriginalLayout.setVisibility(0);
        }
    }

    protected void sendBackResult(boolean z) {
        Intent intent = new Intent();
        intent.putExtra("extra_result_bundle", this.mSelectedCollection.getDataWithBundle());
        intent.putExtra("extra_result_apply", z);
        intent.putExtra("extra_result_original_enable", this.mOriginalEnable);
        setResult(-1, intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean assertAddSelection(Item item) {
        IncapableCause isAcceptable = this.mSelectedCollection.isAcceptable(item);
        IncapableCause.handleCause(this, isAcceptable);
        return isAcceptable == null;
    }
}
