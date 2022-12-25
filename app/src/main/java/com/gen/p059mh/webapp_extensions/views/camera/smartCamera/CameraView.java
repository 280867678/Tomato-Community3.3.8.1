package com.gen.p059mh.webapp_extensions.views.camera.smartCamera;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.p002v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import com.gen.p059mh.webapp_extensions.R$style;
import com.gen.p059mh.webapp_extensions.R$styleable;
import com.gen.p059mh.webapp_extensions.views.camera.smartCamera.CameraViewImpl;
import com.gen.p059mh.webapp_extensions.views.camera.smartCamera.api14.Camera1;
import com.gen.p059mh.webapp_extensions.views.camera.smartCamera.api14.SurfaceViewPreview;
import com.gen.p059mh.webapp_extensions.views.camera.smartCamera.api14.TextureViewPreview;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

/* renamed from: com.gen.mh.webapp_extensions.views.camera.smartCamera.CameraView */
/* loaded from: classes2.dex */
public class CameraView extends FrameLayout {
    private boolean mAdjustViewBounds;
    private final CallbackBridge mCallbacks;
    private final DisplayOrientationDetector mDisplayOrientationDetector;
    CameraViewImpl mImpl;

    /* renamed from: com.gen.mh.webapp_extensions.views.camera.smartCamera.CameraView$Callback */
    /* loaded from: classes2.dex */
    public static abstract class Callback {
        public void onCameraClosed(CameraView cameraView) {
        }

        public void onCameraOpened(CameraView cameraView) {
        }

        public void onPictureTaken(CameraView cameraView, byte[] bArr) {
        }
    }

    public CameraView(Context context) {
        this(context, null);
    }

    public CameraView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public CameraView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        if (isInEditMode()) {
            this.mCallbacks = null;
            this.mDisplayOrientationDetector = null;
            return;
        }
        PreviewImpl createPreviewImpl = createPreviewImpl(context);
        this.mCallbacks = new CallbackBridge();
        this.mImpl = new Camera1(this.mCallbacks, createPreviewImpl);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.CameraView, i, R$style.Widget_CameraView);
        this.mAdjustViewBounds = obtainStyledAttributes.getBoolean(R$styleable.CameraView_android_adjustViewBounds, false);
        setFacing(obtainStyledAttributes.getInt(R$styleable.CameraView_facing, 0));
        String string = obtainStyledAttributes.getString(R$styleable.CameraView_aspectRatio);
        if (string != null) {
            setAspectRatio(AspectRatio.parse(string));
        } else {
            setAspectRatio(Constants.DEFAULT_ASPECT_RATIO);
        }
        setAutoFocus(obtainStyledAttributes.getBoolean(R$styleable.CameraView_autoFocus, true));
        setFlash(obtainStyledAttributes.getInt(R$styleable.CameraView_flash, 3));
        obtainStyledAttributes.recycle();
        this.mDisplayOrientationDetector = new DisplayOrientationDetector(context) { // from class: com.gen.mh.webapp_extensions.views.camera.smartCamera.CameraView.1
            @Override // com.gen.p059mh.webapp_extensions.views.camera.smartCamera.DisplayOrientationDetector
            public void onDisplayOrientationChanged(int i2) {
                CameraView.this.mImpl.setDisplayOrientation(i2);
            }
        };
    }

    @NonNull
    private PreviewImpl createPreviewImpl(Context context) {
        if (Build.VERSION.SDK_INT >= 23) {
            return new SurfaceViewPreview(context, this);
        }
        return new TextureViewPreview(context, this);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!isInEditMode()) {
            this.mDisplayOrientationDetector.enable(ViewCompat.getDisplay(this));
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        if (!isInEditMode()) {
            this.mDisplayOrientationDetector.disable();
        }
        super.onDetachedFromWindow();
    }

    @Override // android.widget.FrameLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        if (isInEditMode()) {
            super.onMeasure(i, i2);
            return;
        }
        if (this.mAdjustViewBounds) {
            if (!isCameraOpened()) {
                this.mCallbacks.reserveRequestLayoutOnOpen();
                super.onMeasure(i, i2);
                return;
            }
            int mode = View.MeasureSpec.getMode(i);
            int mode2 = View.MeasureSpec.getMode(i2);
            if (mode == 1073741824 && mode2 != 1073741824) {
                int size = (int) (View.MeasureSpec.getSize(i) * getAspectRatio().toFloat());
                if (mode2 == Integer.MIN_VALUE) {
                    size = Math.min(size, View.MeasureSpec.getSize(i2));
                }
                super.onMeasure(i, View.MeasureSpec.makeMeasureSpec(size, 1073741824));
            } else if (mode != 1073741824 && mode2 == 1073741824) {
                int size2 = (int) (View.MeasureSpec.getSize(i2) * getAspectRatio().toFloat());
                if (mode == Integer.MIN_VALUE) {
                    size2 = Math.min(size2, View.MeasureSpec.getSize(i));
                }
                super.onMeasure(View.MeasureSpec.makeMeasureSpec(size2, 1073741824), i2);
            } else {
                super.onMeasure(i, i2);
            }
        } else {
            super.onMeasure(i, i2);
        }
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        AspectRatio aspectRatio = getAspectRatio();
        if (this.mDisplayOrientationDetector.getLastKnownDisplayOrientation() % 180 == 0) {
            aspectRatio = aspectRatio.inverse();
        }
        if (measuredHeight < (aspectRatio.getY() * measuredWidth) / aspectRatio.getX()) {
            this.mImpl.getView().measure(View.MeasureSpec.makeMeasureSpec(measuredWidth, 1073741824), View.MeasureSpec.makeMeasureSpec((measuredWidth * aspectRatio.getY()) / aspectRatio.getX(), 1073741824));
        } else {
            this.mImpl.getView().measure(View.MeasureSpec.makeMeasureSpec((aspectRatio.getX() * measuredHeight) / aspectRatio.getY(), 1073741824), View.MeasureSpec.makeMeasureSpec(measuredHeight, 1073741824));
        }
    }

    @Override // android.view.View
    protected Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.facing = getFacing();
        savedState.ratio = getAspectRatio();
        savedState.autoFocus = getAutoFocus();
        savedState.flash = getFlash();
        return savedState;
    }

    @Override // android.view.View
    protected void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        setFacing(savedState.facing);
        setAspectRatio(savedState.ratio);
        setAutoFocus(savedState.autoFocus);
        setFlash(savedState.flash);
    }

    public void start() {
        if (!this.mImpl.start()) {
            Parcelable onSaveInstanceState = onSaveInstanceState();
            this.mImpl = new Camera1(this.mCallbacks, createPreviewImpl(getContext()));
            onRestoreInstanceState(onSaveInstanceState);
            this.mImpl.start();
        }
    }

    public void stop() {
        this.mImpl.stop();
    }

    public boolean isCameraOpened() {
        return this.mImpl.isCameraOpened();
    }

    public void addCallback(@NonNull Callback callback) {
        this.mCallbacks.add(callback);
    }

    public void setAdjustViewBounds(boolean z) {
        if (this.mAdjustViewBounds != z) {
            this.mAdjustViewBounds = z;
            requestLayout();
        }
    }

    public boolean getAdjustViewBounds() {
        return this.mAdjustViewBounds;
    }

    public void setFacing(int i) {
        this.mImpl.setFacing(i);
    }

    public int getFacing() {
        return this.mImpl.getFacing();
    }

    public Set<AspectRatio> getSupportedAspectRatios() {
        return this.mImpl.getSupportedAspectRatios();
    }

    public void setAspectRatio(@NonNull AspectRatio aspectRatio) {
        if (this.mImpl.setAspectRatio(aspectRatio)) {
            requestLayout();
        }
    }

    @Nullable
    public AspectRatio getAspectRatio() {
        return this.mImpl.getAspectRatio();
    }

    public void setAutoFocus(boolean z) {
        this.mImpl.setAutoFocus(z);
    }

    public boolean getAutoFocus() {
        return this.mImpl.getAutoFocus();
    }

    public void setFlash(int i) {
        this.mImpl.setFlash(i);
    }

    public int getFlash() {
        return this.mImpl.getFlash();
    }

    public void takePicture() {
        this.mImpl.takePicture();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: com.gen.mh.webapp_extensions.views.camera.smartCamera.CameraView$CallbackBridge */
    /* loaded from: classes2.dex */
    public class CallbackBridge implements CameraViewImpl.Callback {
        private final ArrayList<Callback> mCallbacks = new ArrayList<>();
        private boolean mRequestLayoutOnOpen;

        CallbackBridge() {
        }

        public void add(Callback callback) {
            this.mCallbacks.add(callback);
        }

        @Override // com.gen.p059mh.webapp_extensions.views.camera.smartCamera.CameraViewImpl.Callback
        public void onCameraOpened() {
            if (this.mRequestLayoutOnOpen) {
                this.mRequestLayoutOnOpen = false;
                CameraView.this.requestLayout();
            }
            Iterator<Callback> it2 = this.mCallbacks.iterator();
            while (it2.hasNext()) {
                it2.next().onCameraOpened(CameraView.this);
            }
        }

        @Override // com.gen.p059mh.webapp_extensions.views.camera.smartCamera.CameraViewImpl.Callback
        public void onCameraClosed() {
            Iterator<Callback> it2 = this.mCallbacks.iterator();
            while (it2.hasNext()) {
                it2.next().onCameraClosed(CameraView.this);
            }
        }

        @Override // com.gen.p059mh.webapp_extensions.views.camera.smartCamera.CameraViewImpl.Callback
        public void onPictureTaken(byte[] bArr) {
            Iterator<Callback> it2 = this.mCallbacks.iterator();
            while (it2.hasNext()) {
                it2.next().onPictureTaken(CameraView.this, bArr);
            }
        }

        public void reserveRequestLayoutOnOpen() {
            this.mRequestLayoutOnOpen = true;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* renamed from: com.gen.mh.webapp_extensions.views.camera.smartCamera.CameraView$SavedState */
    /* loaded from: classes2.dex */
    public static class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator<SavedState>() { // from class: com.gen.mh.webapp_extensions.views.camera.smartCamera.CameraView.SavedState.1
            @Override // android.os.Parcelable.Creator
            /* renamed from: createFromParcel */
            public SavedState mo6187createFromParcel(Parcel parcel) {
                return mo6188createFromParcel(parcel, (ClassLoader) null);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.ClassLoaderCreator
            /* renamed from: createFromParcel */
            public SavedState mo6188createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return new SavedState(parcel, classLoader);
            }

            @Override // android.os.Parcelable.Creator
            /* renamed from: newArray */
            public SavedState[] mo6189newArray(int i) {
                return new SavedState[i];
            }
        };
        boolean autoFocus;
        int facing;
        int flash;
        AspectRatio ratio;

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel);
            this.facing = parcel.readInt();
            this.ratio = (AspectRatio) parcel.readParcelable(classLoader);
            this.autoFocus = parcel.readByte() != 0;
            this.flash = parcel.readInt();
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.facing);
            parcel.writeParcelable(this.ratio, 0);
            parcel.writeByte(this.autoFocus ? (byte) 1 : (byte) 0);
            parcel.writeInt(this.flash);
        }
    }
}
