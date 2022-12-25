package com.googlecode.mp4parser.authoring.builder;

import com.googlecode.mp4parser.authoring.Movie;
import com.googlecode.mp4parser.authoring.Track;
import java.util.Arrays;

/* loaded from: classes3.dex */
public class TwoSecondIntersectionFinder implements FragmentIntersectionFinder {
    private int fragmentLength;
    private Movie movie;

    public TwoSecondIntersectionFinder(Movie movie, int i) {
        this.fragmentLength = 2;
        this.movie = movie;
        this.fragmentLength = i;
    }

    @Override // com.googlecode.mp4parser.authoring.builder.FragmentIntersectionFinder
    public long[] sampleNumbers(Track track) {
        long[] sampleDurations;
        double d = 0.0d;
        for (Track track2 : this.movie.getTracks()) {
            double duration = track2.getDuration() / track2.getTrackMetaData().getTimescale();
            if (d < duration) {
                d = duration;
            }
        }
        int min = Math.min(((int) Math.ceil(d / this.fragmentLength)) - 1, track.getSamples().size());
        if (min < 1) {
            min = 1;
        }
        long[] jArr = new long[min];
        Arrays.fill(jArr, -1L);
        jArr[0] = 1;
        long j = 0;
        int i = 0;
        for (long j2 : track.getSampleDurations()) {
            int timescale = ((int) ((j / track.getTrackMetaData().getTimescale()) / this.fragmentLength)) + 1;
            if (timescale >= jArr.length) {
                break;
            }
            i++;
            jArr[timescale] = i;
            j += j2;
        }
        long j3 = i + 1;
        for (int length = jArr.length - 1; length >= 0; length--) {
            if (jArr[length] == -1) {
                jArr[length] = j3;
            }
            j3 = jArr[length];
        }
        long[] jArr2 = new long[0];
        for (long j4 : jArr) {
            if (jArr2.length == 0 || jArr2[jArr2.length - 1] != j4) {
                jArr2 = Arrays.copyOf(jArr2, jArr2.length + 1);
                jArr2[jArr2.length - 1] = j4;
            }
        }
        return jArr2;
    }
}
