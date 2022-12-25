package com.zzhoujay.richtext.drawable;

import android.graphics.RectF;
import android.support.p002v4.view.MotionEventCompat;
import com.zzhoujay.richtext.ImageHolder;
import com.zzhoujay.richtext.ext.Debug;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* loaded from: classes4.dex */
public class DrawableSizeHolder {
    RectF border;
    DrawableBorderHolder borderHolder;
    ImageHolder.ScaleType scaleType;

    private static byte[] int2byte(int i) {
        return new byte[]{(byte) (i & 255), (byte) ((i >> 8) & 255), (byte) ((i >> 16) & 255), (byte) (i >>> 24)};
    }

    private DrawableSizeHolder(String str, RectF rectF, ImageHolder.ScaleType scaleType, DrawableBorderHolder drawableBorderHolder) {
        this.border = rectF;
        this.scaleType = scaleType;
        this.borderHolder = drawableBorderHolder;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public DrawableSizeHolder(ImageHolder imageHolder) {
        this(imageHolder.getKey(), new RectF(0.0f, 0.0f, imageHolder.getWidth(), imageHolder.getHeight()), imageHolder.getScaleType(), new DrawableBorderHolder(imageHolder.getBorderHolder()));
    }

    public void save(OutputStream outputStream) {
        try {
            writeFloat(outputStream, this.border.left);
            writeFloat(outputStream, this.border.top);
            writeFloat(outputStream, this.border.right);
            writeFloat(outputStream, this.border.bottom);
            writeInt(outputStream, this.scaleType.intValue());
            writeBoolean(outputStream, this.borderHolder.isShowBorder());
            writeInt(outputStream, this.borderHolder.getBorderColor());
            writeFloat(outputStream, this.borderHolder.getBorderSize());
            writeFloat(outputStream, this.borderHolder.getRadius());
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            Debug.m122e(e);
        }
    }

    public static DrawableSizeHolder read(InputStream inputStream, String str) {
        try {
            float readFloat = readFloat(inputStream);
            float readFloat2 = readFloat(inputStream);
            float readFloat3 = readFloat(inputStream);
            float readFloat4 = readFloat(inputStream);
            int readInt = readInt(inputStream);
            boolean readBoolean = readBoolean(inputStream);
            int readInt2 = readInt(inputStream);
            float readFloat5 = readFloat(inputStream);
            float readFloat6 = readFloat(inputStream);
            inputStream.close();
            return new DrawableSizeHolder(str, new RectF(readFloat, readFloat2, readFloat3, readFloat4), ImageHolder.ScaleType.valueOf(readInt), new DrawableBorderHolder(readBoolean, readFloat5, readInt2, readFloat6));
        } catch (IOException e) {
            Debug.m122e(e);
            return null;
        }
    }

    private static void writeBoolean(OutputStream outputStream, boolean z) throws IOException {
        outputStream.write(z ? 1 : 0);
    }

    private static boolean readBoolean(InputStream inputStream) throws IOException {
        return inputStream.read() != 0;
    }

    private static int readInt(InputStream inputStream) throws IOException {
        byte[] bArr = new byte[4];
        inputStream.read(bArr);
        return byte2int(bArr);
    }

    private static void writeInt(OutputStream outputStream, int i) throws IOException {
        outputStream.write(int2byte(i));
    }

    private static void writeFloat(OutputStream outputStream, float f) throws IOException {
        outputStream.write(int2byte(Float.floatToIntBits(f)));
    }

    private static float readFloat(InputStream inputStream) throws IOException {
        return Float.intBitsToFloat(readInt(inputStream));
    }

    private static int byte2int(byte[] bArr) {
        return (bArr[3] << 24) | (bArr[0] & 255) | ((bArr[1] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | ((bArr[2] << 24) >>> 8);
    }
}
