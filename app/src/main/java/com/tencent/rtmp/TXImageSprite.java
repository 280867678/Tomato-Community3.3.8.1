package com.tencent.rtmp;

import android.content.Context;
import android.graphics.Bitmap;
import com.tencent.liteav.basic.datareport.TXCDRApi;
import com.tencent.liteav.basic.datareport.TXCDRDef;
import com.tencent.rtmp.p133a.TXIImageSprite;
import com.tencent.rtmp.p133a.TXImageSpriteImpl;
import java.util.List;

/* loaded from: classes3.dex */
public class TXImageSprite implements TXIImageSprite {
    private Context mContext;
    private TXIImageSprite mImageSprite;

    public TXImageSprite(Context context) {
        this.mContext = context.getApplicationContext();
        TXCDRApi.initCrashReport(context);
    }

    @Override // com.tencent.rtmp.p133a.TXIImageSprite
    public void setVTTUrlAndImageUrls(String str, List<String> list) {
        if (this.mImageSprite != null) {
            release();
        }
        if (str == null || list == null || list.size() == 0) {
            return;
        }
        TXCDRApi.txReportDAU(this.mContext, TXCDRDef.f2508bz);
        this.mImageSprite = new TXImageSpriteImpl();
        this.mImageSprite.setVTTUrlAndImageUrls(str, list);
    }

    @Override // com.tencent.rtmp.p133a.TXIImageSprite
    public Bitmap getThumbnail(float f) {
        TXIImageSprite tXIImageSprite = this.mImageSprite;
        if (tXIImageSprite != null) {
            return tXIImageSprite.getThumbnail(f);
        }
        return null;
    }

    @Override // com.tencent.rtmp.p133a.TXIImageSprite
    public void release() {
        TXIImageSprite tXIImageSprite = this.mImageSprite;
        if (tXIImageSprite != null) {
            tXIImageSprite.release();
            this.mImageSprite = null;
        }
    }
}
