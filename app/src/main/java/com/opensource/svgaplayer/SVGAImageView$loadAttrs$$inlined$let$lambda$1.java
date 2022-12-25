package com.opensource.svgaplayer;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import com.opensource.svgaplayer.SVGAParser;
import java.net.URL;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsJVM;

/* compiled from: SVGAImageView.kt */
/* loaded from: classes3.dex */
final class SVGAImageView$loadAttrs$$inlined$let$lambda$1 implements Runnable {
    final /* synthetic */ boolean $antiAlias$inlined;
    final /* synthetic */ boolean $autoPlay$inlined;
    final /* synthetic */ String $it;
    final /* synthetic */ SVGAParser $parser;
    final /* synthetic */ SVGAImageView this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    public SVGAImageView$loadAttrs$$inlined$let$lambda$1(String str, SVGAParser sVGAParser, SVGAImageView sVGAImageView, boolean z, boolean z2) {
        this.$it = str;
        this.$parser = sVGAParser;
        this.this$0 = sVGAImageView;
        this.$antiAlias$inlined = z;
        this.$autoPlay$inlined = z2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        boolean startsWith$default;
        boolean startsWith$default2;
        SVGAParser.ParseCompletion parseCompletion = new SVGAParser.ParseCompletion() { // from class: com.opensource.svgaplayer.SVGAImageView$loadAttrs$$inlined$let$lambda$1.1
            @Override // com.opensource.svgaplayer.SVGAParser.ParseCompletion
            public void onError() {
            }

            @Override // com.opensource.svgaplayer.SVGAParser.ParseCompletion
            public void onComplete(final SVGAVideoEntity videoItem) {
                Intrinsics.checkParameterIsNotNull(videoItem, "videoItem");
                SVGAImageView$loadAttrs$$inlined$let$lambda$1.this.this$0.post(new Runnable() { // from class: com.opensource.svgaplayer.SVGAImageView$loadAttrs$.inlined.let.lambda.1.1.1
                    @Override // java.lang.Runnable
                    public final void run() {
                        videoItem.setAntiAlias(SVGAImageView$loadAttrs$$inlined$let$lambda$1.this.$antiAlias$inlined);
                        SVGAImageView$loadAttrs$$inlined$let$lambda$1.this.this$0.setVideoItem(videoItem);
                        Drawable drawable = SVGAImageView$loadAttrs$$inlined$let$lambda$1.this.this$0.getDrawable();
                        if (!(drawable instanceof SVGADrawable)) {
                            drawable = null;
                        }
                        SVGADrawable sVGADrawable = (SVGADrawable) drawable;
                        if (sVGADrawable != null) {
                            ImageView.ScaleType scaleType = SVGAImageView$loadAttrs$$inlined$let$lambda$1.this.this$0.getScaleType();
                            Intrinsics.checkExpressionValueIsNotNull(scaleType, "scaleType");
                            sVGADrawable.setScaleType(scaleType);
                        }
                        SVGAImageView$loadAttrs$$inlined$let$lambda$1 sVGAImageView$loadAttrs$$inlined$let$lambda$1 = SVGAImageView$loadAttrs$$inlined$let$lambda$1.this;
                        if (sVGAImageView$loadAttrs$$inlined$let$lambda$1.$autoPlay$inlined) {
                            sVGAImageView$loadAttrs$$inlined$let$lambda$1.this$0.startAnimation();
                        }
                    }
                });
            }
        };
        startsWith$default = StringsJVM.startsWith$default(this.$it, "http://", false, 2, null);
        if (!startsWith$default) {
            startsWith$default2 = StringsJVM.startsWith$default(this.$it, "https://", false, 2, null);
            if (!startsWith$default2) {
                this.$parser.parse(this.$it, parseCompletion);
                return;
            }
        }
        this.$parser.parse(new URL(this.$it), parseCompletion);
    }
}
