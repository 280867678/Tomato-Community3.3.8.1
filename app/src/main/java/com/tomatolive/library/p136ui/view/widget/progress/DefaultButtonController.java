package com.tomatolive.library.p136ui.view.widget.progress;

import android.graphics.Color;

/* renamed from: com.tomatolive.library.ui.view.widget.progress.DefaultButtonController */
/* loaded from: classes4.dex */
public class DefaultButtonController implements ButtonController {
    private boolean enableGradient;
    private boolean enablePress;

    @Override // com.tomatolive.library.p136ui.view.widget.progress.ButtonController
    public int getPressedColor(int i) {
        Color.colorToHSV(i, r0);
        float[] fArr = {0.0f, 0.0f, fArr[2] - 0.1f};
        return Color.HSVToColor(fArr);
    }

    @Override // com.tomatolive.library.p136ui.view.widget.progress.ButtonController
    public int getLighterColor(int i) {
        Color.colorToHSV(i, r0);
        float[] fArr = {0.0f, fArr[1] - 0.3f, fArr[2] + 0.3f};
        return Color.HSVToColor(fArr);
    }

    @Override // com.tomatolive.library.p136ui.view.widget.progress.ButtonController
    public int getDarkerColor(int i) {
        Color.colorToHSV(i, r0);
        float[] fArr = {0.0f, fArr[1] + 0.3f, fArr[2] - 0.3f};
        return Color.HSVToColor(fArr);
    }

    @Override // com.tomatolive.library.p136ui.view.widget.progress.ButtonController
    public boolean enablePress() {
        return this.enablePress;
    }

    @Override // com.tomatolive.library.p136ui.view.widget.progress.ButtonController
    public boolean enableGradient() {
        return this.enableGradient;
    }

    public DefaultButtonController setEnablePress(boolean z) {
        this.enablePress = z;
        return this;
    }

    public DefaultButtonController setEnableGradient(boolean z) {
        this.enableGradient = z;
        return this;
    }
}
