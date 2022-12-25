package com.googlecode.mp4parser.authoring.tracks.h265;

import com.googlecode.mp4parser.boxes.mp4.objectdescriptors.BitReaderBuffer;
import java.io.IOException;
import java.io.PrintStream;

/* loaded from: classes3.dex */
public class SEIMessage {
    public SEIMessage(BitReaderBuffer bitReaderBuffer) throws IOException {
        int i = 0;
        while (bitReaderBuffer.readBits(8) == 255) {
            i += 255;
        }
        int readBits = i + bitReaderBuffer.readBits(8);
        do {
        } while (bitReaderBuffer.readBits(8) == 255);
        bitReaderBuffer.readBits(8);
        PrintStream printStream = System.err;
        printStream.println("payloadType " + readBits);
    }
}
