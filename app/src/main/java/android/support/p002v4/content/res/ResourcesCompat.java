package android.support.p002v4.content.res;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.FontRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.p002v4.content.res.FontResourcesParserCompat;
import android.support.p002v4.graphics.TypefaceCompat;
import android.support.p002v4.util.Preconditions;
import android.util.Log;
import android.util.TypedValue;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;

/* renamed from: android.support.v4.content.res.ResourcesCompat */
/* loaded from: classes2.dex */
public final class ResourcesCompat {
    private static final String TAG = "ResourcesCompat";

    @Nullable
    public static Drawable getDrawable(@NonNull Resources resources, @DrawableRes int i, @Nullable Resources.Theme theme) throws Resources.NotFoundException {
        if (Build.VERSION.SDK_INT >= 21) {
            return resources.getDrawable(i, theme);
        }
        return resources.getDrawable(i);
    }

    @Nullable
    public static Drawable getDrawableForDensity(@NonNull Resources resources, @DrawableRes int i, int i2, @Nullable Resources.Theme theme) throws Resources.NotFoundException {
        int i3 = Build.VERSION.SDK_INT;
        if (i3 >= 21) {
            return resources.getDrawableForDensity(i, i2, theme);
        }
        if (i3 >= 15) {
            return resources.getDrawableForDensity(i, i2);
        }
        return resources.getDrawable(i);
    }

    @ColorInt
    public static int getColor(@NonNull Resources resources, @ColorRes int i, @Nullable Resources.Theme theme) throws Resources.NotFoundException {
        if (Build.VERSION.SDK_INT >= 23) {
            return resources.getColor(i, theme);
        }
        return resources.getColor(i);
    }

    @Nullable
    public static ColorStateList getColorStateList(@NonNull Resources resources, @ColorRes int i, @Nullable Resources.Theme theme) throws Resources.NotFoundException {
        if (Build.VERSION.SDK_INT >= 23) {
            return resources.getColorStateList(i, theme);
        }
        return resources.getColorStateList(i);
    }

    @Nullable
    public static Typeface getFont(@NonNull Context context, @FontRes int i) throws Resources.NotFoundException {
        if (context.isRestricted()) {
            return null;
        }
        return loadFont(context, i, new TypedValue(), 0, null, null, false);
    }

    /* renamed from: android.support.v4.content.res.ResourcesCompat$FontCallback */
    /* loaded from: classes2.dex */
    public static abstract class FontCallback {
        public abstract void onFontRetrievalFailed(int i);

        public abstract void onFontRetrieved(@NonNull Typeface typeface);

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public final void callbackSuccessAsync(final Typeface typeface, @Nullable Handler handler) {
            if (handler == null) {
                handler = new Handler(Looper.getMainLooper());
            }
            handler.post(new Runnable() { // from class: android.support.v4.content.res.ResourcesCompat.FontCallback.1
                @Override // java.lang.Runnable
                public void run() {
                    FontCallback.this.onFontRetrieved(typeface);
                }
            });
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public final void callbackFailAsync(final int i, @Nullable Handler handler) {
            if (handler == null) {
                handler = new Handler(Looper.getMainLooper());
            }
            handler.post(new Runnable() { // from class: android.support.v4.content.res.ResourcesCompat.FontCallback.2
                @Override // java.lang.Runnable
                public void run() {
                    FontCallback.this.onFontRetrievalFailed(i);
                }
            });
        }
    }

    public static void getFont(@NonNull Context context, @FontRes int i, @NonNull FontCallback fontCallback, @Nullable Handler handler) throws Resources.NotFoundException {
        Preconditions.checkNotNull(fontCallback);
        if (context.isRestricted()) {
            fontCallback.callbackFailAsync(-4, handler);
        } else {
            loadFont(context, i, new TypedValue(), 0, fontCallback, handler, false);
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static Typeface getFont(@NonNull Context context, @FontRes int i, TypedValue typedValue, int i2, @Nullable FontCallback fontCallback) throws Resources.NotFoundException {
        if (context.isRestricted()) {
            return null;
        }
        return loadFont(context, i, typedValue, i2, fontCallback, null, true);
    }

    private static Typeface loadFont(@NonNull Context context, int i, TypedValue typedValue, int i2, @Nullable FontCallback fontCallback, @Nullable Handler handler, boolean z) {
        Resources resources = context.getResources();
        resources.getValue(i, typedValue, true);
        Typeface loadFont = loadFont(context, resources, typedValue, i, i2, fontCallback, handler, z);
        if (loadFont == null && fontCallback == null) {
            throw new Resources.NotFoundException("Font resource ID #0x" + Integer.toHexString(i) + " could not be retrieved.");
        }
        return loadFont;
    }

    /* JADX WARN: Removed duplicated region for block: B:34:0x00a9  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static Typeface loadFont(@NonNull Context context, Resources resources, TypedValue typedValue, int i, int i2, @Nullable FontCallback fontCallback, @Nullable Handler handler, boolean z) {
        CharSequence charSequence = typedValue.string;
        if (charSequence == null) {
            throw new Resources.NotFoundException("Resource \"" + resources.getResourceName(i) + "\" (" + Integer.toHexString(i) + ") is not a Font: " + typedValue);
        }
        String charSequence2 = charSequence.toString();
        if (!charSequence2.startsWith("res/")) {
            if (fontCallback != null) {
                fontCallback.callbackFailAsync(-3, handler);
            }
            return null;
        }
        Typeface findFromCache = TypefaceCompat.findFromCache(resources, i, i2);
        if (findFromCache != null) {
            if (fontCallback != null) {
                fontCallback.callbackSuccessAsync(findFromCache, handler);
            }
            return findFromCache;
        }
        try {
            if (charSequence2.toLowerCase().endsWith(".xml")) {
                FontResourcesParserCompat.FamilyResourceEntry parse = FontResourcesParserCompat.parse(resources.getXml(i), resources);
                if (parse == null) {
                    Log.e(TAG, "Failed to find font-family tag");
                    if (fontCallback != null) {
                        fontCallback.callbackFailAsync(-3, handler);
                    }
                    return null;
                }
                return TypefaceCompat.createFromResourcesFamilyXml(context, parse, resources, i, i2, fontCallback, handler, z);
            }
            Typeface createFromResourcesFontFile = TypefaceCompat.createFromResourcesFontFile(context, resources, i, charSequence2, i2);
            if (fontCallback != null) {
                if (createFromResourcesFontFile != null) {
                    fontCallback.callbackSuccessAsync(createFromResourcesFontFile, handler);
                } else {
                    fontCallback.callbackFailAsync(-3, handler);
                }
            }
            return createFromResourcesFontFile;
        } catch (IOException e) {
            Log.e(TAG, "Failed to read xml resource " + charSequence2, e);
            if (fontCallback != null) {
                fontCallback.callbackFailAsync(-3, handler);
            }
            return null;
        } catch (XmlPullParserException e2) {
            Log.e(TAG, "Failed to parse xml resource " + charSequence2, e2);
            if (fontCallback != null) {
            }
            return null;
        }
    }

    private ResourcesCompat() {
    }
}
