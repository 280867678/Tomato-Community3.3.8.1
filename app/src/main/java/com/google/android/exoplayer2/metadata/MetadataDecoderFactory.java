package com.google.android.exoplayer2.metadata;

import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.metadata.emsg.EventMessageDecoder;
import com.google.android.exoplayer2.metadata.id3.Id3Decoder;
import com.google.android.exoplayer2.metadata.scte35.SpliceInfoDecoder;

/* loaded from: classes.dex */
public interface MetadataDecoderFactory {
    public static final MetadataDecoderFactory DEFAULT = new MetadataDecoderFactory() { // from class: com.google.android.exoplayer2.metadata.MetadataDecoderFactory.1
        @Override // com.google.android.exoplayer2.metadata.MetadataDecoderFactory
        public boolean supportsFormat(Format format) {
            String str = format.sampleMimeType;
            return "application/id3".equals(str) || "application/x-emsg".equals(str) || "application/x-scte35".equals(str);
        }

        @Override // com.google.android.exoplayer2.metadata.MetadataDecoderFactory
        public MetadataDecoder createDecoder(Format format) {
            char c;
            String str = format.sampleMimeType;
            int hashCode = str.hashCode();
            if (hashCode == -1248341703) {
                if (str.equals("application/id3")) {
                    c = 0;
                }
                c = 65535;
            } else if (hashCode != 1154383568) {
                if (hashCode == 1652648887 && str.equals("application/x-scte35")) {
                    c = 2;
                }
                c = 65535;
            } else {
                if (str.equals("application/x-emsg")) {
                    c = 1;
                }
                c = 65535;
            }
            if (c != 0) {
                if (c == 1) {
                    return new EventMessageDecoder();
                }
                if (c == 2) {
                    return new SpliceInfoDecoder();
                }
                throw new IllegalArgumentException("Attempted to create decoder for unsupported format");
            }
            return new Id3Decoder();
        }
    };

    MetadataDecoder createDecoder(Format format);

    boolean supportsFormat(Format format);
}
