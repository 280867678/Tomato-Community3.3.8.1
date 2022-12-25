package com.one.tomato.utils.p088tt;

import android.text.TextUtils;
import java.io.Serializable;
import java.util.ArrayList;

/* renamed from: com.one.tomato.utils.tt.SplitFileInfo */
/* loaded from: classes3.dex */
public class SplitFileInfo implements Serializable {
    private String blockDir;
    private ArrayList<Block> blockPathList;
    private String coverHorizontal;
    private String coverVertical;
    private String oriFilePath;
    private long oriFileSize;

    public Block getNextBlockWithoutUploadPath() {
        if (this.blockPathList == null) {
            return null;
        }
        for (int i = 0; i < this.blockPathList.size(); i++) {
            if (TextUtils.isEmpty(this.blockPathList.get(i).getUploadPath())) {
                return this.blockPathList.get(i);
            }
        }
        return null;
    }

    public int getBlockCount() {
        ArrayList<Block> arrayList = this.blockPathList;
        if (arrayList == null) {
            return 0;
        }
        return arrayList.size();
    }

    public String getBlockDir() {
        return this.blockDir;
    }

    public void setBlockDir(String str) {
        this.blockDir = str;
    }

    public String getCoverHorizontal() {
        return this.coverHorizontal;
    }

    public void setCoverHorizontal(String str) {
        this.coverHorizontal = str;
    }

    public String getCoverVertical() {
        return this.coverVertical;
    }

    public void setCoverVertical(String str) {
        this.coverVertical = str;
    }

    public String getOriFilePath() {
        return this.oriFilePath;
    }

    public long getOriFileSize() {
        return this.oriFileSize;
    }

    public void setOriFileSize(long j) {
        this.oriFileSize = j;
    }

    public void setOriFilePath(String str) {
        this.oriFilePath = str;
    }

    public ArrayList<Block> getBlockPathList() {
        return this.blockPathList;
    }

    public void setBlockPathList(ArrayList<Block> arrayList) {
        this.blockPathList = arrayList;
    }

    public String toString() {
        return "\n源文件路径：" + this.oriFilePath + "\n源文件大小：" + this.oriFileSize + "\n横图封面路径：" + this.coverHorizontal + "\n竖图封面路径：" + this.coverVertical + "\n分片信息：" + this.blockPathList + "\n";
    }

    /* renamed from: com.one.tomato.utils.tt.SplitFileInfo$Block */
    /* loaded from: classes3.dex */
    public static class Block implements Serializable {
        private String blockPath;
        private int count;
        private long current;
        private long end;
        private int index;
        private boolean isFirst;
        private boolean isLast;
        private String oriFilePath;
        private long size;
        private long start;
        private long total = -1;
        private String uploadPath;

        public int getCount() {
            return this.count;
        }

        public void setCount(int i) {
            this.count = i;
        }

        public long getCurrent() {
            return this.current;
        }

        public void setCurrent(long j) {
            this.current = j;
        }

        public long getTotal() {
            return this.total;
        }

        public void setTotal(long j) {
            this.total = j;
        }

        public String getUploadPath() {
            return this.uploadPath;
        }

        public void setUploadPath(String str) {
            this.uploadPath = str;
        }

        public long getStart() {
            return this.start;
        }

        public void setStart(long j) {
            this.start = j;
        }

        public long getEnd() {
            return this.end;
        }

        public void setEnd(long j) {
            this.end = j;
        }

        public int getIndex() {
            return this.index;
        }

        public void setIndex(int i) {
            this.index = i;
        }

        public long getSize() {
            return this.size;
        }

        public void setSize(long j) {
            this.size = j;
        }

        public String getOriFilePath() {
            return this.oriFilePath;
        }

        public void setOriFilePath(String str) {
            this.oriFilePath = str;
        }

        public String getBlockPath() {
            return this.blockPath;
        }

        public void setBlockPath(String str) {
            this.blockPath = str;
        }

        public boolean isFirst() {
            return this.isFirst;
        }

        public void setFirst(boolean z) {
            this.isFirst = z;
        }

        public boolean isLast() {
            return this.isLast;
        }

        public void setLast(boolean z) {
            this.isLast = z;
        }

        public String toString() {
            return "\n第" + this.index + "个分片\n 起始点：" + this.start + "\n结束点：" + this.end + "\n分片路径：" + this.blockPath + "\n分片大小：" + this.size + "\n是否是第一个分片：" + this.isFirst + "\n是否是最后一个分片：" + this.isLast + "\n源视频路径：" + this.oriFilePath + "\n后台上传路径：" + this.uploadPath + "\n";
        }
    }
}
