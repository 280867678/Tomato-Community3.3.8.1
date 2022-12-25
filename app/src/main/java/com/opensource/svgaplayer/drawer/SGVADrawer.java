package com.opensource.svgaplayer.drawer;

import android.graphics.Canvas;
import android.widget.ImageView;
import com.opensource.svgaplayer.SVGAVideoEntity;
import com.opensource.svgaplayer.entities.SVGAVideoSpriteEntity;
import com.opensource.svgaplayer.entities.SVGAVideoSpriteFrameEntity;
import com.opensource.svgaplayer.utils.SVGAScaleInfo;
import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsJVM;

/* compiled from: SGVADrawer.kt */
/* loaded from: classes3.dex */
public class SGVADrawer {
    private final SVGAScaleInfo scaleInfo = new SVGAScaleInfo();
    private final SVGAVideoEntity videoItem;

    public SGVADrawer(SVGAVideoEntity videoItem) {
        Intrinsics.checkParameterIsNotNull(videoItem, "videoItem");
        this.videoItem = videoItem;
    }

    public final SVGAVideoEntity getVideoItem() {
        return this.videoItem;
    }

    public final SVGAScaleInfo getScaleInfo() {
        return this.scaleInfo;
    }

    /* compiled from: SGVADrawer.kt */
    /* loaded from: classes3.dex */
    public final class SVGADrawerSprite {
        private final SVGAVideoSpriteFrameEntity frameEntity;
        private final String imageKey;
        private final String matteKey;

        public SVGADrawerSprite(SGVADrawer sGVADrawer, String str, String str2, SVGAVideoSpriteFrameEntity frameEntity) {
            Intrinsics.checkParameterIsNotNull(frameEntity, "frameEntity");
            this.matteKey = str;
            this.imageKey = str2;
            this.frameEntity = frameEntity;
        }

        public final SVGAVideoSpriteFrameEntity getFrameEntity() {
            return this.frameEntity;
        }

        public final String getImageKey() {
            return this.imageKey;
        }

        public final String getMatteKey() {
            return this.matteKey;
        }
    }

    public final List<SVGADrawerSprite> requestFrameSprites$library_release(int i) {
        String imageKey;
        boolean endsWith$default;
        List<SVGAVideoSpriteEntity> sprites$library_release = this.videoItem.getSprites$library_release();
        ArrayList arrayList = new ArrayList();
        for (SVGAVideoSpriteEntity sVGAVideoSpriteEntity : sprites$library_release) {
            SVGADrawerSprite sVGADrawerSprite = null;
            if (i >= 0 && i < sVGAVideoSpriteEntity.getFrames().size() && (imageKey = sVGAVideoSpriteEntity.getImageKey()) != null) {
                endsWith$default = StringsJVM.endsWith$default(imageKey, ".matte", false, 2, null);
                if (endsWith$default || sVGAVideoSpriteEntity.getFrames().get(i).getAlpha() > 0.0d) {
                    sVGADrawerSprite = new SVGADrawerSprite(this, sVGAVideoSpriteEntity.getMatteKey(), sVGAVideoSpriteEntity.getImageKey(), sVGAVideoSpriteEntity.getFrames().get(i));
                }
            }
            if (sVGADrawerSprite != null) {
                arrayList.add(sVGADrawerSprite);
            }
        }
        return arrayList;
    }

    public void drawFrame(Canvas canvas, int i, ImageView.ScaleType scaleType) {
        Intrinsics.checkParameterIsNotNull(canvas, "canvas");
        Intrinsics.checkParameterIsNotNull(scaleType, "scaleType");
        this.scaleInfo.performScaleType(canvas.getWidth(), canvas.getHeight(), (float) this.videoItem.getVideoSize().getWidth(), (float) this.videoItem.getVideoSize().getHeight(), scaleType);
    }
}
