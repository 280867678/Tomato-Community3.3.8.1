package com.zzhoujay.richtext.ext;

import android.graphics.Color;
import android.text.Editable;
import android.text.Html;
import android.widget.TextView;
import com.zzhoujay.markdown.style.CodeSpan;
import com.zzhoujay.markdown.style.MarkDownBulletSpan;
import java.lang.ref.SoftReference;
import java.util.Stack;
import org.xml.sax.XMLReader;

/* loaded from: classes4.dex */
public class HtmlTagHandler implements Html.TagHandler {
    private static final int code_color = Color.parseColor("#F0F0F0");
    private static final int h1_color = Color.parseColor("#333333");
    private SoftReference<TextView> textViewSoftReference;
    private int index = 0;
    private Stack<Integer> stack = new Stack<>();
    private Stack<Boolean> list = new Stack<>();

    public HtmlTagHandler(TextView textView) {
        this.textViewSoftReference = new SoftReference<>(textView);
    }

    @Override // android.text.Html.TagHandler
    public void handleTag(boolean z, String str, Editable editable, XMLReader xMLReader) {
        if (z) {
            startTag(str, editable, xMLReader);
            this.stack.push(Integer.valueOf(editable.length()));
            return;
        }
        reallyHandler(this.stack.isEmpty() ? 0 : this.stack.pop().intValue(), editable.length(), str.toLowerCase(), editable, xMLReader);
    }

    private void startTag(String str, Editable editable, XMLReader xMLReader) {
        char c;
        String lowerCase = str.toLowerCase();
        int hashCode = lowerCase.hashCode();
        if (hashCode == 3549) {
            if (lowerCase.equals("ol")) {
                c = 1;
            }
            c = 65535;
        } else if (hashCode != 3735) {
            if (hashCode == 111267 && lowerCase.equals("pre")) {
                c = 2;
            }
            c = 65535;
        } else {
            if (lowerCase.equals("ul")) {
                c = 0;
            }
            c = 65535;
        }
        if (c == 0) {
            this.list.push(true);
            editable.append('\n');
        } else if (c != 1) {
        } else {
            this.list.push(false);
            editable.append('\n');
        }
    }

    private void reallyHandler(int i, int i2, String str, Editable editable, XMLReader xMLReader) {
        char c;
        String lowerCase = str.toLowerCase();
        int hashCode = lowerCase.hashCode();
        int i3 = -1;
        if (hashCode == 3453) {
            if (lowerCase.equals("li")) {
                c = 3;
            }
            c = 65535;
        } else if (hashCode == 3549) {
            if (lowerCase.equals("ol")) {
                c = 1;
            }
            c = 65535;
        } else if (hashCode != 3735) {
            if (hashCode == 3059181 && lowerCase.equals("code")) {
                c = 0;
            }
            c = 65535;
        } else {
            if (lowerCase.equals("ul")) {
                c = 2;
            }
            c = 65535;
        }
        if (c == 0) {
            editable.setSpan(new CodeSpan(code_color), i, i2, 33);
        } else if (c == 1 || c == 2) {
            editable.append('\n');
            if (this.list.isEmpty()) {
                return;
            }
            this.list.pop();
        } else if (c != 3) {
        } else {
            if (this.list.peek().booleanValue()) {
                this.index = 0;
            } else {
                i3 = this.index + 1;
                this.index = i3;
            }
            editable.append('\n');
            TextView textView = this.textViewSoftReference.get();
            if (textView == null) {
                return;
            }
            editable.setSpan(new MarkDownBulletSpan(this.list.size() - 1, h1_color, i3, textView), i, editable.length(), 33);
        }
    }
}
