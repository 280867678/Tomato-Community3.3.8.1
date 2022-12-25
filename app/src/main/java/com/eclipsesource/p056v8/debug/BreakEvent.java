package com.eclipsesource.p056v8.debug;

import com.eclipsesource.p056v8.V8Object;

/* renamed from: com.eclipsesource.v8.debug.BreakEvent */
/* loaded from: classes2.dex */
public class BreakEvent extends EventData {
    private static final String SOURCE_COLUMN = "sourceColumn";
    private static final String SOURCE_LINE = "sourceLine";
    private static final String SOURCE_LINE_TEXT = "sourceLineText";

    /* JADX INFO: Access modifiers changed from: package-private */
    public BreakEvent(V8Object v8Object) {
        super(v8Object);
    }

    public int getSourceLine() {
        return this.v8Object.executeIntegerFunction(SOURCE_LINE, null);
    }

    public int getSourceColumn() {
        return this.v8Object.executeIntegerFunction(SOURCE_COLUMN, null);
    }

    public String getSourceLineText() {
        return this.v8Object.executeStringFunction(SOURCE_LINE_TEXT, null);
    }
}
