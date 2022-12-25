package com.p096sj.emoji;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Spannable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* renamed from: com.sj.emoji.EmojiDisplay */
/* loaded from: classes3.dex */
public class EmojiDisplay {
    public static final Pattern EMOJI_RANGE = Pattern.compile("[\\u20a0-\\u32ff\\ud83c\\udc00-\\ud83d\\udeff\\udbb9\\udce5-\\udbb9\\udcee]");

    public static Matcher getMatcher(CharSequence charSequence) {
        return EMOJI_RANGE.matcher(charSequence);
    }

    public static Spannable spannableFilter(Context context, Spannable spannable, CharSequence charSequence, int i) {
        spannableFilter(context, spannable, charSequence, i, null);
        return spannable;
    }

    public static Spannable spannableFilter(Context context, Spannable spannable, CharSequence charSequence, int i, EmojiDisplayListener emojiDisplayListener) {
        Matcher matcher = getMatcher(charSequence);
        if (matcher != null) {
            while (matcher.find()) {
                String hexString = Integer.toHexString(Character.codePointAt(matcher.group(), 0));
                if (emojiDisplayListener == null) {
                    emojiDisplay(context, spannable, hexString, i, matcher.start(), matcher.end());
                } else {
                    emojiDisplayListener.onEmojiDisplay(context, spannable, hexString, i, matcher.start(), matcher.end());
                }
            }
        }
        return spannable;
    }

    public static void emojiDisplay(Context context, Spannable spannable, String str, int i, int i2, int i3) {
        int i4;
        Drawable drawable = getDrawable(context, "emoji_0x" + str);
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

    public static Drawable getDrawable(Context context, String str) {
        int identifier = context.getResources().getIdentifier(str, "mipmap", context.getPackageName());
        if (identifier <= 0) {
            identifier = context.getResources().getIdentifier(str, "drawable", context.getPackageName());
        }
        try {
            if (Build.VERSION.SDK_INT >= 21) {
                return context.getResources().getDrawable(identifier, null);
            }
            return context.getResources().getDrawable(identifier);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
