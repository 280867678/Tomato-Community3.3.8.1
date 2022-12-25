package com.opensource.svgaplayer.entities;

import com.opensource.svgaplayer.proto.FrameEntity;
import com.opensource.svgaplayer.proto.SpriteEntity;
import java.util.ArrayList;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.Iterables;
import kotlin.collections._Collections;
import kotlin.jvm.internal.Intrinsics;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: SVGAVideoSpriteEntity.kt */
/* loaded from: classes3.dex */
public final class SVGAVideoSpriteEntity {
    private final List<SVGAVideoSpriteFrameEntity> frames;
    private final String imageKey;
    private final String matteKey;

    public final String getImageKey() {
        return this.imageKey;
    }

    public final String getMatteKey() {
        return this.matteKey;
    }

    public final List<SVGAVideoSpriteFrameEntity> getFrames() {
        return this.frames;
    }

    public SVGAVideoSpriteEntity(JSONObject obj) {
        List<SVGAVideoSpriteFrameEntity> list;
        Intrinsics.checkParameterIsNotNull(obj, "obj");
        this.imageKey = obj.optString("imageKey");
        this.matteKey = obj.optString("matteKey");
        ArrayList arrayList = new ArrayList();
        JSONArray optJSONArray = obj.optJSONArray("frames");
        if (optJSONArray != null) {
            int length = optJSONArray.length();
            for (int i = 0; i < length; i++) {
                JSONObject optJSONObject = optJSONArray.optJSONObject(i);
                if (optJSONObject != null) {
                    SVGAVideoSpriteFrameEntity sVGAVideoSpriteFrameEntity = new SVGAVideoSpriteFrameEntity(optJSONObject);
                    if ((!sVGAVideoSpriteFrameEntity.getShapes().isEmpty()) && ((SVGAVideoShapeEntity) CollectionsKt.first((List<? extends Object>) sVGAVideoSpriteFrameEntity.getShapes())).isKeep() && arrayList.size() > 0) {
                        sVGAVideoSpriteFrameEntity.setShapes(((SVGAVideoSpriteFrameEntity) CollectionsKt.last(arrayList)).getShapes());
                    }
                    arrayList.add(sVGAVideoSpriteFrameEntity);
                }
            }
        }
        list = _Collections.toList(arrayList);
        this.frames = list;
    }

    public SVGAVideoSpriteEntity(SpriteEntity obj) {
        List<SVGAVideoSpriteFrameEntity> emptyList;
        int collectionSizeOrDefault;
        Intrinsics.checkParameterIsNotNull(obj, "obj");
        this.imageKey = obj.imageKey;
        this.matteKey = obj.matteKey;
        List<FrameEntity> list = obj.frames;
        if (list == null) {
            emptyList = CollectionsKt__CollectionsKt.emptyList();
        } else {
            collectionSizeOrDefault = Iterables.collectionSizeOrDefault(list, 10);
            emptyList = new ArrayList<>(collectionSizeOrDefault);
            SVGAVideoSpriteFrameEntity sVGAVideoSpriteFrameEntity = null;
            for (FrameEntity it2 : list) {
                Intrinsics.checkExpressionValueIsNotNull(it2, "it");
                SVGAVideoSpriteFrameEntity sVGAVideoSpriteFrameEntity2 = new SVGAVideoSpriteFrameEntity(it2);
                if ((!sVGAVideoSpriteFrameEntity2.getShapes().isEmpty()) && ((SVGAVideoShapeEntity) CollectionsKt.first((List<? extends Object>) sVGAVideoSpriteFrameEntity2.getShapes())).isKeep() && sVGAVideoSpriteFrameEntity != null) {
                    sVGAVideoSpriteFrameEntity2.setShapes(sVGAVideoSpriteFrameEntity.getShapes());
                }
                emptyList.add(sVGAVideoSpriteFrameEntity2);
                sVGAVideoSpriteFrameEntity = sVGAVideoSpriteFrameEntity2;
            }
        }
        this.frames = emptyList;
    }
}
