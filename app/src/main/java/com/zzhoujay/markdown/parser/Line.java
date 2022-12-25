package com.zzhoujay.markdown.parser;

import android.text.SpannableStringBuilder;

/* loaded from: classes4.dex */
public class Line {
    private int attr;
    private Line child;
    private int count;
    private int data;
    private int handle;
    private Line next;
    private Line parent;
    private Line prev;
    private String source;
    private CharSequence style;
    private int type;

    public Line get() {
        return this;
    }

    public Line(String str) {
        this.source = str;
        this.count = 1;
        this.type = 0;
    }

    private Line(Line line) {
        this.source = line.source;
        this.count = line.count;
        this.attr = line.attr;
        CharSequence charSequence = line.style;
        if (charSequence != null) {
            this.style = new SpannableStringBuilder(charSequence);
        }
        this.type = line.type;
    }

    public void setSource(String str) {
        this.source = str;
    }

    public String getSource() {
        return this.source;
    }

    public void setStyle(CharSequence charSequence) {
        this.style = charSequence;
    }

    public CharSequence getStyle() {
        return this.style;
    }

    public void setType(int i) {
        this.type = i;
    }

    public int getType() {
        return this.type;
    }

    public void setCount(int i) {
        this.count = i;
    }

    public int getCount() {
        return this.count;
    }

    public int getHandle() {
        return this.handle;
    }

    public void setHandle(int i) {
        this.handle = i;
    }

    public int getData() {
        return this.data;
    }

    public void setData(int i) {
        this.data = i;
    }

    public void setAttr(int i) {
        this.attr = i;
    }

    public int getAttr() {
        return this.attr;
    }

    public Line nextLine() {
        return this.next;
    }

    public Line prevLine() {
        return this.prev;
    }

    public Line childLine() {
        return this.child;
    }

    public Line parentLine() {
        return this.parent;
    }

    public Line addNext(Line line) {
        if (line == null) {
            this.next = null;
        } else {
            Line line2 = line.next;
            if (line2 != null) {
                line2.prev = null;
            }
            line.next = this.next;
            Line line3 = this.next;
            if (line3 != null) {
                line3.prev = line;
            }
            Line line4 = line.prev;
            if (line4 != null) {
                line4.next = null;
            }
            line.prev = this;
            this.next = line;
            Line line5 = this.child;
            if (line5 != null) {
                line5.addNext(line.child);
            }
        }
        return line;
    }

    public Line add(Line line) {
        addNext(line);
        return line;
    }

    private void delete() {
        Line line = this.child;
        if (line != null) {
            line.delete();
        }
        Line line2 = this.prev;
        if (line2 != null) {
            line2.next = null;
        }
        this.prev = null;
        Line line3 = this.next;
        if (line3 != null) {
            line3.prev = null;
        }
        this.next = null;
    }

    private void reduce() {
        Line line = this.child;
        if (line != null) {
            line.reduce();
        }
        Line line2 = this.prev;
        if (line2 != null) {
            line2.next = this.next;
        }
        Line line3 = this.next;
        if (line3 != null) {
            line3.prev = this.prev;
        }
        this.next = null;
        this.prev = null;
    }

    public void remove() {
        if (this.parent == null) {
            reduce();
        } else {
            delete();
        }
    }

    public Line removeNext() {
        Line line = this.next;
        if (line != null) {
            line.remove();
        }
        return this;
    }

    public Line removePrev() {
        Line line = this.prev;
        if (line != null) {
            line.remove();
        }
        return this;
    }

    public void addChild(Line line) {
        Line line2 = this.child;
        if (line2 != null) {
            line2.parent = null;
        }
        this.child = line;
        Line line3 = line.parent;
        if (line3 != null) {
            line3.child = null;
        }
        line.parent = this;
        attachChildToNext();
        attachChildToPrev();
    }

    public void attachChildToNext() {
        Line line = this.child;
        if (line == null || this.next == null) {
            return;
        }
        Line line2 = line.next;
        if (line2 != null) {
            line2.prev = null;
        }
        this.child.next = this.next.child;
        Line line3 = this.next.child;
        if (line3 != null) {
            Line line4 = line3.prev;
            if (line4 != null) {
                line4.next = null;
            }
            this.next.child.prev = this.child;
        }
        this.child.attachChildToNext();
    }

    public void attachChildToPrev() {
        Line line = this.child;
        if (line == null || this.prev == null) {
            return;
        }
        Line line2 = line.prev;
        if (line2 != null) {
            line2.next = null;
        }
        this.child.prev = this.prev.child;
        Line line3 = this.prev.child;
        if (line3 != null) {
            Line line4 = line3.next;
            if (line4 != null) {
                line4.prev = null;
            }
            this.prev.child.next = this.child;
        }
        this.child.attachChildToPrev();
    }

    public void unAttachFromParent() {
        if (this.parent != null) {
            delete();
            this.parent.child = null;
        }
        this.parent = null;
    }

    public Line createChild(String str) {
        Line line = new Line(str);
        addChild(line);
        return line;
    }

    public Line copyToNext() {
        Line line = this.parent;
        Line copyToNext = line != null ? line.copyToNext() : null;
        Line line2 = new Line(this);
        if (copyToNext == null) {
            line2.next = this.next;
            Line line3 = this.next;
            if (line3 != null) {
                line3.prev = line2;
            }
            line2.prev = this;
            this.next = line2;
        } else {
            copyToNext.addChild(line2);
        }
        return line2;
    }

    public String toString() {
        return this.source;
    }
}
