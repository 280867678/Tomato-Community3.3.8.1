package com.google.android.exoplayer2.source.dash;

import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.FormatHolder;
import com.google.android.exoplayer2.decoder.DecoderInputBuffer;
import com.google.android.exoplayer2.metadata.emsg.EventMessageEncoder;
import com.google.android.exoplayer2.source.SampleStream;
import com.google.android.exoplayer2.source.dash.manifest.EventStream;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;

/* loaded from: classes3.dex */
final class EventSampleStream implements SampleStream {
    private int currentIndex;
    private EventStream eventStream;
    private boolean eventStreamAppendable;
    private long[] eventTimesUs;
    private boolean isFormatSentDownstream;
    private final Format upstreamFormat;
    private final EventMessageEncoder eventMessageEncoder = new EventMessageEncoder();
    private long pendingSeekPositionUs = -9223372036854775807L;

    @Override // com.google.android.exoplayer2.source.SampleStream
    public boolean isReady() {
        return true;
    }

    @Override // com.google.android.exoplayer2.source.SampleStream
    public void maybeThrowError() throws IOException {
    }

    public EventSampleStream(EventStream eventStream, Format format, boolean z) {
        this.upstreamFormat = format;
        this.eventStream = eventStream;
        this.eventTimesUs = eventStream.presentationTimesUs;
        updateEventStream(eventStream, z);
    }

    public void updateEventStream(EventStream eventStream, boolean z) {
        int i = this.currentIndex;
        long j = i == 0 ? -9223372036854775807L : this.eventTimesUs[i - 1];
        this.eventStreamAppendable = z;
        this.eventStream = eventStream;
        this.eventTimesUs = eventStream.presentationTimesUs;
        long j2 = this.pendingSeekPositionUs;
        if (j2 != -9223372036854775807L) {
            seekToUs(j2);
        } else if (j == -9223372036854775807L) {
        } else {
            this.currentIndex = Util.binarySearchCeil(this.eventTimesUs, j, false, false);
        }
    }

    public void seekToUs(long j) {
        boolean z = false;
        this.currentIndex = Util.binarySearchCeil(this.eventTimesUs, j, true, false);
        if (this.eventStreamAppendable && this.currentIndex == this.eventTimesUs.length) {
            z = true;
        }
        if (!z) {
            j = -9223372036854775807L;
        }
        this.pendingSeekPositionUs = j;
    }

    @Override // com.google.android.exoplayer2.source.SampleStream
    public int readData(FormatHolder formatHolder, DecoderInputBuffer decoderInputBuffer, boolean z) {
        if (z || !this.isFormatSentDownstream) {
            formatHolder.format = this.upstreamFormat;
            this.isFormatSentDownstream = true;
            return -5;
        }
        int i = this.currentIndex;
        if (i == this.eventTimesUs.length) {
            if (this.eventStreamAppendable) {
                return -3;
            }
            decoderInputBuffer.setFlags(4);
            return -4;
        }
        this.currentIndex = i + 1;
        EventMessageEncoder eventMessageEncoder = this.eventMessageEncoder;
        EventStream eventStream = this.eventStream;
        byte[] encode = eventMessageEncoder.encode(eventStream.events[i], eventStream.timescale);
        if (encode == null) {
            return -3;
        }
        decoderInputBuffer.ensureSpaceForWrite(encode.length);
        decoderInputBuffer.setFlags(1);
        decoderInputBuffer.data.put(encode);
        decoderInputBuffer.timeUs = this.eventTimesUs[i];
        return -4;
    }

    @Override // com.google.android.exoplayer2.source.SampleStream
    public int skipData(long j) {
        int max = Math.max(this.currentIndex, Util.binarySearchCeil(this.eventTimesUs, j, true, false));
        int i = max - this.currentIndex;
        this.currentIndex = max;
        return i;
    }
}
