package cn.bertsir.zbar.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.p002v4.util.Pair;
import android.support.p002v4.view.ViewCompat;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.bertsir.zbar.p036Qr.Image;
import cn.bertsir.zbar.p036Qr.ImageScanner;
import cn.bertsir.zbar.p036Qr.Symbol;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.GlobalHistogramBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import java.util.Hashtable;
import java.util.Iterator;

/* loaded from: classes2.dex */
public class QRUtils {
    private static QRUtils instance;
    private Context mContext;
    private Bitmap scanBitmap;

    public static QRUtils getInstance() {
        if (instance == null) {
            instance = new QRUtils();
        }
        return instance;
    }

    public String decodeQRcode(String str) throws Exception {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        Bitmap decodeFile = BitmapFactory.decodeFile(str, options);
        return decodeFile != null ? decodeQRcode(decodeFile) : "";
    }

    public Pair<String, Integer> decodeQRcodeDetail(String str) throws Exception {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        Bitmap decodeFile = BitmapFactory.decodeFile(str, options);
        if (decodeFile != null) {
            return decodeQRcodeDetail(decodeFile);
        }
        return null;
    }

    public String decodeQRcode(ImageView imageView) throws Exception {
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        return bitmap != null ? decodeQRcode(bitmap) : "";
    }

    public String decodeQRcode(Bitmap bitmap) throws Exception {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] iArr = new int[width * height];
        bitmap.getPixels(iArr, 0, width, 0, 0, width, height);
        Image image = new Image(width, height, "RGB4");
        image.setData(iArr);
        ImageScanner imageScanner = new ImageScanner();
        imageScanner.setConfig(0, 0, 0);
        imageScanner.setConfig(64, 0, 1);
        String str = null;
        if (imageScanner.scanImage(image.convert("Y800")) != 0) {
            Iterator<Symbol> it2 = imageScanner.getResults().iterator();
            while (it2.hasNext()) {
                str = it2.next().getData();
            }
        }
        return str;
    }

    public Pair<String, Integer> decodeQRcodeDetail(Bitmap bitmap) throws Exception {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] iArr = new int[width * height];
        bitmap.getPixels(iArr, 0, width, 0, 0, width, height);
        Image image = new Image(width, height, "RGB4");
        image.setData(iArr);
        ImageScanner imageScanner = new ImageScanner();
        imageScanner.setConfig(0, 0, 0);
        imageScanner.setConfig(64, 0, 1);
        String str = null;
        int i = -1;
        if (imageScanner.scanImage(image.convert("Y800")) != 0) {
            Iterator<Symbol> it2 = imageScanner.getResults().iterator();
            while (it2.hasNext()) {
                Symbol next = it2.next();
                str = next.getData();
                i = next.getType();
            }
        }
        return new Pair<>(str, Integer.valueOf(i));
    }

    public String decodeQRcodeByZxing(String str) {
        Result result = null;
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        Hashtable hashtable = new Hashtable();
        hashtable.put(DecodeHintType.CHARACTER_SET, "UTF-8");
        BitmapFactory.Options options = new BitmapFactory.Options();
        int i = 1;
        options.inJustDecodeBounds = true;
        this.scanBitmap = BitmapFactory.decodeFile(str, options);
        options.inJustDecodeBounds = false;
        int i2 = (int) (options.outHeight / 200.0f);
        if (i2 > 0) {
            i = i2;
        }
        options.inSampleSize = i;
        this.scanBitmap = BitmapFactory.decodeFile(str, options);
        int[] iArr = new int[this.scanBitmap.getWidth() * this.scanBitmap.getHeight()];
        Bitmap bitmap = this.scanBitmap;
        bitmap.getPixels(iArr, 0, bitmap.getWidth(), 0, 0, this.scanBitmap.getWidth(), this.scanBitmap.getHeight());
        try {
            result = new QRCodeReader().decode(new BinaryBitmap(new GlobalHistogramBinarizer(new RGBLuminanceSource(this.scanBitmap.getWidth(), this.scanBitmap.getHeight(), iArr))), hashtable);
        } catch (ChecksumException | FormatException | NotFoundException unused) {
        }
        return result == null ? "" : result.getText();
    }

    public String decodeQRcodeByZxing(Bitmap bitmap) {
        Result result;
        Hashtable hashtable = new Hashtable();
        hashtable.put(DecodeHintType.CHARACTER_SET, "UTF-8");
        this.scanBitmap = bitmap;
        int[] iArr = new int[this.scanBitmap.getWidth() * this.scanBitmap.getHeight()];
        Bitmap bitmap2 = this.scanBitmap;
        bitmap2.getPixels(iArr, 0, bitmap2.getWidth(), 0, 0, this.scanBitmap.getWidth(), this.scanBitmap.getHeight());
        try {
            result = new QRCodeReader().decode(new BinaryBitmap(new GlobalHistogramBinarizer(new RGBLuminanceSource(this.scanBitmap.getWidth(), this.scanBitmap.getHeight(), iArr))), hashtable);
        } catch (ChecksumException | FormatException | NotFoundException unused) {
            result = null;
        }
        return result == null ? "" : result.getText();
    }

    public String decodeBarcode(String str) {
        Bitmap decodeFile = BitmapFactory.decodeFile(str);
        return decodeFile != null ? decodeBarcode(decodeFile) : "";
    }

    public Pair<String, Integer> decodeBarcodeDetail(String str) {
        Bitmap decodeFile = BitmapFactory.decodeFile(str);
        if (decodeFile != null) {
            return decodeBarcodeDetail(decodeFile);
        }
        return null;
    }

    public String decodeBarcode(ImageView imageView) {
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        return bitmap != null ? decodeBarcode(bitmap) : "";
    }

    public String decodeBarcode(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] iArr = new int[width * height];
        bitmap.getPixels(iArr, 0, width, 0, 0, width, height);
        Image image = new Image(width, height, "RGB4");
        image.setData(iArr);
        ImageScanner imageScanner = new ImageScanner();
        imageScanner.setConfig(0, 0, 0);
        imageScanner.setConfig(128, 0, 1);
        imageScanner.setConfig(39, 0, 1);
        imageScanner.setConfig(13, 0, 1);
        imageScanner.setConfig(8, 0, 1);
        imageScanner.setConfig(12, 0, 1);
        imageScanner.setConfig(9, 0, 1);
        String str = null;
        if (imageScanner.scanImage(image.convert("Y800")) != 0) {
            Iterator<Symbol> it2 = imageScanner.getResults().iterator();
            while (it2.hasNext()) {
                str = it2.next().getData();
            }
        }
        return str;
    }

    public Pair<String, Integer> decodeBarcodeDetail(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] iArr = new int[width * height];
        bitmap.getPixels(iArr, 0, width, 0, 0, width, height);
        Image image = new Image(width, height, "RGB4");
        image.setData(iArr);
        ImageScanner imageScanner = new ImageScanner();
        imageScanner.setConfig(0, 0, 0);
        imageScanner.setConfig(128, 0, 1);
        imageScanner.setConfig(39, 0, 1);
        imageScanner.setConfig(13, 0, 1);
        imageScanner.setConfig(8, 0, 1);
        imageScanner.setConfig(12, 0, 1);
        imageScanner.setConfig(9, 0, 1);
        String str = null;
        int i = -1;
        if (imageScanner.scanImage(image.convert("Y800")) != 0) {
            Iterator<Symbol> it2 = imageScanner.getResults().iterator();
            while (it2.hasNext()) {
                Symbol next = it2.next();
                str = next.getData();
                i = next.getType();
            }
        }
        return new Pair<>(str, Integer.valueOf(i));
    }

    public Bitmap createQRCode(String str) {
        return createQRCode(str, 300, 300);
    }

    public Bitmap createQRCode(String str, int i, int i2) {
        Bitmap bitmap;
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            Hashtable hashtable = new Hashtable();
            hashtable.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
            hashtable.put(EncodeHintType.MARGIN, 1);
            BitMatrix encode = multiFormatWriter.encode(new String(str.getBytes("UTF-8"), "ISO-8859-1"), BarcodeFormat.QR_CODE, i, i2, hashtable);
            int width = encode.getWidth();
            int height = encode.getHeight();
            int[] iArr = new int[width * height];
            for (int i3 = 0; i3 < height; i3++) {
                int i4 = i3 * width;
                for (int i5 = 0; i5 < width; i5++) {
                    iArr[i4 + i5] = encode.get(i5, i3) ? ViewCompat.MEASURED_STATE_MASK : -1;
                }
            }
            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            try {
                bitmap.setPixels(iArr, 0, width, 0, 0, width, height);
            } catch (Exception e) {
                e = e;
                e.printStackTrace();
                return bitmap;
            }
        } catch (Exception e2) {
            e = e2;
            bitmap = null;
        }
        return bitmap;
    }

    public Bitmap createQRCodeAddLogo(String str, Bitmap bitmap) {
        Bitmap createQRCode = createQRCode(str);
        createQRCode.getHeight();
        return createWaterMaskCenter(createQRCode, zoomImg(bitmap, ((int) (createQRCode.getWidth() * 0.3d)) / bitmap.getWidth()));
    }

    public Bitmap createQRCodeAddLogo(String str, int i, int i2, Bitmap bitmap) {
        Bitmap createQRCode = createQRCode(str, i, i2);
        createQRCode.getHeight();
        return createWaterMaskCenter(createQRCode, zoomImg(bitmap, ((int) (createQRCode.getWidth() * 0.3d)) / bitmap.getWidth()));
    }

    @Deprecated
    public Bitmap createBarcode(Context context, String str, int i, int i2) {
        if (!TextUtils.isEmpty(str)) {
            if (i == 0 || i2 == 0) {
                throw new NullPointerException("desiredWidth or desiredHeight not be null");
            }
            return encodeAsBitmap(str, BarcodeFormat.CODE_128, i, i2);
        }
        throw new NullPointerException("contents not be null");
    }

    public Bitmap createBarCodeWithText(Context context, String str, int i, int i2) {
        return createBarCodeWithText(context, str, i, i2, null);
    }

    public Bitmap createBarCodeWithText(Context context, String str, int i, int i2, TextViewConfig textViewConfig) {
        if (!TextUtils.isEmpty(str)) {
            if (i == 0 || i2 == 0) {
                throw new NullPointerException("desiredWidth or desiredHeight not be null");
            }
            Bitmap encodeAsBitmap = encodeAsBitmap(str, BarcodeFormat.CODE_128, i, i2);
            return mixtureBitmap(encodeAsBitmap, createCodeBitmap(str, encodeAsBitmap.getWidth(), encodeAsBitmap.getHeight(), context, textViewConfig), new PointF(0.0f, i2));
        }
        throw new NullPointerException("contents not be null");
    }

    private Bitmap encodeAsBitmap(String str, BarcodeFormat barcodeFormat, int i, int i2) {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(str, barcodeFormat, i, i2, null);
        } catch (WriterException e) {
            e.printStackTrace();
            bitMatrix = null;
        }
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        int[] iArr = new int[width * height];
        for (int i3 = 0; i3 < height; i3++) {
            int i4 = i3 * width;
            for (int i5 = 0; i5 < width; i5++) {
                iArr[i4 + i5] = bitMatrix.get(i5, i3) ? ViewCompat.MEASURED_STATE_MASK : -1;
            }
        }
        Bitmap createBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        createBitmap.setPixels(iArr, 0, width, 0, 0, width, height);
        return createBitmap;
    }

    private Bitmap createCodeBitmap(String str, int i, int i2, Context context, TextViewConfig textViewConfig) {
        if (textViewConfig == null) {
            textViewConfig = new TextViewConfig();
        }
        TextView textView = new TextView(context);
        textView.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
        textView.setText(str);
        textView.setTextSize(textViewConfig.size == 0.0f ? textView.getTextSize() : textViewConfig.size);
        textView.setHeight(i2);
        textView.setGravity(textViewConfig.gravity);
        textView.setMaxLines(textViewConfig.maxLines);
        textView.setWidth(i);
        textView.setDrawingCacheEnabled(true);
        textView.setTextColor(textViewConfig.color);
        textView.measure(View.MeasureSpec.makeMeasureSpec(0, 0), View.MeasureSpec.makeMeasureSpec(0, 0));
        textView.layout(0, 0, textView.getMeasuredWidth(), textView.getMeasuredHeight());
        textView.buildDrawingCache();
        return textView.getDrawingCache();
    }

    /* loaded from: classes2.dex */
    public static class TextViewConfig {
        private float size;
        private int gravity = 17;
        private int maxLines = 1;
        private int color = ViewCompat.MEASURED_STATE_MASK;

        public void setGravity(int i) {
            this.gravity = i;
        }

        public void setMaxLines(int i) {
            this.maxLines = i;
        }

        public void setColor(int i) {
            this.color = i;
        }

        public void setSize(float f) {
            this.size = f;
        }
    }

    private Bitmap mixtureBitmap(Bitmap bitmap, Bitmap bitmap2, PointF pointF) {
        if (bitmap == null || bitmap2 == null || pointF == null) {
            return null;
        }
        Bitmap createBitmap = Bitmap.createBitmap(Math.max(bitmap.getWidth(), bitmap2.getWidth()), bitmap.getHeight() + bitmap2.getHeight(), Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(createBitmap);
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, (Paint) null);
        canvas.drawBitmap(bitmap2, pointF.x, pointF.y, (Paint) null);
        canvas.save();
        canvas.restore();
        return createBitmap;
    }

    private Bitmap createWaterMaskCenter(Bitmap bitmap, Bitmap bitmap2) {
        return createWaterMaskBitmap(bitmap, bitmap2, (bitmap.getWidth() - bitmap2.getWidth()) / 2, (bitmap.getHeight() - bitmap2.getHeight()) / 2);
    }

    private Bitmap createWaterMaskBitmap(Bitmap bitmap, Bitmap bitmap2, int i, int i2) {
        if (bitmap == null) {
            return null;
        }
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, (Paint) null);
        canvas.drawBitmap(bitmap2, i, i2, (Paint) null);
        canvas.save();
        canvas.restore();
        return createBitmap;
    }

    private Bitmap zoomImg(Bitmap bitmap, float f) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.postScale(f, f);
        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
    }

    public boolean isMIUI() {
        return "xiaomi".equalsIgnoreCase(Build.MANUFACTURER);
    }

    public int getScreenWidth(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService("window");
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= 17) {
            windowManager.getDefaultDisplay().getRealSize(point);
        } else {
            windowManager.getDefaultDisplay().getSize(point);
        }
        return point.x;
    }

    public int getScreenHeight(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService("window");
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= 17) {
            windowManager.getDefaultDisplay().getRealSize(point);
        } else {
            windowManager.getDefaultDisplay().getSize(point);
        }
        return point.y;
    }

    public boolean isScreenOriatationPortrait(Context context) {
        return context.getResources().getConfiguration().orientation == 1;
    }

    public float getFingerSpacing(MotionEvent motionEvent) {
        float x = motionEvent.getX(0) - motionEvent.getX(1);
        float y = motionEvent.getY(0) - motionEvent.getY(1);
        return (float) Math.sqrt((x * x) + (y * y));
    }
}
