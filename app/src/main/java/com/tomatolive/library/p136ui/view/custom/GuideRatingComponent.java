package com.tomatolive.library.p136ui.view.custom;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.p136ui.view.widget.guideview.Component;

/* renamed from: com.tomatolive.library.ui.view.custom.GuideRatingComponent */
/* loaded from: classes3.dex */
public class GuideRatingComponent implements Component {
    @Override // com.tomatolive.library.p136ui.view.widget.guideview.Component
    public int getAnchor() {
        return 4;
    }

    @Override // com.tomatolive.library.p136ui.view.widget.guideview.Component
    public int getFitPosition() {
        return 48;
    }

    @Override // com.tomatolive.library.p136ui.view.widget.guideview.Component
    public int getXOffset() {
        return -10;
    }

    @Override // com.tomatolive.library.p136ui.view.widget.guideview.Component
    public int getYOffset() {
        return 20;
    }

    @Override // com.tomatolive.library.p136ui.view.widget.guideview.Component
    public View getView(LayoutInflater layoutInflater) {
        return layoutInflater.inflate(R$layout.fq_view_guide_rate, (ViewGroup) null);
    }
}
