package com.zzhoujay.markdown.parser;

import android.text.SpannableStringBuilder;

/* loaded from: classes4.dex */
public interface StyleBuilder {
    SpannableStringBuilder code(CharSequence charSequence);

    SpannableStringBuilder codeBlock(String str);

    SpannableStringBuilder delete(CharSequence charSequence);

    /* renamed from: em */
    SpannableStringBuilder mo144em(CharSequence charSequence);

    SpannableStringBuilder emItalic(CharSequence charSequence);

    SpannableStringBuilder email(CharSequence charSequence);

    SpannableStringBuilder gap();

    /* renamed from: h1 */
    SpannableStringBuilder mo142h1(CharSequence charSequence);

    /* renamed from: h2 */
    SpannableStringBuilder mo141h2(CharSequence charSequence);

    /* renamed from: h3 */
    SpannableStringBuilder mo140h3(CharSequence charSequence);

    /* renamed from: h4 */
    SpannableStringBuilder mo139h4(CharSequence charSequence);

    /* renamed from: h5 */
    SpannableStringBuilder mo138h5(CharSequence charSequence);

    /* renamed from: h6 */
    SpannableStringBuilder mo137h6(CharSequence charSequence);

    SpannableStringBuilder image(CharSequence charSequence, String str, String str2);

    SpannableStringBuilder italic(CharSequence charSequence);

    SpannableStringBuilder link(CharSequence charSequence, String str, String str2);

    /* renamed from: ol */
    SpannableStringBuilder mo136ol(CharSequence charSequence, int i, int i2);

    SpannableStringBuilder ol2(CharSequence charSequence, int i, int i2, int i3);

    SpannableStringBuilder quota(CharSequence charSequence);

    /* renamed from: ul */
    SpannableStringBuilder mo135ul(CharSequence charSequence, int i);

    SpannableStringBuilder ul2(CharSequence charSequence, int i, int i2);
}
