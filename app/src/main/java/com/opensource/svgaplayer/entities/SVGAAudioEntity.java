package com.opensource.svgaplayer.entities;

import com.opensource.svgaplayer.proto.AudioEntity;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SVGAAudioEntity.kt */
/* loaded from: classes3.dex */
public final class SVGAAudioEntity {
    private final int endFrame;
    private Integer playID;
    private Integer soundID;
    private final int startFrame;

    public final int getStartFrame() {
        return this.startFrame;
    }

    public final int getEndFrame() {
        return this.endFrame;
    }

    public final Integer getSoundID() {
        return this.soundID;
    }

    public final void setSoundID(Integer num) {
        this.soundID = num;
    }

    public final Integer getPlayID() {
        return this.playID;
    }

    public final void setPlayID(Integer num) {
        this.playID = num;
    }

    public SVGAAudioEntity(AudioEntity audioItem) {
        Intrinsics.checkParameterIsNotNull(audioItem, "audioItem");
        String str = audioItem.audioKey;
        Integer num = audioItem.startFrame;
        int i = 0;
        this.startFrame = num != null ? num.intValue() : 0;
        Integer num2 = audioItem.endFrame;
        this.endFrame = num2 != null ? num2.intValue() : i;
        Integer num3 = audioItem.startTime;
        if (num3 != null) {
            num3.intValue();
        }
        Integer num4 = audioItem.totalTime;
        if (num4 != null) {
            num4.intValue();
        }
    }
}
