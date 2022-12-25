package com.tomatolive.library.model.p135db;

/* renamed from: com.tomatolive.library.model.db.StickerEntity */
/* loaded from: classes3.dex */
public class StickerEntity extends BaseDBEntity {
    public int color;
    public float rotation;
    public float scale;
    public String text;
    public float translationX;
    public float translationY;
    public String userId;
    public String uuID;

    public StickerEntity() {
    }

    public StickerEntity(String str, float f, float f2, float f3, float f4, int i, String str2) {
        this.userId = str;
        this.translationX = f;
        this.translationY = f2;
        this.scale = f3;
        this.rotation = f4;
        this.color = i;
        this.text = str2;
    }

    public StickerEntity(String str, String str2, float f, float f2, float f3, float f4, int i, String str3) {
        this.uuID = str;
        this.userId = str2;
        this.translationX = f;
        this.translationY = f2;
        this.scale = f3;
        this.rotation = f4;
        this.color = i;
        this.text = str3;
    }
}
