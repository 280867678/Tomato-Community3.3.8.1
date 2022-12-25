package com.p076mh.webappStart.util.video_compress;

import android.content.Context;
import android.media.MediaMetadataRetriever;
import android.util.Log;
import java.io.File;

/* renamed from: com.mh.webappStart.util.video_compress.VideoUtil */
/* loaded from: classes3.dex */
public class VideoUtil {
    public static VideoInfo getVideoInfo(Context context, String str) {
        Log.e("czxx", "getVideoInfo: " + str);
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        mediaMetadataRetriever.setDataSource(str);
        String extractMetadata = mediaMetadataRetriever.extractMetadata(19);
        String extractMetadata2 = mediaMetadataRetriever.extractMetadata(18);
        String extractMetadata3 = mediaMetadataRetriever.extractMetadata(9);
        VideoInfo videoInfo = new VideoInfo();
        videoInfo.setDuration((int) (Float.parseFloat(extractMetadata3) / 1000.0f));
        videoInfo.setWidth(Integer.parseInt(extractMetadata2));
        videoInfo.setHeight(Integer.parseInt(extractMetadata));
        videoInfo.setSize(new File(str).length());
        mediaMetadataRetriever.release();
        return videoInfo;
    }
}
