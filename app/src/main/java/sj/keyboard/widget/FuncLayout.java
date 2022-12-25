package sj.keyboard.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import java.util.ArrayList;
import java.util.List;
import sj.keyboard.utils.EmoticonsKeyboardUtils;

/* loaded from: classes4.dex */
public class FuncLayout extends LinearLayout {
    private List<OnFuncKeyBoardListener> mListenerList;
    private OnFuncChangeListener onFuncChangeListener;
    public final int DEF_KEY = Integer.MIN_VALUE;
    private final SparseArray<View> mFuncViewArrayMap = new SparseArray<>();
    private int mCurrentFuncKey = Integer.MIN_VALUE;
    protected int mHeight = 0;

    /* loaded from: classes4.dex */
    public interface OnFuncChangeListener {
        void onFuncChange(int i);
    }

    /* loaded from: classes4.dex */
    public interface OnFuncKeyBoardListener {
        void OnFuncClose();

        void OnFuncPop(int i);
    }

    public FuncLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setOrientation(1);
    }

    public void addFuncView(int i, View view) {
        if (this.mFuncViewArrayMap.get(i) != null) {
            return;
        }
        this.mFuncViewArrayMap.put(i, view);
        addView(view, new ViewGroup.LayoutParams(-1, -1));
        view.setVisibility(8);
    }

    public void hideAllFuncView() {
        for (int i = 0; i < this.mFuncViewArrayMap.size(); i++) {
            this.mFuncViewArrayMap.get(this.mFuncViewArrayMap.keyAt(i)).setVisibility(8);
        }
        this.mCurrentFuncKey = Integer.MIN_VALUE;
        setVisibility(false);
    }

    public void toggleFuncView(int i, boolean z, EditText editText) {
        if (getCurrentFuncKey() != i) {
            if (z) {
                EmoticonsKeyboardUtils.closeSoftKeyboard(editText);
            }
            showFuncView(i);
        } else if (z) {
            EmoticonsKeyboardUtils.closeSoftKeyboard(editText);
        } else {
            EmoticonsKeyboardUtils.openSoftKeyboard(editText);
        }
    }

    public void showFuncView(int i) {
        if (this.mFuncViewArrayMap.get(i) == null) {
            return;
        }
        for (int i2 = 0; i2 < this.mFuncViewArrayMap.size(); i2++) {
            int keyAt = this.mFuncViewArrayMap.keyAt(i2);
            if (keyAt == i) {
                this.mFuncViewArrayMap.get(keyAt).setVisibility(0);
            } else {
                this.mFuncViewArrayMap.get(keyAt).setVisibility(8);
            }
        }
        this.mCurrentFuncKey = i;
        setVisibility(true);
        OnFuncChangeListener onFuncChangeListener = this.onFuncChangeListener;
        if (onFuncChangeListener == null) {
            return;
        }
        onFuncChangeListener.onFuncChange(this.mCurrentFuncKey);
    }

    public int getCurrentFuncKey() {
        return this.mCurrentFuncKey;
    }

    public void updateHeight(int i) {
        this.mHeight = i;
    }

    public void setVisibility(boolean z) {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) getLayoutParams();
        if (z) {
            setVisibility(0);
            layoutParams.height = this.mHeight;
            List<OnFuncKeyBoardListener> list = this.mListenerList;
            if (list != null) {
                for (OnFuncKeyBoardListener onFuncKeyBoardListener : list) {
                    onFuncKeyBoardListener.OnFuncPop(this.mHeight);
                }
            }
        } else {
            setVisibility(8);
            layoutParams.height = 0;
            List<OnFuncKeyBoardListener> list2 = this.mListenerList;
            if (list2 != null) {
                for (OnFuncKeyBoardListener onFuncKeyBoardListener2 : list2) {
                    onFuncKeyBoardListener2.OnFuncClose();
                }
            }
        }
        setLayoutParams(layoutParams);
    }

    public boolean isOnlyShowSoftKeyboard() {
        return this.mCurrentFuncKey == Integer.MIN_VALUE;
    }

    public void addOnKeyBoardListener(OnFuncKeyBoardListener onFuncKeyBoardListener) {
        if (this.mListenerList == null) {
            this.mListenerList = new ArrayList();
        }
        this.mListenerList.add(onFuncKeyBoardListener);
    }

    public void setOnFuncChangeListener(OnFuncChangeListener onFuncChangeListener) {
        this.onFuncChangeListener = onFuncChangeListener;
    }
}
