package com.tomatolive.library.p136ui.view.widget.bgabanner;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.p002v4.util.Pair;
import android.support.p002v4.view.ViewCompat;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.view.widget.bgabanner.BGABannerUtil */
/* loaded from: classes4.dex */
public class BGABannerUtil {
    public static int getResizedDimension(int i, int i2, int i3, int i4) {
        if (i == 0 && i2 == 0) {
            return i3;
        }
        if (i == 0) {
            return (int) (i3 * (i2 / i4));
        } else if (i2 == 0) {
            return i;
        } else {
            double d = i4 / i3;
            double d2 = i2;
            return ((double) i) * d > d2 ? (int) (d2 / d) : i;
        }
    }

    private BGABannerUtil() {
    }

    public static int dp2px(Context context, float f) {
        return (int) TypedValue.applyDimension(1, f, context.getResources().getDisplayMetrics());
    }

    public static int sp2px(Context context, float f) {
        return (int) TypedValue.applyDimension(2, f, context.getResources().getDisplayMetrics());
    }

    public static ImageView getItemImageView(Context context, @DrawableRes int i, BGALocalImageSize bGALocalImageSize, ImageView.ScaleType scaleType) {
        ImageView imageView = new ImageView(context);
        imageView.setImageBitmap(getScaledImageFromResource(context, i, bGALocalImageSize.getMaxWidth(), bGALocalImageSize.getMaxHeight(), bGALocalImageSize.getMinWidth(), bGALocalImageSize.getMinHeight()));
        imageView.setClickable(true);
        imageView.setScaleType(scaleType);
        return imageView;
    }

    public static void resetPageTransformer(List<? extends View> list) {
        if (list == null) {
            return;
        }
        for (View view : list) {
            view.setVisibility(0);
            ViewCompat.setAlpha(view, 1.0f);
            ViewCompat.setPivotX(view, view.getMeasuredWidth() * 0.5f);
            ViewCompat.setPivotY(view, view.getMeasuredHeight() * 0.5f);
            ViewCompat.setTranslationX(view, 0.0f);
            ViewCompat.setTranslationY(view, 0.0f);
            ViewCompat.setScaleX(view, 1.0f);
            ViewCompat.setScaleY(view, 1.0f);
            ViewCompat.setRotationX(view, 0.0f);
            ViewCompat.setRotationY(view, 0.0f);
            ViewCompat.setRotation(view, 0.0f);
        }
    }

    public static boolean isIndexNotOutOfBounds(int i, Collection collection) {
        return isCollectionNotEmpty(collection, new Collection[0]) && i < collection.size();
    }

    public static boolean isCollectionEmpty(Collection collection, Collection... collectionArr) {
        if (collection == null || collection.isEmpty()) {
            return true;
        }
        for (Collection collection2 : collectionArr) {
            if (collection2 == null || collection2.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public static boolean isCollectionNotEmpty(Collection collection, Collection... collectionArr) {
        return !isCollectionEmpty(collection, collectionArr);
    }

    /* JADX WARN: Code restructure failed: missing block: B:7:0x0023, code lost:
        return null;
     */
    @Nullable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static Bitmap getScaledImageFromResource(@NonNull Context context, int i, int i2, int i3, float f, float f2) {
        LoadBitmapPair<Throwable> imageFromResource;
        do {
            imageFromResource = getImageFromResource(context, i, i2, i3);
            if (imageFromResource != null && imageFromResource.first != 0) {
                break;
            }
            i2 /= 2;
            i3 /= 2;
            if (imageFromResource == null || !(imageFromResource.second instanceof OutOfMemoryError) || i2 <= f) {
                break;
            }
        } while (i3 > f2);
        return (Bitmap) imageFromResource.first;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r10v0, types: [int] */
    /* JADX WARN: Type inference failed for: r10v1, types: [com.tomatolive.library.ui.view.widget.bgabanner.BGABannerUtil$LoadBitmapPair<java.lang.Throwable>] */
    /* JADX WARN: Type inference failed for: r10v10 */
    /* JADX WARN: Type inference failed for: r10v11 */
    /* JADX WARN: Type inference failed for: r10v3, types: [com.tomatolive.library.ui.view.widget.bgabanner.BGABannerUtil$LoadBitmapPair<java.lang.Throwable>] */
    /* JADX WARN: Type inference failed for: r8v0, types: [android.content.Context] */
    /* JADX WARN: Type inference failed for: r8v1 */
    /* JADX WARN: Type inference failed for: r8v10 */
    /* JADX WARN: Type inference failed for: r8v11 */
    /* JADX WARN: Type inference failed for: r8v2 */
    /* JADX WARN: Type inference failed for: r8v3 */
    /* JADX WARN: Type inference failed for: r8v5, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r8v7, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r8v8, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r8v9 */
    public static LoadBitmapPair<Throwable> getImageFromResource(@NonNull Context context, int i, int i2, int i3) {
        InputStream openRawResource;
        LoadBitmapPair<Throwable> loadBitmapPair;
        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap.Config config = Bitmap.Config.RGB_565;
        try {
            try {
                try {
                    try {
                        if (i2 == 0 && i3 == 0) {
                            options.inPreferredConfig = config;
                            openRawResource = context.getResources().openRawResource(i);
                            loadBitmapPair = new LoadBitmapPair<>(BitmapFactory.decodeStream(openRawResource, null, options), null);
                            openRawResource.close();
                        } else {
                            options.inJustDecodeBounds = true;
                            options.inPreferredConfig = config;
                            InputStream openRawResource2 = context.getResources().openRawResource(i);
                            try {
                                BitmapFactory.decodeStream(openRawResource2, null, options);
                                openRawResource2.reset();
                                openRawResource2.close();
                                int i4 = options.outWidth;
                                int i5 = options.outHeight;
                                int resizedDimension = getResizedDimension(i2, i3, i4, i5);
                                int resizedDimension2 = getResizedDimension(i3, i2, i5, i4);
                                options.inJustDecodeBounds = false;
                                options.inSampleSize = calculateInSampleSize(options, resizedDimension, resizedDimension2);
                                options.inPreferredConfig = config;
                                openRawResource = context.getResources().openRawResource(i);
                                Bitmap decodeStream = BitmapFactory.decodeStream(openRawResource, null, options);
                                openRawResource.close();
                                if (decodeStream != null && (decodeStream.getWidth() > resizedDimension || decodeStream.getHeight() > resizedDimension2)) {
                                    LoadBitmapPair<Throwable> loadBitmapPair2 = new LoadBitmapPair<>(Bitmap.createScaledBitmap(decodeStream, resizedDimension, resizedDimension2, true), null);
                                    decodeStream.recycle();
                                    loadBitmapPair = loadBitmapPair2;
                                } else {
                                    loadBitmapPair = new LoadBitmapPair<>(decodeStream, null);
                                }
                            } catch (Exception e) {
                                e = e;
                                context = openRawResource2;
                                e.printStackTrace();
                                LoadBitmapPair<Throwable> loadBitmapPair3 = new LoadBitmapPair<>(null, e);
                                if (context == 0) {
                                    return loadBitmapPair3;
                                }
                                context.close();
                                i2 = loadBitmapPair3;
                                return i2;
                            } catch (OutOfMemoryError e2) {
                                e = e2;
                                context = openRawResource2;
                                e.printStackTrace();
                                LoadBitmapPair<Throwable> loadBitmapPair4 = new LoadBitmapPair<>(null, e);
                                if (context == 0) {
                                    return loadBitmapPair4;
                                }
                                context.close();
                                i2 = loadBitmapPair4;
                                return i2;
                            } catch (Throwable th) {
                                th = th;
                                context = openRawResource2;
                                if (context != 0) {
                                    try {
                                        context.close();
                                    } catch (IOException e3) {
                                        e3.printStackTrace();
                                    }
                                }
                                throw th;
                            }
                        }
                        if (openRawResource != null) {
                            try {
                                openRawResource.close();
                            } catch (IOException e4) {
                                e4.printStackTrace();
                            }
                        }
                        return loadBitmapPair;
                    } catch (Throwable th2) {
                        th = th2;
                    }
                } catch (IOException e5) {
                    e5.printStackTrace();
                    return i2;
                }
            } catch (Exception e6) {
                e = e6;
            } catch (OutOfMemoryError e7) {
                e = e7;
            }
        } catch (Exception e8) {
            e = e8;
            context = 0;
        } catch (OutOfMemoryError e9) {
            e = e9;
            context = 0;
        } catch (Throwable th3) {
            th = th3;
            context = 0;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: com.tomatolive.library.ui.view.widget.bgabanner.BGABannerUtil$LoadBitmapPair */
    /* loaded from: classes4.dex */
    public static class LoadBitmapPair<S extends Throwable> extends Pair<Bitmap, S> {
        LoadBitmapPair(@Nullable Bitmap bitmap, @Nullable S s) {
            super(bitmap, s);
        }
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int i, int i2) {
        int i3 = 1;
        if (i != 0 && i2 != 0) {
            int i4 = options.outHeight;
            int i5 = options.outWidth;
            if (i4 > i2 || i5 > i) {
                int i6 = i4 / 2;
                int i7 = i5 / 2;
                while (i6 / i3 >= i2 && i7 / i3 >= i) {
                    i3 *= 2;
                }
            }
        }
        return i3;
    }
}
