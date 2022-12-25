package com.tomato.ucrop;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.p002v4.content.ContextCompat;
import android.support.p005v7.app.ActionBar;
import android.support.p005v7.app.AppCompatActivity;
import android.support.p005v7.widget.LinearLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import android.support.p005v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.one.tomato.ucrop.R$anim;
import com.one.tomato.ucrop.R$color;
import com.one.tomato.ucrop.R$dimen;
import com.one.tomato.ucrop.R$drawable;
import com.one.tomato.ucrop.R$id;
import com.one.tomato.ucrop.R$layout;
import com.one.tomato.ucrop.R$menu;
import com.one.tomato.ucrop.R$string;
import com.tomato.ucrop.callback.BitmapCropCallback;
import com.tomato.ucrop.model.AspectRatio;
import com.tomato.ucrop.model.CutInfo;
import com.tomato.ucrop.util.FileUtils;
import com.tomato.ucrop.util.SelectedStateListDrawable;
import com.tomato.ucrop.view.GestureCropImageView;
import com.tomato.ucrop.view.OverlayView;
import com.tomato.ucrop.view.TransformImageView;
import com.tomato.ucrop.view.UCropView;
import com.tomato.ucrop.view.widget.AspectRatioTextView;
import com.tomato.ucrop.view.widget.HorizontalProgressWheelView;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/* loaded from: classes3.dex */
public class PictureMultiCuttingActivity extends AppCompatActivity {
    public static final Bitmap.CompressFormat DEFAULT_COMPRESS_FORMAT = Bitmap.CompressFormat.PNG;
    private PicturePhotoGalleryAdapter adapter;
    private boolean circleDimmedLayer;
    private int cutIndex;
    private boolean isDragFrame;
    private ArrayList<String> list;
    private int mActiveWidgetColor;
    private View mBlockingView;
    private GestureCropImageView mGestureCropImageView;
    private ViewGroup mLayoutAspectRatio;
    private ViewGroup mLayoutRotate;
    private ViewGroup mLayoutScale;
    private OverlayView mOverlayView;
    private boolean mShowBottomControls;
    private int mStatusBarColor;
    private TextView mTextViewRotateAngle;
    private TextView mTextViewScalePercent;
    @DrawableRes
    private int mToolbarCancelDrawable;
    private int mToolbarColor;
    @DrawableRes
    private int mToolbarCropDrawable;
    private String mToolbarTitle;
    private int mToolbarWidgetColor;
    private UCropView mUCropView;
    private ViewGroup mWrapperStateAspectRatio;
    private ViewGroup mWrapperStateRotate;
    private ViewGroup mWrapperStateScale;
    private RecyclerView recyclerView;
    private boolean rotateEnabled;
    private boolean scaleEnabled;
    private boolean mShowLoader = true;
    private List<ViewGroup> mCropAspectRatioViews = new ArrayList();
    private Bitmap.CompressFormat mCompressFormat = DEFAULT_COMPRESS_FORMAT;
    private int mCompressQuality = 90;
    private List<CutInfo> cutInfos = new ArrayList();
    private TransformImageView.TransformImageListener mImageListener = new TransformImageView.TransformImageListener() { // from class: com.tomato.ucrop.PictureMultiCuttingActivity.1
        @Override // com.tomato.ucrop.view.TransformImageView.TransformImageListener
        public void onRotate(float f) {
            PictureMultiCuttingActivity.this.setAngleText(f);
        }

        @Override // com.tomato.ucrop.view.TransformImageView.TransformImageListener
        public void onScale(float f) {
            PictureMultiCuttingActivity.this.setScaleText(f);
        }

        @Override // com.tomato.ucrop.view.TransformImageView.TransformImageListener
        public void onLoadComplete() {
            PictureMultiCuttingActivity.this.mUCropView.animate().alpha(1.0f).setDuration(300L).setInterpolator(new AccelerateInterpolator());
            PictureMultiCuttingActivity.this.mBlockingView.setClickable(false);
            PictureMultiCuttingActivity.this.mShowLoader = false;
            PictureMultiCuttingActivity.this.supportInvalidateOptionsMenu();
        }

        @Override // com.tomato.ucrop.view.TransformImageView.TransformImageListener
        public void onLoadFailure(@NonNull Exception exc) {
            PictureMultiCuttingActivity.this.setResultError(exc);
            PictureMultiCuttingActivity.this.closeActivity();
        }
    };
    private final View.OnClickListener mStateClickListener = new View.OnClickListener() { // from class: com.tomato.ucrop.PictureMultiCuttingActivity.7
        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            if (!view.isSelected()) {
                PictureMultiCuttingActivity.this.setWidgetState(view.getId());
            }
        }
    };

    private void setAllowedGestures(int i) {
    }

    @Override // android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.support.p002v4.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R$layout.tomato_ucrop_picture_activity_multi_cutting);
        Intent intent = getIntent();
        this.list = getIntent().getStringArrayListExtra("com.one.tomato.ucrop.cuts");
        ArrayList<String> arrayList = this.list;
        if (arrayList != null && arrayList.size() > 0) {
            Iterator<String> it2 = this.list.iterator();
            while (it2.hasNext()) {
                this.cutInfos.add(new CutInfo(it2.next(), false));
            }
            initData();
        }
        setupViews(intent);
        setInitialState();
        addBlockingView();
        setImageData(intent);
    }

    private void initData() {
        this.recyclerView = (RecyclerView) findViewById(R$id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(0);
        this.recyclerView.setLayoutManager(linearLayoutManager);
        for (CutInfo cutInfo : this.cutInfos) {
            cutInfo.setCut(false);
        }
        this.cutInfos.get(this.cutIndex).setCut(true);
        this.adapter = new PicturePhotoGalleryAdapter(this, this.cutInfos);
        this.recyclerView.setAdapter(this.adapter);
    }

    @Override // android.app.Activity
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R$menu.ucrop_menu_activity, menu);
        MenuItem findItem = menu.findItem(R$id.menu_loader);
        Drawable icon = findItem.getIcon();
        if (icon != null) {
            try {
                icon.mutate();
                icon.setColorFilter(this.mToolbarWidgetColor, PorterDuff.Mode.SRC_ATOP);
                findItem.setIcon(icon);
            } catch (IllegalStateException e) {
                Log.i("UCropActivity", String.format("%s - %s", e.getMessage(), getString(R$string.ucrop_mutate_exception_hint)));
            }
            ((Animatable) findItem.getIcon()).start();
        }
        MenuItem findItem2 = menu.findItem(R$id.menu_crop);
        Drawable drawable = ContextCompat.getDrawable(this, this.mToolbarCropDrawable);
        if (drawable != null) {
            drawable.mutate();
            drawable.setColorFilter(this.mToolbarWidgetColor, PorterDuff.Mode.SRC_ATOP);
            findItem2.setIcon(drawable);
        }
        return true;
    }

    @Override // android.app.Activity
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R$id.menu_crop).setVisible(!this.mShowLoader);
        menu.findItem(R$id.menu_loader).setVisible(this.mShowLoader);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override // android.app.Activity
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == R$id.menu_crop) {
            cropAndSaveImage();
        } else if (menuItem.getItemId() == 16908332) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onStop() {
        super.onStop();
        GestureCropImageView gestureCropImageView = this.mGestureCropImageView;
        if (gestureCropImageView != null) {
            gestureCropImageView.cancelAllAnimations();
        }
    }

    private void setImageData(@NonNull Intent intent) {
        Uri uri = (Uri) intent.getParcelableExtra("com.one.tomato.ucrop.InputUri");
        Uri uri2 = (Uri) intent.getParcelableExtra("com.one.tomato.ucrop.OutputUri");
        processOptions(intent);
        if (uri != null && uri2 != null) {
            try {
                if (FileUtils.isGif(uri.getPath())) {
                    this.mGestureCropImageView.setRotateEnabled(false);
                    this.mGestureCropImageView.setScaleEnabled(false);
                } else {
                    this.mGestureCropImageView.setRotateEnabled(this.rotateEnabled);
                    this.mGestureCropImageView.setScaleEnabled(this.scaleEnabled);
                }
                this.mGestureCropImageView.setImageUri(uri, uri2);
                return;
            } catch (Exception e) {
                setResultError(e);
                closeActivity();
                return;
            }
        }
        setResultError(new NullPointerException(getString(R$string.ucrop_error_input_data_is_absent)));
        closeActivity();
    }

    private void processOptions(@NonNull Intent intent) {
        String stringExtra = intent.getStringExtra("com.one.tomato.ucrop.CompressionFormatName");
        Bitmap.CompressFormat valueOf = !TextUtils.isEmpty(stringExtra) ? Bitmap.CompressFormat.valueOf(stringExtra) : null;
        if (valueOf == null) {
            valueOf = DEFAULT_COMPRESS_FORMAT;
        }
        this.mCompressFormat = valueOf;
        this.mCompressQuality = intent.getIntExtra("com.one.tomato.ucrop.CompressionQuality", 90);
        int[] intArrayExtra = intent.getIntArrayExtra("com.one.tomato.ucrop.AllowedGestures");
        if (intArrayExtra != null) {
            int length = intArrayExtra.length;
        }
        this.mGestureCropImageView.setMaxBitmapSize(intent.getIntExtra("com.one.tomato.ucrop.MaxBitmapSize", 0));
        this.mGestureCropImageView.setMaxScaleMultiplier(intent.getFloatExtra("com.one.tomato.ucrop.MaxScaleMultiplier", 10.0f));
        this.mGestureCropImageView.setImageToWrapCropBoundsAnimDuration(intent.getIntExtra("com.one.tomato.ucrop.ImageToCropBoundsAnimDuration", 500));
        this.mOverlayView.setDragFrame(this.isDragFrame);
        this.mOverlayView.setFreestyleCropEnabled(intent.getBooleanExtra("com.one.tomato.ucrop.FreeStyleCrop", false));
        this.circleDimmedLayer = intent.getBooleanExtra("com.one.tomato.ucrop.CircleDimmedLayer", false);
        this.mOverlayView.setDimmedColor(intent.getIntExtra("com.one.tomato.ucrop.DimmedLayerColor", getResources().getColor(R$color.ucrop_color_default_dimmed)));
        this.mOverlayView.setCircleDimmedLayer(this.circleDimmedLayer);
        this.mOverlayView.setShowCropFrame(intent.getBooleanExtra("com.one.tomato.ucrop.ShowCropFrame", true));
        this.mOverlayView.setCropFrameColor(intent.getIntExtra("com.one.tomato.ucrop.CropFrameColor", getResources().getColor(R$color.ucrop_color_default_crop_frame)));
        this.mOverlayView.setCropFrameStrokeWidth(intent.getIntExtra("com.one.tomato.ucrop.CropFrameStrokeWidth", getResources().getDimensionPixelSize(R$dimen.ucrop_default_crop_frame_stoke_width)));
        this.mOverlayView.setShowCropGrid(intent.getBooleanExtra("com.one.tomato.ucrop.ShowCropGrid", true));
        this.mOverlayView.setCropGridRowCount(intent.getIntExtra("com.one.tomato.ucrop.CropGridRowCount", 2));
        this.mOverlayView.setCropGridColumnCount(intent.getIntExtra("com.one.tomato.ucrop.CropGridColumnCount", 2));
        this.mOverlayView.setCropGridColor(intent.getIntExtra("com.one.tomato.ucrop.CropGridColor", getResources().getColor(R$color.ucrop_color_default_crop_grid)));
        this.mOverlayView.setCropGridStrokeWidth(intent.getIntExtra("com.one.tomato.ucrop.CropGridStrokeWidth", getResources().getDimensionPixelSize(R$dimen.ucrop_default_crop_grid_stoke_width)));
        float floatExtra = intent.getFloatExtra("com.one.tomato.ucrop.AspectRatioX", 0.0f);
        float floatExtra2 = intent.getFloatExtra("com.one.tomato.ucrop.AspectRatioY", 0.0f);
        int intExtra = intent.getIntExtra("com.one.tomato.ucrop.AspectRatioSelectedByDefault", 0);
        ArrayList parcelableArrayListExtra = intent.getParcelableArrayListExtra("com.one.tomato.ucrop.AspectRatioOptions");
        if (floatExtra > 0.0f && floatExtra2 > 0.0f) {
            ViewGroup viewGroup = this.mWrapperStateAspectRatio;
            if (viewGroup != null) {
                viewGroup.setVisibility(8);
            }
            this.mGestureCropImageView.setTargetAspectRatio(floatExtra / floatExtra2);
        } else if (parcelableArrayListExtra != null && intExtra < parcelableArrayListExtra.size()) {
            this.mGestureCropImageView.setTargetAspectRatio(((AspectRatio) parcelableArrayListExtra.get(intExtra)).getAspectRatioX() / ((AspectRatio) parcelableArrayListExtra.get(intExtra)).getAspectRatioY());
        } else {
            this.mGestureCropImageView.setTargetAspectRatio(0.0f);
        }
        int intExtra2 = intent.getIntExtra("com.one.tomato.ucrop.MaxSizeX", 0);
        int intExtra3 = intent.getIntExtra("com.one.tomato.ucrop.MaxSizeY", 0);
        if (intExtra2 <= 0 || intExtra3 <= 0) {
            return;
        }
        this.mGestureCropImageView.setMaxResultImageSizeX(intExtra2);
        this.mGestureCropImageView.setMaxResultImageSizeY(intExtra3);
    }

    private void setupViews(@NonNull Intent intent) {
        this.scaleEnabled = intent.getBooleanExtra("com.one.tomato.ucrop.scale", false);
        this.rotateEnabled = intent.getBooleanExtra("com.one.tomato.ucrop.rotate", false);
        this.mStatusBarColor = intent.getIntExtra("com.one.tomato.ucrop.StatusBarColor", ContextCompat.getColor(this, R$color.ucrop_color_statusbar));
        this.mToolbarColor = intent.getIntExtra("com.one.tomato.ucrop.ToolbarColor", ContextCompat.getColor(this, R$color.ucrop_color_toolbar));
        if (this.mToolbarColor == -1) {
            this.mToolbarColor = ContextCompat.getColor(this, R$color.ucrop_color_toolbar);
        }
        if (this.mStatusBarColor == -1) {
            this.mStatusBarColor = ContextCompat.getColor(this, R$color.ucrop_color_statusbar);
        }
        this.mActiveWidgetColor = intent.getIntExtra("com.one.tomato.ucrop.UcropColorWidgetActive", ContextCompat.getColor(this, R$color.ucrop_color_widget_active));
        this.mToolbarWidgetColor = intent.getIntExtra("com.one.tomato.ucrop.UcropToolbarWidgetColor", ContextCompat.getColor(this, R$color.ucrop_color_toolbar_widget));
        if (this.mToolbarWidgetColor == -1) {
            this.mToolbarWidgetColor = ContextCompat.getColor(this, R$color.ucrop_color_toolbar_widget);
        }
        this.mToolbarCancelDrawable = intent.getIntExtra("com.one.tomato.ucrop.UcropToolbarCancelDrawable", R$drawable.ucrop_ic_cross);
        this.mToolbarCropDrawable = intent.getIntExtra("com.one.tomato.ucrop.UcropToolbarCropDrawable", R$drawable.ucrop_ic_done);
        this.mToolbarTitle = intent.getStringExtra("com.one.tomato.ucrop.UcropToolbarTitleText");
        String str = this.mToolbarTitle;
        if (str == null) {
            str = getResources().getString(R$string.ucrop_label_edit_photo);
        }
        this.mToolbarTitle = str;
        intent.getIntExtra("com.one.tomato.ucrop.UcropLogoColor", ContextCompat.getColor(this, R$color.ucrop_color_default_logo));
        this.mShowBottomControls = !intent.getBooleanExtra("com.one.tomato.ucrop.HideBottomControls", false);
        intent.getIntExtra("com.one.tomato.ucrop.UcropRootViewBackgroundColor", ContextCompat.getColor(this, R$color.ucrop_color_crop_background));
        setupAppBar();
        initiateRootViews();
        if (this.mShowBottomControls) {
            View.inflate(this, R$layout.tomato_ucrop_controls, (ViewGroup) findViewById(R$id.ucrop_mulit_photobox));
            this.mWrapperStateAspectRatio = (ViewGroup) findViewById(R$id.state_aspect_ratio);
            this.mWrapperStateAspectRatio.setOnClickListener(this.mStateClickListener);
            this.mWrapperStateRotate = (ViewGroup) findViewById(R$id.state_rotate);
            this.mWrapperStateRotate.setOnClickListener(this.mStateClickListener);
            this.mWrapperStateScale = (ViewGroup) findViewById(R$id.state_scale);
            this.mWrapperStateScale.setOnClickListener(this.mStateClickListener);
            this.mLayoutAspectRatio = (ViewGroup) findViewById(R$id.layout_aspect_ratio);
            this.mLayoutRotate = (ViewGroup) findViewById(R$id.layout_rotate_wheel);
            this.mLayoutScale = (ViewGroup) findViewById(R$id.layout_scale_wheel);
            setupAspectRatioWidget(intent);
            setupRotateWidget();
            setupScaleWidget();
            setupStatesWrapper();
        }
    }

    private void setupAppBar() {
        setStatusBarColor(this.mStatusBarColor);
        Toolbar toolbar = (Toolbar) findViewById(R$id.toolbar);
        toolbar.setBackgroundColor(this.mToolbarColor);
        toolbar.setTitleTextColor(this.mToolbarWidgetColor);
        TextView textView = (TextView) toolbar.findViewById(R$id.toolbar_title);
        textView.setTextColor(this.mToolbarWidgetColor);
        textView.setText(this.mToolbarTitle);
        Drawable mutate = ContextCompat.getDrawable(this, this.mToolbarCancelDrawable).mutate();
        mutate.setColorFilter(this.mToolbarWidgetColor, PorterDuff.Mode.SRC_ATOP);
        toolbar.setNavigationIcon(mutate);
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayShowTitleEnabled(false);
        }
    }

    private void initiateRootViews() {
        this.mUCropView = (UCropView) findViewById(R$id.ucrop);
        this.mGestureCropImageView = this.mUCropView.getCropImageView();
        this.mOverlayView = this.mUCropView.getOverlayView();
        this.mGestureCropImageView.setTransformImageListener(this.mImageListener);
    }

    private void setupStatesWrapper() {
        ImageView imageView = (ImageView) findViewById(R$id.image_view_state_scale);
        ImageView imageView2 = (ImageView) findViewById(R$id.image_view_state_rotate);
        ImageView imageView3 = (ImageView) findViewById(R$id.image_view_state_aspect_ratio);
        imageView.setImageDrawable(new SelectedStateListDrawable(imageView.getDrawable(), this.mActiveWidgetColor));
        imageView2.setImageDrawable(new SelectedStateListDrawable(imageView2.getDrawable(), this.mActiveWidgetColor));
        imageView3.setImageDrawable(new SelectedStateListDrawable(imageView3.getDrawable(), this.mActiveWidgetColor));
    }

    @TargetApi(21)
    private void setStatusBarColor(@ColorInt int i) {
        Window window;
        if (Build.VERSION.SDK_INT < 21 || (window = getWindow()) == null) {
            return;
        }
        window.addFlags(Integer.MIN_VALUE);
        window.setStatusBarColor(i);
    }

    private void setupAspectRatioWidget(@NonNull Intent intent) {
        int intExtra = intent.getIntExtra("com.one.tomato.ucrop.AspectRatioSelectedByDefault", 0);
        ArrayList parcelableArrayListExtra = intent.getParcelableArrayListExtra("com.one.tomato.ucrop.AspectRatioOptions");
        if (parcelableArrayListExtra == null || parcelableArrayListExtra.isEmpty()) {
            intExtra = 2;
            parcelableArrayListExtra = new ArrayList();
            parcelableArrayListExtra.add(new AspectRatio(null, 1.0f, 1.0f));
            parcelableArrayListExtra.add(new AspectRatio(null, 3.0f, 4.0f));
            parcelableArrayListExtra.add(new AspectRatio(getString(R$string.ucrop_label_original).toUpperCase(), 0.0f, 0.0f));
            parcelableArrayListExtra.add(new AspectRatio(null, 3.0f, 2.0f));
            parcelableArrayListExtra.add(new AspectRatio(null, 16.0f, 9.0f));
        }
        LinearLayout linearLayout = (LinearLayout) findViewById(R$id.layout_aspect_ratio);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, -1);
        layoutParams.weight = 1.0f;
        Iterator it2 = parcelableArrayListExtra.iterator();
        while (it2.hasNext()) {
            FrameLayout frameLayout = (FrameLayout) getLayoutInflater().inflate(R$layout.tomato_ucrop_aspect_ratio, (ViewGroup) null);
            frameLayout.setLayoutParams(layoutParams);
            AspectRatioTextView aspectRatioTextView = (AspectRatioTextView) frameLayout.getChildAt(0);
            aspectRatioTextView.setActiveColor(this.mActiveWidgetColor);
            aspectRatioTextView.setAspectRatio((AspectRatio) it2.next());
            linearLayout.addView(frameLayout);
            this.mCropAspectRatioViews.add(frameLayout);
        }
        this.mCropAspectRatioViews.get(intExtra).setSelected(true);
        for (ViewGroup viewGroup : this.mCropAspectRatioViews) {
            viewGroup.setOnClickListener(new View.OnClickListener() { // from class: com.tomato.ucrop.PictureMultiCuttingActivity.2
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    PictureMultiCuttingActivity.this.mGestureCropImageView.setTargetAspectRatio(((AspectRatioTextView) ((ViewGroup) view).getChildAt(0)).getAspectRatio(view.isSelected()));
                    PictureMultiCuttingActivity.this.mGestureCropImageView.setImageToWrapCropBounds();
                    if (!view.isSelected()) {
                        for (ViewGroup viewGroup2 : PictureMultiCuttingActivity.this.mCropAspectRatioViews) {
                            viewGroup2.setSelected(viewGroup2 == view);
                        }
                    }
                }
            });
        }
    }

    private void setupRotateWidget() {
        this.mTextViewRotateAngle = (TextView) findViewById(R$id.text_view_rotate);
        ((HorizontalProgressWheelView) findViewById(R$id.rotate_scroll_wheel)).setScrollingListener(new HorizontalProgressWheelView.ScrollingListener() { // from class: com.tomato.ucrop.PictureMultiCuttingActivity.3
            @Override // com.tomato.ucrop.view.widget.HorizontalProgressWheelView.ScrollingListener
            public void onScroll(float f, float f2) {
                PictureMultiCuttingActivity.this.mGestureCropImageView.postRotate(f / 42.0f);
            }

            @Override // com.tomato.ucrop.view.widget.HorizontalProgressWheelView.ScrollingListener
            public void onScrollEnd() {
                PictureMultiCuttingActivity.this.mGestureCropImageView.setImageToWrapCropBounds();
            }

            @Override // com.tomato.ucrop.view.widget.HorizontalProgressWheelView.ScrollingListener
            public void onScrollStart() {
                PictureMultiCuttingActivity.this.mGestureCropImageView.cancelAllAnimations();
            }
        });
        ((HorizontalProgressWheelView) findViewById(R$id.rotate_scroll_wheel)).setMiddleLineColor(this.mActiveWidgetColor);
        findViewById(R$id.wrapper_reset_rotate).setOnClickListener(new View.OnClickListener() { // from class: com.tomato.ucrop.PictureMultiCuttingActivity.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PictureMultiCuttingActivity.this.resetRotation();
            }
        });
        findViewById(R$id.wrapper_rotate_by_angle).setOnClickListener(new View.OnClickListener() { // from class: com.tomato.ucrop.PictureMultiCuttingActivity.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PictureMultiCuttingActivity.this.rotateByAngle(90);
            }
        });
    }

    private void setupScaleWidget() {
        this.mTextViewScalePercent = (TextView) findViewById(R$id.text_view_scale);
        ((HorizontalProgressWheelView) findViewById(R$id.scale_scroll_wheel)).setScrollingListener(new HorizontalProgressWheelView.ScrollingListener() { // from class: com.tomato.ucrop.PictureMultiCuttingActivity.6
            @Override // com.tomato.ucrop.view.widget.HorizontalProgressWheelView.ScrollingListener
            public void onScroll(float f, float f2) {
                if (f > 0.0f) {
                    PictureMultiCuttingActivity.this.mGestureCropImageView.zoomInImage(PictureMultiCuttingActivity.this.mGestureCropImageView.getCurrentScale() + (f * ((PictureMultiCuttingActivity.this.mGestureCropImageView.getMaxScale() - PictureMultiCuttingActivity.this.mGestureCropImageView.getMinScale()) / 15000.0f)));
                } else {
                    PictureMultiCuttingActivity.this.mGestureCropImageView.zoomOutImage(PictureMultiCuttingActivity.this.mGestureCropImageView.getCurrentScale() + (f * ((PictureMultiCuttingActivity.this.mGestureCropImageView.getMaxScale() - PictureMultiCuttingActivity.this.mGestureCropImageView.getMinScale()) / 15000.0f)));
                }
            }

            @Override // com.tomato.ucrop.view.widget.HorizontalProgressWheelView.ScrollingListener
            public void onScrollEnd() {
                PictureMultiCuttingActivity.this.mGestureCropImageView.setImageToWrapCropBounds();
            }

            @Override // com.tomato.ucrop.view.widget.HorizontalProgressWheelView.ScrollingListener
            public void onScrollStart() {
                PictureMultiCuttingActivity.this.mGestureCropImageView.cancelAllAnimations();
            }
        });
        ((HorizontalProgressWheelView) findViewById(R$id.scale_scroll_wheel)).setMiddleLineColor(this.mActiveWidgetColor);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setAngleText(float f) {
        TextView textView = this.mTextViewRotateAngle;
        if (textView != null) {
            textView.setText(String.format(Locale.getDefault(), "%.1f°", Float.valueOf(f)));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setScaleText(float f) {
        TextView textView = this.mTextViewScalePercent;
        if (textView != null) {
            textView.setText(String.format(Locale.getDefault(), "%d%%", Integer.valueOf((int) (f * 100.0f))));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resetRotation() {
        GestureCropImageView gestureCropImageView = this.mGestureCropImageView;
        gestureCropImageView.postRotate(-gestureCropImageView.getCurrentAngle());
        this.mGestureCropImageView.setImageToWrapCropBounds();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void rotateByAngle(int i) {
        this.mGestureCropImageView.postRotate(i);
        this.mGestureCropImageView.setImageToWrapCropBounds();
    }

    private void setInitialState() {
        if (this.mShowBottomControls) {
            if (this.mWrapperStateAspectRatio.getVisibility() == 0) {
                setWidgetState(R$id.state_aspect_ratio);
                return;
            } else {
                setWidgetState(R$id.state_scale);
                return;
            }
        }
        setAllowedGestures(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setWidgetState(@IdRes int i) {
        if (!this.mShowBottomControls) {
            return;
        }
        this.mWrapperStateAspectRatio.setSelected(i == R$id.state_aspect_ratio);
        this.mWrapperStateRotate.setSelected(i == R$id.state_rotate);
        this.mWrapperStateScale.setSelected(i == R$id.state_scale);
        int i2 = 8;
        this.mLayoutAspectRatio.setVisibility(i == R$id.state_aspect_ratio ? 0 : 8);
        this.mLayoutRotate.setVisibility(i == R$id.state_rotate ? 0 : 8);
        ViewGroup viewGroup = this.mLayoutScale;
        if (i == R$id.state_scale) {
            i2 = 0;
        }
        viewGroup.setVisibility(i2);
        if (i == R$id.state_scale) {
            setAllowedGestures(0);
        } else if (i == R$id.state_rotate) {
            setAllowedGestures(1);
        } else {
            setAllowedGestures(2);
        }
    }

    private void addBlockingView() {
        if (this.mBlockingView == null) {
            this.mBlockingView = new View(this);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -1);
            layoutParams.addRule(3, R$id.toolbar);
            this.mBlockingView.setLayoutParams(layoutParams);
            this.mBlockingView.setClickable(true);
        }
        ((RelativeLayout) findViewById(R$id.ucrop_mulit_photobox)).addView(this.mBlockingView);
    }

    protected void cropAndSaveImage() {
        this.mBlockingView.setClickable(true);
        this.mShowLoader = true;
        supportInvalidateOptionsMenu();
        this.mGestureCropImageView.cropAndSaveImage(this.mCompressFormat, this.mCompressQuality, new BitmapCropCallback() { // from class: com.tomato.ucrop.PictureMultiCuttingActivity.8
            @Override // com.tomato.ucrop.callback.BitmapCropCallback
            public void onBitmapCropped(@NonNull Uri uri, int i, int i2, int i3, int i4) {
                PictureMultiCuttingActivity pictureMultiCuttingActivity = PictureMultiCuttingActivity.this;
                pictureMultiCuttingActivity.setResultUri(uri, pictureMultiCuttingActivity.mGestureCropImageView.getTargetAspectRatio(), i, i2, i3, i4);
            }

            @Override // com.tomato.ucrop.callback.BitmapCropCallback
            public void onCropFailure(@NonNull Throwable th) {
                PictureMultiCuttingActivity.this.setResultError(th);
                PictureMultiCuttingActivity.this.closeActivity();
            }
        });
    }

    protected void setResultUri(Uri uri, float f, int i, int i2, int i3, int i4) {
        try {
            CutInfo cutInfo = this.cutInfos.get(this.cutIndex);
            cutInfo.setCutPath(uri.getPath());
            cutInfo.setCut(true);
            cutInfo.setResultAspectRatio(f);
            cutInfo.setOffsetX(i);
            cutInfo.setOffsetY(i2);
            cutInfo.setImageWidth(i3);
            cutInfo.setImageHeight(i4);
            this.cutIndex++;
            if (this.cutIndex >= this.cutInfos.size()) {
                setResult(-1, new Intent().putExtra("com.one.tomato.ucrop.OutputUriList", (Serializable) this.cutInfos));
                closeActivity();
            } else {
                resetCutData();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void resetCutData() {
        setContentView(R$layout.tomato_ucrop_picture_activity_multi_cutting);
        Intent intent = new Intent();
        Bundle extras = getIntent().getExtras();
        String path = this.cutInfos.get(this.cutIndex).getPath();
        boolean isHttp = FileUtils.isHttp(path);
        String lastImgType = getLastImgType(path);
        extras.putParcelable("com.one.tomato.ucrop.InputUri", isHttp ? Uri.parse(path) : Uri.fromFile(new File(path)));
        File cacheDir = getCacheDir();
        extras.putParcelable("com.one.tomato.ucrop.OutputUri", Uri.fromFile(new File(cacheDir, System.currentTimeMillis() + lastImgType)));
        intent.putExtras(extras);
        setupViews(intent);
        setInitialState();
        ((RelativeLayout) findViewById(R$id.ucrop_mulit_photobox)).removeView(this.mBlockingView);
        this.mBlockingView = null;
        addBlockingView();
        setImageData(intent);
        initData();
        int i = this.cutIndex;
        if (i >= 5) {
            this.recyclerView.scrollToPosition(i);
        }
    }

    public static String getLastImgType(String str) {
        try {
            int lastIndexOf = str.lastIndexOf(".");
            if (lastIndexOf > 0) {
                String substring = str.substring(lastIndexOf, str.length());
                char c = 65535;
                switch (substring.hashCode()) {
                    case 1436279:
                        if (substring.equals(".BMP")) {
                            c = 7;
                            break;
                        }
                        break;
                    case 1449755:
                        if (substring.equals(".PNG")) {
                            c = 1;
                            break;
                        }
                        break;
                    case 1468055:
                        if (substring.equals(".bmp")) {
                            c = 6;
                            break;
                        }
                        break;
                    case 1475827:
                        if (substring.equals(".jpg")) {
                            c = 2;
                            break;
                        }
                        break;
                    case 1481531:
                        if (substring.equals(".png")) {
                            c = 0;
                            break;
                        }
                        break;
                    case 44765590:
                        if (substring.equals(".JPEG")) {
                            c = 4;
                            break;
                        }
                        break;
                    case 45142218:
                        if (substring.equals(".WEBP")) {
                            c = 5;
                            break;
                        }
                        break;
                    case 45750678:
                        if (substring.equals(".jpeg")) {
                            c = 3;
                            break;
                        }
                        break;
                    case 46127306:
                        if (substring.equals(".webp")) {
                            c = '\b';
                            break;
                        }
                        break;
                }
                switch (c) {
                    case 0:
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                    case 7:
                    case '\b':
                        return substring;
                }
            }
            return ".png";
        } catch (Exception e) {
            e.printStackTrace();
            return ".png";
        }
    }

    protected void setResultError(Throwable th) {
        setResult(96, new Intent().putExtra("com.one.tomato.ucrop.Error", th));
    }

    protected void closeActivity() {
        finish();
        overridePendingTransition(0, R$anim.ucrop_close);
    }
}
