package com.opensource.svgaplayer;

import com.opensource.svgaplayer.SVGAParser;
import com.opensource.svgaplayer.proto.MovieEntity;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import kotlin.p143io.Closeable;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: SVGAParser.kt */
/* loaded from: classes3.dex */
public final class SVGAParser$decodeFromInputStream$1 implements Runnable {
    final /* synthetic */ String $cacheKey;
    final /* synthetic */ SVGAParser.ParseCompletion $callback;
    final /* synthetic */ boolean $closeInputStream;
    final /* synthetic */ InputStream $inputStream;
    final /* synthetic */ SVGAParser this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    public SVGAParser$decodeFromInputStream$1(SVGAParser sVGAParser, InputStream inputStream, String str, SVGAParser.ParseCompletion parseCompletion, boolean z) {
        this.this$0 = sVGAParser;
        this.$inputStream = inputStream;
        this.$cacheKey = str;
        this.$callback = parseCompletion;
        this.$closeInputStream = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        byte[] readAsBytes;
        byte[] inflate;
        File buildCacheDir;
        try {
            try {
                readAsBytes = this.this$0.readAsBytes(this.$inputStream);
                if (readAsBytes != null) {
                    if (readAsBytes.length <= 4 || readAsBytes[0] != 80 || readAsBytes[1] != 75 || readAsBytes[2] != 3 || readAsBytes[3] != 4) {
                        inflate = this.this$0.inflate(readAsBytes);
                        if (inflate != null) {
                            MovieEntity decode = MovieEntity.ADAPTER.decode(inflate);
                            Intrinsics.checkExpressionValueIsNotNull(decode, "MovieEntity.ADAPTER.decode(it)");
                            SVGAVideoEntity sVGAVideoEntity = new SVGAVideoEntity(decode, new File(this.$cacheKey));
                            sVGAVideoEntity.prepare$library_release(new C3042x384fd0b9(sVGAVideoEntity, this));
                        }
                    } else {
                        buildCacheDir = this.this$0.buildCacheDir(this.$cacheKey);
                        if (!buildCacheDir.exists()) {
                            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(readAsBytes);
                            th = null;
                            try {
                                this.this$0.unzip(byteArrayInputStream, this.$cacheKey);
                                Unit unit = Unit.INSTANCE;
                            } catch (Throwable th) {
                                try {
                                    throw th;
                                } finally {
                                    Closeable.closeFinally(byteArrayInputStream, th);
                                }
                            }
                        }
                        this.this$0.decodeFromCacheKey(this.$cacheKey, this.$callback);
                    }
                }
                if (!this.$closeInputStream) {
                    return;
                }
            } catch (Exception e) {
                this.this$0.invokeErrorCallback(e, this.$callback);
                if (!this.$closeInputStream) {
                    return;
                }
            }
            this.$inputStream.close();
        } catch (Throwable th2) {
            if (this.$closeInputStream) {
                this.$inputStream.close();
            }
            throw th2;
        }
    }
}
