package com.one.tomato.thirdpart.emotion;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.TextUtils;
import android.widget.EditText;
import com.p096sj.emoji.EmojiDisplayListener;
import com.p096sj.emoji.EmojiSpan;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import sj.keyboard.interfaces.EmoticonFilter;
import sj.keyboard.utils.EmoticonsKeyboardUtils;
import sj.keyboard.widget.EmoticonSpan;

/* loaded from: classes3.dex */
public class EmotionEditFilter extends EmoticonFilter {
    public static final int WRAP_DRAWABLE = -1;
    public static final Pattern XHS_RANGE = Pattern.compile("\\[(.*?)\\]");
    private int emoticonSize = -1;

    public static Matcher getMatcher(CharSequence charSequence) {
        return XHS_RANGE.matcher(charSequence);
    }

    @Override // sj.keyboard.interfaces.EmoticonFilter
    public void filter(EditText editText, CharSequence charSequence, int i, int i2, int i3) {
        int i4 = this.emoticonSize;
        if (i4 == -1) {
            i4 = EmoticonsKeyboardUtils.getFontHeight(editText);
        }
        this.emoticonSize = i4;
        clearSpan(editText.getText(), i, charSequence.toString().length());
        Matcher matcher = getMatcher(charSequence.toString().substring(i, charSequence.toString().length()));
        if (matcher != null) {
            while (matcher.find()) {
                String replaceAll = matcher.group().replaceAll("\\[|\\]", "");
                if (!TextUtils.isEmpty(replaceAll)) {
                    emoticonDisplay(editText.getContext(), editText.getText(), replaceAll, this.emoticonSize, i + matcher.start(), i + matcher.end());
                }
            }
        }
    }

    public static Spannable spannableFilter(Context context, Spannable spannable, CharSequence charSequence, int i, EmojiDisplayListener emojiDisplayListener) {
        Matcher matcher = getMatcher(charSequence);
        if (matcher != null) {
            while (matcher.find()) {
                String replaceAll = matcher.group().replaceAll("\\[|\\]", "");
                if (!TextUtils.isEmpty(replaceAll)) {
                    emoticonDisplay(context, spannable, replaceAll, i, matcher.start(), matcher.end());
                }
            }
        }
        return spannable;
    }

    private void clearSpan(Spannable spannable, int i, int i2) {
        if (i == i2) {
            return;
        }
        EmoticonSpan[] emoticonSpanArr = (EmoticonSpan[]) spannable.getSpans(i, i2, EmoticonSpan.class);
        for (EmoticonSpan emoticonSpan : emoticonSpanArr) {
            spannable.removeSpan(emoticonSpan);
        }
    }

    public static void emoticonDisplay(Context context, Spannable spannable, String str, int i, int i2, int i3) {
        int i4;
        Drawable drawable = EmoticonFilter.getDrawable(context, str);
        if (drawable != null) {
            if (i == -1) {
                i = drawable.getIntrinsicHeight();
                i4 = drawable.getIntrinsicWidth();
            } else {
                i4 = i;
            }
            drawable.setBounds(0, 0, i, i4);
            spannable.setSpan(new EmojiSpan(drawable), i2, i3, 17);
        }
    }
}
