package com.gen.p059mh.webapp_extensions.matisse.internal.utils;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.support.media.ExifInterface;
import android.util.DisplayMetrics;
import android.util.Log;
import com.gen.p059mh.webapp_extensions.R$string;
import com.gen.p059mh.webapp_extensions.matisse.MimeType;
import com.gen.p059mh.webapp_extensions.matisse.filter.Filter;
import com.gen.p059mh.webapp_extensions.matisse.internal.entity.IncapableCause;
import com.gen.p059mh.webapp_extensions.matisse.internal.entity.Item;
import com.gen.p059mh.webapp_extensions.matisse.internal.entity.SelectionSpec;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/* renamed from: com.gen.mh.webapp_extensions.matisse.internal.utils.PhotoMetadataUtils */
/* loaded from: classes2.dex */
public final class PhotoMetadataUtils {
    private static final int MAX_WIDTH = 1600;
    private static final String SCHEME_CONTENT = "content";
    private static final String TAG = "PhotoMetadataUtils";

    private PhotoMetadataUtils() {
        throw new AssertionError("oops! the utility class is about to be instantiated...");
    }

    public static int getPixelsCount(ContentResolver contentResolver, Uri uri) {
        Point bitmapBound = getBitmapBound(contentResolver, uri);
        return bitmapBound.x * bitmapBound.y;
    }

    public static Point getBitmapSize(Uri uri, Activity activity) {
        DisplayMetrics displayMetrics;
        ContentResolver contentResolver = activity.getContentResolver();
        Point bitmapBound = getBitmapBound(contentResolver, uri);
        int i = bitmapBound.x;
        int i2 = bitmapBound.y;
        if (shouldRotate(contentResolver, uri)) {
            i = bitmapBound.y;
            i2 = bitmapBound.x;
        }
        if (i2 == 0) {
            return new Point(MAX_WIDTH, MAX_WIDTH);
        }
        activity.getWindowManager().getDefaultDisplay().getMetrics(new DisplayMetrics());
        float f = i;
        float f2 = displayMetrics.widthPixels / f;
        float f3 = i2;
        float f4 = displayMetrics.heightPixels / f3;
        if (f2 > f4) {
            return new Point((int) (f * f2), (int) (f3 * f4));
        }
        return new Point((int) (f * f2), (int) (f3 * f4));
    }

    public static Point getBitmapBound(ContentResolver contentResolver, Uri uri) {
        BitmapFactory.Options options;
        InputStream openInputStream;
        InputStream inputStream = null;
        try {
            try {
                options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                openInputStream = contentResolver.openInputStream(uri);
            } catch (Throwable th) {
                th = th;
            }
        } catch (FileNotFoundException unused) {
        }
        try {
            BitmapFactory.decodeStream(openInputStream, null, options);
            Point point = new Point(options.outWidth, options.outHeight);
            if (openInputStream != null) {
                try {
                    openInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return point;
        } catch (FileNotFoundException unused2) {
            inputStream = openInputStream;
            Point point2 = new Point(0, 0);
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
            return point2;
        } catch (Throwable th2) {
            th = th2;
            inputStream = openInputStream;
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
            }
            throw th;
        }
    }

    public static String getPath(ContentResolver contentResolver, Uri uri) {
        Cursor cursor;
        if (uri == null) {
            return null;
        }
        if (SCHEME_CONTENT.equals(uri.getScheme())) {
            try {
                cursor = contentResolver.query(uri, new String[]{"_data"}, null, null, null);
                if (cursor != null) {
                    try {
                        if (cursor.moveToFirst()) {
                            String string = cursor.getString(cursor.getColumnIndex("_data"));
                            if (cursor != null) {
                                cursor.close();
                            }
                            return string;
                        }
                    } catch (Throwable th) {
                        th = th;
                        if (cursor != null) {
                            cursor.close();
                        }
                        throw th;
                    }
                }
                if (cursor != null) {
                    cursor.close();
                }
                return null;
            } catch (Throwable th2) {
                th = th2;
                cursor = null;
            }
        } else {
            return uri.getPath();
        }
    }

    public static IncapableCause isAcceptable(Context context, Item item) {
        if (!isSelectableType(context, item)) {
            return new IncapableCause(context.getString(R$string.error_file_type));
        }
        if (SelectionSpec.getInstance().filters == null) {
            return null;
        }
        for (Filter filter : SelectionSpec.getInstance().filters) {
            IncapableCause filter2 = filter.filter(context, item);
            if (filter2 != null) {
                return filter2;
            }
        }
        return null;
    }

    private static boolean isSelectableType(Context context, Item item) {
        if (context == null) {
            return false;
        }
        ContentResolver contentResolver = context.getContentResolver();
        for (MimeType mimeType : SelectionSpec.getInstance().mimeTypeSet) {
            if (mimeType.checkType(contentResolver, item.getContentUri())) {
                return true;
            }
        }
        return false;
    }

    private static boolean shouldRotate(ContentResolver contentResolver, Uri uri) {
        try {
            int attributeInt = ExifInterfaceCompat.newInstance(getPath(contentResolver, uri)).getAttributeInt(ExifInterface.TAG_ORIENTATION, -1);
            return attributeInt == 6 || attributeInt == 8;
        } catch (IOException unused) {
            String str = TAG;
            Log.e(str, "could not read exif info of the image: " + uri);
            return false;
        }
    }

    public static float getSizeInMB(long j) {
        DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getNumberInstance(Locale.US);
        decimalFormat.applyPattern("0.0");
        String format = decimalFormat.format((((float) j) / 1024.0f) / 1024.0f);
        String str = TAG;
        Log.e(str, "getSizeInMB: " + format);
        return Float.valueOf(format.replaceAll(",", ".")).floatValue();
    }
}
