package com.googlecode.mp4parser.authoring.tracks.h265;

import com.googlecode.mp4parser.h264.read.CAVLCReader;
import com.googlecode.mp4parser.util.ByteBufferByteChannel;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;

/* loaded from: classes3.dex */
public class VideoParameterSet {
    ByteBuffer vps;
    int vps_parameter_set_id;

    public VideoParameterSet(ByteBuffer byteBuffer) throws IOException {
        this.vps = byteBuffer;
        CAVLCReader cAVLCReader = new CAVLCReader(Channels.newInputStream(new ByteBufferByteChannel((ByteBuffer) byteBuffer.position(0))));
        this.vps_parameter_set_id = cAVLCReader.readU(4, "vps_parameter_set_id");
        cAVLCReader.readU(2, "vps_reserved_three_2bits");
        cAVLCReader.readU(6, "vps_max_layers_minus1");
        int readU = cAVLCReader.readU(3, "vps_max_sub_layers_minus1");
        cAVLCReader.readBool("vps_temporal_id_nesting_flag");
        cAVLCReader.readU(16, "vps_reserved_0xffff_16bits");
        profile_tier_level(readU, cAVLCReader);
        boolean readBool = cAVLCReader.readBool("vps_sub_layer_ordering_info_present_flag");
        int[] iArr = new int[readBool ? 1 : readU + 1];
        int[] iArr2 = new int[readBool ? 1 : readU + 1];
        int[] iArr3 = new int[readBool ? 1 : readU + 1];
        for (int i = readBool ? 0 : readU; i <= readU; i++) {
            iArr[i] = cAVLCReader.readUE("vps_max_dec_pic_buffering_minus1[" + i + "]");
            iArr2[i] = cAVLCReader.readUE("vps_max_dec_pic_buffering_minus1[" + i + "]");
            iArr3[i] = cAVLCReader.readUE("vps_max_dec_pic_buffering_minus1[" + i + "]");
        }
        int readU2 = cAVLCReader.readU(6, "vps_max_layer_id");
        int readUE = cAVLCReader.readUE("vps_num_layer_sets_minus1");
        boolean[][] zArr = (boolean[][]) Array.newInstance(boolean.class, readUE, readU2);
        for (int i2 = 1; i2 <= readUE; i2++) {
            for (int i3 = 0; i3 <= readU2; i3++) {
                boolean[] zArr2 = zArr[i2];
                zArr2[i3] = cAVLCReader.readBool("layer_id_included_flag[" + i2 + "][" + i3 + "]");
            }
        }
        if (cAVLCReader.readBool("vps_timing_info_present_flag")) {
            cAVLCReader.readU(32, "vps_num_units_in_tick");
            cAVLCReader.readU(32, "vps_time_scale");
            if (cAVLCReader.readBool("vps_poc_proportional_to_timing_flag")) {
                cAVLCReader.readUE("vps_num_ticks_poc_diff_one_minus1");
            }
            int readUE2 = cAVLCReader.readUE("vps_num_hrd_parameters");
            int[] iArr4 = new int[readUE2];
            boolean[] zArr3 = new boolean[readUE2];
            for (int i4 = 0; i4 < readUE2; i4++) {
                iArr4[i4] = cAVLCReader.readUE("hrd_layer_set_idx[" + i4 + "]");
                if (i4 > 0) {
                    zArr3[i4] = cAVLCReader.readBool("cprms_present_flag[" + i4 + "]");
                } else {
                    zArr3[0] = true;
                }
                hrd_parameters(zArr3[i4], readU, cAVLCReader);
            }
        }
        if (cAVLCReader.readBool("vps_extension_flag")) {
            while (cAVLCReader.moreRBSPData()) {
                cAVLCReader.readBool("vps_extension_data_flag");
            }
        }
        cAVLCReader.readTrailingBits();
    }

    public void profile_tier_level(int i, CAVLCReader cAVLCReader) throws IOException {
        boolean[] zArr;
        int i2 = i;
        cAVLCReader.readU(2, "general_profile_space ");
        cAVLCReader.readBool("general_tier_flag");
        cAVLCReader.readU(5, "general_profile_idc");
        boolean[] zArr2 = new boolean[32];
        for (int i3 = 0; i3 < 32; i3++) {
            zArr2[i3] = cAVLCReader.readBool("general_profile_compatibility_flag[" + i3 + "]");
        }
        cAVLCReader.readBool("general_progressive_source_flag");
        cAVLCReader.readBool("general_interlaced_source_flag");
        cAVLCReader.readBool("general_non_packed_constraint_flag");
        cAVLCReader.readBool("general_frame_only_constraint_flag");
        cAVLCReader.readU(44, "general_reserved_zero_44bits");
        cAVLCReader.readU(8, "general_level_idc");
        boolean[] zArr3 = new boolean[i2];
        boolean[] zArr4 = new boolean[i2];
        for (int i4 = 0; i4 < i2; i4++) {
            zArr3[i4] = cAVLCReader.readBool("sub_layer_profile_present_flag[" + i4 + "]");
            zArr4[i4] = cAVLCReader.readBool("sub_layer_level_present_flag[" + i4 + "]");
        }
        if (i2 > 0) {
            for (int i5 = i2; i5 < 8; i5++) {
                cAVLCReader.readU(2, "reserved_zero_2bits");
            }
        }
        int[] iArr = new int[i2];
        boolean[] zArr5 = new boolean[i2];
        int[] iArr2 = new int[i2];
        boolean[][] zArr6 = (boolean[][]) Array.newInstance(boolean.class, i2, 32);
        boolean[] zArr7 = new boolean[i2];
        boolean[] zArr8 = new boolean[i2];
        boolean[] zArr9 = new boolean[i2];
        boolean[] zArr10 = new boolean[i2];
        int[] iArr3 = new int[i2];
        int i6 = 0;
        while (i6 < i2) {
            if (zArr3[i6]) {
                iArr[i6] = cAVLCReader.readU(2, "sub_layer_profile_space[" + i6 + "]");
                zArr5[i6] = cAVLCReader.readBool("sub_layer_tier_flag[" + i6 + "]");
                iArr2[i6] = cAVLCReader.readU(5, "sub_layer_profile_idc[" + i6 + "]");
                int i7 = 0;
                while (i7 < 32) {
                    boolean[] zArr11 = zArr6[i6];
                    zArr11[i7] = cAVLCReader.readBool("sub_layer_profile_compatibility_flag[" + i6 + "][" + i7 + "]");
                    i7++;
                    zArr3 = zArr3;
                }
                zArr = zArr3;
                zArr7[i6] = cAVLCReader.readBool("sub_layer_progressive_source_flag[" + i6 + "]");
                zArr8[i6] = cAVLCReader.readBool("sub_layer_interlaced_source_flag[" + i6 + "]");
                zArr9[i6] = cAVLCReader.readBool("sub_layer_non_packed_constraint_flag[" + i6 + "]");
                zArr10[i6] = cAVLCReader.readBool("sub_layer_frame_only_constraint_flag[" + i6 + "]");
                cAVLCReader.readNBit(44, "reserved");
            } else {
                zArr = zArr3;
            }
            if (zArr4[i6]) {
                iArr3[i6] = cAVLCReader.readU(8, "sub_layer_level_idc");
            }
            i6++;
            i2 = i;
            zArr3 = zArr;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:9:0x0070  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void hrd_parameters(boolean z, int i, CAVLCReader cAVLCReader) throws IOException {
        boolean z2;
        boolean z3;
        boolean z4;
        if (z) {
            z2 = cAVLCReader.readBool("nal_hrd_parameters_present_flag");
            z3 = cAVLCReader.readBool("vcl_hrd_parameters_present_flag");
            if (z2 || z3) {
                z4 = cAVLCReader.readBool("sub_pic_hrd_params_present_flag");
                if (z4) {
                    cAVLCReader.readU(8, "tick_divisor_minus2");
                    cAVLCReader.readU(5, "du_cpb_removal_delay_increment_length_minus1");
                    cAVLCReader.readBool("sub_pic_cpb_params_in_pic_timing_sei_flag");
                    cAVLCReader.readU(5, "dpb_output_delay_du_length_minus1");
                }
                cAVLCReader.readU(4, "bit_rate_scale");
                cAVLCReader.readU(4, "cpb_size_scale");
                if (z4) {
                    cAVLCReader.readU(4, "cpb_size_du_scale");
                }
                cAVLCReader.readU(5, "initial_cpb_removal_delay_length_minus1");
                cAVLCReader.readU(5, "au_cpb_removal_delay_length_minus1");
                cAVLCReader.readU(5, "dpb_output_delay_length_minus1");
                boolean[] zArr = new boolean[i];
                boolean[] zArr2 = new boolean[i];
                boolean[] zArr3 = new boolean[i];
                int[] iArr = new int[i];
                int[] iArr2 = new int[i];
                for (int i2 = 0; i2 <= i; i2++) {
                    zArr[i2] = cAVLCReader.readBool("fixed_pic_rate_general_flag[" + i2 + "]");
                    if (!zArr[i2]) {
                        zArr2[i2] = cAVLCReader.readBool("fixed_pic_rate_within_cvs_flag[" + i2 + "]");
                    }
                    if (zArr2[i2]) {
                        iArr2[i2] = cAVLCReader.readUE("elemental_duration_in_tc_minus1[" + i2 + "]");
                    } else {
                        zArr3[i2] = cAVLCReader.readBool("low_delay_hrd_flag[" + i2 + "]");
                    }
                    if (!zArr3[i2]) {
                        iArr[i2] = cAVLCReader.readUE("cpb_cnt_minus1[" + i2 + "]");
                    }
                    if (z2) {
                        sub_layer_hrd_parameters(i2, iArr[i2], z4, cAVLCReader);
                    }
                    if (z3) {
                        sub_layer_hrd_parameters(i2, iArr[i2], z4, cAVLCReader);
                    }
                }
            }
        } else {
            z2 = false;
            z3 = false;
        }
        z4 = false;
        boolean[] zArr4 = new boolean[i];
        boolean[] zArr22 = new boolean[i];
        boolean[] zArr32 = new boolean[i];
        int[] iArr3 = new int[i];
        int[] iArr22 = new int[i];
        while (i2 <= i) {
        }
    }

    void sub_layer_hrd_parameters(int i, int i2, boolean z, CAVLCReader cAVLCReader) throws IOException {
        int[] iArr = new int[i2];
        int[] iArr2 = new int[i2];
        int[] iArr3 = new int[i2];
        int[] iArr4 = new int[i2];
        boolean[] zArr = new boolean[i2];
        for (int i3 = 0; i3 <= i2; i3++) {
            iArr[i3] = cAVLCReader.readUE("bit_rate_value_minus1[" + i3 + "]");
            iArr2[i3] = cAVLCReader.readUE("cpb_size_value_minus1[" + i3 + "]");
            if (z) {
                iArr3[i3] = cAVLCReader.readUE("cpb_size_du_value_minus1[" + i3 + "]");
                iArr4[i3] = cAVLCReader.readUE("bit_rate_du_value_minus1[" + i3 + "]");
            }
            zArr[i3] = cAVLCReader.readBool("cbr_flag[" + i3 + "]");
        }
    }

    public ByteBuffer toByteBuffer() {
        return this.vps;
    }
}
