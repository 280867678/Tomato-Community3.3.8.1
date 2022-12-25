package com.gen.p059mh.webapp_extensions.matisse.internal.entity;

import android.support.annotation.StyleRes;
import com.gen.p059mh.webapp_extensions.R$style;
import com.gen.p059mh.webapp_extensions.matisse.MimeType;
import com.gen.p059mh.webapp_extensions.matisse.engine.ImageEngine;
import com.gen.p059mh.webapp_extensions.matisse.engine.impl.GlideEngine;
import com.gen.p059mh.webapp_extensions.matisse.filter.Filter;
import com.gen.p059mh.webapp_extensions.matisse.listener.OnCheckedListener;
import com.gen.p059mh.webapp_extensions.matisse.listener.OnSelectedListener;
import java.util.List;
import java.util.Set;

/* renamed from: com.gen.mh.webapp_extensions.matisse.internal.entity.SelectionSpec */
/* loaded from: classes2.dex */
public final class SelectionSpec {
    public boolean autoHideToobar;
    public boolean capture;
    public CaptureStrategy captureStrategy;
    public boolean countable;
    public List<Filter> filters;
    public int gridExpectedSize;
    public boolean hasInited;
    public ImageEngine imageEngine;
    public int maxImageSelectable;
    public int maxSelectable;
    public int maxVideoSelectable;
    public boolean mediaTypeExclusive;
    public Set<MimeType> mimeTypeSet;
    public OnCheckedListener onCheckedListener;
    public OnSelectedListener onSelectedListener;
    public int orientation;
    public int originalMaxSize;
    public boolean originalable;
    public boolean showSingleMediaType;
    public int spanCount;
    @StyleRes
    public int themeId;
    public float thumbnailScale;

    private SelectionSpec() {
    }

    public static SelectionSpec getInstance() {
        return InstanceHolder.INSTANCE;
    }

    public static SelectionSpec getCleanInstance() {
        SelectionSpec selectionSpec = getInstance();
        selectionSpec.reset();
        return selectionSpec;
    }

    private void reset() {
        this.mimeTypeSet = null;
        this.mediaTypeExclusive = true;
        this.showSingleMediaType = false;
        this.themeId = R$style.Web_sdk_Matisse_Zhihu;
        this.orientation = 0;
        this.countable = false;
        this.maxSelectable = 1;
        this.maxImageSelectable = 0;
        this.maxVideoSelectable = 0;
        this.filters = null;
        this.capture = false;
        this.captureStrategy = null;
        this.spanCount = 3;
        this.gridExpectedSize = 0;
        this.thumbnailScale = 0.5f;
        if (this.imageEngine == null) {
            this.imageEngine = new GlideEngine();
        }
        this.hasInited = true;
        this.originalable = false;
        this.autoHideToobar = false;
        this.originalMaxSize = Integer.MAX_VALUE;
    }

    public boolean singleSelectionModeEnabled() {
        if (!this.countable) {
            if (this.maxSelectable == 1) {
                return true;
            }
            if (this.maxImageSelectable == 1 && this.maxVideoSelectable == 1) {
                return true;
            }
        }
        return false;
    }

    public boolean needOrientationRestriction() {
        return this.orientation != -1;
    }

    public boolean onlyShowImages() {
        return this.showSingleMediaType && MimeType.ofImage().containsAll(this.mimeTypeSet);
    }

    public boolean onlyShowVideos() {
        return this.showSingleMediaType && MimeType.ofVideo().containsAll(this.mimeTypeSet);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: com.gen.mh.webapp_extensions.matisse.internal.entity.SelectionSpec$InstanceHolder */
    /* loaded from: classes2.dex */
    public static final class InstanceHolder {
        private static final SelectionSpec INSTANCE = new SelectionSpec();

        private InstanceHolder() {
        }
    }
}
