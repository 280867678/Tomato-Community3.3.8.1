package com.google.android.exoplayer2.text.cea;

import android.graphics.Color;
import android.support.p006v8.renderscript.ScriptIntrinsicBLAS;
import android.text.Layout;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.text.Subtitle;
import com.google.android.exoplayer2.text.SubtitleInputBuffer;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.ParsableBitArray;
import com.google.android.exoplayer2.util.ParsableByteArray;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes3.dex */
public final class Cea708Decoder extends CeaDecoder {
    private final CueBuilder[] cueBuilders;
    private List<Cue> cues;
    private CueBuilder currentCueBuilder;
    private DtvCcPacket currentDtvCcPacket;
    private int currentWindow;
    private List<Cue> lastCues;
    private final int selectedServiceNumber;
    private final ParsableByteArray ccData = new ParsableByteArray();
    private final ParsableBitArray serviceBlockPacket = new ParsableBitArray();

    public Cea708Decoder(int i) {
        this.selectedServiceNumber = i == -1 ? 1 : i;
        this.cueBuilders = new CueBuilder[8];
        for (int i2 = 0; i2 < 8; i2++) {
            this.cueBuilders[i2] = new CueBuilder();
        }
        this.currentCueBuilder = this.cueBuilders[0];
        resetCueBuilders();
    }

    @Override // com.google.android.exoplayer2.text.cea.CeaDecoder, com.google.android.exoplayer2.decoder.Decoder
    public void flush() {
        super.flush();
        this.cues = null;
        this.lastCues = null;
        this.currentWindow = 0;
        this.currentCueBuilder = this.cueBuilders[this.currentWindow];
        resetCueBuilders();
        this.currentDtvCcPacket = null;
    }

    @Override // com.google.android.exoplayer2.text.cea.CeaDecoder
    protected boolean isNewSubtitleDataAvailable() {
        return this.cues != this.lastCues;
    }

    @Override // com.google.android.exoplayer2.text.cea.CeaDecoder
    protected Subtitle createSubtitle() {
        List<Cue> list = this.cues;
        this.lastCues = list;
        return new CeaSubtitle(list);
    }

    @Override // com.google.android.exoplayer2.text.cea.CeaDecoder
    protected void decode(SubtitleInputBuffer subtitleInputBuffer) {
        this.ccData.reset(subtitleInputBuffer.data.array(), subtitleInputBuffer.data.limit());
        while (this.ccData.bytesLeft() >= 3) {
            int readUnsignedByte = this.ccData.readUnsignedByte() & 7;
            int i = readUnsignedByte & 3;
            boolean z = false;
            boolean z2 = (readUnsignedByte & 4) == 4;
            byte readUnsignedByte2 = (byte) this.ccData.readUnsignedByte();
            byte readUnsignedByte3 = (byte) this.ccData.readUnsignedByte();
            if (i == 2 || i == 3) {
                if (z2) {
                    if (i == 3) {
                        finalizeCurrentPacket();
                        int i2 = (readUnsignedByte2 & 192) >> 6;
                        int i3 = readUnsignedByte2 & 63;
                        if (i3 == 0) {
                            i3 = 64;
                        }
                        this.currentDtvCcPacket = new DtvCcPacket(i2, i3);
                        DtvCcPacket dtvCcPacket = this.currentDtvCcPacket;
                        byte[] bArr = dtvCcPacket.packetData;
                        int i4 = dtvCcPacket.currentIndex;
                        dtvCcPacket.currentIndex = i4 + 1;
                        bArr[i4] = readUnsignedByte3;
                    } else {
                        if (i == 2) {
                            z = true;
                        }
                        Assertions.checkArgument(z);
                        DtvCcPacket dtvCcPacket2 = this.currentDtvCcPacket;
                        if (dtvCcPacket2 == null) {
                            Log.e("Cea708Decoder", "Encountered DTVCC_PACKET_DATA before DTVCC_PACKET_START");
                        } else {
                            byte[] bArr2 = dtvCcPacket2.packetData;
                            int i5 = dtvCcPacket2.currentIndex;
                            dtvCcPacket2.currentIndex = i5 + 1;
                            bArr2[i5] = readUnsignedByte2;
                            int i6 = dtvCcPacket2.currentIndex;
                            dtvCcPacket2.currentIndex = i6 + 1;
                            bArr2[i6] = readUnsignedByte3;
                        }
                    }
                    DtvCcPacket dtvCcPacket3 = this.currentDtvCcPacket;
                    if (dtvCcPacket3.currentIndex == (dtvCcPacket3.packetSize * 2) - 1) {
                        finalizeCurrentPacket();
                    }
                }
            }
        }
    }

    private void finalizeCurrentPacket() {
        if (this.currentDtvCcPacket == null) {
            return;
        }
        processCurrentPacket();
        this.currentDtvCcPacket = null;
    }

    private void processCurrentPacket() {
        DtvCcPacket dtvCcPacket = this.currentDtvCcPacket;
        int i = dtvCcPacket.currentIndex;
        if (i != (dtvCcPacket.packetSize * 2) - 1) {
            Log.w("Cea708Decoder", "DtvCcPacket ended prematurely; size is " + ((this.currentDtvCcPacket.packetSize * 2) - 1) + ", but current index is " + this.currentDtvCcPacket.currentIndex + " (sequence number " + this.currentDtvCcPacket.sequenceNumber + "); ignoring packet");
            return;
        }
        this.serviceBlockPacket.reset(dtvCcPacket.packetData, i);
        int readBits = this.serviceBlockPacket.readBits(3);
        int readBits2 = this.serviceBlockPacket.readBits(5);
        if (readBits == 7) {
            this.serviceBlockPacket.skipBits(2);
            readBits += this.serviceBlockPacket.readBits(6);
        }
        if (readBits2 == 0) {
            if (readBits == 0) {
                return;
            }
            Log.w("Cea708Decoder", "serviceNumber is non-zero (" + readBits + ") when blockSize is 0");
        } else if (readBits != this.selectedServiceNumber) {
        } else {
            boolean z = false;
            while (this.serviceBlockPacket.bitsLeft() > 0) {
                int readBits3 = this.serviceBlockPacket.readBits(8);
                if (readBits3 == 16) {
                    int readBits4 = this.serviceBlockPacket.readBits(8);
                    if (readBits4 <= 31) {
                        handleC2Command(readBits4);
                    } else {
                        if (readBits4 <= 127) {
                            handleG2Character(readBits4);
                        } else if (readBits4 <= 159) {
                            handleC3Command(readBits4);
                        } else if (readBits4 <= 255) {
                            handleG3Character(readBits4);
                        } else {
                            Log.w("Cea708Decoder", "Invalid extended command: " + readBits4);
                        }
                        z = true;
                    }
                } else if (readBits3 <= 31) {
                    handleC0Command(readBits3);
                } else {
                    if (readBits3 <= 127) {
                        handleG0Character(readBits3);
                    } else if (readBits3 <= 159) {
                        handleC1Command(readBits3);
                    } else if (readBits3 <= 255) {
                        handleG1Character(readBits3);
                    } else {
                        Log.w("Cea708Decoder", "Invalid base command: " + readBits3);
                    }
                    z = true;
                }
            }
            if (!z) {
                return;
            }
            this.cues = getDisplayCues();
        }
    }

    private void handleC0Command(int i) {
        if (i != 0) {
            if (i == 3) {
                this.cues = getDisplayCues();
            } else if (i == 8) {
                this.currentCueBuilder.backspace();
            } else {
                switch (i) {
                    case 12:
                        resetCueBuilders();
                        return;
                    case 13:
                        this.currentCueBuilder.append('\n');
                        return;
                    case 14:
                        return;
                    default:
                        if (i >= 17 && i <= 23) {
                            Log.w("Cea708Decoder", "Currently unsupported COMMAND_EXT1 Command: " + i);
                            this.serviceBlockPacket.skipBits(8);
                            return;
                        } else if (i >= 24 && i <= 31) {
                            Log.w("Cea708Decoder", "Currently unsupported COMMAND_P16 Command: " + i);
                            this.serviceBlockPacket.skipBits(16);
                            return;
                        } else {
                            Log.w("Cea708Decoder", "Invalid C0 command: " + i);
                            return;
                        }
                }
            }
        }
    }

    private void handleC1Command(int i) {
        CueBuilder cueBuilder;
        int i2 = 1;
        switch (i) {
            case 128:
            case 129:
            case 130:
            case ScriptIntrinsicBLAS.NON_UNIT /* 131 */:
            case ScriptIntrinsicBLAS.UNIT /* 132 */:
            case 133:
            case 134:
            case 135:
                int i3 = i - 128;
                if (this.currentWindow == i3) {
                    return;
                }
                this.currentWindow = i3;
                this.currentCueBuilder = this.cueBuilders[i3];
                return;
            case 136:
                while (i2 <= 8) {
                    if (this.serviceBlockPacket.readBit()) {
                        this.cueBuilders[8 - i2].clear();
                    }
                    i2++;
                }
                return;
            case 137:
                for (int i4 = 1; i4 <= 8; i4++) {
                    if (this.serviceBlockPacket.readBit()) {
                        this.cueBuilders[8 - i4].setVisibility(true);
                    }
                }
                return;
            case 138:
                while (i2 <= 8) {
                    if (this.serviceBlockPacket.readBit()) {
                        this.cueBuilders[8 - i2].setVisibility(false);
                    }
                    i2++;
                }
                return;
            case 139:
                for (int i5 = 1; i5 <= 8; i5++) {
                    if (this.serviceBlockPacket.readBit()) {
                        this.cueBuilders[8 - i5].setVisibility(!cueBuilder.isVisible());
                    }
                }
                return;
            case 140:
                while (i2 <= 8) {
                    if (this.serviceBlockPacket.readBit()) {
                        this.cueBuilders[8 - i2].reset();
                    }
                    i2++;
                }
                return;
            case ScriptIntrinsicBLAS.LEFT /* 141 */:
                this.serviceBlockPacket.skipBits(8);
                return;
            case ScriptIntrinsicBLAS.RIGHT /* 142 */:
                return;
            case 143:
                resetCueBuilders();
                return;
            case 144:
                if (!this.currentCueBuilder.isDefined()) {
                    this.serviceBlockPacket.skipBits(16);
                    return;
                } else {
                    handleSetPenAttributes();
                    return;
                }
            case 145:
                if (!this.currentCueBuilder.isDefined()) {
                    this.serviceBlockPacket.skipBits(24);
                    return;
                } else {
                    handleSetPenColor();
                    return;
                }
            case 146:
                if (!this.currentCueBuilder.isDefined()) {
                    this.serviceBlockPacket.skipBits(16);
                    return;
                } else {
                    handleSetPenLocation();
                    return;
                }
            case 147:
            case 148:
            case 149:
            case 150:
            default:
                Log.w("Cea708Decoder", "Invalid C1 command: " + i);
                return;
            case 151:
                if (!this.currentCueBuilder.isDefined()) {
                    this.serviceBlockPacket.skipBits(32);
                    return;
                } else {
                    handleSetWindowAttributes();
                    return;
                }
            case 152:
            case 153:
            case 154:
            case 155:
            case 156:
            case 157:
            case 158:
            case 159:
                int i6 = i - 152;
                handleDefineWindow(i6);
                if (this.currentWindow == i6) {
                    return;
                }
                this.currentWindow = i6;
                this.currentCueBuilder = this.cueBuilders[i6];
                return;
        }
    }

    private void handleC2Command(int i) {
        if (i <= 7) {
            return;
        }
        if (i <= 15) {
            this.serviceBlockPacket.skipBits(8);
        } else if (i <= 23) {
            this.serviceBlockPacket.skipBits(16);
        } else if (i > 31) {
        } else {
            this.serviceBlockPacket.skipBits(24);
        }
    }

    private void handleC3Command(int i) {
        if (i <= 135) {
            this.serviceBlockPacket.skipBits(32);
        } else if (i <= 143) {
            this.serviceBlockPacket.skipBits(40);
        } else if (i > 159) {
        } else {
            this.serviceBlockPacket.skipBits(2);
            this.serviceBlockPacket.skipBits(this.serviceBlockPacket.readBits(6) * 8);
        }
    }

    private void handleG0Character(int i) {
        if (i == 127) {
            this.currentCueBuilder.append((char) 9835);
        } else {
            this.currentCueBuilder.append((char) (i & 255));
        }
    }

    private void handleG1Character(int i) {
        this.currentCueBuilder.append((char) (i & 255));
    }

    private void handleG2Character(int i) {
        if (i == 32) {
            this.currentCueBuilder.append(' ');
        } else if (i == 33) {
            this.currentCueBuilder.append((char) 160);
        } else if (i == 37) {
            this.currentCueBuilder.append((char) 8230);
        } else if (i == 42) {
            this.currentCueBuilder.append((char) 352);
        } else if (i == 44) {
            this.currentCueBuilder.append((char) 338);
        } else if (i == 63) {
            this.currentCueBuilder.append((char) 376);
        } else if (i == 57) {
            this.currentCueBuilder.append((char) 8482);
        } else if (i == 58) {
            this.currentCueBuilder.append((char) 353);
        } else if (i == 60) {
            this.currentCueBuilder.append((char) 339);
        } else if (i != 61) {
            switch (i) {
                case 48:
                    this.currentCueBuilder.append((char) 9608);
                    return;
                case 49:
                    this.currentCueBuilder.append((char) 8216);
                    return;
                case 50:
                    this.currentCueBuilder.append((char) 8217);
                    return;
                case 51:
                    this.currentCueBuilder.append((char) 8220);
                    return;
                case 52:
                    this.currentCueBuilder.append((char) 8221);
                    return;
                case 53:
                    this.currentCueBuilder.append((char) 8226);
                    return;
                default:
                    switch (i) {
                        case 118:
                            this.currentCueBuilder.append((char) 8539);
                            return;
                        case 119:
                            this.currentCueBuilder.append((char) 8540);
                            return;
                        case 120:
                            this.currentCueBuilder.append((char) 8541);
                            return;
                        case ScriptIntrinsicBLAS.UPPER /* 121 */:
                            this.currentCueBuilder.append((char) 8542);
                            return;
                        case 122:
                            this.currentCueBuilder.append((char) 9474);
                            return;
                        case 123:
                            this.currentCueBuilder.append((char) 9488);
                            return;
                        case 124:
                            this.currentCueBuilder.append((char) 9492);
                            return;
                        case 125:
                            this.currentCueBuilder.append((char) 9472);
                            return;
                        case 126:
                            this.currentCueBuilder.append((char) 9496);
                            return;
                        case 127:
                            this.currentCueBuilder.append((char) 9484);
                            return;
                        default:
                            Log.w("Cea708Decoder", "Invalid G2 character: " + i);
                            return;
                    }
            }
        } else {
            this.currentCueBuilder.append((char) 8480);
        }
    }

    private void handleG3Character(int i) {
        if (i == 160) {
            this.currentCueBuilder.append((char) 13252);
            return;
        }
        Log.w("Cea708Decoder", "Invalid G3 character: " + i);
        this.currentCueBuilder.append('_');
    }

    private void handleSetPenAttributes() {
        this.currentCueBuilder.setPenAttributes(this.serviceBlockPacket.readBits(4), this.serviceBlockPacket.readBits(2), this.serviceBlockPacket.readBits(2), this.serviceBlockPacket.readBit(), this.serviceBlockPacket.readBit(), this.serviceBlockPacket.readBits(3), this.serviceBlockPacket.readBits(3));
    }

    private void handleSetPenColor() {
        int argbColorFromCeaColor = CueBuilder.getArgbColorFromCeaColor(this.serviceBlockPacket.readBits(2), this.serviceBlockPacket.readBits(2), this.serviceBlockPacket.readBits(2), this.serviceBlockPacket.readBits(2));
        int argbColorFromCeaColor2 = CueBuilder.getArgbColorFromCeaColor(this.serviceBlockPacket.readBits(2), this.serviceBlockPacket.readBits(2), this.serviceBlockPacket.readBits(2), this.serviceBlockPacket.readBits(2));
        this.serviceBlockPacket.skipBits(2);
        this.currentCueBuilder.setPenColor(argbColorFromCeaColor, argbColorFromCeaColor2, CueBuilder.getArgbColorFromCeaColor(this.serviceBlockPacket.readBits(2), this.serviceBlockPacket.readBits(2), this.serviceBlockPacket.readBits(2)));
    }

    private void handleSetPenLocation() {
        this.serviceBlockPacket.skipBits(4);
        int readBits = this.serviceBlockPacket.readBits(4);
        this.serviceBlockPacket.skipBits(2);
        this.currentCueBuilder.setPenLocation(readBits, this.serviceBlockPacket.readBits(6));
    }

    private void handleSetWindowAttributes() {
        int argbColorFromCeaColor = CueBuilder.getArgbColorFromCeaColor(this.serviceBlockPacket.readBits(2), this.serviceBlockPacket.readBits(2), this.serviceBlockPacket.readBits(2), this.serviceBlockPacket.readBits(2));
        int readBits = this.serviceBlockPacket.readBits(2);
        int argbColorFromCeaColor2 = CueBuilder.getArgbColorFromCeaColor(this.serviceBlockPacket.readBits(2), this.serviceBlockPacket.readBits(2), this.serviceBlockPacket.readBits(2));
        if (this.serviceBlockPacket.readBit()) {
            readBits |= 4;
        }
        boolean readBit = this.serviceBlockPacket.readBit();
        int readBits2 = this.serviceBlockPacket.readBits(2);
        int readBits3 = this.serviceBlockPacket.readBits(2);
        int readBits4 = this.serviceBlockPacket.readBits(2);
        this.serviceBlockPacket.skipBits(8);
        this.currentCueBuilder.setWindowAttributes(argbColorFromCeaColor, argbColorFromCeaColor2, readBit, readBits, readBits2, readBits3, readBits4);
    }

    private void handleDefineWindow(int i) {
        CueBuilder cueBuilder = this.cueBuilders[i];
        this.serviceBlockPacket.skipBits(2);
        boolean readBit = this.serviceBlockPacket.readBit();
        boolean readBit2 = this.serviceBlockPacket.readBit();
        boolean readBit3 = this.serviceBlockPacket.readBit();
        int readBits = this.serviceBlockPacket.readBits(3);
        boolean readBit4 = this.serviceBlockPacket.readBit();
        int readBits2 = this.serviceBlockPacket.readBits(7);
        int readBits3 = this.serviceBlockPacket.readBits(8);
        int readBits4 = this.serviceBlockPacket.readBits(4);
        int readBits5 = this.serviceBlockPacket.readBits(4);
        this.serviceBlockPacket.skipBits(2);
        int readBits6 = this.serviceBlockPacket.readBits(6);
        this.serviceBlockPacket.skipBits(2);
        cueBuilder.defineWindow(readBit, readBit2, readBit3, readBits, readBit4, readBits2, readBits3, readBits5, readBits6, readBits4, this.serviceBlockPacket.readBits(3), this.serviceBlockPacket.readBits(3));
    }

    private List<Cue> getDisplayCues() {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < 8; i++) {
            if (!this.cueBuilders[i].isEmpty() && this.cueBuilders[i].isVisible()) {
                arrayList.add(this.cueBuilders[i].build());
            }
        }
        Collections.sort(arrayList);
        return Collections.unmodifiableList(arrayList);
    }

    private void resetCueBuilders() {
        for (int i = 0; i < 8; i++) {
            this.cueBuilders[i].reset();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static final class DtvCcPacket {
        int currentIndex = 0;
        public final byte[] packetData;
        public final int packetSize;
        public final int sequenceNumber;

        public DtvCcPacket(int i, int i2) {
            this.sequenceNumber = i;
            this.packetSize = i2;
            this.packetData = new byte[(i2 * 2) - 1];
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static final class CueBuilder {
        private static final int[] PEN_STYLE_BACKGROUND;
        private static final int[] WINDOW_STYLE_FILL;
        private int anchorId;
        private int backgroundColor;
        private int backgroundColorStartPosition;
        private boolean defined;
        private int foregroundColor;
        private int foregroundColorStartPosition;
        private int horizontalAnchor;
        private int italicsStartPosition;
        private int justification;
        private int penStyleId;
        private int priority;
        private boolean relativePositioning;
        private int row;
        private int rowCount;
        private boolean rowLock;
        private int underlineStartPosition;
        private int verticalAnchor;
        private boolean visible;
        private int windowFillColor;
        private int windowStyleId;
        public static final int COLOR_SOLID_WHITE = getArgbColorFromCeaColor(2, 2, 2, 0);
        public static final int COLOR_SOLID_BLACK = getArgbColorFromCeaColor(0, 0, 0, 0);
        public static final int COLOR_TRANSPARENT = getArgbColorFromCeaColor(0, 0, 0, 3);
        private static final int[] WINDOW_STYLE_JUSTIFICATION = {0, 0, 0, 0, 0, 2, 0};
        private static final int[] WINDOW_STYLE_PRINT_DIRECTION = {0, 0, 0, 0, 0, 0, 2};
        private static final int[] WINDOW_STYLE_SCROLL_DIRECTION = {3, 3, 3, 3, 3, 3, 1};
        private static final boolean[] WINDOW_STYLE_WORD_WRAP = {false, false, false, true, true, true, false};
        private static final int[] PEN_STYLE_FONT_STYLE = {0, 1, 2, 3, 4, 3, 4};
        private static final int[] PEN_STYLE_EDGE_TYPE = {0, 0, 0, 0, 0, 3, 3};
        private final List<SpannableString> rolledUpCaptions = new ArrayList();
        private final SpannableStringBuilder captionStringBuilder = new SpannableStringBuilder();

        static {
            int i = COLOR_SOLID_BLACK;
            int i2 = COLOR_TRANSPARENT;
            WINDOW_STYLE_FILL = new int[]{i, i2, i, i, i2, i, i};
            PEN_STYLE_BACKGROUND = new int[]{i, i, i, i, i, i2, i2};
        }

        public CueBuilder() {
            reset();
        }

        public boolean isEmpty() {
            return !isDefined() || (this.rolledUpCaptions.isEmpty() && this.captionStringBuilder.length() == 0);
        }

        public void reset() {
            clear();
            this.defined = false;
            this.visible = false;
            this.priority = 4;
            this.relativePositioning = false;
            this.verticalAnchor = 0;
            this.horizontalAnchor = 0;
            this.anchorId = 0;
            this.rowCount = 15;
            this.rowLock = true;
            this.justification = 0;
            this.windowStyleId = 0;
            this.penStyleId = 0;
            int i = COLOR_SOLID_BLACK;
            this.windowFillColor = i;
            this.foregroundColor = COLOR_SOLID_WHITE;
            this.backgroundColor = i;
        }

        public void clear() {
            this.rolledUpCaptions.clear();
            this.captionStringBuilder.clear();
            this.italicsStartPosition = -1;
            this.underlineStartPosition = -1;
            this.foregroundColorStartPosition = -1;
            this.backgroundColorStartPosition = -1;
            this.row = 0;
        }

        public boolean isDefined() {
            return this.defined;
        }

        public void setVisibility(boolean z) {
            this.visible = z;
        }

        public boolean isVisible() {
            return this.visible;
        }

        public void defineWindow(boolean z, boolean z2, boolean z3, int i, boolean z4, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
            this.defined = true;
            this.visible = z;
            this.rowLock = z2;
            this.priority = i;
            this.relativePositioning = z4;
            this.verticalAnchor = i2;
            this.horizontalAnchor = i3;
            this.anchorId = i6;
            int i9 = i4 + 1;
            if (this.rowCount != i9) {
                this.rowCount = i9;
                while (true) {
                    if ((!z2 || this.rolledUpCaptions.size() < this.rowCount) && this.rolledUpCaptions.size() < 15) {
                        break;
                    }
                    this.rolledUpCaptions.remove(0);
                }
            }
            if (i7 != 0 && this.windowStyleId != i7) {
                this.windowStyleId = i7;
                int i10 = i7 - 1;
                setWindowAttributes(WINDOW_STYLE_FILL[i10], COLOR_TRANSPARENT, WINDOW_STYLE_WORD_WRAP[i10], 0, WINDOW_STYLE_PRINT_DIRECTION[i10], WINDOW_STYLE_SCROLL_DIRECTION[i10], WINDOW_STYLE_JUSTIFICATION[i10]);
            }
            if (i8 == 0 || this.penStyleId == i8) {
                return;
            }
            this.penStyleId = i8;
            int i11 = i8 - 1;
            setPenAttributes(0, 1, 1, false, false, PEN_STYLE_EDGE_TYPE[i11], PEN_STYLE_FONT_STYLE[i11]);
            setPenColor(COLOR_SOLID_WHITE, PEN_STYLE_BACKGROUND[i11], COLOR_SOLID_BLACK);
        }

        public void setWindowAttributes(int i, int i2, boolean z, int i3, int i4, int i5, int i6) {
            this.windowFillColor = i;
            this.justification = i6;
        }

        public void setPenAttributes(int i, int i2, int i3, boolean z, boolean z2, int i4, int i5) {
            if (this.italicsStartPosition != -1) {
                if (!z) {
                    this.captionStringBuilder.setSpan(new StyleSpan(2), this.italicsStartPosition, this.captionStringBuilder.length(), 33);
                    this.italicsStartPosition = -1;
                }
            } else if (z) {
                this.italicsStartPosition = this.captionStringBuilder.length();
            }
            if (this.underlineStartPosition == -1) {
                if (!z2) {
                    return;
                }
                this.underlineStartPosition = this.captionStringBuilder.length();
            } else if (z2) {
            } else {
                this.captionStringBuilder.setSpan(new UnderlineSpan(), this.underlineStartPosition, this.captionStringBuilder.length(), 33);
                this.underlineStartPosition = -1;
            }
        }

        public void setPenColor(int i, int i2, int i3) {
            int i4;
            int i5;
            if (this.foregroundColorStartPosition != -1 && (i5 = this.foregroundColor) != i) {
                this.captionStringBuilder.setSpan(new ForegroundColorSpan(i5), this.foregroundColorStartPosition, this.captionStringBuilder.length(), 33);
            }
            if (i != COLOR_SOLID_WHITE) {
                this.foregroundColorStartPosition = this.captionStringBuilder.length();
                this.foregroundColor = i;
            }
            if (this.backgroundColorStartPosition != -1 && (i4 = this.backgroundColor) != i2) {
                this.captionStringBuilder.setSpan(new BackgroundColorSpan(i4), this.backgroundColorStartPosition, this.captionStringBuilder.length(), 33);
            }
            if (i2 != COLOR_SOLID_BLACK) {
                this.backgroundColorStartPosition = this.captionStringBuilder.length();
                this.backgroundColor = i2;
            }
        }

        public void setPenLocation(int i, int i2) {
            if (this.row != i) {
                append('\n');
            }
            this.row = i;
        }

        public void backspace() {
            int length = this.captionStringBuilder.length();
            if (length > 0) {
                this.captionStringBuilder.delete(length - 1, length);
            }
        }

        public void append(char c) {
            if (c == '\n') {
                this.rolledUpCaptions.add(buildSpannableString());
                this.captionStringBuilder.clear();
                if (this.italicsStartPosition != -1) {
                    this.italicsStartPosition = 0;
                }
                if (this.underlineStartPosition != -1) {
                    this.underlineStartPosition = 0;
                }
                if (this.foregroundColorStartPosition != -1) {
                    this.foregroundColorStartPosition = 0;
                }
                if (this.backgroundColorStartPosition != -1) {
                    this.backgroundColorStartPosition = 0;
                }
                while (true) {
                    if ((!this.rowLock || this.rolledUpCaptions.size() < this.rowCount) && this.rolledUpCaptions.size() < 15) {
                        return;
                    }
                    this.rolledUpCaptions.remove(0);
                }
            } else {
                this.captionStringBuilder.append(c);
            }
        }

        public SpannableString buildSpannableString() {
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(this.captionStringBuilder);
            int length = spannableStringBuilder.length();
            if (length > 0) {
                if (this.italicsStartPosition != -1) {
                    spannableStringBuilder.setSpan(new StyleSpan(2), this.italicsStartPosition, length, 33);
                }
                if (this.underlineStartPosition != -1) {
                    spannableStringBuilder.setSpan(new UnderlineSpan(), this.underlineStartPosition, length, 33);
                }
                if (this.foregroundColorStartPosition != -1) {
                    spannableStringBuilder.setSpan(new ForegroundColorSpan(this.foregroundColor), this.foregroundColorStartPosition, length, 33);
                }
                if (this.backgroundColorStartPosition != -1) {
                    spannableStringBuilder.setSpan(new BackgroundColorSpan(this.backgroundColor), this.backgroundColorStartPosition, length, 33);
                }
            }
            return new SpannableString(spannableStringBuilder);
        }

        /* JADX WARN: Removed duplicated region for block: B:20:0x0066  */
        /* JADX WARN: Removed duplicated region for block: B:23:0x0092  */
        /* JADX WARN: Removed duplicated region for block: B:26:0x00a0  */
        /* JADX WARN: Removed duplicated region for block: B:29:0x00ae  */
        /* JADX WARN: Removed duplicated region for block: B:32:0x00a2  */
        /* JADX WARN: Removed duplicated region for block: B:36:0x0094  */
        /* JADX WARN: Removed duplicated region for block: B:40:0x0071  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public Cea708Cue build() {
            Layout.Alignment alignment;
            float f;
            float f2;
            int i;
            int i2;
            int i3;
            int i4;
            if (isEmpty()) {
                return null;
            }
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
            boolean z = false;
            for (int i5 = 0; i5 < this.rolledUpCaptions.size(); i5++) {
                spannableStringBuilder.append((CharSequence) this.rolledUpCaptions.get(i5));
                spannableStringBuilder.append('\n');
            }
            spannableStringBuilder.append((CharSequence) buildSpannableString());
            int i6 = this.justification;
            if (i6 != 0) {
                if (i6 == 1) {
                    alignment = Layout.Alignment.ALIGN_OPPOSITE;
                } else if (i6 == 2) {
                    alignment = Layout.Alignment.ALIGN_CENTER;
                } else if (i6 != 3) {
                    throw new IllegalArgumentException("Unexpected justification value: " + this.justification);
                }
                Layout.Alignment alignment2 = alignment;
                if (!this.relativePositioning) {
                    f = this.horizontalAnchor / 99.0f;
                    f2 = this.verticalAnchor / 99.0f;
                } else {
                    f = this.horizontalAnchor / 209.0f;
                    f2 = this.verticalAnchor / 74.0f;
                }
                float f3 = (f * 0.9f) + 0.05f;
                float f4 = (f2 * 0.9f) + 0.05f;
                i = this.anchorId;
                if (i % 3 != 0) {
                    i2 = 0;
                } else {
                    i2 = i % 3 == 1 ? 1 : 2;
                }
                i3 = this.anchorId;
                if (i3 / 3 != 0) {
                    i4 = 0;
                } else {
                    i4 = i3 / 3 == 1 ? 1 : 2;
                }
                if (this.windowFillColor != COLOR_SOLID_BLACK) {
                    z = true;
                }
                return new Cea708Cue(spannableStringBuilder, alignment2, f4, 0, i2, f3, i4, Float.MIN_VALUE, z, this.windowFillColor, this.priority);
            }
            alignment = Layout.Alignment.ALIGN_NORMAL;
            Layout.Alignment alignment22 = alignment;
            if (!this.relativePositioning) {
            }
            float f32 = (f * 0.9f) + 0.05f;
            float f42 = (f2 * 0.9f) + 0.05f;
            i = this.anchorId;
            if (i % 3 != 0) {
            }
            i3 = this.anchorId;
            if (i3 / 3 != 0) {
            }
            if (this.windowFillColor != COLOR_SOLID_BLACK) {
            }
            return new Cea708Cue(spannableStringBuilder, alignment22, f42, 0, i2, f32, i4, Float.MIN_VALUE, z, this.windowFillColor, this.priority);
        }

        public static int getArgbColorFromCeaColor(int i, int i2, int i3) {
            return getArgbColorFromCeaColor(i, i2, i3, 0);
        }

        /* JADX WARN: Removed duplicated region for block: B:10:0x0024  */
        /* JADX WARN: Removed duplicated region for block: B:12:0x002a  */
        /* JADX WARN: Removed duplicated region for block: B:14:0x0030  */
        /* JADX WARN: Removed duplicated region for block: B:18:0x002d  */
        /* JADX WARN: Removed duplicated region for block: B:19:0x0027  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public static int getArgbColorFromCeaColor(int i, int i2, int i3, int i4) {
            int i5;
            int i6 = 0;
            Assertions.checkIndex(i, 0, 4);
            Assertions.checkIndex(i2, 0, 4);
            Assertions.checkIndex(i3, 0, 4);
            Assertions.checkIndex(i4, 0, 4);
            if (i4 != 0 && i4 != 1) {
                if (i4 == 2) {
                    i5 = 127;
                } else if (i4 == 3) {
                    i5 = 0;
                }
                int i7 = i <= 1 ? 255 : 0;
                int i8 = i2 <= 1 ? 255 : 0;
                if (i3 > 1) {
                    i6 = 255;
                }
                return Color.argb(i5, i7, i8, i6);
            }
            i5 = 255;
            if (i <= 1) {
            }
            if (i2 <= 1) {
            }
            if (i3 > 1) {
            }
            return Color.argb(i5, i7, i8, i6);
        }
    }
}
