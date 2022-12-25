package com.googlecode.mp4parser.authoring.tracks.h265;

import com.googlecode.mp4parser.h264.read.CAVLCReader;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;

/* loaded from: classes3.dex */
public class SequenceParameterSetRbsp {
    public SequenceParameterSetRbsp(InputStream inputStream) throws IOException {
        CAVLCReader cAVLCReader = new CAVLCReader(inputStream);
        cAVLCReader.readNBit(4, "sps_video_parameter_set_id");
        int readNBit = (int) cAVLCReader.readNBit(3, "sps_max_sub_layers_minus1");
        cAVLCReader.readBool("sps_temporal_id_nesting_flag");
        profile_tier_level(readNBit, cAVLCReader);
        cAVLCReader.readUE("sps_seq_parameter_set_id");
        if (cAVLCReader.readUE("chroma_format_idc") == 3) {
            cAVLCReader.read1Bit();
            cAVLCReader.readUE("pic_width_in_luma_samples");
            cAVLCReader.readUE("pic_width_in_luma_samples");
            if (cAVLCReader.readBool("conformance_window_flag")) {
                cAVLCReader.readUE("conf_win_left_offset");
                cAVLCReader.readUE("conf_win_right_offset");
                cAVLCReader.readUE("conf_win_top_offset");
                cAVLCReader.readUE("conf_win_bottom_offset");
            }
        }
        cAVLCReader.readUE("bit_depth_luma_minus8");
        cAVLCReader.readUE("bit_depth_chroma_minus8");
        cAVLCReader.readUE("log2_max_pic_order_cnt_lsb_minus4");
        boolean readBool = cAVLCReader.readBool("sps_sub_layer_ordering_info_present_flag");
        int i = 0;
        int i2 = (readNBit - (readBool ? 0 : readNBit)) + 1;
        int[] iArr = new int[i2];
        int[] iArr2 = new int[i2];
        int[] iArr3 = new int[i2];
        for (i = !readBool ? readNBit : i; i <= readNBit; i++) {
            iArr[i] = cAVLCReader.readUE("sps_max_dec_pic_buffering_minus1[" + i + "]");
            iArr2[i] = cAVLCReader.readUE("sps_max_num_reorder_pics[" + i + "]");
            iArr3[i] = cAVLCReader.readUE("sps_max_latency_increase_plus1[" + i + "]");
        }
        cAVLCReader.readUE("log2_min_luma_coding_block_size_minus3");
        cAVLCReader.readUE("log2_diff_max_min_luma_coding_block_size");
        cAVLCReader.readUE("log2_min_transform_block_size_minus2");
        cAVLCReader.readUE("log2_diff_max_min_transform_block_size");
        cAVLCReader.readUE("max_transform_hierarchy_depth_inter");
        cAVLCReader.readUE("max_transform_hierarchy_depth_intra");
        if (cAVLCReader.readBool("scaling_list_enabled_flag") && cAVLCReader.readBool("sps_scaling_list_data_present_flag")) {
            scaling_list_data(cAVLCReader);
        }
        cAVLCReader.readBool("amp_enabled_flag");
        cAVLCReader.readBool("sample_adaptive_offset_enabled_flag");
        if (cAVLCReader.readBool("pcm_enabled_flag")) {
            cAVLCReader.readNBit(4, "pcm_sample_bit_depth_luma_minus1");
            cAVLCReader.readNBit(4, "pcm_sample_bit_depth_chroma_minus1");
            cAVLCReader.readUE("log2_min_pcm_luma_coding_block_size_minus3");
        }
    }

    private void scaling_list_data(CAVLCReader cAVLCReader) throws IOException {
        int i = 4;
        boolean[][] zArr = new boolean[4];
        int[][] iArr = new int[4];
        int[][] iArr2 = new int[2];
        int[][][] iArr3 = new int[4][];
        int i2 = 0;
        while (i2 < i) {
            int i3 = 0;
            while (true) {
                int i4 = 6;
                if (i3 < (i2 == 3 ? 2 : 6)) {
                    zArr[i2] = new boolean[i2 == 3 ? 2 : 6];
                    iArr[i2] = new int[i2 == 3 ? 2 : 6];
                    if (i2 == 3) {
                        i4 = 2;
                    }
                    iArr3[i2] = new int[i4];
                    zArr[i2][i3] = cAVLCReader.readBool();
                    if (!zArr[i2][i3]) {
                        iArr[i2][i3] = cAVLCReader.readUE("scaling_list_pred_matrix_id_delta[" + i2 + "][" + i3 + "]");
                    } else {
                        int min = Math.min(64, 1 << ((i2 << 1) + i));
                        int i5 = 8;
                        if (i2 > 1) {
                            int i6 = i2 - 2;
                            iArr2[i6][i3] = cAVLCReader.readSE("scaling_list_dc_coef_minus8[" + i2 + "- 2][" + i3 + "]");
                            i5 = 8 + iArr2[i6][i3];
                        }
                        iArr3[i2][i3] = new int[min];
                        for (int i7 = 0; i7 < min; i7++) {
                            i5 = ((i5 + cAVLCReader.readSE("scaling_list_delta_coef ")) + 256) % 256;
                            iArr3[i2][i3][i7] = i5;
                        }
                    }
                    i3++;
                    i = 4;
                }
            }
            i2++;
            i = 4;
        }
    }

    private void profile_tier_level(int i, CAVLCReader cAVLCReader) throws IOException {
        boolean[] zArr;
        int[] iArr;
        int i2 = i;
        cAVLCReader.readU(2, "general_profile_space");
        cAVLCReader.readBool("general_tier_flag");
        cAVLCReader.readU(5, "general_profile_idc");
        boolean[] zArr2 = new boolean[32];
        for (int i3 = 0; i3 < 32; i3++) {
            zArr2[i3] = cAVLCReader.readBool();
        }
        cAVLCReader.readBool("general_progressive_source_flag");
        cAVLCReader.readBool("general_interlaced_source_flag");
        cAVLCReader.readBool("general_non_packed_constraint_flag");
        cAVLCReader.readBool("general_frame_only_constraint_flag");
        cAVLCReader.readNBit(44, "general_reserved_zero_44bits");
        cAVLCReader.readByte();
        boolean[] zArr3 = new boolean[i2];
        boolean[] zArr4 = new boolean[i2];
        for (int i4 = 0; i4 < i2; i4++) {
            zArr3[i4] = cAVLCReader.readBool("sub_layer_profile_present_flag[" + i4 + "]");
            zArr4[i4] = cAVLCReader.readBool("sub_layer_level_present_flag[" + i4 + "]");
        }
        if (i2 > 0) {
            int[] iArr2 = new int[8];
            for (int i5 = i2; i5 < 8; i5++) {
                iArr2[i5] = cAVLCReader.readU(2, "reserved_zero_2bits[" + i5 + "]");
            }
        }
        int[] iArr3 = new int[i2];
        boolean[] zArr5 = new boolean[i2];
        int[] iArr4 = new int[i2];
        boolean[][] zArr6 = (boolean[][]) Array.newInstance(boolean.class, i2, 32);
        boolean[] zArr7 = new boolean[i2];
        boolean[] zArr8 = new boolean[i2];
        boolean[] zArr9 = new boolean[i2];
        boolean[] zArr10 = new boolean[i2];
        long[] jArr = new long[i2];
        int[] iArr5 = new int[i2];
        int i6 = 0;
        while (i6 < i2) {
            if (zArr3[i6]) {
                StringBuilder sb = new StringBuilder();
                zArr = zArr3;
                sb.append("sub_layer_profile_space[");
                sb.append(i6);
                sb.append("]");
                iArr3[i6] = cAVLCReader.readU(2, sb.toString());
                zArr5[i6] = cAVLCReader.readBool("sub_layer_tier_flag[" + i6 + "]");
                iArr4[i6] = cAVLCReader.readU(5, "sub_layer_profile_idc[" + i6 + "]");
                int i7 = 0;
                while (i7 < 32) {
                    boolean[] zArr11 = zArr6[i6];
                    zArr11[i7] = cAVLCReader.readBool("sub_layer_profile_compatibility_flag[" + i6 + "][" + i7 + "]");
                    i7++;
                    iArr3 = iArr3;
                }
                iArr = iArr3;
                zArr7[i6] = cAVLCReader.readBool("sub_layer_progressive_source_flag[" + i6 + "]");
                zArr8[i6] = cAVLCReader.readBool("sub_layer_interlaced_source_flag[" + i6 + "]");
                zArr9[i6] = cAVLCReader.readBool("sub_layer_non_packed_constraint_flag[" + i6 + "]");
                zArr10[i6] = cAVLCReader.readBool("sub_layer_frame_only_constraint_flag[" + i6 + "]");
                jArr[i6] = cAVLCReader.readNBit(44);
            } else {
                zArr = zArr3;
                iArr = iArr3;
            }
            if (zArr4[i6]) {
                iArr5[i6] = cAVLCReader.readU(8, "sub_layer_level_idc[" + i6 + "]");
            }
            i6++;
            i2 = i;
            zArr3 = zArr;
            iArr3 = iArr;
        }
    }
}
