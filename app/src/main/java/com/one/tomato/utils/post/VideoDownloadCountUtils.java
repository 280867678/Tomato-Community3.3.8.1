package com.one.tomato.utils.post;

import com.one.tomato.entity.VideoPay;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class VideoDownloadCountUtils {
    private static VideoDownloadCountUtils videoDownloadCountUtils;
    private boolean openVipTip = false;
    private int vipSaveCount = 3;
    private int vipSaveTotalCount = 3;
    private List<String> downVideoList = new ArrayList();
    private List<String> photoDownVideoList = new ArrayList();

    private VideoDownloadCountUtils() {
    }

    public static VideoDownloadCountUtils getInstance() {
        if (videoDownloadCountUtils == null) {
            synchronized (VideoDownloadCountUtils.class) {
                if (videoDownloadCountUtils == null) {
                    videoDownloadCountUtils = new VideoDownloadCountUtils();
                }
            }
        }
        return videoDownloadCountUtils;
    }

    public void setInfo(VideoPay videoPay) {
        if (videoPay != null) {
            this.vipSaveCount = videoPay.getFreeTimes();
            this.vipSaveTotalCount = videoPay.getVipFreeVideoDownloads();
            this.downVideoList = videoPay.getDownVideoList();
            if (this.downVideoList == null) {
                this.downVideoList = new ArrayList();
            }
            this.photoDownVideoList = videoPay.getPhotoDownVideoList();
            if (this.photoDownVideoList != null) {
                return;
            }
            this.photoDownVideoList = new ArrayList();
        }
    }

    public int getVipSaveCount() {
        if (this.vipSaveCount <= 0) {
            this.vipSaveCount = 0;
        }
        return this.vipSaveCount;
    }

    public int getVipSaveTotalCount() {
        if (this.vipSaveTotalCount <= 0) {
            this.vipSaveTotalCount = 0;
        }
        return this.vipSaveTotalCount;
    }

    public boolean hasDownloadRecord(String str) {
        return this.downVideoList.contains(str);
    }

    public boolean hasSaveLocalRecord(String str) {
        return this.photoDownVideoList.contains(str);
    }

    public void addDownloadRecord(String str) {
        if (!this.downVideoList.contains(str)) {
            this.downVideoList.add(str);
        }
    }

    public void addSaveLocalRecord(String str, boolean z) {
        if (!this.photoDownVideoList.contains(str)) {
            this.photoDownVideoList.add(str);
            if (!z) {
                return;
            }
            this.vipSaveCount--;
        }
    }

    public boolean isOpenVipTip() {
        return this.openVipTip;
    }

    public void setOpenVipTip(boolean z) {
        this.openVipTip = z;
    }
}
