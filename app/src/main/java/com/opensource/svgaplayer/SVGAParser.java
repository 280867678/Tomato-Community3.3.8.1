package com.opensource.svgaplayer;

import android.content.Context;
import android.content.res.AssetManager;
import android.net.http.HttpResponseCache;
import android.os.Handler;
import android.util.Log;
import com.opensource.svgaplayer.SVGAParser;
import com.opensource.svgaplayer.proto.MovieEntity;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.zip.Inflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Functions;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$BooleanRef;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.p143io.Closeable;
import kotlin.text.StringsKt__StringsKt;
import org.json.JSONObject;

/* compiled from: SVGAParser.kt */
/* loaded from: classes3.dex */
public final class SVGAParser {
    public static final Companion Companion = new Companion(null);
    private static ExecutorService threadPoolExecutor = Executors.newCachedThreadPool();
    private FileDownloader fileDownloader = new FileDownloader();
    private Context mContext;

    /* compiled from: SVGAParser.kt */
    /* loaded from: classes3.dex */
    public interface ParseCompletion {
        void onComplete(SVGAVideoEntity sVGAVideoEntity);

        void onError();
    }

    public SVGAParser(Context context) {
        this.mContext = context;
    }

    /* compiled from: SVGAParser.kt */
    /* loaded from: classes3.dex */
    public static class FileDownloader {
        private boolean noCache;

        public final boolean getNoCache() {
            return this.noCache;
        }

        public Functions<Unit> resume(final URL url, final Function1<? super InputStream, Unit> complete, final Function1<? super Exception, Unit> failure) {
            Intrinsics.checkParameterIsNotNull(url, "url");
            Intrinsics.checkParameterIsNotNull(complete, "complete");
            Intrinsics.checkParameterIsNotNull(failure, "failure");
            final Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
            ref$BooleanRef.element = false;
            SVGAParser$FileDownloader$resume$cancelBlock$1 sVGAParser$FileDownloader$resume$cancelBlock$1 = new SVGAParser$FileDownloader$resume$cancelBlock$1(ref$BooleanRef);
            SVGAParser.Companion.getThreadPoolExecutor$library_release().execute(new Runnable() { // from class: com.opensource.svgaplayer.SVGAParser$FileDownloader$resume$1
                /* JADX WARN: Multi-variable type inference failed */
                /* JADX WARN: Type inference failed for: r2v0, types: [java.lang.Throwable] */
                @Override // java.lang.Runnable
                public final void run() {
                    int read;
                    try {
                        if (HttpResponseCache.getInstalled() == null && !SVGAParser.FileDownloader.this.getNoCache()) {
                            Log.e("SVGAParser", "SVGAParser can not handle cache before install HttpResponseCache. see https://github.com/yyued/SVGAPlayer-Android#cache");
                            Log.e("SVGAParser", "在配置 HttpResponseCache 前 SVGAParser 无法缓存. 查看 https://github.com/yyued/SVGAPlayer-Android#cache ");
                        }
                        URLConnection openConnection = url.openConnection();
                        th = 0;
                        if (!(openConnection instanceof HttpURLConnection)) {
                            openConnection = th;
                        }
                        HttpURLConnection httpURLConnection = (HttpURLConnection) openConnection;
                        if (httpURLConnection == null) {
                            return;
                        }
                        httpURLConnection.setConnectTimeout(20000);
                        httpURLConnection.setRequestMethod("GET");
                        httpURLConnection.connect();
                        ByteArrayInputStream inputStream = httpURLConnection.getInputStream();
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        byte[] bArr = new byte[4096];
                        while (!ref$BooleanRef.element && (read = inputStream.read(bArr, 0, 4096)) != -1) {
                            byteArrayOutputStream.write(bArr, 0, read);
                        }
                        if (ref$BooleanRef.element) {
                            Closeable.closeFinally(byteArrayOutputStream, th);
                            return;
                        }
                        inputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
                        try {
                            complete.mo6794invoke(inputStream);
                            Unit unit = Unit.INSTANCE;
                            Closeable.closeFinally(inputStream, th);
                            Unit unit2 = Unit.INSTANCE;
                            Closeable.closeFinally(byteArrayOutputStream, th);
                            Unit unit3 = Unit.INSTANCE;
                        } catch (Throwable th) {
                            try {
                                throw th;
                            } finally {
                                Closeable.closeFinally(inputStream, th);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        failure.mo6794invoke(e);
                    }
                }
            });
            return sVGAParser$FileDownloader$resume$cancelBlock$1;
        }
    }

    /* compiled from: SVGAParser.kt */
    /* loaded from: classes3.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final ExecutorService getThreadPoolExecutor$library_release() {
            return SVGAParser.threadPoolExecutor;
        }
    }

    static {
        new SVGAParser(null);
    }

    public final void decodeFromAssets(String name, ParseCompletion parseCompletion) {
        AssetManager assets;
        InputStream open;
        Intrinsics.checkParameterIsNotNull(name, "name");
        if (this.mContext == null) {
            Log.e("SVGAParser", "在配置 SVGAParser context 前, 无法解析 SVGA 文件。");
        }
        try {
            Context context = this.mContext;
            if (context == null || (assets = context.getAssets()) == null || (open = assets.open(name)) == null) {
                return;
            }
            decodeFromInputStream(open, buildCacheKey("file:///assets/" + name), parseCompletion, true);
        } catch (Exception e) {
            invokeErrorCallback(e, parseCompletion);
        }
    }

    public final Functions<Unit> decodeFromURL(final URL url, final ParseCompletion parseCompletion) {
        Intrinsics.checkParameterIsNotNull(url, "url");
        if (isCached(buildCacheKey(url))) {
            threadPoolExecutor.execute(new Runnable() { // from class: com.opensource.svgaplayer.SVGAParser$decodeFromURL$1
                @Override // java.lang.Runnable
                public final void run() {
                    String buildCacheKey;
                    SVGAParser sVGAParser = SVGAParser.this;
                    buildCacheKey = sVGAParser.buildCacheKey(url);
                    sVGAParser.decodeFromCacheKey(buildCacheKey, parseCompletion);
                }
            });
            return null;
        }
        return this.fileDownloader.resume(url, new SVGAParser$decodeFromURL$2(this, url, parseCompletion), new SVGAParser$decodeFromURL$3(this, parseCompletion));
    }

    public static /* synthetic */ void decodeFromInputStream$default(SVGAParser sVGAParser, InputStream inputStream, String str, ParseCompletion parseCompletion, boolean z, int i, Object obj) {
        if ((i & 8) != 0) {
            z = false;
        }
        sVGAParser.decodeFromInputStream(inputStream, str, parseCompletion, z);
    }

    public final void decodeFromInputStream(InputStream inputStream, String cacheKey, ParseCompletion parseCompletion, boolean z) {
        Intrinsics.checkParameterIsNotNull(inputStream, "inputStream");
        Intrinsics.checkParameterIsNotNull(cacheKey, "cacheKey");
        threadPoolExecutor.execute(new SVGAParser$decodeFromInputStream$1(this, inputStream, cacheKey, parseCompletion, z));
    }

    public final void parse(String assetsName, ParseCompletion parseCompletion) {
        Intrinsics.checkParameterIsNotNull(assetsName, "assetsName");
        decodeFromAssets(assetsName, parseCompletion);
    }

    public final void parse(URL url, ParseCompletion parseCompletion) {
        Intrinsics.checkParameterIsNotNull(url, "url");
        decodeFromURL(url, parseCompletion);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void invokeCompleteCallback(final SVGAVideoEntity sVGAVideoEntity, final ParseCompletion parseCompletion) {
        if (this.mContext == null) {
            Log.e("SVGAParser", "在配置 SVGAParser context 前, 无法解析 SVGA 文件。");
        }
        Context context = this.mContext;
        new Handler(context != null ? context.getMainLooper() : null).post(new Runnable() { // from class: com.opensource.svgaplayer.SVGAParser$invokeCompleteCallback$1
            @Override // java.lang.Runnable
            public final void run() {
                SVGAParser.ParseCompletion parseCompletion2 = SVGAParser.ParseCompletion.this;
                if (parseCompletion2 != null) {
                    parseCompletion2.onComplete(sVGAVideoEntity);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void invokeErrorCallback(Exception exc, final ParseCompletion parseCompletion) {
        exc.printStackTrace();
        if (this.mContext == null) {
            Log.e("SVGAParser", "在配置 SVGAParser context 前, 无法解析 SVGA 文件。");
        }
        Context context = this.mContext;
        new Handler(context != null ? context.getMainLooper() : null).post(new Runnable() { // from class: com.opensource.svgaplayer.SVGAParser$invokeErrorCallback$1
            @Override // java.lang.Runnable
            public final void run() {
                SVGAParser.ParseCompletion parseCompletion2 = SVGAParser.ParseCompletion.this;
                if (parseCompletion2 != null) {
                    parseCompletion2.onError();
                }
            }
        });
    }

    private final boolean isCached(String str) {
        return buildCacheDir(str).exists();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v0, types: [java.lang.Throwable] */
    public final void decodeFromCacheKey(String str, ParseCompletion parseCompletion) {
        File cacheDir;
        if (this.mContext == null) {
            Log.e("SVGAParser", "在配置 SVGAParser context 前, 无法解析 SVGA 文件。");
        }
        try {
            StringBuilder sb = new StringBuilder();
            Context context = this.mContext;
            th = 0;
            sb.append((context == null || (cacheDir = context.getCacheDir()) == null) ? th : cacheDir.getAbsolutePath());
            sb.append("/");
            sb.append(str);
            sb.append("/");
            File file = new File(sb.toString());
            File file2 = new File(file, "movie.binary");
            if (!file2.isFile()) {
                file2 = th;
            }
            if (file2 != null) {
                try {
                    MovieEntity decode = MovieEntity.ADAPTER.decode(new FileInputStream(file2));
                    Intrinsics.checkExpressionValueIsNotNull(decode, "MovieEntity.ADAPTER.decode(it)");
                    invokeCompleteCallback(new SVGAVideoEntity(decode, file), parseCompletion);
                    Unit unit = Unit.INSTANCE;
                } catch (Exception e) {
                    file.delete();
                    file2.delete();
                    throw e;
                }
            }
            File file3 = new File(file, "movie.spec");
            if (!file3.isFile()) {
                file3 = th;
            }
            if (file3 == null) {
                return;
            }
            try {
                ByteArrayOutputStream fileInputStream = new FileInputStream(file3);
                fileInputStream = new ByteArrayOutputStream();
                try {
                    byte[] bArr = new byte[2048];
                    while (true) {
                        int read = fileInputStream.read(bArr, 0, bArr.length);
                        if (read != -1) {
                            fileInputStream.write(bArr, 0, read);
                        } else {
                            invokeCompleteCallback(new SVGAVideoEntity(new JSONObject(fileInputStream.toString()), file), parseCompletion);
                            Unit unit2 = Unit.INSTANCE;
                            Closeable.closeFinally(fileInputStream, th);
                            Unit unit3 = Unit.INSTANCE;
                            return;
                        }
                    }
                } catch (Throwable th) {
                    try {
                        throw th;
                    } finally {
                        Closeable.closeFinally(fileInputStream, th);
                    }
                }
            } catch (Exception e2) {
                file.delete();
                file3.delete();
                throw e2;
            }
        } catch (Exception e3) {
            invokeErrorCallback(e3, parseCompletion);
        }
    }

    private final String buildCacheKey(String str) {
        byte[] digest;
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        Charset forName = Charset.forName("UTF-8");
        Intrinsics.checkExpressionValueIsNotNull(forName, "Charset.forName(charsetName)");
        if (str == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        }
        byte[] bytes = str.getBytes(forName);
        Intrinsics.checkExpressionValueIsNotNull(bytes, "(this as java.lang.String).getBytes(charset)");
        messageDigest.update(bytes);
        String str2 = "";
        for (byte b : messageDigest.digest()) {
            StringBuilder sb = new StringBuilder();
            sb.append(str2);
            StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
            Object[] objArr = {Byte.valueOf(b)};
            String format = String.format("%02x", Arrays.copyOf(objArr, objArr.length));
            Intrinsics.checkExpressionValueIsNotNull(format, "java.lang.String.format(format, *args)");
            sb.append(format);
            str2 = sb.toString();
        }
        return str2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String buildCacheKey(URL url) {
        String url2 = url.toString();
        Intrinsics.checkExpressionValueIsNotNull(url2, "url.toString()");
        return buildCacheKey(url2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final File buildCacheDir(String str) {
        File cacheDir;
        StringBuilder sb = new StringBuilder();
        Context context = this.mContext;
        sb.append((context == null || (cacheDir = context.getCacheDir()) == null) ? null : cacheDir.getAbsolutePath());
        sb.append("/");
        sb.append(str);
        sb.append("/");
        return new File(sb.toString());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final byte[] readAsBytes(InputStream inputStream) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            byte[] bArr = new byte[2048];
            while (true) {
                int read = inputStream.read(bArr, 0, 2048);
                if (read > 0) {
                    byteArrayOutputStream.write(bArr, 0, read);
                } else {
                    th = null;
                    return byteArrayOutputStream.toByteArray();
                }
            }
        } finally {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final byte[] inflate(byte[] bArr) {
        Inflater inflater = new Inflater();
        inflater.setInput(bArr, 0, bArr.length);
        byte[] bArr2 = new byte[2048];
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        while (true) {
            try {
                int inflate = inflater.inflate(bArr2, 0, 2048);
                if (inflate > 0) {
                    byteArrayOutputStream.write(bArr2, 0, inflate);
                } else {
                    inflater.end();
                    th = null;
                    return byteArrayOutputStream.toByteArray();
                }
            } finally {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void unzip(InputStream inputStream, String str) {
        int i;
        boolean contains$default;
        i = SVGAParserKt.fileLock;
        synchronized (Integer.valueOf(i)) {
            File buildCacheDir = buildCacheDir(str);
            buildCacheDir.mkdirs();
            try {
                try {
                    ZipInputStream zipInputStream = new ZipInputStream(new BufferedInputStream(inputStream));
                    while (true) {
                        ZipEntry nextEntry = zipInputStream.getNextEntry();
                        th = null;
                        if (nextEntry != null) {
                            String name = nextEntry.getName();
                            Intrinsics.checkExpressionValueIsNotNull(name, "zipItem.name");
                            contains$default = StringsKt__StringsKt.contains$default(name, "/", false, 2, th);
                            if (!contains$default) {
                                FileOutputStream fileOutputStream = new FileOutputStream(new File(buildCacheDir, nextEntry.getName()));
                                byte[] bArr = new byte[2048];
                                while (true) {
                                    int read = zipInputStream.read(bArr);
                                    if (read <= 0) {
                                        break;
                                    }
                                    fileOutputStream.write(bArr, 0, read);
                                }
                                Unit unit = Unit.INSTANCE;
                                Closeable.closeFinally(fileOutputStream, th);
                                zipInputStream.closeEntry();
                            }
                        } else {
                            Unit unit2 = Unit.INSTANCE;
                            Closeable.closeFinally(zipInputStream, th);
                            Unit unit3 = Unit.INSTANCE;
                            Unit unit4 = Unit.INSTANCE;
                        }
                    }
                } finally {
                }
            } catch (Exception e) {
                buildCacheDir.delete();
                throw e;
            }
        }
    }
}
