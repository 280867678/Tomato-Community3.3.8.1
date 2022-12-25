package com.dueeeke.videoplayer.controller;

import android.content.Context;
import android.media.AudioManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import com.dueeeke.videoplayer.C1228R;
import com.dueeeke.videoplayer.util.PlayerUtils;
import com.dueeeke.videoplayer.widget.CenterView;

/* loaded from: classes2.dex */
public abstract class GestureVideoController extends BaseVideoController {
    protected AudioManager mAudioManager;
    protected float mBrightness;
    protected CenterView mCenterView;
    protected GestureDetector mGestureDetector;
    protected boolean mIsGestureEnabled;
    protected boolean mNeedSeek;
    protected int mPosition;
    protected int mStreamVolume;
    protected boolean virtualConsume = true;

    public GestureVideoController(@NonNull Context context) {
        super(context);
    }

    public GestureVideoController(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public GestureVideoController(@NonNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.dueeeke.videoplayer.controller.BaseVideoController
    public void initView() {
        super.initView();
        this.mCenterView = new CenterView(getContext());
        this.mCenterView.setVisibility(8);
        addView(this.mCenterView);
        this.mAudioManager = (AudioManager) getContext().getSystemService("audio");
        this.mGestureDetector = new GestureDetector(getContext(), new MyGestureListener());
        setOnTouchListener(new View.OnTouchListener() { // from class: com.dueeeke.videoplayer.controller.GestureVideoController.1
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return GestureVideoController.this.mGestureDetector.onTouchEvent(motionEvent);
            }
        });
    }

    /* loaded from: classes2.dex */
    protected class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        private boolean mChangeBrightness;
        private boolean mChangePosition;
        private boolean mChangeVolume;
        private boolean mFirstTouch;

        protected MyGestureListener() {
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public boolean onDown(MotionEvent motionEvent) {
            GestureVideoController gestureVideoController = GestureVideoController.this;
            if (!gestureVideoController.mIsGestureEnabled || PlayerUtils.isEdge(gestureVideoController.getContext(), motionEvent)) {
                return super.onDown(motionEvent);
            }
            GestureVideoController gestureVideoController2 = GestureVideoController.this;
            gestureVideoController2.mStreamVolume = gestureVideoController2.mAudioManager.getStreamVolume(3);
            GestureVideoController gestureVideoController3 = GestureVideoController.this;
            gestureVideoController3.mBrightness = PlayerUtils.scanForActivity(gestureVideoController3.getContext()).getWindow().getAttributes().screenBrightness;
            this.mFirstTouch = true;
            this.mChangePosition = false;
            this.mChangeBrightness = false;
            this.mChangeVolume = false;
            return true;
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
        public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
            GestureVideoController gestureVideoController = GestureVideoController.this;
            if (gestureVideoController.mShowing) {
                gestureVideoController.hide();
                return true;
            }
            gestureVideoController.show();
            return true;
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            GestureVideoController gestureVideoController = GestureVideoController.this;
            if (!gestureVideoController.mIsGestureEnabled || PlayerUtils.isEdge(gestureVideoController.getContext(), motionEvent)) {
                return super.onScroll(motionEvent, motionEvent2, f, f2);
            }
            float x = motionEvent.getX() - motionEvent2.getX();
            float y = motionEvent.getY() - motionEvent2.getY();
            if (this.mFirstTouch) {
                this.mChangePosition = Math.abs(f) >= Math.abs(f2);
                if (!this.mChangePosition) {
                    if (motionEvent2.getX() > PlayerUtils.getScreenWidth(GestureVideoController.this.getContext(), true) / 2) {
                        this.mChangeBrightness = true;
                    } else {
                        this.mChangeVolume = true;
                    }
                }
                this.mFirstTouch = false;
            }
            if (this.mChangePosition) {
                GestureVideoController.this.slideToChangePosition(x);
            } else if (this.mChangeBrightness) {
                GestureVideoController.this.slideToChangeBrightness(y);
            } else if (this.mChangeVolume) {
                GestureVideoController.this.slideToChangeVolume(y);
            }
            return true;
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
        public boolean onDoubleTap(MotionEvent motionEvent) {
            GestureVideoController gestureVideoController = GestureVideoController.this;
            if (gestureVideoController.mIsLocked || !gestureVideoController.virtualConsume) {
                return true;
            }
            gestureVideoController.doPauseResume();
            return true;
        }
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        boolean z = true;
        if (motionEvent.getAction() != 1) {
            z = false;
        }
        if (!this.mGestureDetector.onTouchEvent(motionEvent) && z) {
            if (this.mCenterView.getVisibility() == 0) {
                this.mCenterView.setVisibility(8);
            }
            if (this.mNeedSeek) {
                this.mMediaPlayer.seekTo(this.mPosition);
                this.mNeedSeek = false;
            }
        }
        return super.onTouchEvent(motionEvent);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void slideToChangePosition(float f) {
        this.mCenterView.setVisibility(0);
        hide();
        this.mCenterView.setProVisibility(8);
        int measuredWidth = getMeasuredWidth();
        int duration = (int) this.mMediaPlayer.getDuration();
        int currentPosition = (int) this.mMediaPlayer.getCurrentPosition();
        int i = (int) ((((-f) / measuredWidth) * 120000.0f) + currentPosition);
        if (i > currentPosition) {
            this.mCenterView.setIcon(C1228R.C1230drawable.dkplayer_ic_action_fast_forward);
        } else {
            this.mCenterView.setIcon(C1228R.C1230drawable.dkplayer_ic_action_fast_rewind);
        }
        if (i > duration) {
            i = duration;
        }
        if (i < 0) {
            i = 0;
        }
        this.mPosition = i;
        CenterView centerView = this.mCenterView;
        centerView.setTextView(stringForTime(i) + "/" + stringForTime(duration));
        this.mNeedSeek = true;
    }

    protected void slideToChangeBrightness(float f) {
        this.mCenterView.setVisibility(0);
        hide();
        this.mCenterView.setProVisibility(0);
        Window window = PlayerUtils.scanForActivity(getContext()).getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        this.mCenterView.setIcon(C1228R.C1230drawable.dkplayer_ic_action_brightness);
        int measuredHeight = getMeasuredHeight();
        if (this.mBrightness == -1.0f) {
            this.mBrightness = 0.5f;
        }
        float f2 = (((f * 2.0f) / measuredHeight) * 1.0f) + this.mBrightness;
        if (f2 < 0.0f) {
            f2 = 0.0f;
        }
        if (f2 > 1.0f) {
            f2 = 1.0f;
        }
        int i = (int) (100.0f * f2);
        CenterView centerView = this.mCenterView;
        centerView.setTextView(i + "%");
        this.mCenterView.setProPercent(i);
        attributes.screenBrightness = f2;
        window.setAttributes(attributes);
    }

    protected void slideToChangeVolume(float f) {
        this.mCenterView.setVisibility(0);
        hide();
        this.mCenterView.setProVisibility(0);
        float streamMaxVolume = this.mAudioManager.getStreamMaxVolume(3);
        float measuredHeight = (((f * 2.0f) / getMeasuredHeight()) * streamMaxVolume) + this.mStreamVolume;
        if (measuredHeight > streamMaxVolume) {
            measuredHeight = streamMaxVolume;
        }
        if (measuredHeight < 0.0f) {
            this.mCenterView.setIcon(C1228R.C1230drawable.dkplayer_ic_action_volume_off);
            measuredHeight = 0.0f;
        } else {
            this.mCenterView.setIcon(C1228R.C1230drawable.dkplayer_ic_action_volume_up);
        }
        int i = (int) ((measuredHeight / streamMaxVolume) * 100.0f);
        this.mCenterView.setTextView(i + "%");
        this.mCenterView.setProPercent(i);
        this.mAudioManager.setStreamVolume(3, (int) measuredHeight, 0);
    }
}
