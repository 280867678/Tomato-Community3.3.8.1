package com.gen.p059mh.webapp_extensions.matisse;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.StyleRes;
import android.support.p002v4.app.Fragment;
import com.gen.p059mh.webapp_extensions.matisse.engine.ImageEngine;
import com.gen.p059mh.webapp_extensions.matisse.filter.Filter;
import com.gen.p059mh.webapp_extensions.matisse.internal.entity.CaptureStrategy;
import com.gen.p059mh.webapp_extensions.matisse.internal.entity.SelectionSpec;
import com.gen.p059mh.webapp_extensions.matisse.listener.OnCheckedListener;
import com.gen.p059mh.webapp_extensions.matisse.listener.OnSelectedListener;
import com.gen.p059mh.webapp_extensions.matisse.p061ui.WebSdkMatisseActivity;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Set;

/* renamed from: com.gen.mh.webapp_extensions.matisse.SelectionCreator */
/* loaded from: classes2.dex */
public final class SelectionCreator {
    private final Matisse mMatisse;
    private final SelectionSpec mSelectionSpec = SelectionSpec.getCleanInstance();

    @RequiresApi(api = 18)
    @Retention(RetentionPolicy.SOURCE)
    /* renamed from: com.gen.mh.webapp_extensions.matisse.SelectionCreator$ScreenOrientation */
    /* loaded from: classes2.dex */
    @interface ScreenOrientation {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public SelectionCreator(Matisse matisse, @NonNull Set<MimeType> set, boolean z) {
        this.mMatisse = matisse;
        SelectionSpec selectionSpec = this.mSelectionSpec;
        selectionSpec.mimeTypeSet = set;
        selectionSpec.mediaTypeExclusive = z;
        selectionSpec.orientation = -1;
    }

    public SelectionCreator showSingleMediaType(boolean z) {
        this.mSelectionSpec.showSingleMediaType = z;
        return this;
    }

    public SelectionCreator theme(@StyleRes int i) {
        this.mSelectionSpec.themeId = i;
        return this;
    }

    public SelectionCreator countable(boolean z) {
        this.mSelectionSpec.countable = z;
        return this;
    }

    public SelectionCreator maxSelectable(int i) {
        if (i < 1) {
            throw new IllegalArgumentException("maxSelectable must be greater than or equal to one");
        }
        SelectionSpec selectionSpec = this.mSelectionSpec;
        if (selectionSpec.maxImageSelectable > 0 || selectionSpec.maxVideoSelectable > 0) {
            throw new IllegalStateException("already set maxImageSelectable and maxVideoSelectable");
        }
        selectionSpec.maxSelectable = i;
        return this;
    }

    public SelectionCreator maxSelectablePerMediaType(int i, int i2) {
        if (i < 1 || i2 < 1) {
            throw new IllegalArgumentException("max selectable must be greater than or equal to one");
        }
        SelectionSpec selectionSpec = this.mSelectionSpec;
        selectionSpec.maxSelectable = -1;
        selectionSpec.maxImageSelectable = i;
        selectionSpec.maxVideoSelectable = i2;
        return this;
    }

    public SelectionCreator addFilter(@NonNull Filter filter) {
        SelectionSpec selectionSpec = this.mSelectionSpec;
        if (selectionSpec.filters == null) {
            selectionSpec.filters = new ArrayList();
        }
        if (filter == null) {
            throw new IllegalArgumentException("filter cannot be null");
        }
        this.mSelectionSpec.filters.add(filter);
        return this;
    }

    public SelectionCreator capture(boolean z) {
        this.mSelectionSpec.capture = z;
        return this;
    }

    public SelectionCreator originalEnable(boolean z) {
        this.mSelectionSpec.originalable = z;
        return this;
    }

    public SelectionCreator autoHideToolbarOnSingleTap(boolean z) {
        this.mSelectionSpec.autoHideToobar = z;
        return this;
    }

    public SelectionCreator maxOriginalSize(int i) {
        this.mSelectionSpec.originalMaxSize = i;
        return this;
    }

    public SelectionCreator captureStrategy(CaptureStrategy captureStrategy) {
        this.mSelectionSpec.captureStrategy = captureStrategy;
        return this;
    }

    public SelectionCreator restrictOrientation(int i) {
        this.mSelectionSpec.orientation = i;
        return this;
    }

    public SelectionCreator spanCount(int i) {
        if (i < 1) {
            throw new IllegalArgumentException("spanCount cannot be less than 1");
        }
        this.mSelectionSpec.spanCount = i;
        return this;
    }

    public SelectionCreator gridExpectedSize(int i) {
        this.mSelectionSpec.gridExpectedSize = i;
        return this;
    }

    public SelectionCreator thumbnailScale(float f) {
        if (f <= 0.0f || f > 1.0f) {
            throw new IllegalArgumentException("Thumbnail scale must be between (0.0, 1.0]");
        }
        this.mSelectionSpec.thumbnailScale = f;
        return this;
    }

    public SelectionCreator imageEngine(ImageEngine imageEngine) {
        this.mSelectionSpec.imageEngine = imageEngine;
        return this;
    }

    @NonNull
    public SelectionCreator setOnSelectedListener(@Nullable OnSelectedListener onSelectedListener) {
        this.mSelectionSpec.onSelectedListener = onSelectedListener;
        return this;
    }

    public SelectionCreator setOnCheckedListener(@Nullable OnCheckedListener onCheckedListener) {
        this.mSelectionSpec.onCheckedListener = onCheckedListener;
        return this;
    }

    public void forResult(int i, String str) {
        Activity activity = this.mMatisse.getActivity();
        if (activity == null) {
            return;
        }
        Intent intent = new Intent(activity, WebSdkMatisseActivity.class);
        intent.putExtra(WebSdkMatisseActivity.PHOTO_PATH, str);
        Fragment fragment = this.mMatisse.getFragment();
        if (fragment != null) {
            fragment.startActivityForResult(intent, i);
        } else {
            activity.startActivityForResult(intent, i);
        }
    }
}
