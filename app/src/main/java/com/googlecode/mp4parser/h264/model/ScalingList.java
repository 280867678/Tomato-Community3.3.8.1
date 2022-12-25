package com.googlecode.mp4parser.h264.model;

import android.support.p002v4.view.InputDeviceCompat;
import com.googlecode.mp4parser.h264.read.CAVLCReader;
import com.googlecode.mp4parser.h264.write.CAVLCWriter;
import java.io.IOException;

/* loaded from: classes3.dex */
public class ScalingList {
    public int[] scalingList;
    public boolean useDefaultScalingMatrixFlag;

    public void write(CAVLCWriter cAVLCWriter) throws IOException {
        int i = 0;
        if (this.useDefaultScalingMatrixFlag) {
            cAVLCWriter.writeSE(0, "SPS: ");
            return;
        }
        int i2 = 8;
        while (true) {
            int[] iArr = this.scalingList;
            if (i >= iArr.length) {
                return;
            }
            cAVLCWriter.writeSE((iArr[i] - i2) + InputDeviceCompat.SOURCE_ANY, "SPS: ");
            i2 = this.scalingList[i];
            i++;
        }
    }

    public static ScalingList read(CAVLCReader cAVLCReader, int i) throws IOException {
        ScalingList scalingList = new ScalingList();
        scalingList.scalingList = new int[i];
        int i2 = 0;
        int i3 = 8;
        int i4 = 8;
        while (i2 < i) {
            if (i3 != 0) {
                i3 = ((cAVLCReader.readSE("deltaScale") + i4) + 256) % 256;
                scalingList.useDefaultScalingMatrixFlag = i2 == 0 && i3 == 0;
            }
            int[] iArr = scalingList.scalingList;
            if (i3 != 0) {
                i4 = i3;
            }
            iArr[i2] = i4;
            i4 = scalingList.scalingList[i2];
            i2++;
        }
        return scalingList;
    }

    public String toString() {
        return "ScalingList{scalingList=" + this.scalingList + ", useDefaultScalingMatrixFlag=" + this.useDefaultScalingMatrixFlag + '}';
    }
}
