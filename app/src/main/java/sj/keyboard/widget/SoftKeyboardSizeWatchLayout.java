package sj.keyboard.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class SoftKeyboardSizeWatchLayout extends RelativeLayout {
    private Context mContext;
    private List<OnResizeListener> mListenerList;
    private int mOldh = -1;
    private int mNowh = -1;
    protected int mScreenHeight = 0;
    protected boolean mIsSoftKeyboardPop = false;

    /* loaded from: classes4.dex */
    public interface OnResizeListener {
        void OnSoftClose();

        void OnSoftPop(int i);
    }

    public SoftKeyboardSizeWatchLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mContext = context;
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: sj.keyboard.widget.SoftKeyboardSizeWatchLayout.1
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                Rect rect = new Rect();
                ((Activity) SoftKeyboardSizeWatchLayout.this.mContext).getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
                SoftKeyboardSizeWatchLayout softKeyboardSizeWatchLayout = SoftKeyboardSizeWatchLayout.this;
                if (softKeyboardSizeWatchLayout.mScreenHeight == 0) {
                    softKeyboardSizeWatchLayout.mScreenHeight = rect.bottom;
                }
                SoftKeyboardSizeWatchLayout softKeyboardSizeWatchLayout2 = SoftKeyboardSizeWatchLayout.this;
                softKeyboardSizeWatchLayout2.mNowh = softKeyboardSizeWatchLayout2.mScreenHeight - rect.bottom;
                if (SoftKeyboardSizeWatchLayout.this.mOldh != -1 && SoftKeyboardSizeWatchLayout.this.mNowh != SoftKeyboardSizeWatchLayout.this.mOldh) {
                    if (SoftKeyboardSizeWatchLayout.this.mNowh > 0) {
                        SoftKeyboardSizeWatchLayout softKeyboardSizeWatchLayout3 = SoftKeyboardSizeWatchLayout.this;
                        softKeyboardSizeWatchLayout3.mIsSoftKeyboardPop = true;
                        if (softKeyboardSizeWatchLayout3.mListenerList != null) {
                            for (OnResizeListener onResizeListener : SoftKeyboardSizeWatchLayout.this.mListenerList) {
                                onResizeListener.OnSoftPop(SoftKeyboardSizeWatchLayout.this.mNowh);
                            }
                        }
                    } else {
                        SoftKeyboardSizeWatchLayout softKeyboardSizeWatchLayout4 = SoftKeyboardSizeWatchLayout.this;
                        softKeyboardSizeWatchLayout4.mIsSoftKeyboardPop = false;
                        if (softKeyboardSizeWatchLayout4.mListenerList != null) {
                            for (OnResizeListener onResizeListener2 : SoftKeyboardSizeWatchLayout.this.mListenerList) {
                                onResizeListener2.OnSoftClose();
                            }
                        }
                    }
                }
                SoftKeyboardSizeWatchLayout softKeyboardSizeWatchLayout5 = SoftKeyboardSizeWatchLayout.this;
                softKeyboardSizeWatchLayout5.mOldh = softKeyboardSizeWatchLayout5.mNowh;
            }
        });
    }

    public boolean isSoftKeyboardPop() {
        return this.mIsSoftKeyboardPop;
    }

    public void addOnResizeListener(OnResizeListener onResizeListener) {
        if (this.mListenerList == null) {
            this.mListenerList = new ArrayList();
        }
        this.mListenerList.add(onResizeListener);
    }
}
