package com.one.tomato.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;
import com.eclipsesource.p056v8.Platform;
import com.one.tomato.thirdpart.emotion.EmotionEditFilter;
import com.p096sj.emoji.EmojiDisplay;
import sj.keyboard.utils.EmoticonsKeyboardUtils;

/* loaded from: classes3.dex */
public class ViewUtil {

    /* loaded from: classes3.dex */
    public interface OnResizeListener {
        void OnSoftClose();

        void OnSoftPop(int i);
    }

    public static void initTextViewWithSpannableString(TextView textView, String[] strArr, String[] strArr2, String[] strArr3) {
        textView.setText("");
        SpannableString[] spannableString = getSpannableString(strArr, strArr2, strArr3);
        if (spannableString == null) {
            return;
        }
        for (SpannableString spannableString2 : spannableString) {
            textView.append(spannableString2);
        }
    }

    public static void initTextViewWithSpannableEmotionString(TextView textView, String str, String[] strArr, String[] strArr2, String[] strArr3) {
        textView.setText("");
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        SpannableString[] spannableString = getSpannableString(strArr, strArr2, strArr3);
        if (spannableString == null) {
            return;
        }
        for (SpannableString spannableString2 : spannableString) {
            spannableStringBuilder.append((CharSequence) spannableString2);
        }
        SpannableStringBuilder spannableStringBuilder2 = new SpannableStringBuilder(str);
        EmojiDisplay.spannableFilter(textView.getContext(), spannableStringBuilder2, str, EmoticonsKeyboardUtils.getFontHeight(textView));
        spannableStringBuilder.append((CharSequence) EmotionEditFilter.spannableFilter(textView.getContext(), spannableStringBuilder2, str, EmoticonsKeyboardUtils.getFontHeight(textView), null));
        textView.setText(spannableStringBuilder);
    }

    public static SpannableString[] getSpannableString(String[] strArr, String[] strArr2, String[] strArr3) {
        if (strArr.length != 0 && strArr3.length == strArr.length && strArr2.length == strArr.length) {
            SpannableString[] spannableStringArr = new SpannableString[strArr.length];
            for (int i = 0; i < strArr.length; i++) {
                spannableStringArr[i] = new SpannableString(strArr[i]);
                ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Integer.valueOf(strArr2[i]).intValue());
                AbsoluteSizeSpan absoluteSizeSpan = new AbsoluteSizeSpan((int) (DisplayMetricsUtils.getDensity() * Integer.valueOf(strArr3[i]).intValue()));
                spannableStringArr[i].setSpan(foregroundColorSpan, 0, spannableStringArr[i].length(), 33);
                spannableStringArr[i].setSpan(absoluteSizeSpan, 0, spannableStringArr[i].length(), 33);
            }
            return spannableStringArr;
        }
        return null;
    }

    public static Typeface getNumFontTypeface(Context context) {
        try {
            return Typeface.createFromAsset(context.getAssets(), "number.ttf");
        } catch (Exception unused) {
            return Typeface.defaultFromStyle(0);
        }
    }

    public static void controlKeyboardLayout(final View view, final View view2, final OnResizeListener onResizeListener) {
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.one.tomato.utils.ViewUtil.1

            /* renamed from: r */
            private Rect f1759r = new Rect();
            private int oldh = -1;
            private int nowh = -1;

            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                int i;
                Context context = view.getContext();
                int identifier = context.getResources().getIdentifier("status_bar_height", "dimen", Platform.ANDROID);
                int dimensionPixelSize = identifier > 0 ? context.getResources().getDimensionPixelSize(identifier) : -1;
                Log.e("onGlobalLayout", "状态栏高度:" + dimensionPixelSize);
                Activity activity = (Activity) context;
                activity.getWindow().getDecorView().findViewById(16908290).getWindowVisibleDisplayFrame(this.f1759r);
                int height = activity.getWindow().getDecorView().findViewById(16908290).getHeight();
                activity.getWindow().getDecorView().getRootView().getHeight();
                Rect rect = this.f1759r;
                int i2 = (height - rect.bottom) + rect.top;
                Log.e("onGlobalLayout", "键盘高度 :" + i2);
                this.nowh = i2;
                int i3 = this.oldh;
                if (i3 != -1 && (i = this.nowh) != i3) {
                    if (i > 0) {
                        if (onResizeListener != null) {
                            int bottom = height - view2.getBottom();
                            int i4 = 0;
                            int i5 = this.nowh;
                            if (bottom < i5) {
                                i4 = i5 - bottom;
                            }
                            onResizeListener.OnSoftPop(i4);
                        }
                    } else {
                        OnResizeListener onResizeListener2 = onResizeListener;
                        if (onResizeListener2 != null) {
                            onResizeListener2.OnSoftClose();
                        }
                    }
                }
                this.oldh = this.nowh;
            }
        });
    }
}
