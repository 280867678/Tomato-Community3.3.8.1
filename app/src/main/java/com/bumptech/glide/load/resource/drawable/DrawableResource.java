package com.bumptech.glide.load.resource.drawable;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import com.bumptech.glide.load.engine.Initializable;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.util.Preconditions;

/* loaded from: classes2.dex */
public abstract class DrawableResource<T extends Drawable> implements Resource<T>, Initializable {
    protected final T drawable;

    public DrawableResource(T t) {
        this.drawable = (T) Preconditions.checkNotNull(t);
    }

    @Override // com.bumptech.glide.load.engine.Resource
    @NonNull
    /* renamed from: get */
    public final T mo5902get() {
        Drawable.ConstantState constantState = this.drawable.getConstantState();
        if (constantState == null) {
            return this.drawable;
        }
        return (T) constantState.newDrawable();
    }

    public void initialize() {
        T t = this.drawable;
        if (t instanceof BitmapDrawable) {
            ((BitmapDrawable) t).getBitmap().prepareToDraw();
        } else if (!(t instanceof GifDrawable)) {
        } else {
            ((GifDrawable) t).getFirstFrame().prepareToDraw();
        }
    }
}
