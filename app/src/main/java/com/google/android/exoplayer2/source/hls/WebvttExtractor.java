package com.google.android.exoplayer2.source.hls;

import android.text.TextUtils;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.drm.DrmInitData;
import com.google.android.exoplayer2.extractor.Extractor;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.PositionHolder;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.text.SubtitleDecoderException;
import com.google.android.exoplayer2.text.webvtt.WebvttParserUtil;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.TimestampAdjuster;
import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes3.dex */
final class WebvttExtractor implements Extractor {
    private static final Pattern LOCAL_TIMESTAMP = Pattern.compile("LOCAL:([^,]+)");
    private static final Pattern MEDIA_TIMESTAMP = Pattern.compile("MPEGTS:(\\d+)");
    private final String language;
    private ExtractorOutput output;
    private int sampleSize;
    private final TimestampAdjuster timestampAdjuster;
    private final ParsableByteArray sampleDataWrapper = new ParsableByteArray();
    private byte[] sampleData = new byte[1024];

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public void release() {
    }

    public WebvttExtractor(String str, TimestampAdjuster timestampAdjuster) {
        this.language = str;
        this.timestampAdjuster = timestampAdjuster;
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public boolean sniff(ExtractorInput extractorInput) throws IOException, InterruptedException {
        throw new IllegalStateException();
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public void init(ExtractorOutput extractorOutput) {
        this.output = extractorOutput;
        extractorOutput.seekMap(new SeekMap.Unseekable(-9223372036854775807L));
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public void seek(long j, long j2) {
        throw new IllegalStateException();
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public int read(ExtractorInput extractorInput, PositionHolder positionHolder) throws IOException, InterruptedException {
        int length = (int) extractorInput.getLength();
        int i = this.sampleSize;
        byte[] bArr = this.sampleData;
        if (i == bArr.length) {
            this.sampleData = Arrays.copyOf(bArr, ((length != -1 ? length : bArr.length) * 3) / 2);
        }
        byte[] bArr2 = this.sampleData;
        int i2 = this.sampleSize;
        int read = extractorInput.read(bArr2, i2, bArr2.length - i2);
        if (read != -1) {
            this.sampleSize += read;
            if (length == -1 || this.sampleSize != length) {
                return 0;
            }
        }
        processSample();
        return -1;
    }

    private void processSample() throws ParserException {
        ParsableByteArray parsableByteArray = new ParsableByteArray(this.sampleData);
        try {
            WebvttParserUtil.validateWebvttHeaderLine(parsableByteArray);
            long j = 0;
            long j2 = 0;
            while (true) {
                String readLine = parsableByteArray.readLine();
                if (!TextUtils.isEmpty(readLine)) {
                    if (readLine.startsWith("X-TIMESTAMP-MAP")) {
                        Matcher matcher = LOCAL_TIMESTAMP.matcher(readLine);
                        if (!matcher.find()) {
                            throw new ParserException("X-TIMESTAMP-MAP doesn't contain local timestamp: " + readLine);
                        }
                        Matcher matcher2 = MEDIA_TIMESTAMP.matcher(readLine);
                        if (!matcher2.find()) {
                            throw new ParserException("X-TIMESTAMP-MAP doesn't contain media timestamp: " + readLine);
                        }
                        j2 = WebvttParserUtil.parseTimestampUs(matcher.group(1));
                        j = TimestampAdjuster.ptsToUs(Long.parseLong(matcher2.group(1)));
                    }
                } else {
                    Matcher findNextCueHeader = WebvttParserUtil.findNextCueHeader(parsableByteArray);
                    if (findNextCueHeader == null) {
                        buildTrackOutput(0L);
                        return;
                    }
                    long parseTimestampUs = WebvttParserUtil.parseTimestampUs(findNextCueHeader.group(1));
                    long adjustTsTimestamp = this.timestampAdjuster.adjustTsTimestamp(TimestampAdjuster.usToPts((j + parseTimestampUs) - j2));
                    TrackOutput buildTrackOutput = buildTrackOutput(adjustTsTimestamp - parseTimestampUs);
                    this.sampleDataWrapper.reset(this.sampleData, this.sampleSize);
                    buildTrackOutput.sampleData(this.sampleDataWrapper, this.sampleSize);
                    buildTrackOutput.sampleMetadata(adjustTsTimestamp, 1, this.sampleSize, 0, null);
                    return;
                }
            }
        } catch (SubtitleDecoderException e) {
            throw new ParserException(e);
        }
    }

    private TrackOutput buildTrackOutput(long j) {
        TrackOutput track = this.output.track(0, 3);
        track.format(Format.createTextSampleFormat((String) null, "text/vtt", (String) null, -1, 0, this.language, (DrmInitData) null, j));
        this.output.endTracks();
        return track;
    }
}
