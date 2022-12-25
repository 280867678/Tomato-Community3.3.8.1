package com.tomatolive.library.p136ui.view.widget;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;

/* renamed from: com.tomatolive.library.ui.view.widget.EmotionKeyboard */
/* loaded from: classes4.dex */
public class EmotionKeyboard {
    private static final String SHARE_PREFERENCE_NAME = "EmotionKeyboard";
    private static final String SHARE_PREFERENCE_SOFT_INPUT_HEIGHT = "soft_input_height";
    private Activity mActivity;
    private View mContentView;
    private EditText mEditText;
    private View mEmotionLayout;
    private InputMethodManager mInputManager;

    /* renamed from: sp */
    private SharedPreferences f5865sp;

    private EmotionKeyboard() {
    }

    public static EmotionKeyboard with(Activity activity) {
        EmotionKeyboard emotionKeyboard = new EmotionKeyboard();
        emotionKeyboard.mActivity = activity;
        emotionKeyboard.mInputManager = (InputMethodManager) activity.getSystemService("input_method");
        emotionKeyboard.f5865sp = activity.getSharedPreferences(SHARE_PREFERENCE_NAME, 0);
        return emotionKeyboard;
    }

    public EmotionKeyboard bindToContent(View view) {
        this.mContentView = view;
        return this;
    }

    public EmotionKeyboard bindToEditText(EditText editText) {
        this.mEditText = editText;
        this.mEditText.requestFocus();
        this.mEditText.setOnTouchListener(new View.OnTouchListener() { // from class: com.tomatolive.library.ui.view.widget.EmotionKeyboard.1
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() != 1 || !EmotionKeyboard.this.mEmotionLayout.isShown()) {
                    return false;
                }
                EmotionKeyboard.this.lockContentHeight();
                EmotionKeyboard.this.hideEmotionLayout(true);
                EmotionKeyboard.this.mEditText.postDelayed(new Runnable() { // from class: com.tomatolive.library.ui.view.widget.EmotionKeyboard.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        EmotionKeyboard.this.unlockContentHeightDelayed();
                    }
                }, 200L);
                return false;
            }
        });
        return this;
    }

    public EmotionKeyboard bindToEmotionButton(View view) {
        view.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.widget.EmotionKeyboard.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                if (EmotionKeyboard.this.mEmotionLayout.isShown()) {
                    EmotionKeyboard.this.lockContentHeight();
                    EmotionKeyboard.this.hideEmotionLayout(true);
                    EmotionKeyboard.this.unlockContentHeightDelayed();
                } else if (EmotionKeyboard.this.isSoftInputShown()) {
                    EmotionKeyboard.this.lockContentHeight();
                    EmotionKeyboard.this.showEmotionLayout();
                    EmotionKeyboard.this.unlockContentHeightDelayed();
                } else {
                    EmotionKeyboard.this.showEmotionLayout();
                }
            }
        });
        return this;
    }

    public EmotionKeyboard setEmotionView(View view) {
        this.mEmotionLayout = view;
        return this;
    }

    public EmotionKeyboard build() {
        this.mActivity.getWindow().setSoftInputMode(19);
        hideSoftInput();
        return this;
    }

    public boolean interceptBackPress() {
        if (this.mEmotionLayout.isShown()) {
            hideEmotionLayout(false);
            return true;
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showEmotionLayout() {
        int supportSoftInputHeight = getSupportSoftInputHeight();
        if (supportSoftInputHeight == 0) {
            supportSoftInputHeight = getKeyBoardHeight();
        }
        hideSoftInput();
        this.mEmotionLayout.getLayoutParams().height = supportSoftInputHeight;
        this.mEmotionLayout.setVisibility(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void hideEmotionLayout(boolean z) {
        if (this.mEmotionLayout.isShown()) {
            this.mEmotionLayout.setVisibility(8);
            if (!z) {
                return;
            }
            showSoftInput();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void lockContentHeight() {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mContentView.getLayoutParams();
        layoutParams.height = this.mContentView.getHeight();
        layoutParams.weight = 0.0f;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void unlockContentHeightDelayed() {
        this.mEditText.postDelayed(new Runnable() { // from class: com.tomatolive.library.ui.view.widget.EmotionKeyboard.3
            @Override // java.lang.Runnable
            public void run() {
                ((LinearLayout.LayoutParams) EmotionKeyboard.this.mContentView.getLayoutParams()).weight = 1.0f;
            }
        }, 200L);
    }

    private void showSoftInput() {
        this.mEditText.requestFocus();
        this.mEditText.post(new Runnable() { // from class: com.tomatolive.library.ui.view.widget.EmotionKeyboard.4
            @Override // java.lang.Runnable
            public void run() {
                EmotionKeyboard.this.mInputManager.showSoftInput(EmotionKeyboard.this.mEditText, 0);
            }
        });
    }

    private void hideSoftInput() {
        this.mInputManager.hideSoftInputFromWindow(this.mEditText.getWindowToken(), 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isSoftInputShown() {
        return getSupportSoftInputHeight() != 0;
    }

    private int getSupportSoftInputHeight() {
        Rect rect = new Rect();
        this.mActivity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        int height = this.mActivity.getWindow().getDecorView().getRootView().getHeight() - rect.bottom;
        if (Build.VERSION.SDK_INT >= 20) {
            Log.i("meme", "虚拟导航栏高度：" + getSoftButtonsBarHeight());
            height -= getSoftButtonsBarHeight();
        }
        if (height > 0) {
            this.f5865sp.edit().putInt(SHARE_PREFERENCE_SOFT_INPUT_HEIGHT, height).apply();
        }
        Log.i("meme", "键盘高度：" + height);
        return height;
    }

    @TargetApi(17)
    private int getSoftButtonsBarHeight() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.mActivity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int i = displayMetrics.heightPixels;
        this.mActivity.getWindowManager().getDefaultDisplay().getRealMetrics(displayMetrics);
        int i2 = displayMetrics.heightPixels;
        if (i2 > i) {
            return i2 - i;
        }
        return 0;
    }

    public int getKeyBoardHeight() {
        return this.f5865sp.getInt(SHARE_PREFERENCE_SOFT_INPUT_HEIGHT, 787);
    }
}
