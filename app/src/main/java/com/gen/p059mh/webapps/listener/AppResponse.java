package com.gen.p059mh.webapps.listener;

/* renamed from: com.gen.mh.webapps.listener.AppResponse */
/* loaded from: classes2.dex */
public class AppResponse {
    public String appletAlias;
    public String desc;
    public String filePath;
    public String imageUrl;
    public String loadingBgColor;
    public String loadingBgImg;
    public LoadingImgBean loadingImg;
    public String title;
    public String url;
    public String version;
    public String zipUrl;

    /* renamed from: com.gen.mh.webapps.listener.AppResponse$LoadingImgBean */
    /* loaded from: classes2.dex */
    public static class LoadingImgBean {
        public int cropHeight;
        public int cropWidth;
        public String imgPath;
        public int spriteCount;
        public String spriteDirection;
        public int spriteFps;

        public String toString() {
            return "LoadingImgBean{imgPath='" + this.imgPath + "', cropWidth=" + this.cropWidth + ", cropHeight=" + this.cropHeight + ", spriteCount=" + this.spriteCount + ", spriteFps=" + this.spriteFps + ", spriteDirection='" + this.spriteDirection + "'}";
        }
    }

    public String toString() {
        return "AppResponse{appletAlias='" + this.appletAlias + "', title='" + this.title + "', imageUrl='" + this.imageUrl + "', version='" + this.version + "', desc='" + this.desc + "', filePath='" + this.filePath + "', url='" + this.url + "', zipUrl='" + this.zipUrl + "', loadingBgColor='" + this.loadingBgColor + "', loadingBgImg='" + this.loadingBgImg + "', loadingImg=" + this.loadingImg + '}';
    }
}
