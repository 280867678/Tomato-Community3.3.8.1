package com.google.android.exoplayer2.source.hls;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Pair;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.drm.DrmInitData;
import com.google.android.exoplayer2.extractor.Extractor;
import com.google.android.exoplayer2.extractor.mp3.Mp3Extractor;
import com.google.android.exoplayer2.extractor.mp4.FragmentedMp4Extractor;
import com.google.android.exoplayer2.extractor.p062ts.Ac3Extractor;
import com.google.android.exoplayer2.extractor.p062ts.AdtsExtractor;
import com.google.android.exoplayer2.extractor.p062ts.DefaultTsPayloadReaderFactory;
import com.google.android.exoplayer2.extractor.p062ts.TsExtractor;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.TimestampAdjuster;
import com.iceteck.silicompressorr.videocompression.MediaController;
import java.util.Collections;
import java.util.List;

/* loaded from: classes3.dex */
public final class DefaultHlsExtractorFactory implements HlsExtractorFactory {
    @Override // com.google.android.exoplayer2.source.hls.HlsExtractorFactory
    public Pair<Extractor, Boolean> createExtractor(Extractor extractor, Uri uri, Format format, List<Format> list, DrmInitData drmInitData, TimestampAdjuster timestampAdjuster) {
        String lastPathSegment = uri.getLastPathSegment();
        if (lastPathSegment == null) {
            lastPathSegment = "";
        }
        boolean z = false;
        if ("text/vtt".equals(format.sampleMimeType) || lastPathSegment.endsWith(".webvtt") || lastPathSegment.endsWith(".vtt")) {
            extractor = new WebvttExtractor(format.language, timestampAdjuster);
        } else {
            if (lastPathSegment.endsWith(".aac")) {
                extractor = new AdtsExtractor();
            } else if (lastPathSegment.endsWith(".ac3") || lastPathSegment.endsWith(".ec3")) {
                extractor = new Ac3Extractor();
            } else if (lastPathSegment.endsWith(".mp3")) {
                extractor = new Mp3Extractor(0, 0L);
            } else if (extractor == null) {
                if (lastPathSegment.endsWith(".mp4") || lastPathSegment.startsWith(".m4", lastPathSegment.length() - 4) || lastPathSegment.startsWith(".mp4", lastPathSegment.length() - 5)) {
                    if (list == null) {
                        list = Collections.emptyList();
                    }
                    extractor = new FragmentedMp4Extractor(0, timestampAdjuster, null, drmInitData, list);
                } else {
                    int i = 16;
                    if (list != null) {
                        i = 48;
                    } else {
                        list = Collections.emptyList();
                    }
                    String str = format.codecs;
                    if (!TextUtils.isEmpty(str)) {
                        if (!"audio/mp4a-latm".equals(MimeTypes.getAudioMediaMimeType(str))) {
                            i |= 2;
                        }
                        if (!MediaController.MIME_TYPE.equals(MimeTypes.getVideoMediaMimeType(str))) {
                            i |= 4;
                        }
                    }
                    extractor = new TsExtractor(2, timestampAdjuster, new DefaultTsPayloadReaderFactory(i, list));
                }
            }
            z = true;
        }
        return Pair.create(extractor, Boolean.valueOf(z));
    }
}
