package com.gen.p059mh.webapp_extensions.unity;

import com.hlw.movie.download.data.MovieInfo;
import com.sensorsdata.analytics.android.sdk.AopConstants;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/* renamed from: com.gen.mh.webapp_extensions.unity.DownloadData */
/* loaded from: classes2.dex */
public class DownloadData extends MovieInfo implements Serializable {
    private String resolution;
    private long time;
    private int videoId;
    private String videoImage;
    private String videoName;
    private long videoSize;

    public static DownloadData createData(Map map) {
        DownloadData downloadData = new DownloadData();
        downloadData.videoName = (String) map.get("videoName");
        downloadData.videoId = ((Number) map.get("videoId")).intValue();
        downloadData.videoImage = (String) map.get("videoImage");
        downloadData.resolution = (String) map.get("resolution");
        downloadData.videoSize = ((Number) map.get("videoSize")).longValue();
        downloadData.time = ((Number) map.get(AopConstants.TIME_KEY)).longValue();
        return downloadData;
    }

    public Map object2Map() {
        HashMap hashMap = new HashMap();
        hashMap.put("videoName", this.videoName);
        hashMap.put("videoId", Integer.valueOf(this.videoId));
        hashMap.put("videoImage", this.videoImage);
        hashMap.put("resolution", this.resolution);
        hashMap.put("videoSize", Long.valueOf(this.videoSize));
        hashMap.put(AopConstants.TIME_KEY, Long.valueOf(this.time));
        return hashMap;
    }
}
