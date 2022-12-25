package com.google.android.exoplayer2.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/* loaded from: classes.dex */
public class SlidingPercentile {
    private static final Comparator<Sample> INDEX_COMPARATOR = new Comparator<Sample>() { // from class: com.google.android.exoplayer2.util.SlidingPercentile.1
        @Override // java.util.Comparator
        public int compare(Sample sample, Sample sample2) {
            return sample.index - sample2.index;
        }
    };
    private static final Comparator<Sample> VALUE_COMPARATOR = new Comparator<Sample>() { // from class: com.google.android.exoplayer2.util.SlidingPercentile.2
        @Override // java.util.Comparator
        public int compare(Sample sample, Sample sample2) {
            float f = sample.value;
            float f2 = sample2.value;
            if (f < f2) {
                return -1;
            }
            return f2 < f ? 1 : 0;
        }
    };
    private final int maxWeight;
    private int nextSampleIndex;
    private int recycledSampleCount;
    private int totalWeight;
    private final Sample[] recycledSamples = new Sample[5];
    private final ArrayList<Sample> samples = new ArrayList<>();
    private int currentSortOrder = -1;

    public SlidingPercentile(int i) {
        this.maxWeight = i;
    }

    public void addSample(int i, float f) {
        Sample sample;
        ensureSortedByIndex();
        int i2 = this.recycledSampleCount;
        if (i2 > 0) {
            Sample[] sampleArr = this.recycledSamples;
            int i3 = i2 - 1;
            this.recycledSampleCount = i3;
            sample = sampleArr[i3];
        } else {
            sample = new Sample();
        }
        int i4 = this.nextSampleIndex;
        this.nextSampleIndex = i4 + 1;
        sample.index = i4;
        sample.weight = i;
        sample.value = f;
        this.samples.add(sample);
        this.totalWeight += i;
        while (true) {
            int i5 = this.totalWeight;
            int i6 = this.maxWeight;
            if (i5 > i6) {
                int i7 = i5 - i6;
                Sample sample2 = this.samples.get(0);
                int i8 = sample2.weight;
                if (i8 <= i7) {
                    this.totalWeight -= i8;
                    this.samples.remove(0);
                    int i9 = this.recycledSampleCount;
                    if (i9 < 5) {
                        Sample[] sampleArr2 = this.recycledSamples;
                        this.recycledSampleCount = i9 + 1;
                        sampleArr2[i9] = sample2;
                    }
                } else {
                    sample2.weight = i8 - i7;
                    this.totalWeight -= i7;
                }
            } else {
                return;
            }
        }
    }

    public float getPercentile(float f) {
        ensureSortedByValue();
        float f2 = f * this.totalWeight;
        int i = 0;
        for (int i2 = 0; i2 < this.samples.size(); i2++) {
            Sample sample = this.samples.get(i2);
            i += sample.weight;
            if (i >= f2) {
                return sample.value;
            }
        }
        if (this.samples.isEmpty()) {
            return Float.NaN;
        }
        ArrayList<Sample> arrayList = this.samples;
        return arrayList.get(arrayList.size() - 1).value;
    }

    private void ensureSortedByIndex() {
        if (this.currentSortOrder != 1) {
            Collections.sort(this.samples, INDEX_COMPARATOR);
            this.currentSortOrder = 1;
        }
    }

    private void ensureSortedByValue() {
        if (this.currentSortOrder != 0) {
            Collections.sort(this.samples, VALUE_COMPARATOR);
            this.currentSortOrder = 0;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class Sample {
        public int index;
        public float value;
        public int weight;

        private Sample() {
        }
    }
}
