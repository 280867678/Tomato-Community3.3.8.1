package com.zzhoujay.markdown.parser;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.StrikethroughSpan;
import android.widget.TextView;
import com.zzhoujay.markdown.style.CodeBlockSpan;
import com.zzhoujay.markdown.style.CodeSpan;
import com.zzhoujay.markdown.style.EmailSpan;
import com.zzhoujay.markdown.style.FontSpan;
import com.zzhoujay.markdown.style.LinkSpan;
import com.zzhoujay.markdown.style.MarkDownBulletSpan;
import com.zzhoujay.markdown.style.MarkDownQuoteSpan;
import com.zzhoujay.markdown.style.QuotaBulletSpan;
import com.zzhoujay.markdown.style.UnderLineSpan;
import java.lang.ref.WeakReference;

/* loaded from: classes4.dex */
public class StyleBuilderImpl implements StyleBuilder {
    private Html.ImageGetter imageGetter;
    private WeakReference<TextView> textViewWeakReference;
    private static final int h1_color = Color.parseColor("#333333");
    private static final int h6_color = Color.parseColor("#777777");
    private static final int quota_color = Color.parseColor("#DDDDDD");
    private static final int code_color = Color.parseColor("#F0F0F0");
    private static final int link_color = Color.parseColor("#4078C0");
    private static final int h_under_line_color = Color.parseColor("#eeeeee");

    public StyleBuilderImpl(TextView textView, Html.ImageGetter imageGetter) {
        this.textViewWeakReference = new WeakReference<>(textView);
        this.imageGetter = imageGetter;
    }

    @Override // com.zzhoujay.markdown.parser.StyleBuilder
    /* renamed from: em */
    public SpannableStringBuilder mo144em(CharSequence charSequence) {
        SpannableStringBuilder valueOf = SpannableStringBuilder.valueOf(charSequence);
        valueOf.setSpan(new FontSpan(1.0f, 1, h1_color), 0, charSequence.length(), 33);
        return valueOf;
    }

    @Override // com.zzhoujay.markdown.parser.StyleBuilder
    public SpannableStringBuilder italic(CharSequence charSequence) {
        SpannableStringBuilder valueOf = SpannableStringBuilder.valueOf(charSequence);
        valueOf.setSpan(new FontSpan(1.0f, 2, h1_color), 0, charSequence.length(), 33);
        return valueOf;
    }

    @Override // com.zzhoujay.markdown.parser.StyleBuilder
    public SpannableStringBuilder emItalic(CharSequence charSequence) {
        SpannableStringBuilder valueOf = SpannableStringBuilder.valueOf(charSequence);
        valueOf.setSpan(new FontSpan(1.0f, 3, h1_color), 0, charSequence.length(), 33);
        return valueOf;
    }

    @Override // com.zzhoujay.markdown.parser.StyleBuilder
    public SpannableStringBuilder delete(CharSequence charSequence) {
        SpannableStringBuilder valueOf = SpannableStringBuilder.valueOf(charSequence);
        StrikethroughSpan strikethroughSpan = new StrikethroughSpan();
        valueOf.setSpan(new ForegroundColorSpan(h1_color), 0, charSequence.length(), 33);
        valueOf.setSpan(strikethroughSpan, 0, charSequence.length(), 33);
        return valueOf;
    }

    @Override // com.zzhoujay.markdown.parser.StyleBuilder
    public SpannableStringBuilder email(CharSequence charSequence) {
        SpannableStringBuilder valueOf = SpannableStringBuilder.valueOf(charSequence);
        valueOf.setSpan(new EmailSpan(charSequence.toString(), link_color), 0, charSequence.length(), 33);
        return valueOf;
    }

    @Override // com.zzhoujay.markdown.parser.StyleBuilder
    public SpannableStringBuilder code(CharSequence charSequence) {
        SpannableStringBuilder valueOf = SpannableStringBuilder.valueOf(charSequence);
        valueOf.setSpan(new CodeSpan(code_color), 0, charSequence.length(), 33);
        return valueOf;
    }

    @Override // com.zzhoujay.markdown.parser.StyleBuilder
    /* renamed from: h1 */
    public SpannableStringBuilder mo142h1(CharSequence charSequence) {
        return hWithUnderLine(charSequence, 2.25f);
    }

    @Override // com.zzhoujay.markdown.parser.StyleBuilder
    /* renamed from: h2 */
    public SpannableStringBuilder mo141h2(CharSequence charSequence) {
        return hWithUnderLine(charSequence, 1.75f);
    }

    @Override // com.zzhoujay.markdown.parser.StyleBuilder
    /* renamed from: h3 */
    public SpannableStringBuilder mo140h3(CharSequence charSequence) {
        return m143h(charSequence, 1.5f, h1_color);
    }

    @Override // com.zzhoujay.markdown.parser.StyleBuilder
    /* renamed from: h4 */
    public SpannableStringBuilder mo139h4(CharSequence charSequence) {
        return m143h(charSequence, 1.25f, h1_color);
    }

    @Override // com.zzhoujay.markdown.parser.StyleBuilder
    /* renamed from: h5 */
    public SpannableStringBuilder mo138h5(CharSequence charSequence) {
        return m143h(charSequence, 1.0f, h1_color);
    }

    @Override // com.zzhoujay.markdown.parser.StyleBuilder
    /* renamed from: h6 */
    public SpannableStringBuilder mo137h6(CharSequence charSequence) {
        return m143h(charSequence, 1.0f, h6_color);
    }

    @Override // com.zzhoujay.markdown.parser.StyleBuilder
    public SpannableStringBuilder quota(CharSequence charSequence) {
        SpannableStringBuilder valueOf = SpannableStringBuilder.valueOf(charSequence);
        valueOf.setSpan(new MarkDownQuoteSpan(quota_color), 0, valueOf.length(), 33);
        return valueOf;
    }

    @Override // com.zzhoujay.markdown.parser.StyleBuilder
    /* renamed from: ul */
    public SpannableStringBuilder mo135ul(CharSequence charSequence, int i) {
        SpannableStringBuilder valueOf = SpannableStringBuilder.valueOf(charSequence);
        valueOf.setSpan(new MarkDownBulletSpan(i, h1_color, 0), 0, valueOf.length(), 33);
        return valueOf;
    }

    @Override // com.zzhoujay.markdown.parser.StyleBuilder
    /* renamed from: ol */
    public SpannableStringBuilder mo136ol(CharSequence charSequence, int i, int i2) {
        SpannableStringBuilder valueOf = SpannableStringBuilder.valueOf(charSequence);
        valueOf.setSpan(new MarkDownBulletSpan(i, h1_color, i2, this.textViewWeakReference.get()), 0, valueOf.length(), 33);
        return valueOf;
    }

    @Override // com.zzhoujay.markdown.parser.StyleBuilder
    public SpannableStringBuilder ul2(CharSequence charSequence, int i, int i2) {
        SpannableStringBuilder valueOf = SpannableStringBuilder.valueOf(charSequence);
        valueOf.setSpan(new QuotaBulletSpan(i, i2, quota_color, h1_color, 0, this.textViewWeakReference.get()), 0, valueOf.length(), 33);
        return valueOf;
    }

    @Override // com.zzhoujay.markdown.parser.StyleBuilder
    public SpannableStringBuilder ol2(CharSequence charSequence, int i, int i2, int i3) {
        SpannableStringBuilder valueOf = SpannableStringBuilder.valueOf(charSequence);
        valueOf.setSpan(new QuotaBulletSpan(i, i2, quota_color, h1_color, i3, this.textViewWeakReference.get()), 0, valueOf.length(), 33);
        return valueOf;
    }

    public SpannableStringBuilder codeBlock(CharSequence... charSequenceArr) {
        SpannableStringBuilder valueOf = SpannableStringBuilder.valueOf("$");
        valueOf.setSpan(new CodeBlockSpan(getTextViewRealWidth(), code_color, charSequenceArr), 0, valueOf.length(), 33);
        return valueOf;
    }

    @Override // com.zzhoujay.markdown.parser.StyleBuilder
    public SpannableStringBuilder codeBlock(String str) {
        return codeBlock(str.split("\n"));
    }

    @Override // com.zzhoujay.markdown.parser.StyleBuilder
    public SpannableStringBuilder link(CharSequence charSequence, String str, String str2) {
        SpannableStringBuilder valueOf = SpannableStringBuilder.valueOf(charSequence);
        valueOf.setSpan(new LinkSpan(str, link_color), 0, charSequence.length(), 33);
        return valueOf;
    }

    @Override // com.zzhoujay.markdown.parser.StyleBuilder
    public SpannableStringBuilder image(CharSequence charSequence, String str, String str2) {
        if (charSequence == null || charSequence.length() == 0) {
            charSequence = "$";
        }
        SpannableStringBuilder valueOf = SpannableStringBuilder.valueOf(charSequence);
        Drawable drawable = null;
        Html.ImageGetter imageGetter = this.imageGetter;
        if (imageGetter != null) {
            drawable = imageGetter.getDrawable(str);
        }
        if (drawable == null) {
            drawable = new ColorDrawable(0);
        }
        valueOf.setSpan(new ImageSpan(drawable, str), 0, valueOf.length(), 33);
        return valueOf;
    }

    /* renamed from: h */
    protected SpannableStringBuilder m143h(CharSequence charSequence, float f, int i) {
        SpannableStringBuilder valueOf = SpannableStringBuilder.valueOf(charSequence);
        valueOf.setSpan(new FontSpan(f, 1, i), 0, valueOf.length(), 33);
        return valueOf;
    }

    private SpannableStringBuilder hWithUnderLine(CharSequence charSequence, float f) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(charSequence);
        spannableStringBuilder.setSpan(new FontSpan(f, 1, h1_color), 0, spannableStringBuilder.length(), 33);
        UnderLineSpan underLineSpan = new UnderLineSpan(new ColorDrawable(h_under_line_color), getTextViewRealWidth(), 5);
        spannableStringBuilder.append('\n');
        spannableStringBuilder.append((CharSequence) "$");
        spannableStringBuilder.setSpan(underLineSpan, charSequence.length() + 1 + 0, spannableStringBuilder.length(), 33);
        return spannableStringBuilder;
    }

    private int getTextViewRealWidth() {
        TextView textView = this.textViewWeakReference.get();
        if (textView != null) {
            return (textView.getWidth() - textView.getPaddingRight()) - textView.getPaddingLeft();
        }
        return 0;
    }

    @Override // com.zzhoujay.markdown.parser.StyleBuilder
    public SpannableStringBuilder gap() {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder("$");
        spannableStringBuilder.setSpan(new UnderLineSpan(new ColorDrawable(h_under_line_color), getTextViewRealWidth(), 10), 0, spannableStringBuilder.length(), 33);
        return spannableStringBuilder;
    }
}
