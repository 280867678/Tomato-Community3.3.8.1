package com.tomatolive.library.p136ui.view.widget.guideview;

/* renamed from: com.tomatolive.library.ui.view.widget.guideview.BuildException */
/* loaded from: classes4.dex */
public class BuildException extends RuntimeException {
    private static final long serialVersionUID = 6208777692136933357L;
    private final String mDetailMessage;

    public BuildException() {
        this.mDetailMessage = "General error.";
    }

    public BuildException(String str) {
        this.mDetailMessage = str;
    }

    @Override // java.lang.Throwable
    public String getMessage() {
        return "Build GuideFragment failed: " + this.mDetailMessage;
    }
}
