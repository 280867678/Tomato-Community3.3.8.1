package cn.bertsir.zbar;

import android.content.Context;
import android.hardware.Camera;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import cn.bertsir.zbar.format_control.BarCodeUtil;
import cn.bertsir.zbar.format_control.BarcodeFormat;
import cn.bertsir.zbar.format_control.BarcodeType;
import cn.bertsir.zbar.p036Qr.Image;
import cn.bertsir.zbar.p036Qr.ImageScanner;
import cn.bertsir.zbar.p036Qr.ScanResult;
import cn.bertsir.zbar.p036Qr.Symbol;
import cn.bertsir.zbar.utils.QRUtils;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.PlanarYUVLuminanceSource;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.datamatrix.detector.Detector;
import com.google.zxing.qrcode.QRCodeReader;
import com.tomatolive.library.http.utils.EncryptUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* loaded from: classes2.dex */
class CameraScanAnalysis implements Camera.PreviewCallback {
    private static final String TAG = "CameraScanAnalysis";
    private Image barcode;
    private Camera camera;
    private Context context;
    private int cropHeight;
    private int cropWidth;
    private byte[] data;
    private ScanCallback mCallback;
    private Handler mHandler;
    private Camera.Size size;
    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private boolean allowAnalysis = true;
    private long lastResultTime = 0;
    private MultiFormatReader multiFormatReader = new MultiFormatReader();
    private Runnable mAnalysisTask = new Runnable() { // from class: cn.bertsir.zbar.CameraScanAnalysis.2
        @Override // java.lang.Runnable
        public void run() {
            if (Symbol.is_auto_zoom && Symbol.scanType == BarcodeType.QRCODE && QRUtils.getInstance().isScreenOriatationPortrait(CameraScanAnalysis.this.context)) {
                if (Symbol.cropX == 0 || Symbol.cropY == 0 || CameraScanAnalysis.this.cropWidth == 0 || CameraScanAnalysis.this.cropHeight == 0) {
                    return;
                }
                try {
                    ResultPoint[] points = new Detector(new BinaryBitmap(new HybridBinarizer(new PlanarYUVLuminanceSource(CameraScanAnalysis.this.data, CameraScanAnalysis.this.size.width, CameraScanAnalysis.this.size.height, Symbol.cropX, Symbol.cropY, CameraScanAnalysis.this.cropWidth, CameraScanAnalysis.this.cropHeight, true))).getBlackMatrix()).detect().getPoints();
                    float x = points[0].getX();
                    float y = points[0].getY();
                    float x2 = points[1].getX();
                    float f = x - x2;
                    float y2 = y - points[1].getY();
                    int sqrt = (int) Math.sqrt((Math.abs(f) * Math.abs(f)) + (Math.abs(y2) * Math.abs(y2)));
                    if (sqrt < CameraScanAnalysis.this.cropWidth / 4 && sqrt > 10) {
                        CameraScanAnalysis.this.cameraZoom(CameraScanAnalysis.this.camera);
                    }
                } catch (NotFoundException e) {
                    e.printStackTrace();
                }
            }
            String str = null;
            int i = -1;
            if (CameraScanAnalysis.this.mImageScanner.scanImage(CameraScanAnalysis.this.barcode) != 0) {
                Iterator<Symbol> it2 = CameraScanAnalysis.this.mImageScanner.getResults().iterator();
                while (it2.hasNext()) {
                    Symbol next = it2.next();
                    String data = next.getData();
                    i = next.getType();
                    str = data;
                }
            }
            if (!TextUtils.isEmpty(str)) {
                ScanResult scanResult = new ScanResult();
                scanResult.setResult(str);
                scanResult.setScanType(BarCodeUtil.getScanTypeNameFromSymbolType(i));
                Message obtainMessage = CameraScanAnalysis.this.mHandler.obtainMessage();
                obtainMessage.obj = scanResult;
                obtainMessage.sendToTarget();
                CameraScanAnalysis.this.lastResultTime = System.currentTimeMillis();
                if (!Symbol.looperScan) {
                    return;
                }
                CameraScanAnalysis.this.allowAnalysis = true;
            } else if (!Symbol.doubleEngine) {
                CameraScanAnalysis.this.allowAnalysis = true;
            } else {
                CameraScanAnalysis cameraScanAnalysis = CameraScanAnalysis.this;
                cameraScanAnalysis.decode(cameraScanAnalysis.data, CameraScanAnalysis.this.size.width, CameraScanAnalysis.this.size.height);
            }
        }
    };
    private ImageScanner mImageScanner = new ImageScanner();

    /* JADX INFO: Access modifiers changed from: package-private */
    public CameraScanAnalysis(Context context) {
        this.context = context;
        if (Symbol.scanType == BarcodeType.ALL) {
            this.mImageScanner.setConfig(0, 0, 0);
            this.mImageScanner.setConfig(64, 0, 1);
            this.mImageScanner.setConfig(128, 0, 1);
            this.mImageScanner.setConfig(39, 0, 1);
            this.mImageScanner.setConfig(13, 0, 1);
            this.mImageScanner.setConfig(8, 0, 1);
            this.mImageScanner.setConfig(12, 0, 1);
            this.mImageScanner.setConfig(9, 0, 1);
            this.mImageScanner.setConfig(9, 0, 1);
        } else {
            this.mImageScanner.setConfig(0, 0, 0);
            for (BarcodeFormat barcodeFormat : getFormats()) {
                this.mImageScanner.setConfig(barcodeFormat.getId(), 0, 1);
            }
        }
        this.mHandler = new Handler(Looper.getMainLooper()) { // from class: cn.bertsir.zbar.CameraScanAnalysis.1
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                if (CameraScanAnalysis.this.mCallback != null) {
                    CameraScanAnalysis.this.mCallback.onScanResult((ScanResult) message.obj);
                }
            }
        };
    }

    private Collection<BarcodeFormat> getFormats() {
        if (Symbol.scanType == BarcodeType.BARCODE) {
            return BarcodeFormat.ONE_DIMENSION_FORMAT_LIST;
        }
        if (Symbol.scanType == BarcodeType.QRCODE) {
            return BarcodeFormat.TWO_DIMENSION_FORMAT_LIST;
        }
        if (Symbol.scanType == BarcodeType.ONLY_QR_CODE) {
            return Collections.singletonList(BarcodeFormat.QRCODE);
        }
        if (Symbol.scanType == BarcodeType.ONLY_CODE_128) {
            return Collections.singletonList(BarcodeFormat.CODE128);
        }
        if (Symbol.scanType == BarcodeType.ONLY_EAN_13) {
            return Collections.singletonList(BarcodeFormat.EAN13);
        }
        if (Symbol.scanType == BarcodeType.HIGH_FREQUENCY) {
            return BarcodeFormat.HIGH_FREQUENCY_FORMAT_LIST;
        }
        if (Symbol.scanType == BarcodeType.CUSTOM) {
            return Symbol.scanFormat;
        }
        return BarcodeFormat.ALL_FORMAT_LIST;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setScanCallback(ScanCallback scanCallback) {
        this.mCallback = scanCallback;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void onStop() {
        this.allowAnalysis = false;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void onStart() {
        this.allowAnalysis = true;
    }

    @Override // android.hardware.Camera.PreviewCallback
    public void onPreviewFrame(byte[] bArr, Camera camera) {
        if (this.allowAnalysis) {
            this.allowAnalysis = false;
            this.data = bArr;
            this.camera = camera;
            this.size = camera.getParameters().getPreviewSize();
            Camera.Size size = this.size;
            this.barcode = new Image(size.width, size.height, "Y800");
            this.barcode.setData(bArr);
            if (Symbol.is_only_scan_center) {
                Camera.Size size2 = this.size;
                int i = size2.height;
                this.cropWidth = (int) (Symbol.cropWidth * (i / Symbol.screenWidth));
                int i2 = size2.width;
                this.cropHeight = (int) (Symbol.cropHeight * (i2 / Symbol.screenHeight));
                int i3 = this.cropHeight;
                Symbol.cropX = (i2 / 2) - (i3 / 2);
                int i4 = this.cropWidth;
                Symbol.cropY = (i / 2) - (i4 / 2);
                this.barcode.setCrop(Symbol.cropX, Symbol.cropY, i3, i4);
            }
            if (Symbol.looperScan && System.currentTimeMillis() - this.lastResultTime < Symbol.looperWaitTime) {
                this.allowAnalysis = true;
            } else {
                this.executorService.execute(this.mAnalysisTask);
            }
        }
    }

    public void cameraZoom(Camera camera) {
        if (camera != null) {
            Camera.Parameters parameters = camera.getParameters();
            if (!parameters.isZoomSupported() || parameters.getMaxZoom() == 0 || parameters.getZoom() + 10 > parameters.getMaxZoom()) {
                return;
            }
            parameters.setZoom(parameters.getZoom() + 10);
            camera.setParameters(parameters);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void decode(byte[] bArr, int i, int i2) {
        byte[] bArr2 = new byte[bArr.length];
        for (int i3 = 0; i3 < i2; i3++) {
            for (int i4 = 0; i4 < i; i4++) {
                bArr2[(((i4 * i2) + i2) - i3) - 1] = bArr[(i3 * i) + i4];
            }
        }
        PlanarYUVLuminanceSource planarYUVLuminanceSource = new PlanarYUVLuminanceSource(bArr2, i2, i, 0, 0, i2, i, true);
        Hashtable hashtable = new Hashtable();
        hashtable.put(DecodeHintType.CHARACTER_SET, EncryptUtil.CHARSET);
        ArrayList arrayList = new ArrayList();
        arrayList.add(new QRCodeReader());
        hashtable.put(DecodeHintType.POSSIBLE_FORMATS, arrayList);
        this.multiFormatReader.setHints(hashtable);
        try {
            try {
                Result decodeWithState = this.multiFormatReader.decodeWithState(new BinaryBitmap(new HybridBinarizer(planarYUVLuminanceSource)));
                String result = decodeWithState.toString();
                com.google.zxing.BarcodeFormat barcodeFormat = decodeWithState.getBarcodeFormat();
                if (!TextUtils.isEmpty(result)) {
                    ScanResult scanResult = new ScanResult();
                    scanResult.setResult(result);
                    scanResult.setScanType(BarCodeUtil.getScanTypeNameFromZXingBarCodeFormat(barcodeFormat));
                    Message obtainMessage = this.mHandler.obtainMessage();
                    obtainMessage.obj = scanResult;
                    obtainMessage.sendToTarget();
                    this.lastResultTime = System.currentTimeMillis();
                    if (Symbol.looperScan) {
                        this.allowAnalysis = true;
                    }
                } else {
                    this.allowAnalysis = true;
                }
            } catch (ReaderException unused) {
                this.allowAnalysis = true;
            }
        } finally {
            this.multiFormatReader.reset();
        }
    }
}
