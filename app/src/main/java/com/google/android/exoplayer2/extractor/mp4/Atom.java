package com.google.android.exoplayer2.extractor.mp4;

import android.support.p002v4.view.ViewCompat;
import com.coremedia.iso.boxes.ChunkOffset64BitBox;
import com.coremedia.iso.boxes.CompositionTimeToSample;
import com.coremedia.iso.boxes.EditBox;
import com.coremedia.iso.boxes.EditListBox;
import com.coremedia.iso.boxes.FileTypeBox;
import com.coremedia.iso.boxes.HandlerBox;
import com.coremedia.iso.boxes.MediaBox;
import com.coremedia.iso.boxes.MediaHeaderBox;
import com.coremedia.iso.boxes.MediaInformationBox;
import com.coremedia.iso.boxes.MetaBox;
import com.coremedia.iso.boxes.MovieBox;
import com.coremedia.iso.boxes.MovieHeaderBox;
import com.coremedia.iso.boxes.OriginalFormatBox;
import com.coremedia.iso.boxes.ProtectionSchemeInformationBox;
import com.coremedia.iso.boxes.SampleDescriptionBox;
import com.coremedia.iso.boxes.SampleSizeBox;
import com.coremedia.iso.boxes.SampleTableBox;
import com.coremedia.iso.boxes.SampleToChunkBox;
import com.coremedia.iso.boxes.SchemeInformationBox;
import com.coremedia.iso.boxes.SchemeTypeBox;
import com.coremedia.iso.boxes.StaticChunkOffsetBox;
import com.coremedia.iso.boxes.SyncSampleBox;
import com.coremedia.iso.boxes.TimeToSampleBox;
import com.coremedia.iso.boxes.TrackBox;
import com.coremedia.iso.boxes.TrackHeaderBox;
import com.coremedia.iso.boxes.UserBox;
import com.coremedia.iso.boxes.UserDataBox;
import com.coremedia.iso.boxes.VideoMediaHeaderBox;
import com.coremedia.iso.boxes.apple.AppleItemListBox;
import com.coremedia.iso.boxes.apple.AppleWaveBox;
import com.coremedia.iso.boxes.fragment.MovieExtendsBox;
import com.coremedia.iso.boxes.fragment.MovieExtendsHeaderBox;
import com.coremedia.iso.boxes.fragment.MovieFragmentBox;
import com.coremedia.iso.boxes.fragment.TrackExtendsBox;
import com.coremedia.iso.boxes.fragment.TrackFragmentBaseMediaDecodeTimeBox;
import com.coremedia.iso.boxes.fragment.TrackFragmentBox;
import com.coremedia.iso.boxes.fragment.TrackFragmentHeaderBox;
import com.coremedia.iso.boxes.fragment.TrackRunBox;
import com.coremedia.iso.boxes.mdat.MediaDataBox;
import com.coremedia.iso.boxes.sampleentry.AudioSampleEntry;
import com.coremedia.iso.boxes.sampleentry.TextSampleEntry;
import com.coremedia.iso.boxes.sampleentry.VisualSampleEntry;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import com.googlecode.mp4parser.boxes.AC3SpecificBox;
import com.googlecode.mp4parser.boxes.DTSSpecificBox;
import com.googlecode.mp4parser.boxes.EC3SpecificBox;
import com.googlecode.mp4parser.boxes.apple.PixelAspectRationAtom;
import com.googlecode.mp4parser.boxes.dece.SampleEncryptionBox;
import com.googlecode.mp4parser.boxes.mp4.ESDescriptorBox;
import com.googlecode.mp4parser.boxes.mp4.samplegrouping.SampleGroupDescriptionBox;
import com.googlecode.mp4parser.boxes.mp4.samplegrouping.SampleToGroupBox;
import com.googlecode.mp4parser.boxes.threegpp26244.SegmentIndexBox;
import com.mp4parser.iso14496.part12.SampleAuxiliaryInformationOffsetsBox;
import com.mp4parser.iso14496.part12.SampleAuxiliaryInformationSizesBox;
import com.mp4parser.iso14496.part15.AvcConfigurationBox;
import com.mp4parser.iso14496.part15.HevcConfigurationBox;
import com.mp4parser.iso14496.part30.WebVTTSampleEntry;
import com.mp4parser.iso14496.part30.XMLSubtitleSampleEntry;
import com.mp4parser.iso23001.part7.ProtectionSystemSpecificHeaderBox;
import com.mp4parser.iso23009.part1.EventMessageBox;
import com.sensorsdata.analytics.android.sdk.AopConstants;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes2.dex */
abstract class Atom {
    public final int type;
    public static final int TYPE_ftyp = Util.getIntegerCodeForString(FileTypeBox.TYPE);
    public static final int TYPE_avc1 = Util.getIntegerCodeForString(VisualSampleEntry.TYPE3);
    public static final int TYPE_avc3 = Util.getIntegerCodeForString(VisualSampleEntry.TYPE4);
    public static final int TYPE_hvc1 = Util.getIntegerCodeForString(VisualSampleEntry.TYPE6);
    public static final int TYPE_hev1 = Util.getIntegerCodeForString(VisualSampleEntry.TYPE7);
    public static final int TYPE_s263 = Util.getIntegerCodeForString(VisualSampleEntry.TYPE2);
    public static final int TYPE_d263 = Util.getIntegerCodeForString("d263");
    public static final int TYPE_mdat = Util.getIntegerCodeForString(MediaDataBox.TYPE);
    public static final int TYPE_mp4a = Util.getIntegerCodeForString(AudioSampleEntry.TYPE3);
    public static final int TYPE__mp3 = Util.getIntegerCodeForString(".mp3");
    public static final int TYPE_wave = Util.getIntegerCodeForString(AppleWaveBox.TYPE);
    public static final int TYPE_lpcm = Util.getIntegerCodeForString("lpcm");
    public static final int TYPE_sowt = Util.getIntegerCodeForString("sowt");
    public static final int TYPE_ac_3 = Util.getIntegerCodeForString(AudioSampleEntry.TYPE8);
    public static final int TYPE_dac3 = Util.getIntegerCodeForString(AC3SpecificBox.TYPE);
    public static final int TYPE_ec_3 = Util.getIntegerCodeForString(AudioSampleEntry.TYPE9);
    public static final int TYPE_dec3 = Util.getIntegerCodeForString(EC3SpecificBox.TYPE);
    public static final int TYPE_dtsc = Util.getIntegerCodeForString("dtsc");
    public static final int TYPE_dtsh = Util.getIntegerCodeForString(AudioSampleEntry.TYPE12);
    public static final int TYPE_dtsl = Util.getIntegerCodeForString(AudioSampleEntry.TYPE11);
    public static final int TYPE_dtse = Util.getIntegerCodeForString(AudioSampleEntry.TYPE13);
    public static final int TYPE_ddts = Util.getIntegerCodeForString(DTSSpecificBox.TYPE);
    public static final int TYPE_tfdt = Util.getIntegerCodeForString(TrackFragmentBaseMediaDecodeTimeBox.TYPE);
    public static final int TYPE_tfhd = Util.getIntegerCodeForString(TrackFragmentHeaderBox.TYPE);
    public static final int TYPE_trex = Util.getIntegerCodeForString(TrackExtendsBox.TYPE);
    public static final int TYPE_trun = Util.getIntegerCodeForString(TrackRunBox.TYPE);
    public static final int TYPE_sidx = Util.getIntegerCodeForString(SegmentIndexBox.TYPE);
    public static final int TYPE_moov = Util.getIntegerCodeForString(MovieBox.TYPE);
    public static final int TYPE_mvhd = Util.getIntegerCodeForString(MovieHeaderBox.TYPE);
    public static final int TYPE_trak = Util.getIntegerCodeForString(TrackBox.TYPE);
    public static final int TYPE_mdia = Util.getIntegerCodeForString(MediaBox.TYPE);
    public static final int TYPE_minf = Util.getIntegerCodeForString(MediaInformationBox.TYPE);
    public static final int TYPE_stbl = Util.getIntegerCodeForString(SampleTableBox.TYPE);
    public static final int TYPE_avcC = Util.getIntegerCodeForString(AvcConfigurationBox.TYPE);
    public static final int TYPE_hvcC = Util.getIntegerCodeForString(HevcConfigurationBox.TYPE);
    public static final int TYPE_esds = Util.getIntegerCodeForString(ESDescriptorBox.TYPE);
    public static final int TYPE_moof = Util.getIntegerCodeForString(MovieFragmentBox.TYPE);
    public static final int TYPE_traf = Util.getIntegerCodeForString(TrackFragmentBox.TYPE);
    public static final int TYPE_mvex = Util.getIntegerCodeForString(MovieExtendsBox.TYPE);
    public static final int TYPE_mehd = Util.getIntegerCodeForString(MovieExtendsHeaderBox.TYPE);
    public static final int TYPE_tkhd = Util.getIntegerCodeForString(TrackHeaderBox.TYPE);
    public static final int TYPE_edts = Util.getIntegerCodeForString(EditBox.TYPE);
    public static final int TYPE_elst = Util.getIntegerCodeForString(EditListBox.TYPE);
    public static final int TYPE_mdhd = Util.getIntegerCodeForString(MediaHeaderBox.TYPE);
    public static final int TYPE_hdlr = Util.getIntegerCodeForString(HandlerBox.TYPE);
    public static final int TYPE_stsd = Util.getIntegerCodeForString(SampleDescriptionBox.TYPE);
    public static final int TYPE_pssh = Util.getIntegerCodeForString(ProtectionSystemSpecificHeaderBox.TYPE);
    public static final int TYPE_sinf = Util.getIntegerCodeForString(ProtectionSchemeInformationBox.TYPE);
    public static final int TYPE_schm = Util.getIntegerCodeForString(SchemeTypeBox.TYPE);
    public static final int TYPE_schi = Util.getIntegerCodeForString(SchemeInformationBox.TYPE);
    public static final int TYPE_tenc = Util.getIntegerCodeForString(com.mp4parser.iso23001.part7.TrackEncryptionBox.TYPE);
    public static final int TYPE_encv = Util.getIntegerCodeForString(VisualSampleEntry.TYPE_ENCRYPTED);
    public static final int TYPE_enca = Util.getIntegerCodeForString(AudioSampleEntry.TYPE_ENCRYPTED);
    public static final int TYPE_frma = Util.getIntegerCodeForString(OriginalFormatBox.TYPE);
    public static final int TYPE_saiz = Util.getIntegerCodeForString(SampleAuxiliaryInformationSizesBox.TYPE);
    public static final int TYPE_saio = Util.getIntegerCodeForString(SampleAuxiliaryInformationOffsetsBox.TYPE);
    public static final int TYPE_sbgp = Util.getIntegerCodeForString(SampleToGroupBox.TYPE);
    public static final int TYPE_sgpd = Util.getIntegerCodeForString(SampleGroupDescriptionBox.TYPE);
    public static final int TYPE_uuid = Util.getIntegerCodeForString(UserBox.TYPE);
    public static final int TYPE_senc = Util.getIntegerCodeForString(SampleEncryptionBox.TYPE);
    public static final int TYPE_pasp = Util.getIntegerCodeForString(PixelAspectRationAtom.TYPE);
    public static final int TYPE_TTML = Util.getIntegerCodeForString("TTML");
    public static final int TYPE_mp4v = Util.getIntegerCodeForString(VisualSampleEntry.TYPE1);
    public static final int TYPE_stts = Util.getIntegerCodeForString(TimeToSampleBox.TYPE);
    public static final int TYPE_stss = Util.getIntegerCodeForString(SyncSampleBox.TYPE);
    public static final int TYPE_ctts = Util.getIntegerCodeForString(CompositionTimeToSample.TYPE);
    public static final int TYPE_stsc = Util.getIntegerCodeForString(SampleToChunkBox.TYPE);
    public static final int TYPE_stsz = Util.getIntegerCodeForString(SampleSizeBox.TYPE);
    public static final int TYPE_stz2 = Util.getIntegerCodeForString("stz2");
    public static final int TYPE_stco = Util.getIntegerCodeForString(StaticChunkOffsetBox.TYPE);
    public static final int TYPE_co64 = Util.getIntegerCodeForString(ChunkOffset64BitBox.TYPE);
    public static final int TYPE_tx3g = Util.getIntegerCodeForString(TextSampleEntry.TYPE1);
    public static final int TYPE_wvtt = Util.getIntegerCodeForString(WebVTTSampleEntry.TYPE);
    public static final int TYPE_stpp = Util.getIntegerCodeForString(XMLSubtitleSampleEntry.TYPE);
    public static final int TYPE_c608 = Util.getIntegerCodeForString("c608");
    public static final int TYPE_samr = Util.getIntegerCodeForString(AudioSampleEntry.TYPE1);
    public static final int TYPE_sawb = Util.getIntegerCodeForString(AudioSampleEntry.TYPE2);
    public static final int TYPE_udta = Util.getIntegerCodeForString(UserDataBox.TYPE);
    public static final int TYPE_meta = Util.getIntegerCodeForString(MetaBox.TYPE);
    public static final int TYPE_ilst = Util.getIntegerCodeForString(AppleItemListBox.TYPE);
    public static final int TYPE_mean = Util.getIntegerCodeForString("mean");
    public static final int TYPE_name = Util.getIntegerCodeForString("name");
    public static final int TYPE_data = Util.getIntegerCodeForString(AopConstants.APP_PROPERTIES_KEY);
    public static final int TYPE_emsg = Util.getIntegerCodeForString(EventMessageBox.TYPE);
    public static final int TYPE_st3d = Util.getIntegerCodeForString("st3d");
    public static final int TYPE_sv3d = Util.getIntegerCodeForString("sv3d");
    public static final int TYPE_proj = Util.getIntegerCodeForString("proj");
    public static final int TYPE_vp08 = Util.getIntegerCodeForString("vp08");
    public static final int TYPE_vp09 = Util.getIntegerCodeForString("vp09");
    public static final int TYPE_vpcC = Util.getIntegerCodeForString("vpcC");
    public static final int TYPE_camm = Util.getIntegerCodeForString("camm");
    public static final int TYPE_alac = Util.getIntegerCodeForString("alac");

    public static int parseFullAtomFlags(int i) {
        return i & ViewCompat.MEASURED_SIZE_MASK;
    }

    public static int parseFullAtomVersion(int i) {
        return (i >> 24) & 255;
    }

    static {
        Util.getIntegerCodeForString(VideoMediaHeaderBox.TYPE);
    }

    public Atom(int i) {
        this.type = i;
    }

    public String toString() {
        return getAtomTypeString(this.type);
    }

    /* loaded from: classes2.dex */
    static final class LeafAtom extends Atom {
        public final ParsableByteArray data;

        public LeafAtom(int i, ParsableByteArray parsableByteArray) {
            super(i);
            this.data = parsableByteArray;
        }
    }

    /* loaded from: classes2.dex */
    static final class ContainerAtom extends Atom {
        public final long endPosition;
        public final List<LeafAtom> leafChildren = new ArrayList();
        public final List<ContainerAtom> containerChildren = new ArrayList();

        public ContainerAtom(int i, long j) {
            super(i);
            this.endPosition = j;
        }

        public void add(LeafAtom leafAtom) {
            this.leafChildren.add(leafAtom);
        }

        public void add(ContainerAtom containerAtom) {
            this.containerChildren.add(containerAtom);
        }

        public LeafAtom getLeafAtomOfType(int i) {
            int size = this.leafChildren.size();
            for (int i2 = 0; i2 < size; i2++) {
                LeafAtom leafAtom = this.leafChildren.get(i2);
                if (leafAtom.type == i) {
                    return leafAtom;
                }
            }
            return null;
        }

        public ContainerAtom getContainerAtomOfType(int i) {
            int size = this.containerChildren.size();
            for (int i2 = 0; i2 < size; i2++) {
                ContainerAtom containerAtom = this.containerChildren.get(i2);
                if (containerAtom.type == i) {
                    return containerAtom;
                }
            }
            return null;
        }

        @Override // com.google.android.exoplayer2.extractor.mp4.Atom
        public String toString() {
            return Atom.getAtomTypeString(this.type) + " leaves: " + Arrays.toString(this.leafChildren.toArray()) + " containers: " + Arrays.toString(this.containerChildren.toArray());
        }
    }

    public static String getAtomTypeString(int i) {
        return "" + ((char) ((i >> 24) & 255)) + ((char) ((i >> 16) & 255)) + ((char) ((i >> 8) & 255)) + ((char) (i & 255));
    }
}
