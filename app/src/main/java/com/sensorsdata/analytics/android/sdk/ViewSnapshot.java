package com.sensorsdata.analytics.android.sdk;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Base64OutputStream;
import android.util.DisplayMetrics;
import android.util.JsonWriter;
import android.util.LruCache;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import com.j256.ormlite.field.DatabaseFieldConfigLoader;
import com.j256.ormlite.logger.Logger;
import com.sensorsdata.analytics.android.sdk.util.AopUtil;
import com.sensorsdata.analytics.android.sdk.util.SensorsDataUtils;
import com.sensorsdata.analytics.android.sdk.util.ViewUtil;
import com.sensorsdata.analytics.android.sdk.util.WindowHelper;
import com.sensorsdata.analytics.android.sdk.visual.SnapInfo;
import com.sensorsdata.analytics.android.sdk.visual.ViewNode;
import com.sensorsdata.analytics.android.sdk.visual.VisualUtil;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.json.JSONObject;

@TargetApi(16)
/* loaded from: classes3.dex */
public class ViewSnapshot {
    private static final int MAX_CLASS_NAME_CACHE_SIZE = 255;
    private static final String TAG = "SA.Snapshot";
    private final List<PropertyDescription> mProperties;
    private final ResourceIds mResourceIds;
    private String[] mLastImageHashArray = null;
    private SnapInfo mSnapInfo = new SnapInfo();
    private final Handler mMainThreadHandler = new Handler(Looper.getMainLooper());
    private final RootViewFinder mRootViewFinder = new RootViewFinder();
    private final ClassNameCache mClassnameCache = new ClassNameCache(255);

    public ViewSnapshot(List<PropertyDescription> list, ResourceIds resourceIds) {
        this.mProperties = list;
        this.mResourceIds = resourceIds;
    }

    /* JADX WARN: Removed duplicated region for block: B:9:0x0053 A[Catch: all -> 0x013a, TryCatch #3 {, blocks: (B:3:0x0001, B:6:0x001e, B:7:0x0048, B:9:0x0053, B:11:0x005b, B:13:0x0063, B:15:0x0067, B:17:0x0073, B:19:0x0121, B:20:0x011b, B:23:0x0125, B:33:0x0028, B:35:0x0033, B:30:0x003e), top: B:2:0x0001, inners: #3 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public synchronized SnapInfo snapshots(UIThreadSet<Activity> uIThreadSet, OutputStream outputStream) throws IOException {
        List list;
        int size;
        int i;
        FutureTask futureTask = new FutureTask(this.mRootViewFinder);
        this.mMainThreadHandler.post(futureTask);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
        List emptyList = Collections.emptyList();
        outputStreamWriter.write("[");
        try {
            list = (List) futureTask.get(1L, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            SALog.m3673i(TAG, "Screenshot interrupted, no screenshot will be sent.", e);
            list = emptyList;
            size = list.size();
            String str = null;
            String str2 = null;
            while (i < size) {
            }
            outputStreamWriter.write("]");
            outputStreamWriter.flush();
            this.mSnapInfo.screenName = str;
            this.mSnapInfo.title = str2;
            return this.mSnapInfo;
        } catch (ExecutionException e2) {
            SALog.m3673i(TAG, "Exception thrown during screenshot attempt", e2);
            list = emptyList;
            size = list.size();
            String str3 = null;
            String str22 = null;
            while (i < size) {
            }
            outputStreamWriter.write("]");
            outputStreamWriter.flush();
            this.mSnapInfo.screenName = str3;
            this.mSnapInfo.title = str22;
            return this.mSnapInfo;
        } catch (TimeoutException e3) {
            SALog.m3673i(TAG, "Screenshot took more than 1 second to be scheduled and executed. No screenshot will be sent.", e3);
            list = emptyList;
            size = list.size();
            String str32 = null;
            String str222 = null;
            while (i < size) {
            }
            outputStreamWriter.write("]");
            outputStreamWriter.flush();
            this.mSnapInfo.screenName = str32;
            this.mSnapInfo.title = str222;
            return this.mSnapInfo;
        }
        size = list.size();
        String str322 = null;
        String str2222 = null;
        for (i = 0; i < size; i++) {
            RootViewInfo rootViewInfo = (RootViewInfo) list.get(i);
            if (i > 0) {
                outputStreamWriter.write(",");
            }
            if (rootViewInfo != null && rootViewInfo.screenshot != null && isSnapShotUpdated(rootViewInfo.screenshot.getImageHash())) {
                outputStreamWriter.write("{");
                outputStreamWriter.write("\"activity\":");
                str322 = rootViewInfo.activityName;
                str2222 = rootViewInfo.activityTitle;
                outputStreamWriter.write(JSONObject.quote(rootViewInfo.activityName));
                outputStreamWriter.write(",");
                outputStreamWriter.write("\"scale\":");
                outputStreamWriter.write(String.format("%s", Float.valueOf(rootViewInfo.scale)));
                outputStreamWriter.write(",");
                outputStreamWriter.write("\"serialized_objects\":");
                JsonWriter jsonWriter = new JsonWriter(outputStreamWriter);
                jsonWriter.beginObject();
                jsonWriter.name("rootObject").value(rootViewInfo.rootView.hashCode());
                jsonWriter.name("objects");
                snapshotViewHierarchy(jsonWriter, rootViewInfo.rootView);
                jsonWriter.endObject();
                jsonWriter.flush();
                outputStreamWriter.write(",");
                outputStreamWriter.write("\"image_hash\":");
                outputStreamWriter.write(JSONObject.quote(rootViewInfo.screenshot.getImageHash()));
                outputStreamWriter.write(",");
                outputStreamWriter.write("\"screenshot\":");
                outputStreamWriter.flush();
                rootViewInfo.screenshot.writeBitmapJSON(Bitmap.CompressFormat.PNG, 70, outputStream);
                outputStreamWriter.write("}");
            } else {
                outputStreamWriter.write(Logger.ARG_STRING);
            }
        }
        outputStreamWriter.write("]");
        outputStreamWriter.flush();
        this.mSnapInfo.screenName = str322;
        this.mSnapInfo.title = str2222;
        return this.mSnapInfo;
    }

    public void getVisibleRect(View view, Rect rect, boolean z) {
        if (z) {
            view.getGlobalVisibleRect(rect);
            return;
        }
        int[] iArr = new int[2];
        view.getLocationOnScreen(iArr);
        view.getLocalVisibleRect(rect);
        rect.offset(iArr[0], iArr[1]);
    }

    private void snapshotViewHierarchy(JsonWriter jsonWriter, View view) throws IOException {
        reset();
        jsonWriter.beginArray();
        snapshotView(jsonWriter, view, 0);
        jsonWriter.endArray();
    }

    private void reset() {
        this.mSnapInfo = new SnapInfo();
        ViewUtil.clear();
    }

    private void snapshotView(JsonWriter jsonWriter, View view, int i) throws IOException {
        float f;
        View childAt;
        Rect rect;
        jsonWriter.beginObject();
        jsonWriter.name("hashCode").value(view.hashCode());
        jsonWriter.name(DatabaseFieldConfigLoader.FIELD_NAME_ID).value(view.getId());
        jsonWriter.name(DatabaseFieldConfigLoader.FIELD_NAME_INDEX).value(AopUtil.getChildIndex(view.getParent(), view));
        JsonWriter name = jsonWriter.name("element_level");
        SnapInfo snapInfo = this.mSnapInfo;
        int i2 = snapInfo.elementLevel + 1;
        snapInfo.elementLevel = i2;
        name.value(i2);
        ViewNode viewNode = ViewUtil.getViewNode(view, i);
        if (viewNode != null) {
            if (!TextUtils.isEmpty(viewNode.getViewPath())) {
                jsonWriter.name("element_path").value(viewNode.getViewPath());
            }
            if (!TextUtils.isEmpty(viewNode.getViewPosition())) {
                jsonWriter.name("element_position").value(viewNode.getViewPosition());
            }
            if (!TextUtils.isEmpty(viewNode.getViewContent()) && VisualUtil.isSupportElementContent(view)) {
                jsonWriter.name("element_content").value(viewNode.getViewContent());
            }
        }
        if ((view instanceof WebView) || ViewUtil.instanceOfX5WebView(view)) {
            this.mSnapInfo.isWebView = true;
        }
        jsonWriter.name("sa_id_name").value(getResName(view));
        try {
            String str = (String) view.getTag(C3089R.C3090id.sensors_analytics_tag_view_id);
            if (!TextUtils.isEmpty(str)) {
                jsonWriter.name("sa_id_name").value(str);
            }
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
        if (!WindowHelper.isMainWindow(view.getRootView())) {
            if (WindowHelper.isDecorView(view.getClass())) {
                DisplayMetrics displayMetrics = view.getContext().getResources().getDisplayMetrics();
                int i3 = displayMetrics.widthPixels;
                int i4 = displayMetrics.heightPixels;
                jsonWriter.name("top").value(view.getTop());
                jsonWriter.name("left").value(view.getLeft());
                jsonWriter.name("width").value(i3);
                jsonWriter.name("height").value(i4);
            } else {
                ViewParent parent = view.getParent();
                if (parent != null && WindowHelper.isDecorView(parent.getClass())) {
                    getVisibleRect(view, new Rect(), false);
                    jsonWriter.name("top").value(rect.top);
                    jsonWriter.name("left").value(rect.left);
                    jsonWriter.name("width").value(rect.width());
                    jsonWriter.name("height").value(rect.height());
                } else {
                    jsonWriter.name("top").value(view.getTop());
                    jsonWriter.name("left").value(view.getLeft());
                    jsonWriter.name("width").value(view.getWidth());
                    jsonWriter.name("height").value(view.getHeight());
                }
            }
        } else {
            jsonWriter.name("top").value(view.getTop());
            jsonWriter.name("left").value(view.getLeft());
            jsonWriter.name("width").value(view.getWidth());
            jsonWriter.name("height").value(view.getHeight());
        }
        jsonWriter.name("scrollX").value(view.getScrollX());
        jsonWriter.name("scrollY").value(view.getScrollY());
        jsonWriter.name("visibility").value(VisualUtil.getVisibility(view));
        float f2 = 0.0f;
        if (Build.VERSION.SDK_INT >= 11) {
            f2 = view.getTranslationX();
            f = view.getTranslationY();
        } else {
            f = 0.0f;
        }
        jsonWriter.name("translationX").value(f2);
        jsonWriter.name("translationY").value(f);
        jsonWriter.name("classes");
        jsonWriter.beginArray();
        Class<?> cls = view.getClass();
        do {
            jsonWriter.value(this.mClassnameCache.get(cls));
            cls = cls.getSuperclass();
            if (cls == Object.class) {
                break;
            }
        } while (cls != null);
        jsonWriter.endArray();
        addProperties(jsonWriter, view);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams instanceof RelativeLayout.LayoutParams) {
            int[] rules = ((RelativeLayout.LayoutParams) layoutParams).getRules();
            jsonWriter.name("layoutRules");
            jsonWriter.beginArray();
            for (int i5 : rules) {
                jsonWriter.value(i5);
            }
            jsonWriter.endArray();
        }
        jsonWriter.name("subviews");
        jsonWriter.beginArray();
        boolean z = view instanceof ViewGroup;
        if (z) {
            ViewGroup viewGroup = (ViewGroup) view;
            int childCount = viewGroup.getChildCount();
            for (int i6 = 0; i6 < childCount; i6++) {
                if (viewGroup.getChildAt(i6) != null) {
                    jsonWriter.value(childAt.hashCode());
                }
            }
        }
        jsonWriter.endArray();
        jsonWriter.endObject();
        if (z) {
            ViewGroup viewGroup2 = (ViewGroup) view;
            int childCount2 = viewGroup2.getChildCount();
            for (int i7 = 0; i7 < childCount2; i7++) {
                View childAt2 = viewGroup2.getChildAt(i7);
                if (childAt2 != null) {
                    snapshotView(jsonWriter, childAt2, i7);
                }
            }
        }
    }

    private void addProperties(JsonWriter jsonWriter, View view) throws IOException {
        Caller caller;
        Object applyMethod;
        Class<?> cls = view.getClass();
        for (PropertyDescription propertyDescription : this.mProperties) {
            if (propertyDescription.targetClass.isAssignableFrom(cls) && (caller = propertyDescription.accessor) != null && (applyMethod = caller.applyMethod(view)) != null) {
                if (applyMethod instanceof Number) {
                    jsonWriter.name(propertyDescription.name).value((Number) applyMethod);
                } else if (applyMethod instanceof Boolean) {
                    boolean booleanValue = ((Boolean) applyMethod).booleanValue();
                    if (TextUtils.equals("clickable", propertyDescription.name)) {
                        if (VisualUtil.isSupportClick(view)) {
                            booleanValue = true;
                        } else if (VisualUtil.isForbiddenClick(view)) {
                            booleanValue = false;
                        }
                    }
                    jsonWriter.name(propertyDescription.name).value(booleanValue);
                } else if (applyMethod instanceof ColorStateList) {
                    jsonWriter.name(propertyDescription.name).value(Integer.valueOf(((ColorStateList) applyMethod).getDefaultColor()));
                } else if (applyMethod instanceof Drawable) {
                    Drawable drawable = (Drawable) applyMethod;
                    Rect bounds = drawable.getBounds();
                    jsonWriter.name(propertyDescription.name);
                    jsonWriter.beginObject();
                    jsonWriter.name("classes");
                    jsonWriter.beginArray();
                    for (Class<?> cls2 = drawable.getClass(); cls2 != Object.class; cls2 = cls2.getSuperclass()) {
                        jsonWriter.value(cls2.getCanonicalName());
                    }
                    jsonWriter.endArray();
                    jsonWriter.name("dimensions");
                    jsonWriter.beginObject();
                    jsonWriter.name("left").value(bounds.left);
                    jsonWriter.name("right").value(bounds.right);
                    jsonWriter.name("top").value(bounds.top);
                    jsonWriter.name("bottom").value(bounds.bottom);
                    jsonWriter.endObject();
                    if (drawable instanceof ColorDrawable) {
                        jsonWriter.name("color").value(((ColorDrawable) drawable).getColor());
                    }
                    jsonWriter.endObject();
                } else {
                    jsonWriter.name(propertyDescription.name).value(applyMethod.toString());
                }
            }
        }
    }

    public void updateLastImageHashArray(String str) {
        if (str == null || str.length() <= 0) {
            this.mLastImageHashArray = null;
        } else {
            this.mLastImageHashArray = str.split(",");
        }
    }

    private boolean isSnapShotUpdated(String str) {
        String[] strArr;
        if (str != null && str.length() > 0 && (strArr = this.mLastImageHashArray) != null && strArr.length > 0) {
            for (String str2 : strArr) {
                if (str2.equals(str)) {
                    return false;
                }
            }
        }
        return true;
    }

    private String getResName(View view) {
        int id = view.getId();
        if (-1 == id) {
            return null;
        }
        return this.mResourceIds.nameForId(id);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public static class ClassNameCache extends LruCache<Class<?>, String> {
        public ClassNameCache(int i) {
            super(i);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.util.LruCache
        public String create(Class<?> cls) {
            return cls.getCanonicalName();
        }
    }

    /* loaded from: classes3.dex */
    private static class RootViewFinder implements Callable<List<RootViewInfo>> {
        private final int mClientDensity = 160;
        private final List<RootViewInfo> mRootViews = new ArrayList();
        private final CachedBitmap mCachedBitmap = new CachedBitmap();

        @Override // java.util.concurrent.Callable
        public List<RootViewInfo> call() throws Exception {
            this.mRootViews.clear();
            if (AppSateManager.getInstance().isInBackground()) {
                return this.mRootViews;
            }
            Activity foregroundActivity = AppSateManager.getInstance().getForegroundActivity();
            if (foregroundActivity != null) {
                String canonicalName = foregroundActivity.getClass().getCanonicalName();
                String activityTitle = SensorsDataUtils.getActivityTitle(foregroundActivity);
                RootViewInfo rootViewInfo = new RootViewInfo(canonicalName, activityTitle, foregroundActivity.getWindow().getDecorView().getRootView());
                View[] sortedWindowViews = WindowHelper.getSortedWindowViews();
                Bitmap bitmap = null;
                if (sortedWindowViews != null && sortedWindowViews.length > 0) {
                    bitmap = mergeViewLayers(sortedWindowViews, rootViewInfo);
                    for (View view : sortedWindowViews) {
                        if (view.getWindowVisibility() == 0 && view.getVisibility() == 0 && view.getWidth() != 0 && view.getHeight() != 0 && !TextUtils.equals(WindowHelper.getWindowPrefix(view), WindowHelper.getMainWindowPrefix())) {
                            RootViewInfo rootViewInfo2 = new RootViewInfo(canonicalName, activityTitle, view.getRootView());
                            scaleBitmap(rootViewInfo2, bitmap);
                            this.mRootViews.add(rootViewInfo2);
                        }
                    }
                }
                if (this.mRootViews.size() == 0) {
                    scaleBitmap(rootViewInfo, bitmap);
                    this.mRootViews.add(rootViewInfo);
                }
            }
            return this.mRootViews;
        }

        Bitmap mergeViewLayers(View[] viewArr, RootViewInfo rootViewInfo) {
            int width = rootViewInfo.rootView.getWidth();
            int height = rootViewInfo.rootView.getHeight();
            if (width == 0 || height == 0) {
                return null;
            }
            Bitmap createBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            SoftWareCanvas softWareCanvas = new SoftWareCanvas(createBitmap);
            int[] iArr = new int[2];
            boolean z = ViewUtil.getMainWindowCount(viewArr) > 1;
            WindowHelper.init();
            ViewUtil.invalidateLayerTypeView(viewArr);
            for (View view : viewArr) {
                if (view.getVisibility() == 0 && view.getWidth() != 0 && view.getHeight() != 0 && ViewUtil.isWindowNeedTraverse(view, WindowHelper.getWindowPrefix(view), z)) {
                    view.getLocationOnScreen(iArr);
                    softWareCanvas.save();
                    softWareCanvas.translate(iArr[0], iArr[1]);
                    if (!TextUtils.equals(WindowHelper.getWindowPrefix(view), WindowHelper.getMainWindowPrefix())) {
                        Paint paint = new Paint();
                        paint.setColor(-1610612736);
                        softWareCanvas.drawRect(-iArr[0], -iArr[1], softWareCanvas.getWidth(), softWareCanvas.getHeight(), paint);
                    }
                    view.draw(softWareCanvas);
                    softWareCanvas.restore();
                    softWareCanvas.destroy();
                }
            }
            return createBitmap;
        }

        private void scaleBitmap(RootViewInfo rootViewInfo, Bitmap bitmap) {
            float f = 1.0f;
            if (bitmap != null) {
                int density = bitmap.getDensity();
                if (density != 0) {
                    f = 160.0f / density;
                }
                int width = bitmap.getWidth();
                int height = bitmap.getHeight();
                int width2 = (int) ((bitmap.getWidth() * f) + 0.5d);
                int height2 = (int) ((bitmap.getHeight() * f) + 0.5d);
                if (width > 0 && height > 0 && width2 > 0 && height2 > 0) {
                    this.mCachedBitmap.recreate(width2, height2, 160, bitmap);
                }
            }
            rootViewInfo.scale = f;
            rootViewInfo.screenshot = this.mCachedBitmap;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public static class CachedBitmap {
        private String mImageHash = "";
        private final Paint mPaint = new Paint(2);
        private Bitmap mCached = null;

        public synchronized void recreate(int i, int i2, int i3, Bitmap bitmap) {
            if (this.mCached == null || this.mCached.getWidth() != i || this.mCached.getHeight() != i2) {
                try {
                    this.mCached = Bitmap.createBitmap(i, i2, Bitmap.Config.RGB_565);
                } catch (OutOfMemoryError unused) {
                    this.mCached = null;
                }
                if (this.mCached != null) {
                    this.mCached.setDensity(i3);
                }
            }
            if (this.mCached != null) {
                new Canvas(this.mCached).drawBitmap(bitmap, 0.0f, 0.0f, this.mPaint);
                try {
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    this.mCached.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                    this.mImageHash = toHex(MessageDigest.getInstance("MD5").digest(byteArrayOutputStream.toByteArray()));
                } catch (Exception e) {
                    SALog.m3674i(ViewSnapshot.TAG, "CachedBitmap.recreate;Create image_hash error=" + e);
                }
            }
        }

        public synchronized void writeBitmapJSON(Bitmap.CompressFormat compressFormat, int i, OutputStream outputStream) throws IOException {
            if (this.mCached != null && this.mCached.getWidth() != 0 && this.mCached.getHeight() != 0) {
                outputStream.write(34);
                Base64OutputStream base64OutputStream = new Base64OutputStream(outputStream, 2);
                this.mCached.compress(Bitmap.CompressFormat.PNG, 100, base64OutputStream);
                base64OutputStream.flush();
                outputStream.write(34);
            }
            outputStream.write("null".getBytes());
        }

        public String getImageHash() {
            return this.mImageHash;
        }

        private String toHex(byte[] bArr) {
            String str = "";
            for (int i = 0; i < bArr.length; i++) {
                str = (str + "0123456789ABCDEF".charAt((bArr[i] >> 4) & 15)) + "0123456789ABCDEF".charAt(bArr[i] & 15);
            }
            return str;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public static class RootViewInfo {
        final String activityName;
        final String activityTitle;
        final View rootView;
        CachedBitmap screenshot = null;
        float scale = 1.0f;

        RootViewInfo(String str, String str2, View view) {
            this.activityName = str;
            this.activityTitle = str2;
            this.rootView = view;
        }
    }
}
